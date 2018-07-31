package com.cm55.depDetect.gui;

import java.io.*;

import com.cm55.depDetect.gui.allPkgs.*;
import com.cm55.depDetect.gui.cyclic.*;
import com.cm55.depDetect.gui.depends.*;
import com.cm55.depDetect.gui.i18n.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.resources.*;
import com.cm55.depDetect.gui.settings.*;
import com.cm55.depDetect.gui.unknown.*;
import com.cm55.fx.*;
import com.cm55.fx.util.*;
import com.cm55.fx.winBounds.*;
import com.cm55.miniSerial.*;
import com.google.inject.*;

import javafx.application.*;
import javafx.application.Application.*;

public class MainPanel {

  /** メッセージ */
  @Inject private Msg msg;
  
  /** システムメニューバー */
  @Inject private SystemMenuBar systemMenuBar;

  /** データモデル */
  @Inject private Model model;

  /** 全パッケージ表示、枝刈りパネル */
  @Inject private AllPkgsPanel allPkgsPanel;
  
  /** 循環依存パネル */
  @Inject private CyclicPanels cyclicPanels;
  
  /** 依存先・元パネル */
  @Inject private DependPanels dependPanels;
  
  /** 不明importパネル */
  @Inject private UnknownPanels unknownPanels;  
  
  public void execute(Parameters params, HostServices hostServices, FxStage stage)  {
        
    // Uncatched exceptions
    Thread.currentThread().setUncaughtExceptionHandler((thread, th) -> {
      System.out.println(GetFullStackTrace.get(th));
    });
    
    // データベースオープン
    openDatabase();
        
    FocusControlPolicy.setDefaultFocusable(false);
  
    FxTabPane tabPane = new FxTabPane();
    tabPane.add(msg.get(Msg.循環依存), cyclicPanels);
    tabPane.add(msg.get(Msg.依存先元), dependPanels);
    tabPane.add(msg.get(Msg.外部参照),  unknownPanels);
    
    FxBorderPane.Ver borderPane = new FxBorderPane.Ver(
      systemMenuBar.menuBar,
      new FxSplitPane.Hor(
        allPkgsPanel,        
        tabPane
      ),
      null
    );
    
    stage.setScene(new FxScene(borderPane));
    Resources.setStyleToStage(stage);
    stage.setOnCloseRequest(e-> {
      windowBoundsPersister.finish();
    });
    windowBoundsPersister = new WindowBoundsPersister<>(
      stage, new WindowBoundsSerializer<MyWindowBounds>(MyWindowBounds.class)
    );
    stage.setTitle(AppName.APPLICATION_NAME + " Ver. " + Version.version);
    model.bus.listen(ModelEvent.PkgFocused.class, e-> {
      if (e.isEmpty()) {
        stage.setTitle(AppName.APPLICATION_NAME + " Ver. " + Version.version);
        return;
      }
      String title = e.focusPkg.getPath();
      if (e.focusPruned) title += "（" + msg.get(Msg.刈) + "）";
      stage.setTitle(title);
    });
    stage.show();    
  }

  /** H2データベースのオープン */
  private void openDatabase() {
    try {
      H2Data.create(new File(System.getProperty("user.home"), AppName.DOT_SYSTEM_NAME));
    } catch (Exception ex) {
      ex.printStackTrace();
      FxAlerts.error(null, 
        msg.get(Msg.データベースエラー) + "\n" +
        "\n" +
        ex.getMessage()
      );
      Platform.exit();
      System.exit(0);
    }    
  }
  
  /** ウインドウ状態ロード・セーブオブジェクト */
  private WindowBoundsPersister<MyWindowBounds> windowBoundsPersister;  
  
  /** このウインドウの状態セーブ */
  @Serialized(key=985833802388795844L)
  public static class MyWindowBounds extends WindowBounds {
  }
}
