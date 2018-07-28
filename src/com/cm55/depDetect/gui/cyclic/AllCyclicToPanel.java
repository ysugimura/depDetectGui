package com.cm55.depDetect.gui.cyclic;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.common.*;
import com.cm55.fx.*;
import com.google.inject.*;

/**
 * 指定された循環依存パッケージ(From)についての全循環依存先パッケージ(Tos)を表示する
 * いずれかが選択された場合には、toPkgNode選択イベントを発行する
 * @author ysugimura
 */
public class AllCyclicToPanel implements FxNode {

  private FxTitledBorder titledBorder;
  private PackagesPanel packagesPanel;
  
  @Inject
  public AllCyclicToPanel(CyclicModel cyclicModel) {
    cyclicModel.bus.listen(CyclicModel.FromPkgEvent.class, this::fromPkgSelection);
    packagesPanel = new PackagesPanel().setSelectionCallback(node->cyclicModel.setToPkgNode(node));
    titledBorder = new FxTitledBorder("循環依存先パッケージ一覧", new FxJustBox(packagesPanel));
  }

  void fromPkgSelection(CyclicModel.FromPkgEvent e) {
    if (e.isEmpty()) {
       packagesPanel.clearRows();
       return;
    }
    
    // このパッケージの全循環依存先パッケージを取得する
    Refs cyclics = e.fromPkgNode.getCyclics(e.fromPkgPruned);
    packagesPanel.setRows(cyclics.stream());        
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
