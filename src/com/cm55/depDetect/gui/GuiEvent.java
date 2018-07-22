package com.cm55.depDetect.gui;

import com.cm55.depDetect.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

import javafx.beans.property.*;
import javafx.beans.value.*;

@Singleton
public class GuiEvent {

  public EventBus bus = new EventBus();
  
  /** 参照元パッケージノード */
  private PkgNode fromPkgNode = null;
  private SimpleBooleanProperty fromPkgDescend = new SimpleBooleanProperty();    
  public SimpleBooleanProperty getFromPkgDescendProperty() { return fromPkgDescend; }
  
  private PkgNode toPkgNode = null;
  
  private ClsNode cyclicFocusingClass;
  
  public static class FromPackageSelection {
    public final PkgNode fromPkgNode;
    public final boolean fromPkgDescend;
    private FromPackageSelection(PkgNode fromPkgNode, boolean fromPkgDescend) {
      this.fromPkgNode = fromPkgNode;
      this.fromPkgDescend = fromPkgDescend;
    }
    @Override
    public String toString() {
      return fromPkgNode.getPath() + "," + fromPkgDescend;
    }
  }

  public static class ToPackageSelection {
    public final PkgNode toPkgNode;
    private ToPackageSelection(PkgNode toPkgNode) {
      this.toPkgNode = toPkgNode;
    }
  }
  
  public static class CyclicFocusingClassSelection {
    public final ClsNode node;
    private CyclicFocusingClassSelection(ClsNode node) {
      this.node = node;
    }
  }
  
  public GuiEvent() {
    fromPkgDescend.addListener(new ChangeListener<Boolean>() {
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        fireFromPackageSelection();
      }      
    });
  }
  
  public void setFromPkgNode(PkgNode fromPkgNode) {
    if (this.fromPkgNode == fromPkgNode) return;
    this.fromPkgNode = fromPkgNode;
    fireFromPackageSelection();
  }
  
  public void setToPkgNode(PkgNode toPkgNode) {
    if (this.toPkgNode == toPkgNode) return;
    this.toPkgNode = toPkgNode;
    ToPackageSelection e = new ToPackageSelection(toPkgNode);
    bus.dispatchEvent(e);
  }

  public void setCyclicFocusingClass(ClsNode node) {
    this.cyclicFocusingClass = node;
    fireCyclicFocusingClassSelection();
  }
  
  private void fireFromPackageSelection() {
    FromPackageSelection e = new FromPackageSelection(fromPkgNode, fromPkgDescend.get());
    //ystem.out.println("fire " + e);
    bus.dispatchEvent(e);
  }
  
  private void fireCyclicFocusingClassSelection() {
    CyclicFocusingClassSelection e = new CyclicFocusingClassSelection(cyclicFocusingClass);
    bus.dispatchEvent(e);
  }
}
