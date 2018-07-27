package com.cm55.depDetect.gui.model;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.cm55.depDetect.*;

/**
 * descendフラグセット
 * <p>
 * {@link PkgNode}に対するdescendフラグを保持する。このフラグを保持するパッケージノードは、それ以下のパッケージの属性すべてを統合して保持し、
 * それ以下のパッケージは隠される。
 * </p>
 * <p>
 * 当然だが、親子関係にあるパッケージノードの中の複数のパッケージノードがdescendフラグを持つことはできない。
 * </p>
 * @author ysugimura
 */
public class DescendSet {

  /** descendフラグを持つパッケージノード */
  Set<PkgNode>set = new HashSet<>();
  
  Consumer<DescendSet>changeCallback;
  
  DescendSet(Consumer<DescendSet>changeCallback) {
    this.changeCallback = changeCallback;
  }
  
  DescendSet(Consumer<DescendSet>changeCallback, PkgNode root, DescendSet old) {
    this(changeCallback);
    old.getDescendPackages().forEach(name-> {
      JavaNode found = root.findExact(name);
      if (found == null) return;
      if (found instanceof ClsNode) return;
      internalOn((PkgNode)found);
    });
  }
  
  /** 指定されたパッケージノードがdescend対象かを取得する */
  public boolean contains(PkgNode pkgNode) {
    return set.contains(pkgNode);
  }

  /** 
   * 指定パッケージをONする。変更のある場合はイベント発行
   * 
   * @param pkgNode
   * @param on
   */
  public boolean setOn(PkgNode pkgNode, boolean on) {
    if (on == set.contains(pkgNode)) return false;
    if (!on) {
      set.remove(pkgNode);
      fireEvent();
      return true;
    }
    internalOn(pkgNode);
    fireEvent();
    return true;
  }

  /** 
   * 内部的にONにする操作。
   * ON集合に加える。すべての祖先とすべての子孫をOFFにする。
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
   * クリアする
   */
  public void clear() {
    if (set.size() == 0) return;
    set = new HashSet<>();
    fireEvent();
  }

  private void fireEvent() {
    changeCallback.accept(this);
  }
  
  /**
   * descend指定のあるパッケージ名称をすべて取得する
   * @return
   */
  public Stream<String>getDescendPackages() {
    return set.stream().map(n->n.getPath());
  }

  /** 指定されたパッケージノードが隠されているか */
  public boolean isHidden(PkgNode pkgNode) {
    if (set.contains(pkgNode)) return false;
    for (PkgNode node = pkgNode.getParent(); node != null; node = node.getParent()) {
      if (set.contains(node)) return true;
    }
    return false;
  }
  
  /** 
   * 考慮対象のパッケージをすべて取得する
   * つまり、自身がdescend指定されているか、あるいは先祖がdescend指定されていないすべてのパッケージノード
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
  
}