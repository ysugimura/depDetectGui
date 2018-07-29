package com.cm55.depDetect.gui.projects;

import java.io.*;
import java.util.stream.*;

import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.cm55.fx.winBounds.*;
import com.cm55.miniSerial.*;

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
  FxRadioButtons modeButtons;
  FxButton addButton;
  FxButton deleteButton;
  
  public ProjectEditDialog() {
    borderPane = new FxBorderPane.Ver(      
      new FxTitledBorder("プロジェクト名称/モード", new FxBox.Ver( 
        nameField = new FxTextField().setFocusable(true),
        modeButtons = new FxRadioButtons.Hor(Mode.descs)
      ).setSpacing(10)),      
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
      new FxTable.TextColumn<String>("ソースパス", t->FixedValue.w(t)).setPrefWidth(400)
    );
    pathRows = pathTable.getRows();
    selectionModel = pathTable.getSelectionModel();  
    selectionModel.listenSelection(l->buttonsEnabled());
  }

  @Override
  protected boolean initialize(FxNode node) {
    boolean r = super.initialize(node);
    if (r) {
      Resources.setStyleToDialog(this.dialog);
      dialog.setResizable(true);
    }
    return r;    
  }
  
  /** ソースパスの追加 */
  @SuppressWarnings("restriction")
  private void add(FxButton b) {
    File init = null;
    if (pathRows.size() > 0) {
      init = new File(pathRows.get(pathRows.size() - 1));
      init = init.getParentFile();
    }
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
      modeButtons.select(1);
    } else {
      nameField.setText(input.name);
      pathTable.getRows().addAll(input.sourcePaths);
      modeButtons.select(input.getMode().ordinal());
    }
    buttonsEnabled();
  }

  @Override
  protected Project getOutput() {   
    return new Project(
      Mode.values()[modeButtons.getSelectionIndex()],
      nameField.getText(), 
      pathTable.getRows().stream().collect(Collectors.toList())
    );
  }
  
  public Project showAndWait(FxNode node, Project in) {
    return super.showAndWait(node, in);  
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
  
  @Serialized(key=2222336655578215844L)
  public static class MyWindowBounds extends WindowBounds {
  }
}
