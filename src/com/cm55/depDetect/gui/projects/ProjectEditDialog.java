package com.cm55.depDetect.gui.projects;

import java.io.*;
import java.util.stream.*;

import com.cm55.fx.*;
import com.cm55.fx.FxBorderPane.*;
import com.cm55.fx.FxTable.*;

import javafx.scene.*;

/**
 * プロジェクト編集パネル
 * @author ysugimura
 */
public class ProjectEditDialog extends FxOkCancelDlg<Project, Project> {

  FxBorderPane.Ver borderPane;
  FxTextField nameField;
  FxTable<String>pathTable;
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
    selectionModel = pathTable.getSelectionModel();  
    selectionModel.listenSelection(l->buttonsEnabled());
  }

  private void add(FxButton b) {
    File newPath = new FxDirectoryChooser().setTitle("ソースパスの指定").showDialog(borderPane);    
    if (newPath == null) return;
    pathTable.getRows().add(newPath.toString());
  }
  
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
