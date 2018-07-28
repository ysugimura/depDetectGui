package com.cm55.depDetect.gui.unknown;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

@Singleton
public class UnknownModel {

  EventBus bus = new EventBus();  
  
  @Inject 
  public UnknownModel(Model model) {
    model.bus.listen(ModelEvent.PkgFocused.class,  e-> {
      bus.dispatchEvent(new FocusPkgEvent(e.focusPkg, e.focusPruned));
    });
  }
  
  public static class FocusPkgEvent {
    public final PkgNode focusPkg;
    public final boolean focusPruned;
    FocusPkgEvent(PkgNode focusPkg, boolean focusPruned) {
      this.focusPkg = focusPkg;
      this.focusPruned = focusPruned;
    }
   public boolean isEmpty() {
     return focusPkg == null;
   }
  }
}
