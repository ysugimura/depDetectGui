package com.cm55.depDetect.gui.projects;

import java.io.*;
import java.util.stream.*;

import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.cm55.fx.winBounds.*;
import com.cm55.miniSerial.*;
import com.google.inject.*;

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
  private Msg msg;  
  
  @Inject
  public ProjectEditDialog(Msg msg) {
    this.msg = msg;
    borderPane = new FxBorderPane.Ver(      
      new FxTitledBorder(msg.get(Msg.プロジェクト名), new FxBox.Ver( 
        nameField = new FxTextField().setFocusable(true)
      ).setSpacing(10)),      
      new FxTitledBorder(msg.get(Msg.classフォルダパス), new FxBorderPane.Hor(
        null,
        pathTable = new FxTable<>(),
        new FxBox.Ver(
          addButton = new FxButton(msg.get(Msg.追加), this::add),
          deleteButton = new FxButton(msg.get(Msg.削除), this::delete)
        )
      )),
      null
    );
    pathTable.setColumns(
      new FxTable.TextColumn<String>(msg.get(Msg.classフォルダパス), t->FixedValue.w(t)).setPrefWidth(400)
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
  private void add(FxButton b) {
    FxDirectoryChooser dc = new FxDirectoryChooser().setTitle(msg.get(Msg.classフォルダパス));
    OpenedPath.Serializer serializer = new OpenedPath.Serializer();
    OpenedPath openedPath = serializer.get();
    if (openedPath.getPath() != null) dc.setInitDir(openedPath.getPath());
    File newPath = dc.showDialog(borderPane);    
    if (newPath == null) return;
    serializer.put(new OpenedPath(newPath.getAbsolutePath()));
    pathTable.getRows().add(newPath.toString());
  }

  /** ソースパスの削除 */
  private void delete(FxButton b) {    
    int index = selectionModel.getSelectedIndex();
    if (index < 0) return;
    if (!FxAlerts.confirmYes(pathTable, msg.get(Msg.このパスを削除します))) return ;    
    pathTable.getRows().remove(index);
  }
  
  private void buttonsEnabled() {
    boolean selecting = pathTable.getSelection() != null;
    deleteButton.setEnabled(selecting);
  }
  @Override
  protected String getTitle() {
    return msg.get(Msg.プロジェクト編集);
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
    return new Project(
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
