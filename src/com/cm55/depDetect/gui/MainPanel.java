package com.cm55.depDetect.gui;

import java.io.*;

import com.cm55.depDetect.gui.RefsPanel.*;
import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.fx.*;
import com.cm55.fx.util.*;
import com.google.inject.*;

import javafx.application.*;
import javafx.application.Application.*;
import javafx.geometry.*;

public class MainPanel {


  @Inject private Msg msg;
  @Inject private FileMenuBar fileMenuBar;
  private FxSplitPane splitPane;
  @Inject private GuiEvent guiEvent;
  @Inject private JavaTreePanel javaTreePanel;
  @Inject private Model model;
  @Inject private JavaTreeMenu javaTreeMenu;
  @Inject private DepsToPanel depsToPanel;
  @Inject private DepsFromPanel depsFromPanel;
  @Inject private CyclicsPanel cyclicsPanel;
  
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

    FxTitledBorder javaTreePane = new FxTitledBorder("パッケージツリー", new FxBorderPane.Ver(
      new FxBorderPane.Hor(
        new FxButton("tree", e-> {
          javaTreeMenu.show(e, Side.BOTTOM);
        }),
        new FxCheckBox("Descend").bind(guiEvent.descend),
        null
      ),
      javaTreePanel,
      null
    ));
    
    FxTitledBorder refsPane = new FxTitledBorder("依存", new FxSplitPane.Ver(
      depsToPanel,
      depsFromPanel,
      cyclicsPanel
    ));
    
    borderPane = new FxBorderPane.Ver(
      fileMenuBar.menuBar,
      splitPane = new FxSplitPane.Hor(
        javaTreePane,
        refsPane
      ).setResizeFixed(0),
      null
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
