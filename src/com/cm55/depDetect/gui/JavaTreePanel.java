package com.cm55.depDetect.gui;

import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.Node;

/**
 * Javaパッケージツリーパネル
 * @author ysugimura
 */
public class JavaTreePanel implements FxNode {

  private Model model;
  private FxTreeView<PkgNode> treeView;
  
  @Inject
  public JavaTreePanel(Model model) {
    this.model = model;
    treeView = new FxTreeView<PkgNode>(new Adapter());
    model.listen(ModelEvent.ProjectChanged.class, this::modelProjectChanged);
  }
  
  private void modelProjectChanged(ModelEvent.ProjectChanged e) {
    setRoot(e.root);
  }
  
  private void setRoot(PkgNode root) {
    treeView.setRoot(root);
  }

  public static class Adapter implements FxTreeView.Adapter<PkgNode> {
    public String getLabel(PkgNode node) {
      return node.getName();
    }
    public Stream<PkgNode> children(PkgNode node) {
      return node.packageStream();
    }    
  }

  @Override
  public Node node() {
    return treeView.node();
  }
}
