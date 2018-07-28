package com.cm55.depDetect.gui;

import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.projects.*;
import com.cm55.depDetect.impl.*;
import com.cm55.fx.*;
import com.cm55.fx.AbstractMenu.*;
import com.google.inject.*;

/**
 * ファイルメニューバー
 * @author ysugimura
 */
public class FileMenuBar {

  @Inject private Model model;
  
  public final FxMenuBar menuBar;
  FxProgressMessageDialog loadingDialog;
  TreeNode listNode;
  TreeNode updateNode;
  
  public FileMenuBar() {
    FxMenu<TreeNode> projectMenu = new FxMenu<TreeNode>(new NodeAdapter(), 
      new TreeNode("Project", 
        listNode = new TreeNode("List"),
        updateNode = new TreeNode("Update")
      )
    );
    projectMenu.listenSelection(this::menuItemClicked);  
    menuBar = new FxMenuBar(projectMenu);    

  }

  void menuItemClicked(SelectionEvent<TreeNode>e) {
    javafx.scene.Node node = menuBar.node().getParent();
    if (e.node == listNode) {
      projectList();
    }
    if (e.node == updateNode) {
      update();
    }
  }
  
  @Inject private Provider<ProjectsDialog>projectDialogProvider;
  
  private void projectList() {
    loadingDialog = new FxProgressMessageDialog(menuBar, "ロード中。お待ちください");
    
    Project project = projectDialogProvider.get().showAndWait(menuBar, null);
    if (project == null) return;
    
    loadingDialog.<PkgNode>show(
      ()-> { 
        List<String>list = project.sourcePaths().collect(Collectors.toList());    
        return TreeCreator.create(list);
      },
      r->{
        model.setProject(project, r);
      },
      e->{  FxAlerts.error(menuBar,  "エラー：" + e.getMessage()); }
    );
  }
  
  private void update() {
    Project project = model.getProject();
    if (project == null) {
      FxAlerts.error(menuBar,  "プロジェクトがありません");
      return;
    }
    loadingDialog = new FxProgressMessageDialog(menuBar, "ロード中。お待ちください");
    loadingDialog.show(
        ()-> { 
          return TreeCreator.create(project.sourcePaths().collect(Collectors.toList()));
        },
        r->{
          model.update(r);
        },
        e->{  FxAlerts.error(menuBar,  "エラー：" + e.getMessage()); }
      );  
  }
  
  public static class NodeAdapter implements Adapter<TreeNode> {
    public String getLabel(TreeNode node) {
      return node.name;
    }
    public MenuItemKind getKind(TreeNode node) {
      return (node.children.length > 0)? MenuItemKind.BRANCH:MenuItemKind.LEAF;
    }
    public Stream<TreeNode> children(TreeNode node) {
      return Arrays.stream(node.children);
    }    
  }
  
  public static class TreeNode {
    public final String name;
    public final TreeNode[]children;
    public TreeNode(String name, TreeNode...children) {
      this.name = name;
      this.children = children;
    }
  }
}
