package com.cm55.depDetect.gui.projects;

import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.fx.*;
import com.cm55.fx.FxSingleSelectionModel.*;
import com.cm55.fx.winBounds.*;
import com.cm55.miniSerial.*;
import com.google.inject.*;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.layout.*;

/**
 * プロジェクト一覧パネル
 * <p>
 * プロジェクトの新規作成・編集・削除を行い。いずれかを選択する。
 * </p>
 * @author ysugimura
 */
public class ProjectsDialog extends FxOkCancelDlg<Object, Project> {

  @Inject private Model model;
  @Inject private Provider<ProjectEditDialog>editDlgProvider;
  
  private Msg msg;
  FxBorderPane.Hor borderPane;
  FxTable<RowWrapper>projectTable;
  FxSingleSelectionModel<RowWrapper>selectionModel;
  ProjectList projectList;
  FxButton addButton;
  FxButton editButton;
  FxButton deleteButton;

  public static class RowWrapper {
    Project project;

    SimpleStringProperty name = new SimpleStringProperty();
    SimpleIntegerProperty paths = new SimpleIntegerProperty();
    RowWrapper(Project project) {
      setProject(project);
    }
    
    void setProject(Project project) {
      this.project = project;

      name.set(project.name);
      paths.set(project.sourcePaths.size());
    }
    
    Project getProject() {
      return project;
    }    
  }
  
  @Inject
  public ProjectsDialog(Msg msg) {
    this.msg = msg;
    borderPane = new FxBorderPane.Hor(
      null,
      projectTable = new FxTable<>(),
      new FxBox.Ver(
        addButton = new FxButton(msg.get(Msg.新規), this::add),
        editButton = new FxButton(msg.get(Msg.編集), this::edit),
        deleteButton = new FxButton(msg.get(Msg.削除), this::delete)
      ).setSpacing(5)
    );    
    projectTable.setColumns(
      new FxTable.TextColumn<RowWrapper>(msg.get(Msg.プロジェクト名), t->t.name).setPrefWidth(200),
      new FxTable.Column<RowWrapper, Number>(msg.get(Msg.パス数), t->t.paths)
    );
    selectionModel = projectTable.getSelectionModel();
    selectionModel.listenSelection(this::rowSelection);
    buttonsEnabled();
  }

  protected boolean initialize(FxNode node) {
    boolean result = super.initialize(node);
    if (result) {
      Resources.setStyleToDialog(this.dialog);
      this.dialog.setResizable(true);
    }
    return result;
  }
  
  /** 新規作成 */
  private void add(FxButton b) {    
    Project result = editDlgProvider.get().showAndWait(projectTable, null);
    if (result == null) return;
    projectTable.getRows().add(new RowWrapper(result));
    saveProjectList();
  }
  
  /** 編集 */
  private void edit(FxButton b) {    
    RowWrapper p = projectTable.getSelection();
    if (p == null) return;
    Project result = editDlgProvider.get().showAndWait(projectTable, p.getProject());
    if (result == null) return;
    p.setProject(result);
    saveProjectList();
  }
  
  /** 削除 */
  private void delete(FxButton b) {
    RowWrapper p = projectTable.getSelection();
    if (p == null) return;
    if (!FxAlerts.confirmYes(projectTable, msg.get(Msg.このプロジェクトを削除します))) return;
    projectTable.getRows().remove(p);
    saveProjectList();
  }

  private void rowSelection(FxSingleSelection e) {
    //ystem.out.println("" + e);
    buttonsEnabled();
  }
  
  private void buttonsEnabled() {
    boolean selecting = selectionModel.getSelectedIndex() >= 0;
    editButton.setEnabled(selecting);
    deleteButton.setEnabled(selecting);
  }
  
  public BorderPane node() {
    return borderPane.node();
  }

  @Override
  protected String getTitle() {
    return msg.get(Msg.プロジェクト一覧);
  }

  @Override
  protected Node getContent() {
    return borderPane.node();
  }

  @Override
  protected void setInput(Object input) {    
    projectList = model.getProjectList();
    projectTable.getRows().clear();
    projectTable.getRows().addAll(projectList.stream().map(p->new RowWrapper(p)).collect(Collectors.toList()));
    buttonsEnabled();
  }

  private void saveProjectList() {
    List<Project>list = projectTable.getRows().stream().map(r->r.project).collect(Collectors.toList());
    ProjectList projectList = new ProjectList(list);   
    model.setProjectList(projectList);
  }
  
  @Override
  protected Project getOutput() {   
    RowWrapper row = projectTable.getSelection();
    if (row == null) return null;
    return row.project;
  }
 
  
  @Override
  public Project showAndWait(FxNode node, Object input) {
    return super.showAndWait(node, input);
  }
  
  private WindowBoundsPersister<MyWindowBounds> windowBoundsPersister;  
  
  @Override
  protected void onShowing() {
    windowBoundsPersister = new WindowBoundsPersister<>(dialog, new WindowBoundsSerializer<>(MyWindowBounds.class));
  }
  
  @Override 
  protected void onHiding() {
    windowBoundsPersister.finish();
    window.hide();
  }
    
  @Serialized(key=1598653366578215844L)
  public static class MyWindowBounds extends WindowBounds {
  }
  
}
