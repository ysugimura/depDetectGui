package com.cm55.depDetect.gui;

import java.util.*;

import com.cm55.depDetect.*;
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
  private GuiEvent guiEvent;
  
  @SuppressWarnings("restriction")
  @Inject
  public AllCyclicsPanel(Model model, GuiEvent guiEvent) {
    this.model = model;
    this.guiEvent = guiEvent;
    model.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
    packagesPanel = new PackagesPanel();
    packagesPanel.table.getSelectionModel().listenSelection(e-> {
      int index = e.value;
      if (index < 0) return;
      guiEvent.setFromPkgNode(packagesPanel.rows.get(index));
    });
    titledBorder = new FxTitledBorder("循環依存パッケージ一覧", new FxJustBox(packagesPanel.table));
  } 
  
  private void projectChanged(ModelEvent.ProjectChanged e) {    
    if (e.root == null) {
      packagesPanel.clearRows();
      return;    
    }
    List<PkgNode>list = new ArrayList<>();
    e.root.visitPackages(VisitOrder.PRE, n-> {
      if (n.getCyclics(false).size() > 0) list.add(n);
    });
    packagesPanel.setRows(list);
  }
  
  public Node node() {
    return titledBorder.node();
  }
}
