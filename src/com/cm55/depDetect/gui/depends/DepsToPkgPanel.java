package com.cm55.depDetect.gui.depends;

import com.cm55.depDetect.gui.common.*;
import com.cm55.fx.*;
import com.google.inject.*;

/**
 * 依存先パッケージ一覧
 * @author ysugimura
 */
public class DepsToPkgPanel implements FxNode {

  FxTitledBorder titledBorder;
  PackagesPanel packagesPanel;
  
  @Inject
  public DepsToPkgPanel(DependModel dependModel) {    
    packagesPanel = new PackagesPanel();
    packagesPanel.setSelectionCallback(pkgNode->dependModel.setDepToPkg(pkgNode));
    titledBorder = new FxTitledBorder("Depend-To packages", new FxJustBox(packagesPanel));
    dependModel.bus.listen(DependModel.FocusPkgEvent.class, this::focusPkgChanged);
  }

  void focusPkgChanged(DependModel.FocusPkgEvent e) {
    if (e.isEmpty()) {
      packagesPanel.clearRows();
      return;
    }
    packagesPanel.setRows(e.focusPkg.getDepsTo(e.descend).stream());
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
