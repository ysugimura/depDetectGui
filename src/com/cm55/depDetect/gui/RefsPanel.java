package com.cm55.depDetect.gui;

import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

/**
 * 参照表示パネル
 * @author ysugimura
 */
public abstract class RefsPanel implements FxNode {

  @Inject protected Model model;
  protected GuiEvent guiEvent;
  protected FxTitledBorder titledBorder;
  protected FxTable<PkgNode>table;
  protected FxObservableList<PkgNode>rows;
  
  protected RefsPanel(GuiEvent guiEvent, String title) {
    this.guiEvent = guiEvent;
    titledBorder = new FxTitledBorder(title,
        new FxBorderPane.Hor(null,
      table = new FxTable<PkgNode>()
      ,
      null)
    );
    rows = table.getRows();
    table.setColumns(new FxTable.TextColumn<PkgNode>("パッケージ", t->FixedValue.w(t.getPath())).setPrefWidth(300));    
    guiEvent.bus.listen(GuiEvent.PackageSelection.class, this::packageSelection);
  }
  
  private void packageSelection(GuiEvent.PackageSelection e) {
    //ystem.out.println("packageSelection " + e);
    rows.clear();
    if (e.node == null) return;    
    Refs refs = getRefs(e.node, e.descend);
    rows.addAll(refs.stream().collect(Collectors.toList()));
  }

  protected abstract Refs getRefs(PkgNode node, boolean descend);
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
  
  /**
   * 依存パネル
   */
  public static class DepsToPanel extends RefsPanel {
    @Inject
    public DepsToPanel(GuiEvent guiEvent) {
      super(guiEvent, "依存一覧");
    }
    protected Refs getRefs(PkgNode node, boolean descend) {
      return node.getDepsTo(descend);
    }
  }
  
  /**
   * 被依存パネル
   */
  public static class DepsFromPanel extends RefsPanel {
    @Inject
    public DepsFromPanel(GuiEvent guiEvent) {
      super(guiEvent, "被依存一覧");
    }
    protected Refs getRefs(PkgNode node, boolean descend) {
      return node.getDepsFrom(descend);
    }
  }
  
  /**
   * 循環依存パネル
   * @author ysugimura
   */
  public static class CyclicsPanel extends RefsPanel {  
    @Inject
    public CyclicsPanel(GuiEvent guiEvent) {
      super(guiEvent, "循環依存一覧");
    }
    protected Refs getRefs(PkgNode node, boolean descend) {
      return node.getCyclics(descend);
    }
  }

}
