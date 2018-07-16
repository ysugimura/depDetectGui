package com.cm55.depDetect.gui;

import java.util.*;
import java.util.stream.*;

import com.cm55.fx.*;
import com.cm55.fx.FxMenu.*;

public class FileMenuBar {

  public final FxMenuBar menuBar;
  Node listNode;
  
  public FileMenuBar() {
    FxMenu<Node> projectMenu = new FxMenu<Node>(new NodeAdapter(), 
      new Node("Project", 
        listNode = new Node("List")
      )
    );
    projectMenu.listen(this::menuItemClicked);  
    menuBar = new FxMenuBar(projectMenu);    
  }

  void menuItemClicked(SelectionEvent<Node>e) {
    if (e.node == listNode) {
      //new ProjectsPanel.Dialog
    }
  }
  
  public static class NodeAdapter implements Adapter<Node> {
    public String getLabel(Node node) {
      return node.name;
    }
    public boolean hasChildren(Node node) {
      return node.children.length > 0;
    }
    public Stream<Node> children(Node node) {
      return Arrays.stream(node.children);
    }    
  }
  
  public static class Node {
    public final String name;
    public final Node[]children;
    public Node(String name, Node...children) {
      this.name = name;
      this.children = children;
    }
  }
}
