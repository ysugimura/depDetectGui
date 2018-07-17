package com.cm55.depDetect.gui;

import java.io.*;

import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.fx.*;
import com.cm55.fx.util.*;
import com.google.inject.*;

import javafx.application.*;
import javafx.application.Application.*;

public class MainPanel {


  @Inject private Msg msg;
  @Inject private FileMenuBar fileMenuBar;
  private FxSplitPane splitPane;
  @Inject private JavaTreePanel javaTreePanel;
  @Inject private Model model;
  FxBorderPane.Ver borderPane;
  
  public MainPanel() {
    // TODO Auto-generated constructor stub
  }
  
  public void execute(Parameters params, HostServices hostServices, FxStage stage)  {
    // データベースオープン
    try {
      H2Data.create(new File(System.getProperty("user.home"), AppName.DOT_SYSTEM_NAME));
    } catch (Exception ex) {
      ex.printStackTrace();
      FxAlerts.error(null, 
        msg.get(Msg.データベースエラー) + "\n" +
        "\n" +
        ex.getMessage()
        /*
        "Could not execute the app because that database is in use.\n" +
        "アプリケーションを起動できません。データベース使用中です。"
        */
      );
      Platform.exit();
      System.exit(0);
    }
    
    // Uncatched exceptions
    Thread.currentThread().setUncaughtExceptionHandler((thread, th) -> {
//      System.out.println("VERSION:" + Version.version + "\n");
      System.out.println(GetFullStackTrace.get(th));
    });
    
    FocusControlPolicy.setDefaultFocusable(false);
      
    borderPane = new FxBorderPane.Ver(
      fileMenuBar.menuBar,
      splitPane = new FxSplitPane.Hor(
        new FxTitledBorder("test", 
          javaTreePanel
        ),
        new FxLabel("test")
      ).setResizeFixed(0),
      new FxLabel("C")
    );

    model.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
    
    stage.setScene(new FxScene(borderPane));
//    resources.setStyleToStage(stage.getStage());    
//    stage.getStage().resizableProperty().setValue(Boolean.FALSE);
//    beforeOpen();
    Resources.setStyleToStage(stage);
    stage.show();
    
  }
  
  private void projectChanged(ModelEvent.ProjectChanged e) {
    //ystem.out.println("" + model.getRoot().treeString());
  }

}
