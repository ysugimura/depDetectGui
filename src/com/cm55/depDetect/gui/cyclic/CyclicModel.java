package com.cm55.depDetect.gui.cyclic;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

@Singleton
public class CyclicModel {

  /** イベントバス */
  public EventBus bus = new EventBus();

  /** 枝刈りセット */
  private PrunedPkgs prunedPkgs;
  
  /** 循環参照元パッケージ */
  private PkgNode fromPkgNode = null;

  /** 循環参照元パッケージの枝刈りフラグ */
  private boolean fromPkgPruned;
  
  /** 循環参照先パッケージ */
  private PkgNode toPkgNode = null;

  /** 循環参照先パッケージの枝刈りフラグ */
  private boolean toPkgPruned;
  
  @Inject
  public CyclicModel(Model model) {
    model.bus.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
  }
  
  /**
   * プロジェクト変更時
   * @param e
   */
  private void projectChanged(ModelEvent.ProjectChanged e) {
    this.prunedPkgs = e.prunedPkgs;
    setFromPkgNode(null);
    setToPkgNode(null);
  }

  /** 参照元パッケージノードを指定する */
  public void setFromPkgNode(PkgNode fromPkgNode) {
    if (this.fromPkgNode == fromPkgNode) return;
    this.fromPkgNode = fromPkgNode;
    this.fromPkgPruned = prunedPkgs.contains(fromPkgNode);
    FromPkgEvent e = new FromPkgEvent(fromPkgNode, fromPkgPruned);
    bus.dispatchEvent(e);
  }

  /** 参照先パッケージノードを指定する */
  public void setToPkgNode(PkgNode toPkgNode) {
    if (this.toPkgNode == toPkgNode) return;
    this.toPkgNode = toPkgNode;
    ToPkgEvent e = new ToPkgEvent(fromPkgNode, fromPkgPruned, toPkgNode);
    bus.dispatchEvent(e);
  }
  
  public static class FromPkgEvent {
    public final PkgNode fromPkgNode;
    public final boolean fromPkgPruned;
    private FromPkgEvent(PkgNode fromPkgNode, boolean fromPkgPruned) {
      this.fromPkgNode = fromPkgNode;
      this.fromPkgPruned = fromPkgPruned;
    }
    @Override
    public String toString() {
      return fromPkgNode.getPath();
    }
    public boolean isEmpty() {
      return fromPkgNode == null;
    }
  }

  public static class ToPkgEvent {
    public final PkgNode fromPkgNode;
    public final boolean fromPkgPruned;
    public final PkgNode toPkgNode;  
    private ToPkgEvent(PkgNode fromPkgNode, boolean fromPkgPruned, PkgNode toPkgNode) {
      this.fromPkgNode = fromPkgNode;
      this.fromPkgPruned = fromPkgPruned;
      this.toPkgNode = toPkgNode;
    }
    public boolean isEmpty() {
      return fromPkgNode == null || toPkgNode == null;
    }
    
    @Override
    public String toString() {
      if (isEmpty()) return "empty";
      return fromPkgNode.getPath() + "," + fromPkgPruned + "," + toPkgNode.getPath();
    }
  }
}
