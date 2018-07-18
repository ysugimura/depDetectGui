package com.cm55.depDetect.gui;

import com.cm55.depDetect.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

import javafx.beans.property.*;
import javafx.beans.value.*;

@Singleton
public class GuiEvent {

  public EventBus bus = new EventBus();
  private PkgNode pkgNode = null;
  public SimpleBooleanProperty descend = new SimpleBooleanProperty();
  
  public static class PackageSelection {
    public final PkgNode node;
    public final boolean descend;
    private PackageSelection(PkgNode node, boolean descend) {
      this.node = node;
      this.descend = descend;
    }
    @Override
    public String toString() {
      return node.getPath() + "," + descend;
    }
  }

  public GuiEvent() {
    descend.addListener(new ChangeListener<Boolean>() {
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        firePackageSelection();
      }      
    });
  }
  
  public void setPkgNode(PkgNode pkgNode) {
    if (this.pkgNode == pkgNode) return;
    this.pkgNode = pkgNode;
    firePackageSelection();
  }

  private void firePackageSelection() {
    PackageSelection e = new PackageSelection(pkgNode, descend.get());
    System.out.println("fire " + e);
    bus.dispatchEvent(e);
  }
}
