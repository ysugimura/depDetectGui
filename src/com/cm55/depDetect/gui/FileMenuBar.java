package com.cm55.depDetect.gui;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.gui.model.*;
import com.cm55.depDetect.gui.projects.*;
import com.cm55.fx.*;
import com.cm55.fx.AbstractMenu.*;
import com.google.inject.*;

public class FileMenuBar {

  @Inject private Model model;
  
  public final FxMenuBar menuBar;
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
      Project project = new ProjectsDialog().showAndWait(FxNode.wrap(node), null);
      if (project == null) return;
      try {
        model.setProject(project);
      } catch (IOException ex) {
        FxAlerts.error(menuBar,  "エラー：" + ex.getMessage());
      }
    }
    if (e.node == updateNode) {
      try {
        model.update();
      } catch (Exception ex) {
        FxAlerts.error(menuBar,  "エラー：" + ex.getMessage());
      }
    }
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
