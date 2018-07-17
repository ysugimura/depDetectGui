package com.cm55.depDetect.gui;

import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;
import javafx.scene.Node;

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
    
  }
  
  public static class Adapter implements FxContextMenu.Adapter<PkgNode> {
    public String getLabel(PkgNode node) {
      return node.getName();
    }
    public Stream<PkgNode> children(PkgNode node) {
      return node.packageStream();
    }
    @Override
    public boolean hasChildren(PkgNode node) {

      return false;
    }    
  }
}
