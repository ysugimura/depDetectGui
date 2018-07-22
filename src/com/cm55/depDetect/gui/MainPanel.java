package com.cm55.depDetect.gui;

import java.io.*;

import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.fx.*;
import com.cm55.fx.util.*;
import com.cm55.fx.winBounds.*;
import com.cm55.miniSerial.*;
import com.google.inject.*;

import javafx.application.*;
import javafx.application.Application.*;

public class MainPanel {


  @Inject private Msg msg;
  @Inject private FileMenuBar fileMenuBar;
  @Inject private GuiEvent guiEvent;
  @Inject private Model model;
  @Inject private AllCyclicsPanel allCyclicsPanel;
  @Inject private AllCyclicToPanel allCyclicToPanel;
  @Inject private FromClassesPanel fromClassesPanel;
  @Inject private ToClassesPanel toClassesPanel;
  private WindowBoundsPersister<MyWindowBounds> windowBoundsPersister;  
  
  FxBorderPane.Ver borderPane;
  
  /** このウインドウの状態セーブ */
  @Serialized(key=985833802388795844L)
  public static class MyWindowBounds extends WindowBounds {
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


    
    FxSplitPane.Ver leftPanel = new FxSplitPane.Ver(
      allCyclicsPanel,
      allCyclicToPanel
    );
    FxSplitPane.Ver rightPanel = new FxSplitPane.Ver(
      fromClassesPanel,
      toClassesPanel
    );
    
    borderPane = new FxBorderPane.Ver(
      fileMenuBar.menuBar,
      new FxSplitPane.Hor(
        leftPanel,
        rightPanel
      ),
      null
    );

    
    stage.setScene(new FxScene(borderPane));
//    resources.setStyleToStage(stage.getStage());    
//    stage.getStage().resizableProperty().setValue(Boolean.FALSE);
//    beforeOpen();
    Resources.setStyleToStage(stage);
    stage.setOnCloseRequest(e-> {
      windowBoundsPersister.finish();
    });
    windowBoundsPersister = new WindowBoundsPersister<>(
        stage, new WindowBoundsSerializer<MyWindowBounds>(MyWindowBounds.class)
    );
    stage.setTitle(System.getProperty("java.version"));
    stage.show();
    
    guiEvent.bus.listen(GuiEvent.FromPackageSelection.class,  e-> {
      if (e.fromPkgNode == null) stage.setTitle("");
      else stage.setTitle(e.fromPkgNode.getPath() + " --- " + e.fromPkgDescend);
    });
    
  }


}
