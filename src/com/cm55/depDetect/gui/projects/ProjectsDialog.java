package com.cm55.depDetect.gui.projects;

import java.util.*;
import java.util.stream.*;

import com.cm55.fx.*;
import com.cm55.fx.FxSingleSelectionModel.*;

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
  
  public ProjectsDialog() {
    borderPane = new FxBorderPane.Hor(
      null,
      projectTable = new FxTable<>(),
      new FxBox.Ver(
        addButton = new FxButton("新規", this::add),
        editButton = new FxButton("編集", this::edit),
        deleteButton = new FxButton("削除", this::delete)
      ).setSpacing(5)
    );    
    projectTable.setColumns(
      new FxTable.TextColumn<RowWrapper>("プロジェクト名", t->t.name),
      new FxTable.Column<RowWrapper, Number>("パス数", t->t.paths)
    );
    selectionModel = projectTable.getSelectionModel();
    selectionModel.listenSelection(this::rowSelection);
    buttonsEnabled();
  }

  protected boolean initialize(FxNode node) {
    boolean result = super.initialize(node);
    this.dialog.setResizable(true);
    return result;
  }
  
  /** 新規作成 */
  private void add(FxButton b) {    
    Project result = new ProjectEditDialog().showAndWait(projectTable, null);
    if (result == null) return;
    projectTable.getRows().add(new RowWrapper(result));
  }
  
  /** 編集 */
  private void edit(FxButton b) {    
    RowWrapper p = projectTable.getSelection();
    if (p == null) return;
    Project result = new ProjectEditDialog().showAndWait(projectTable, p.getProject());
    if (result == null) return;
    p.setProject(result);
  }
  
  /** 削除 */
  private void delete(FxButton b) {
    RowWrapper p = projectTable.getSelection();
    if (p == null) return;
    if (!FxAlerts.confirmYes(projectTable,  "このプロジェクトを削除します")) return;
    projectTable.getRows().remove(p);
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
    return "Test";
  }

  @Override
  protected Node getContent() {
    return borderPane.node();
  }

  @Override
  protected void setInput(Object input) {    
    projectList = new ProjectList.Serializer().get();
    projectTable.getRows().clear();
    projectTable.getRows().addAll(projectList.stream().map(p->new RowWrapper(p)).collect(Collectors.toList()));
    buttonsEnabled();
  }

  @Override
  protected Project getOutput() {   
    List<Project>list = projectTable.getRows().stream().map(r->r.project).collect(Collectors.toList());
    ProjectList projectList = new ProjectList(list);    
    new ProjectList.Serializer().put(projectList);    
    RowWrapper row = projectTable.getSelection();
    if (row == null) return null;
    return row.project;
  }
 
  @Override
  public Project showAndWait(FxNode node, Object input) {
    return super.showAndWait(node, input);
  }
  
}
