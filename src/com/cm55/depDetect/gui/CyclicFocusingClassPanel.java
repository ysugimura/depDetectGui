package com.cm55.depDetect.gui;

import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

public class CyclicFocusingClassPanel implements FxNode {

  FxTitledBorder titledBorder;
  FxTable<PkgNode>table;
  FxObservableList<PkgNode>rows;
  
  @Inject 
  public CyclicFocusingClassPanel(GuiEvent guiEvent) {
    table = new FxTable<PkgNode>();
    rows = table.getRows();
    table.setColumns(
      new FxTable.TextColumn<PkgNode>("循環参照ノード", t->FixedValue.w(t.getPath())).setPrefWidth(300)
    );
    guiEvent.bus.listen(GuiEvent.CyclicFocusingClassSelection.class, this::cyclicFocusingClassSelection);
    titledBorder = new FxTitledBorder("循環参照クラス", new FxJustBox(table));
  }
  
  void cyclicFocusingClassSelection(GuiEvent.CyclicFocusingClassSelection e) {
    rows.clear();
    if (e.node == null) return;
    ClsNode clsNode = e.node;
    PkgNode pkgNode = clsNode.getParent();    
    rows.addAll(
      clsNode.getDepsTo().getIntersect(pkgNode.getCyclics(false)).stream().collect(Collectors.toList())
    );
  }

  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
