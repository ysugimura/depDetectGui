package com.cm55.depDetect.gui.cyclic;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.common.*;
import com.cm55.fx.*;
import com.google.inject.*;

/**
 * 指定された循環依存パッケージについての全循環依存先パッケージを表示する
 * いずれかが選択された場合には、toPkgNode選択イベントを発行する
 * @author ysugimura
 */
public class AllCyclicToPanel implements FxNode {

  private FxTitledBorder titledBorder;
  private PackagesPanel packagesPanel;
  private CyclicModel guiEvent;
  
  @Inject
  public AllCyclicToPanel(CyclicModel guiEvent) {
    this.guiEvent = guiEvent;
    guiEvent.bus.listen(CyclicModel.FromPackageSelection.class, this::fromPackageSelection);
    packagesPanel = new PackagesPanel().setSelectionCallback(this::toPackageSelection);
    titledBorder = new FxTitledBorder("循環依存先パッケージ一覧", new FxJustBox(packagesPanel));
  }

  void fromPackageSelection(CyclicModel.FromPackageSelection e) {
    if (e.fromPkgNode == null) {
       packagesPanel.clearRows();
       return;
    }
    
    // このパッケージの全循環依存先パッケージを取得する
    Refs cyclics = e.fromPkgNode.getCyclics(e.fromPkgDescend);
    packagesPanel.setRows(cyclics.stream());        
  }
  
  void toPackageSelection(PkgNode node) {
    guiEvent.setToPkgNode(node);    
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
