package com.cm55.depDetect.gui.projects;

import com.cm55.fx.*;

import javafx.scene.*;

/**
 * プロジェクト編集パネル
 * @author ysugimura
 */
public class ProjectEditPanel {

  FxTable<String>pathTable;
  
  public ProjectEditPanel() {
    pathTable = new FxTable<>();    
  }

  public static class Dialog extends FxOkCancelDlg<Object, Object> {

    @Override
    protected String getTitle() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    protected Node getContent() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    protected void setInput(Object input) {
      // TODO Auto-generated method stub
      
    }

    @Override
    protected Object getOutput() {
      // TODO Auto-generated method stub
      return null;
    }
      
  }
}
