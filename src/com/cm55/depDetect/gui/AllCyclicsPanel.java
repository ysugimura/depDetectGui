package com.cm55.depDetect.gui;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

import javafx.scene.*;

/**
 * 全循環依存パッケージ一覧パネル
 * @author ysugimura
 */
public class AllCyclicsPanel implements FxNode {

  private Model model;
  private FxTitledBorder titledBorder;
  private FxTable<PkgNode>table;
  private FxObservableList<PkgNode>rows;
  private GuiEvent guiEvent;
  
  @Inject
  public AllCyclicsPanel(Model model, GuiEvent guiEvent) {
    this.model = model;
    this.guiEvent = guiEvent;
    model.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
    table = new FxTable<PkgNode>();
    table.setColumns(new FxTable.TextColumn<PkgNode>("パッケージ", t->FixedValue.w(t.getPath())).setPrefWidth(300));
    rows = table.getRows();
    table.getSelectionModel().listenSelection(e-> {
      int index = e.value;
      if (index < 0) return;
      guiEvent.setPkgNode(rows.get(index));
    });
    titledBorder = new FxTitledBorder("循環依存パッケージ一覧", new FxJustBox(table));
  } 
  
  private void projectChanged(ModelEvent.ProjectChanged e) {
    rows.clear();
    if (e.root == null) return;    
    e.root.visitPackages(VisitOrder.PRE, n-> {
      if (n.getCyclics(false).size() > 0) rows.add(n);
    });
  }
  
  public Node node() {
    return titledBorder.node();
  }
}
