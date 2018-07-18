package com.cm55.depDetect.gui;

import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.cm55.fx.AbstractMenu.*;
import com.google.inject.*;

import javafx.geometry.*;

/**
 * モデルを監視し、プロジェクト変更時にパッケージツリーの選択メニューを作成する。
 * 指示されたラメニューを表示し、アイテム選択時にグローバルイベントを発行する。
 * @author ysugimura
 */
public class JavaTreeMenu  {

  /** モデル */
  private Model model;
  
  /** コンテキストメニュー */
  private FxContextMenu<PkgNode> contextMenu;
  
  /** GUIイベント */
  @Inject private GuiEvent guiEvent;

  /** 作成する */
  @Inject
  public JavaTreeMenu(Model model) {
    this.model = model;
    model.listen(ModelEvent.ProjectChanged.class,  this::projectChanged);
    contextMenu = new FxContextMenu<PkgNode>(new Adapter());
    contextMenu.listenSelection(this::pkgNodeSelection);
  }
  
  /** モデル側でプロジェクトが変更されたとき、コンテキストメニューを再構築する */
  private void projectChanged(ModelEvent.ProjectChanged e) {
    contextMenu.setRoot(e.root);
  }

  /** メニューアイテム選択時にグローバルGuiEventを発行する */
  private void pkgNodeSelection(SelectionEvent<PkgNode> e) {
    guiEvent.setPkgNode(e.node);
  }

  /** 指定ノードにメニューを表示する */
  public void show(FxNode node, Side side) {
    contextMenu.show(node, side);
  }
  
  /** パッケージノードをメニューアイテムノードに変換するためのノードアダプタ */
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
      if (hasChildren) return MenuItemKind.SELECTABLE_BRANCH;
      return MenuItemKind.LEAF;
    }    
  }
}
