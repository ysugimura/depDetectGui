package com.cm55.depDetect.gui.common;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.impl.*;
import com.cm55.fx.*;
import com.google.inject.*;

@Singleton
public class ShowClassDetail {

  @Inject private Model model;
  
  public void show(FxNode parent, ClsNode clsNode) {
    String text = "";
    
    if (model.getProject().getMode() == Mode.BIN) {
      text = BinTreeDetail.getClassDetail(model.getJDepsPath().path, model.getProject().sourcePaths(), clsNode);
      if (text == null) {
        FxAlerts.error(parent,  "見つかりません");
        return;
      }
    } else {
      text = SrcTreeDetail.getClassDetail(model.getProject().sourcePaths(), clsNode);
    }
    
    new ShowTextsDialog().show(parent, text);
  }
  
  
}