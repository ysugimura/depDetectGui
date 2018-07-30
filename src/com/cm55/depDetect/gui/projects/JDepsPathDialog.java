package com.cm55.depDetect.gui.projects;

import java.io.*;

import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.fx.*;
import com.google.inject.*;

import javafx.geometry.*;
import javafx.scene.*;

public class JDepsPathDialog extends FxOkCancelDlg<Object, Object> {

  @Inject private Model model;
  FxTitledBorder titledBorder;
  FxLabel pathLabel;
  FxButton setButton;
  FxButton deleteButton;
  JDepsPath jdepsPath;
  
  public JDepsPathDialog() {
    titledBorder = new FxTitledBorder("jdepsのパス", 
      new FxBox.Ver(
        pathLabel = new FxLabel(),
        new FxFlowPane(
          setButton = new FxButton("設定", e->setting()),
          deleteButton = new FxButton("削除", e->delete())  
        )
      ).setPadding(new Insets(10, 10, 10, 10))
    );        
  }

  @Override
  protected boolean initialize(FxNode node) {
    if (super.initialize(node)) {
      Resources.setStyleToDialog(this.dialog);
      return true;
    }
    return false;
  }
  
  @Override
  protected String getTitle() {
    return "jdepsパス設定";
  }

  @Override
  protected Node getContent() {
    return titledBorder.node();
  }

  @Override
  protected void setInput(Object input) {
    jdepsPath = model.getJDepsPath().duplicate();
    updateEnabled();
  }
  
  void updateEnabled() {
    boolean exists = jdepsPath.path != null;
    setButton.setEnabled(!exists);
    deleteButton.setEnabled(exists);
    pathLabel.setText(exists? jdepsPath.path:"");
  }

  void setting() {
    FxFileChooser fc = new FxFileChooser().setTitle("jdepthの場所");
    File newPath = fc.showOpenDialog(titledBorder);
    if (newPath == null) return;
    jdepsPath.path = newPath.getAbsolutePath();
    updateEnabled();
  }

  void delete() {
    jdepsPath.path = null;
    updateEnabled();
  }
  
  @Override
  protected Object getOutput() {
    model.setJDepsPath(jdepsPath);
    return Boolean.TRUE;
  }
  
  public void show(FxNode node, Object o) {
    super.showAndWait(node, o);
  }

}
