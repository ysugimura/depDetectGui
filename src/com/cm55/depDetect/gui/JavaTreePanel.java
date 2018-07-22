package com.cm55.depDetect.gui;

import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.eventBus.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTreeView.*;
import com.google.inject.*;

import javafx.scene.Node;

/**
 * Javaパッケージツリーパネル
 * <p>
 * モデルを監視し、プロジェクト変更時にJavaパッケージツリーを作成する。
 * Javaパッケージツリーを表示する。パッケージが選択されたら
 * </p>
 * @author ysugimura
 */
public class JavaTreePanel implements FxNode {

  /** モデル */
  private Model model;
  
  private FxTreeView<PkgNode> treeView;
  
  /** GUIイベントセンター */
  private GuiEvent guiEvent;
  
  /** 自ら発行したGUIイベントに反応しない */
  private boolean selfGuiEvent;
  
  @Inject
  public JavaTreePanel(Model model, GuiEvent guiEvent) {
    this.model = model;
    this.guiEvent = guiEvent;
    
    // ツリービューのセットアップと、アイテム選択に対応する
    treeView = new FxTreeView<PkgNode>(new Adapter());
    treeView.listen(new EventType<ItemSelectionEvent<PkgNode>>() {}, this::treeItemSelection);
    
    // モデルのイベントを監視し、プロジェクト変更に対応する
    model.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
    
    // guiEventに対応しノード変更する。
    guiEvent.bus.listen(GuiEvent.FromPackageSelection.class,  e-> {
      if (selfGuiEvent) return;
      treeView.selectItem(e.fromPkgNode);
    });
  }
 
  /** プロジェクト変更時にツリービューを再構築する */
  private void projectChanged(ModelEvent.ProjectChanged e) {
    treeView.setRoot(e.root);
  }

  /** ツリーノード選択時にGuiイベントを発行する */
  private void treeItemSelection(ItemSelectionEvent<PkgNode>e) {
    selfGuiEvent = true;
    try {
      guiEvent.setFromPkgNode(e.item);
    } finally {
      selfGuiEvent = false;
    }
  }
  
  /** {@link PkgNode}をツリービューノードに変換するためのアダプタ */
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
