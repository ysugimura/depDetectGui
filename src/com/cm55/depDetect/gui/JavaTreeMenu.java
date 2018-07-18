package com.cm55.depDetect.gui;

import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.cm55.fx.AbstractMenu.*;
import com.google.inject.*;

public class JavaTreeMenu  {

  private Model model;
  private FxContextMenu<PkgNode> contextMenu;
  
  @Inject
  public JavaTreeMenu(Model model) {
    this.model = model;
    model.listen(ModelEvent.ProjectChanged.class,  this::projectChanged);
    contextMenu = new FxContextMenu<PkgNode>(new Adapter());
  }
  
  private void projectChanged(ModelEvent.ProjectChanged e) {
    contextMenu.set(e.root);
  }
  
  public void show(FxNode node) {
    contextMenu.show(node);
  }
  
  public static class Adapter implements FxContextMenu.Adapter<PkgNode> {
    public String getLabel(PkgNode node) {
      return node.getName();
    }
    public Stream<PkgNode> children(PkgNode node) {
      return node.packageStream();
    }
    @Override
    public MenuItemKind getKind(PkgNode node) {
      boolean hasChildren = node.packageCount() > 0;
      if (hasChildren) return MenuItemKind.BRANCH;
      return MenuItemKind.SELECTABLE_BRANCH;
    }    
  }
}
