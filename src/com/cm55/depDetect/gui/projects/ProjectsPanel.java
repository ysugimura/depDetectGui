package com.cm55.depDetect.gui.projects;

import com.cm55.fx.*;

import javafx.scene.*;
import javafx.scene.layout.*;

/**
 * プロジェクト一覧パネル
 * @author ysugimura
 */
public class ProjectsPanel implements FxParent {

  FxBorderPane.Ver borderPane;
  FxTable<Project>projectTable;
  
  public ProjectsPanel() {
    borderPane = new FxBorderPane.Ver(
       null,
      projectTable = new FxTable<>(),
      null
    );
  }

  public BorderPane node() {
    return borderPane.node();
  }
  
  public static class Dialog extends FxOkCancelDlg<Object, Project> {
    protected String getTitle() {
      return "Projects";
    }
    protected Node getContent() {
      return new ProjectsPanel().node();
    }

    @Override
    protected void setInput(Object input) {
    }

    @Override
    protected Project getOutput() {
      return null;
    }    
  }
}
