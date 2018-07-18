package com.cm55.depDetect.gui;

import com.cm55.depDetect.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

@Singleton
public class GuiEvent {

  public EventBus bus = new EventBus();
  private PkgNode pkgNode = null;
  private boolean descend = false;
  
  public static class PackageSelection {
    public final PkgNode node;
    public final boolean descend;
    private PackageSelection(PkgNode node, boolean descend) {
      this.node = node;
      this.descend = descend;
    }
  }
  
  public void setDescend(boolean value) {
    if (descend == value) return;
    descend = value;
    bus.dispatchEvent(new PackageSelection(pkgNode, descend));
  }
  
  public void setPkgNode(PkgNode pkgNode) {
    if (this.pkgNode == pkgNode) return;
    this.pkgNode = pkgNode;
    bus.dispatchEvent(new PackageSelection(pkgNode, descend));
  }
}
