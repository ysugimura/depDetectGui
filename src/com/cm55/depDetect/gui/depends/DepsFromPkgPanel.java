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
    titledBorder = new FxTitledBorder("Depend-From packages", new FxJustBox(packagesPanel));
    dependModel.bus.listen(DependModel.FocusPkgEvent.class, this::focusPkgChanged);
  }

  void focusPkgChanged(DependModel.FocusPkgEvent e) {
    packagesPanel.setRows(e.focusPkg.getDepsFrom(e.descend).stream());
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
