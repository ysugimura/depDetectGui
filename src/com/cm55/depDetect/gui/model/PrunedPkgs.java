package com.cm55.depDetect.gui.model;

import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.eventBus.*;

/**
 * 枝刈りパッケージ集合
 * <p>
 * 枝刈りされた{@link PkgNode}の集合を保持する。というよりも、ここに格納された{@link PkgNode}は枝刈りされていると
 * みなされる。
 * このようなパッケージでは、それ以下のパッケージの属性すべてを統合して保持し、 それ以下のパッケージは隠される。
 * </p>
 * <p>
 * 当然だが、親子関係にあるパッケージノードの中の複数のパッケージノードが枝刈りされることはありえない。一箇所だけになる。
 * 例えば、以下のような場合に、BとDが同時に枝刈りされていることはない。BとEは同時に枝刈り可能。
 * </p>
 * <pre>
 * A
 * +- B
 * |  +- C
 * |     +- D
 * +- E
 * </pre>
 * @author ysugimura
 */
public class PrunedPkgs {

  public final EventBus bus = new EventBus();
  
  /** 枝刈りされたパッケージノード */
  Set<PkgNode>set = new HashSet<>();
  
  PrunedPkgs() {}
  PrunedPkgs(PkgNode root, PrunedPkgs old) {
    old.getPrunedPkgNames().forEach(name-> {
      JavaNode found = root.findExact(name);
      if (found == null) return;
      if (found instanceof ClsNode) return;
      internalOn((PkgNode)found);
    });
  }
  
  /** 
   * 指定されたパッケージノードが枝刈り状態であるかを取得する
   * @param pkgNode　調査対象のパッケージノード
   * @return true:枝刈り指定されている、false:指定されていない。
   */
  public boolean contains(PkgNode pkgNode) {
    return set.contains(pkgNode);
  }

  /** 
   * 指定パッケージの枝刈り状態をON/OFFする。
   * ONの場合には、指定パッケージのすべての先祖、すべての子孫について枝刈り状態があればそれが解除される。
   * 変更のある場合はイベントを発行する。
   * @param pkgNode 対象とするパッケージノード
   * @param on true:枝刈り状態にする。false:枝刈り状態を解除する。
   */
  public boolean setOn(PkgNode pkgNode, boolean on) {
    if (on == set.contains(pkgNode)) return false;
    
    // 枝刈り状態を解除してイベントを発行する
    if (!on) {
      set.remove(pkgNode);
      fireChangedEvent();
      return true;
    }

    // 枝刈り状態にしてイベントを発行する。
    internalOn(pkgNode);
    fireChangedEvent();
    return true;
  }

  /** 
   * 内部的にONにする操作。
   * 枝刈り集合に加える。すべての祖先とすべての子孫について、枝刈り状態があれば解除する
   * @param pkgNode
   */
  void internalOn(PkgNode pkgNode) {
    set.add(pkgNode);
    for (PkgNode node = pkgNode.getParent(); node != null; node = node.getParent()) {
      set.remove(node);
    }
    pkgNode.childPackages(true).forEach(n->set.remove(n));
  }
  
  /**
   * クリアする。一つでも枝刈りパッケージが会った場合はクリア後にイベントを発行
   */
  public void clear() {
    if (set.size() == 0) return;
    set = new HashSet<>();
    fireChangedEvent();
  }

  /** 枝刈り状態が変更されたイベントを発行 */
  private void fireChangedEvent() {
    bus.dispatchEvent(new PrunedChangedEvent(this));
  }
  
  /**
   * 枝刈り指定されたパッケージ名称を文字列で取得する。名前順にソートされている。
   * @return 枝刈り指定されたパッケージの文字列名称のストリーム
   */
  public Stream<String>getPrunedPkgNames() {
    return set.stream().map(n->n.getPath()).sorted();
  }

  /** 
   * 指定されたパッケージノードが隠されているか。
   * つまり、自身は枝刈りされておらず、先祖のいずれかが枝刈りされていることを示す。
   * @param pkgNode 調査対象のパッケージ
   * @return true:隠されている。false:隠されていない。
   */
  public boolean isHidden(PkgNode pkgNode) {
    if (set.contains(pkgNode)) return false;
    for (PkgNode node = pkgNode.getParent(); node != null; node = node.getParent()) {
      if (set.contains(node)) return true;
    }
    return false;
  }
  
  /** 
   * 考慮対象のパッケージをすべて取得する。
   * 自身が枝刈り指定されているか、あるいは先祖が枝刈り指定されていないすべてのパッケージノード
   * @param root
   * @return
   */
  public Stream<PkgNode>getEffectivePackages(PkgNode root) {
    List<PkgNode>list = new ArrayList<>();
    root.visitPackages(VisitOrder.PRE, pkg-> {
      if (!isHidden(pkg)) list.add(pkg);
    });
    return list.stream();
  }
  
  /** 枝刈り状態が変更されたことを通知するイベント */
  public static class PrunedChangedEvent {    
    public PrunedPkgs prunedPkgs;
    PrunedChangedEvent(PrunedPkgs prunedPkgs) {
      this.prunedPkgs = prunedPkgs;
    }
  }
}