package com.cm55.depDetect.gui;

import javax.swing.*;

import com.cm55.fx.*;
import com.google.inject.*;

import javafx.application.*;
import javafx.stage.Stage;

/**
　* アプリのエントリ
 */
public class AppMain extends Application {

  @Override
  public void start(Stage stage) throws Exception {
        
    // フォントの色がおかしくなる問題：
    try {
      System.setProperty( "prism.lcdtext" , "false" );
    } catch (Exception ex) {   
      System.err.println(ex.getMessage());
    }
    
    // コマンドラインパラメータを取得
    Parameters params = getParameters();
    
    Injector injector = Guice.createInjector();
    
    // メニューパネルの実行
    injector.getInstance(MainPanel.class).execute(params, getHostServices(), new FxStage(stage));
  }
  
  public static void main(String[] args) {      
    
    //ystem.out.println("" + System.getProperty("java.version"));
//    if (args.length == 1 && args[0].equals("-version")) {
//      //ystem.out.println("version:" + Version.version);
//      //ystem.exit(0);
//    }
    
    // 実行環境チェック
    checkRuntimes();    
    
    // JavaFXアプリのラウンチ、コマンドラインパラメータを引き渡す
    Application.launch(AppMain.class, args);
  }

  /** 実行環境のチェック */
  private static void checkRuntimes() {
    
    // JavaFXのDialog付バージョンが必要
    try {
      Class.forName("javafx.scene.control.Dialog");
    } catch (Exception ex) {
      showError(
        "You should have Java runtime of version 1.8.0 update 40 or later\n" +
        "Javaのバージョンが低すぎます。最新の 1.8.0 update 40 以上のものをインストールしてください");
    }
    /*
    String javaFxVersion = System.getProperty("javafx.runtime.version");
    if (javaFxVersion.compareTo("8.0") < 0) {
      showError(
        "Your should have Java runtime with JavaFX, try latest Java runtime from Oracle\n" +
        "JavaFXのバージョンが低すぎます。Oracleから配布されている最新のJavaをインストールしてください。"
      );
    }
    */
    

  }
  
  private static void showError(String desc) {
    JOptionPane.showMessageDialog(null, 
        desc,
        "ERROR", 
        JOptionPane.ERROR_MESSAGE);
    System.exit(1);
  }
}
