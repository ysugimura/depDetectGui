package com.cm55.depDetect.gui.depends;


import com.cm55.depDetect.gui.common.*;
import com.cm55.fx.*;
import com.google.inject.*;

/**
 * 被依存先パッケージ一覧
 * @author ysugimura
 */
public class DepsFromPkgPanel implements FxNode {

  FxTitledBorder titledBorder;
  PackagesPanel packagesPanel;
  
  @Inject
  public DepsFromPkgPanel(DependModel dependModel) {    
    packagesPanel = new PackagesPanel();
    packagesPanel.setSelectionCallback(pkgNode->dependModel.setDepFromPkg(pkgNode));
    titledBorder = new FxTitledBorder("被依存先パッケージ", new FxJustBox(packagesPanel));
    dependModel.bus.listen(DependModel.FocusPkgEvent.class, this::focusPkgChanged);
  }

  /** 
   * 着目対象パッケージが変更された
   * このパッケージに依存するパッケージを列挙する。
   * ただし、着目対象パッケージは枝刈りを考慮するが、依存パッケージ側は枝刈りを考慮しない。
   * @param e
   */
  void focusPkgChanged(DependModel.FocusPkgEvent e) {
    // 着目対象パッケージが無い場合
    if (e.isEmpty()) {
      packagesPanel.clearRows();
      return;
    }
    // 着目対象パッケージについて枝刈りを考慮して依存パッケージを取得する
    // ただし依存パッケージ側は枝刈りを考慮しない。すべてのパッケージを列挙する。
    packagesPanel.setRows(e.focusPkg.getDepsFrom(e.descend).stream());
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
