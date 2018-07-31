package com.cm55.depDetect.gui.common;

import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.fx.*;
import com.cm55.fx.winBounds.*;
import com.cm55.miniSerial.*;
import com.google.inject.*;

import javafx.scene.*;

public class ShowTextsDialog extends FxCloseDlg<String, Object> {

  private FxTitledBorder titledBorder;
  private FxTextArea textArea;
  private Msg msg;
  
  @Inject
  public ShowTextsDialog(Msg msg) {
    this.msg = msg;
    titledBorder = new FxTitledBorder(msg.get(Msg.テキスト), new FxJustBox(
      textArea = new FxTextArea().setEditable(false)
    ));
  }

  @Override
  protected void initialize() {
    super.initialize();
    com.cm55.depDetect.gui.resources.Resources.setStyleToDialog(this.dialog);    
    dialog.setResizable(true);
  }
  
  @Override
  protected String getTitle() {
    return msg.get(Msg.テキスト);
  }

  @Override
  protected Node getContent() {
    return titledBorder.node();
  }

  @Override
  protected void setInput(String input) {
    this.textArea.setText(input);

  }

  @Override
  protected Object getOutput() {
    return Boolean.TRUE;
  }

  public void show(FxNode node, String text) {
    super.showAndWait(node, text);
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
  
  @Serialized(key=986532124578981584L)
  public static class MyWindowBounds extends WindowBounds {
  }
}
