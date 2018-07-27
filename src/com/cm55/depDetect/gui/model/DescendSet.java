package com.cm55.depDetect.gui.model;

import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.*;

/**
 * descendフラグセット
 * @author ysugimura
 */
public class DescendSet {
  Set<PkgNode>set = new HashSet<>();
  
  public DescendSet() {}
  
  public DescendSet(PkgNode root, DescendSet old) {
    old.getPackages().forEach(name-> {
      JavaNode found = root.findExact(name);
      if (found == null) return;
      if (found instanceof ClsNode) return;
      internalOn((PkgNode)found);
    });
  }
  
  public boolean contains(PkgNode pkgNode) {
    return set.contains(pkgNode);
  }
  
  public void setOn(PkgNode pkgNode, boolean on) {
    if (!on) {
      set.remove(pkgNode);
      return;
    }
    internalOn(pkgNode);
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
  
  public void clear() {
    set = new HashSet<>();
  }
  
  public Stream<String>getPackages() {
    return set.stream().map(n->n.getPath());
  }
}