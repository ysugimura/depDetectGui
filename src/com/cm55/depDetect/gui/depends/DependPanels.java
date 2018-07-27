package com.cm55.depDetect.gui.depends;

import com.cm55.fx.*;
import com.cm55.fx.FxSplitPane.*;
import com.google.inject.*;

import javafx.scene.*;

/**
 * 依存先と被依存先の表示パネル
 * 左側は依存先パッケージで、いずれかのパッケージをクリックすると、依存しているクラス一覧を表示する。
 * 右側は被依存パッケージで、いずれかのパッケージをクリックすると、被依存クラス一覧を表示する。
 * 
 * @author ysugimura
 */
public class DependPanels implements FxNode {

  FxSplitPane.Hor splitPane;
  
  @Inject 
  public DependPanels(
      AllPackagesPanel allPackagesPanel) {
    splitPane = new FxSplitPane.Hor(allPackagesPanel, new FxLabel("test"));
  }

  public Node node() {
    return splitPane.node();
  }
}
