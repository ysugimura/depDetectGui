package com.cm55.depDetect.gui.depends;

import com.cm55.fx.*;
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
      AllPackagesPanel allPackagesPanel,
      DepsToPkgPanel depsToPkgPanel,
      DepsToClsPanel depsToClsPanel,
      DepsFromPkgPanel depsFromPkgPanel,
      DepsFromClsPanel depsFromClsPanel) {
    
    splitPane = new FxSplitPane.Hor(
      allPackagesPanel, 
      new FxSplitPane.Ver(
          depsToPkgPanel, depsToClsPanel
      ),
      new FxSplitPane.Ver(
          depsFromPkgPanel, depsFromClsPanel
      )
    );
    
  }

  public Node node() {
    return splitPane.node();
  }
}
