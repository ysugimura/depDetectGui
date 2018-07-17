package com.cm55.depDetect.gui.resources;



import com.cm55.fx.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class Resources {

//  private static final String PATH = "/com/cm55/ve/res/";
  
  //private Image iconImage = new Image(Resources.class.getResourceAsStream(PATH + "icon.png"));
  static String style = Resources.class.getResource("stylesheet.txt").toExternalForm();
  
  public static void setStyleToStage(FxStage stage) {
    Stage _stage = stage.getStage();
    setStyle(_stage, _stage.getScene().getStylesheets());
  }
  
  public static void setStyleToDialog(Dialog<?> dialog) {
    setStyle((Stage)dialog.getDialogPane().getScene().getWindow(), dialog.getDialogPane().getStylesheets());
  }
  
  private static void setStyle(Stage stage, ObservableList<String> stylesheets) {
    stylesheets.clear();
    Application.setUserAgentStylesheet("MODENA");
    stylesheets.add(style);
//    stage.getIcons().add(iconImage);    
  }
}
