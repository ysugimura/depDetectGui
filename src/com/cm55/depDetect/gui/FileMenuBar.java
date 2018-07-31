package com.cm55.depDetect.gui;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
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
  @Inject private Provider<JDepsPathDialog>jdepsDlgProvider;
  
  public final FxMenuBar menuBar;
  FxProgressMessageDialog loadingDialog;
  TreeNode listNode;
  TreeNode updateNode;
  TreeNode jdepsNode;
  TreeNode helpNode;
  
  public FileMenuBar() {
    FxMenu<TreeNode> projectMenu = new FxMenu<TreeNode>(new NodeAdapter(), 
      new TreeNode("Project", 
        listNode = new TreeNode("List"),
        updateNode = new TreeNode("Update")
      )
    );
    projectMenu.listenSelection(this::projectMenuClicked);  
    FxMenu<TreeNode>settingMenu = new FxMenu<TreeNode>(new NodeAdapter(), 
      new TreeNode("Others",
        jdepsNode = new TreeNode("jdeps"),
        helpNode = new TreeNode("HELP")
      )
    );
    settingMenu.listenSelection(this::othersMenuClicked);
    menuBar = new FxMenuBar(projectMenu, settingMenu);    

  }

  void projectMenuClicked(SelectionEvent<TreeNode>e) {
    javafx.scene.Node node = menuBar.node().getParent();
    if (e.node == listNode) {
      projectList();
    }
    if (e.node == updateNode) {
      update();
    }
  }
  
  void othersMenuClicked(SelectionEvent<TreeNode>e) {
    if (e.node == jdepsNode) {
      setting();
    }
    if (e.node == helpNode) {
      try {
        Desktop.getDesktop().browse(new URI("http://www.gwtcenter.com/depDetectGui"));
      } catch (Exception ex) {
        FxAlerts.error(menuBar, "ブラウザをオープンできません");
      }
    }
  }
  
  @Inject private Provider<ProjectsDialog>projectDialogProvider;
  
  private void projectList() {
    loadingDialog = new FxProgressMessageDialog(menuBar, "ロード中。お待ちください");
    
    Project project = projectDialogProvider.get().showAndWait(menuBar, null);
    if (project == null) return;
    
    loadingDialog.<PkgNode>show(
      ()-> { 
        return load(project);
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
          return load(project);
        },
        r->{
          model.update(r);
        },
        e->{  FxAlerts.error(menuBar,  "エラー：" + e.getMessage()); }
      );  
  }

  private void setting() {
    jdepsDlgProvider.get().show(menuBar, null);
  }
  
  private PkgNode load(Project project) throws IOException {
    List<String>list = project.sourcePaths().collect(Collectors.toList());   
    switch (project.getMode()) {
    case SRC: 
      return SrcTreeCreator.create(list);
    default:
      //ystem.out.println("" + model.getJDepsPath().path);
      return BinTreeCreator.create(model.getJDepsPath().path, list.stream());
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
