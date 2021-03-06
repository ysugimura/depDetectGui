package com.cm55.depDetect.gui.common;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.impl.*;
import com.cm55.fx.*;
import com.google.inject.*;

@Singleton
public class ShowClassDetail {

  @Inject private Model model;
  @Inject private Provider<ShowTextsDialog>dlgProvider;
  @Inject private Msg msg;
  
  public void show(FxNode parent, ClsNode clsNode) {
    String text = "";
    
    text = BinTreeDetail.getClassDetail(model.getJDepsPath().path, model.getProject().sourcePaths(), clsNode);
    if (text == null) {
      FxAlerts.error(parent, msg.get(Msg.見つかりません));
      return;
    }    
    dlgProvider.get().show(parent, text);
  }
  
  
}
