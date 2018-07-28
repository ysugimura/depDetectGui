package com.cm55.depDetect.gui.cyclic;

import java.util.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.common.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;

/**
 * すべての循環依存パッケージ一覧を表示する
 * @author ysugimura
 */
public class AllCyclicsPanel implements FxNode {

  private Model model;
  private FxTitledBorder titledBorder;
  private PackagesPanel packagesPanel;
  private CyclicModel cyclicModel;
  
  @SuppressWarnings("restriction")
  @Inject
  public AllCyclicsPanel(Model model, CyclicModel cyclicModel) {
    this.model = model;
    this.cyclicModel = cyclicModel;
    
    model.bus.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
    
    packagesPanel = new PackagesPanel();
    packagesPanel.getTable().getSelectionModel().listenSelection(e-> {
      int index = e.value;
      if (index < 0) return;
      cyclicModel.setFromPkgNode(packagesPanel.getRows().get(index));
    });
    titledBorder = new FxTitledBorder("循環依存パッケージ一覧", new FxJustBox(packagesPanel.getTable()));
  } 
  
  /**
   * プロジェクト変更時。循環依存パッケージを列挙する。
   * @param e
   */
  private void projectChanged(ModelEvent.ProjectChanged e) {    
    
    if (e.root == null) {
      packagesPanel.clearRows();
      return;    
    }
    
    // ルートを含めてすべてのパッケージを列強するが、ただし、
    // {@link PrunedPkgs}で隠されているノードは無視し、枝刈りされているノードはそれ以下すべてを取得する
    List<PkgNode>list = new ArrayList<>();
    e.root.visitPackages(VisitOrder.PRE, pkg-> {
      if (e.prunedPkgs.isHidden(pkg)) return;      
      if (pkg.getCyclics(e.prunedPkgs.contains(pkg)).size() > 0) list.add(pkg);
    });
    
    packagesPanel.setRows(list);
  }
  
  public Node node() {
    return titledBorder.node();
  }
}
