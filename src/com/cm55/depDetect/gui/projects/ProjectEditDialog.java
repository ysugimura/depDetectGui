package com.cm55.depDetect.gui.projects;

import java.io.*;
import java.util.stream.*;

import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;

import javafx.scene.Node;

/**
 * プロジェクト編集パネル
 * @author ysugimura
 */
public class ProjectEditDialog extends FxOkCancelDlg<Project, Project> {

  FxBorderPane.Ver borderPane;
  FxTextField nameField;
  FxTable<String>pathTable;
  FxObservableList<String>pathRows;
  FxSingleSelectionModel<String>selectionModel;
  FxButton addButton;
  FxButton deleteButton;
  
  public ProjectEditDialog() {
    borderPane = new FxBorderPane.Ver(      
      new FxTitledBorder("プロジェクト名称", nameField = new FxTextField().setFocusable(true)),      
      new FxTitledBorder("ソースパス", new FxBorderPane.Hor(
        null,
        pathTable = new FxTable<>(),
        new FxBox.Ver(
          addButton = new FxButton("追加", this::add),
          deleteButton = new FxButton("削除", this::delete)
        )
      )),
      null
    );
    pathTable.setColumns(
      new FxTable.TextColumn<String>("ソースパス", t->FixedValue.w(t))
    );
    pathRows = pathTable.getRows();
    selectionModel = pathTable.getSelectionModel();  
    selectionModel.listenSelection(l->buttonsEnabled());
  }

  @Override
  protected boolean initialize(FxNode node) {
    boolean r = super.initialize(node);
    if (r) dialog.setResizable(true);
    return r;    
  }
  
  /** ソースパスの追加 */
  @SuppressWarnings("restriction")
  private void add(FxButton b) {
    String init = null;
    if (pathRows.size() > 0) init = pathRows.get(pathRows.size() - 1);
    FxDirectoryChooser dc = new FxDirectoryChooser().setTitle("ソースパスの指定");
    if (init != null) dc.setInitDir(init);
    File newPath = dc.showDialog(borderPane);    
    if (newPath == null) return;
    pathTable.getRows().add(newPath.toString());
  }

  /** ソースパスの削除 */
  private void delete(FxButton b) {    
    int index = selectionModel.getSelectedIndex();
    if (index < 0) return;
    if (!FxAlerts.confirmYes(pathTable,  "このパスを削除します")) return ;    
    pathTable.getRows().remove(index);
  }
  
  private void buttonsEnabled() {
    boolean selecting = pathTable.getSelection() != null;
    deleteButton.setEnabled(selecting);
  }
  @Override
  protected String getTitle() {
    return "プロジェクト編集";
  }

  @Override
  protected Node getContent() {
    return borderPane.node();
  }

  @Override
  protected void setInput(Project input) {
    if (input == null) {
      nameField.setText("");
      pathTable.getRows().clear();     
    } else {
      nameField.setText(input.name);
      pathTable.getRows().addAll(input.sourcePaths);
    }
    buttonsEnabled();
  }

  @Override
  protected Project getOutput() {   
    return new Project(nameField.getText(), 
      pathTable.getRows().stream().collect(Collectors.toList()));
  }
  
  public Project showAndWait(FxNode node, Project in) {
    return super.showAndWait(node, in);  
  }
}
