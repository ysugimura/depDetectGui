package com.cm55.depDetect.gui.depends;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

/**
 * 依存パッケージ・クラス表示制御モデル
 * @author ysugimura
 */
@Singleton
public class DependModel {

  EventBus bus = new EventBus();
  private DescendSet descendSet;
  
  /** 注目するパッケージ */
  private PkgNode focusPkg;

  /** 注目パッケージのdescend */
  private boolean focusDescend;
  
  /** 注目パッケージの依存先パッケージ中の単一パッケージ */
  private PkgNode depToPkg;
  
  /** 注文パッケージの被依存先パッケージ中の単一パッケージ */
  private PkgNode depFromPkg;
  
  @Inject
  public DependModel(Model model) {
    model.listen(ModelEvent.ProjectChanged.class,  this::projectChanged);
  }
  
  /** プロジェクト変更時 */
  private void projectChanged(ModelEvent.ProjectChanged e) {
    descendSet = e.descendSet;
    setFocusPkg(null);
  }

  /** 注目するパッケージを設定する。依存先、被依存先パッケージをリセット */
  public void setFocusPkg(PkgNode pkgNode) {
    if (focusPkg == pkgNode) return;
    focusPkg = pkgNode;
    focusDescend = descendSet.contains(focusPkg);
    bus.dispatchEvent(new FocusPkgEvent(focusPkg, focusDescend));
    setDepToPkg(null);
    setDepFromPkg(null);
  }
  
  /** 注目する依存先パッケージを通知する */
  public void setDepToPkg(PkgNode pkgNode) {
    if (depToPkg == pkgNode) return;
    depToPkg = pkgNode;
    bus.dispatchEvent(new DepToPkgEvent(focusPkg, focusDescend, depToPkg));
  }
  
  /** 注目する被依存先パッケージを通知する */
  public void setDepFromPkg(PkgNode pkgNode) {
    if (depFromPkg == pkgNode) return;
    depFromPkg = pkgNode;
    bus.dispatchEvent(new DepFromPkgEvent(focusPkg, focusDescend, depFromPkg));
  }

  /** フォーカス対象パッケージの変更イベント */
  public static class FocusPkgEvent {
    public final PkgNode focusPkg;
    public final boolean descend;
    FocusPkgEvent(PkgNode focusPkg, boolean descend) {
      this.focusPkg = focusPkg;
      this.descend = descend;
    }
    public boolean isEmpty() {
      return focusPkg == null;
    }
  }
  
  /** ノードがnullの場合はリセット */
  public abstract static class DepPkgEvent {
    /** 注目パッケージ */
    public final PkgNode focusPkg;
    
    /** 注目パッケージのdescendモード */
    public final boolean focusDescend;
    
    /** 依存・被依存パッケージ */
    protected final PkgNode pkgNode;
    
    DepPkgEvent(PkgNode focusPkg, boolean focusDescend, PkgNode pkgNode) {
      this.focusPkg = focusPkg;
      this.focusDescend = focusDescend;
      this.pkgNode = pkgNode;
    }
    
    public boolean isEmpty() {
      return focusPkg == null || pkgNode == null;
    }
    
    @Override
    public String toString() {
      if (isEmpty()) return "empty";
      return focusPkg.getPath() + "," + focusDescend + "," + pkgNode.getPath();
    }
  }
  
  /** 注目依存パッケージ変更イベント */
  public static class DepToPkgEvent extends DepPkgEvent {
    DepToPkgEvent(PkgNode focusPkg, boolean descend, PkgNode pkgNode) {
      super(focusPkg, descend, pkgNode);
    }
    public PkgNode getDepToPkg() {
      return pkgNode;
    }
  }
  
  /** 注目被依存パッケージ変更イベント */
  public static class DepFromPkgEvent extends DepPkgEvent {
    DepFromPkgEvent(PkgNode focusPkg, boolean descend, PkgNode pkgNode) {
      super(focusPkg, descend, pkgNode);
    }
    public PkgNode getDepFromPkg() {
      return pkgNode;
    }
  }
}
