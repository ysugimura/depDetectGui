package com.cm55.depDetect.gui.cyclic;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

@Singleton
public class CyclicModel {

  /** イベントバス */
  public EventBus bus = new EventBus();

  /** descendセット */
  private PrunedPkgs descendSet;
  
  /** 循環参照元パッケージ */
  private PkgNode fromPkgNode = null;

  /** 循環参照元パッケージのdescendフラグ */
  private boolean fromPkgDescend;
  
  /** 循環参照先パッケージ */
  private PkgNode toPkgNode = null;

  /** 循環参照先パッケージのdescendフラグ */
  private boolean toPkgDescend;
  
  @Inject
  public CyclicModel(Model model) {
    model.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
  }
  
  /**
   * プロジェクト変更時
   * @param e
   */
  private void projectChanged(ModelEvent.ProjectChanged e) {
    this.descendSet = e.descendSet;
    fromPkgNode = null;
    toPkgNode = null;
    setFromPkgNode(null);
    setToPkgNode(null);
  }

  /** 参照元パッケージノードを指定する */
  public void setFromPkgNode(PkgNode fromPkgNode) {
    if (this.fromPkgNode == fromPkgNode) return;
    this.fromPkgNode = fromPkgNode;
    this.fromPkgDescend = descendSet.contains(fromPkgNode);
    FromPkgEvent e = new FromPkgEvent(fromPkgNode, fromPkgDescend);
    bus.dispatchEvent(e);
  }

  /** 参照先パッケージノードを指定する */
  public void setToPkgNode(PkgNode toPkgNode) {
    if (this.toPkgNode == toPkgNode) return;
    this.toPkgNode = toPkgNode;
    ToPkgEvent e = new ToPkgEvent(fromPkgNode, fromPkgDescend, toPkgNode);
    bus.dispatchEvent(e);
  }
  
  public static class FromPkgEvent {
    public final PkgNode fromPkgNode;
    public final boolean fromPkgDescend;
    private FromPkgEvent(PkgNode fromPkgNode, boolean fromPkgDescend) {
      this.fromPkgNode = fromPkgNode;
      this.fromPkgDescend = fromPkgDescend;
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
    public final boolean fromPkgDescend;
    public final PkgNode toPkgNode;  
    private ToPkgEvent(PkgNode fromPkgNode, boolean fromPkgDescend, PkgNode toPkgNode) {
      this.fromPkgNode = fromPkgNode;
      this.fromPkgDescend = fromPkgDescend;
      this.toPkgNode = toPkgNode;
    }
    public boolean isEmpty() {
      return fromPkgNode == null || toPkgNode == null;
    }
    
    @Override
    public String toString() {
      if (isEmpty()) return "empty";
      return fromPkgNode.getPath() + "," + fromPkgDescend + "," + toPkgNode.getPath();
    }
  }
}
