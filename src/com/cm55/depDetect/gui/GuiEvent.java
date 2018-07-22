package com.cm55.depDetect.gui;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
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
    public final PkgNode fromPkgNode;
    public final boolean fromPkgDescend;
    public final PkgNode toPkgNode;
    private ToPackageSelection(PkgNode fromPkgNode, boolean fromPkgDescend, PkgNode toPkgNode) {
      this.fromPkgNode = fromPkgNode;
      this.fromPkgDescend = fromPkgDescend;
      this.toPkgNode = toPkgNode;
    }
  }

  
  @Inject
  public GuiEvent(Model model) {
    fromPkgDescend.addListener(new ChangeListener<Boolean>() {
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        fireFromPackageSelection();
      }      
    });
    model.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
  }
  
  private void projectChanged(ModelEvent.ProjectChanged e) {
    fromPkgNode = null;
    toPkgNode = null;
    this.fireFromPackageSelection();
    this.fireToPackageSelection();
  }
  
  public void setFromPkgNode(PkgNode fromPkgNode) {
    if (this.fromPkgNode == fromPkgNode) return;
    this.fromPkgNode = fromPkgNode;
    fireFromPackageSelection();
  }
  
  public void setToPkgNode(PkgNode toPkgNode) {
    if (this.toPkgNode == toPkgNode) return;
    this.toPkgNode = toPkgNode;
    fireToPackageSelection();
  }

  private void fireFromPackageSelection() {
    FromPackageSelection e = new FromPackageSelection(fromPkgNode, fromPkgDescend.get());
    bus.dispatchEvent(e);
  }

  private void fireToPackageSelection() {
    ToPackageSelection e = new ToPackageSelection(fromPkgNode, fromPkgDescend.get(), toPkgNode);
    bus.dispatchEvent(e);
  }
}
