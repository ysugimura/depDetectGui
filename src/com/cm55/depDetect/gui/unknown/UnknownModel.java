package com.cm55.depDetect.gui.unknown;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

@Singleton
public class UnknownModel {

  EventBus bus = new EventBus();  
  private DescendSet descendSet;
  private PkgNode focusPkg;
  
  @Inject 
  public UnknownModel(Model model) {
    model.listen(ModelEvent.ProjectChanged.class, this::reset);
  }

  private void reset(ModelEvent.ProjectChanged e) {
    this.descendSet = e.descendSet;
    setFocusPkg(null);
  }
  
  public void setFocusPkg(PkgNode value) {
    if (focusPkg == value) return;
    focusPkg = value;
    bus.dispatchEvent(new FocusPkgEvent(focusPkg, descendSet.contains(focusPkg)));
  }
  
  public static class FocusPkgEvent {
    public final PkgNode focusPkg;
    public final boolean descend;
    FocusPkgEvent(PkgNode node, boolean descend) {
      this.focusPkg = node;
      this.descend = descend;
    }
   public boolean isEmpty() {
     return focusPkg == null;
   }
  }
}
