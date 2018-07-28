package com.cm55.depDetect.gui.model;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.PrunedPkgs.*;
import com.cm55.depDetect.impl.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

import javafx.application.*;

/**
 * プロジェクトモデル
 * 
 * @author ysugimura
 */
@Singleton
public class Model {

  public final EventBus bus = new EventBus();
  
  /** 現在のプロジェクトリスト */
  private ProjectList projectList;
  
  /** 現在のプロジェクト */
  private Project project;
  
  /** 現在のルート */
  private PkgNode root;
  public PkgNode getRoot() { return root; }
  
  /** 現在の枝刈り集合 */
  private PrunedPkgs prunedPkgs;
  public PrunedPkgs getPrunedPkgs() { return prunedPkgs; }
  
  /** 現在の着目パッケージノード */
  private PkgNode focusPkg;
  public PkgNode getFocusPkg() { return focusPkg; }
  
  /** 現在の着目パッケージノードの枝刈り状況 */
  private boolean focusPruned;
  public boolean getFocusPruned() { return focusPruned; }
  
  /** プロジェクトリストを取得する　*/
  public ProjectList getProjectList() {
    if (projectList != null) return projectList;
    return projectList = new ProjectList.Serializer().get();
  }
  
  /** プロジェクトリストを設定する */
  public void setProjectList(ProjectList projectList) {
    new ProjectList.Serializer().put(projectList);    
    this.projectList = projectList;
  }
  
  /** 
   * プロジェクトを設定する
   * この操作は時間がかかるのでイベントスレッド以外で実行されることに注意
   * @param project
   * @throws IOException
   */
  public void setProject(Project project, PkgNode root) {       
    this.project = project;
    this.root = root;
    this.prunedPkgs = new PrunedPkgs();
    focusPkg = null;
    focusPruned = false;
    prunedPkgs.bus.listen(PrunedChangedEvent.class, e->fireProjectChanged());    
    fireProjectChanged();    
  }

  public Project getProject() {
    return project;
  }
  
  /** 
   * 現在の プロジェクトを更新する
   * この操作は時間がかかるのでイベントスレッド以外で実行されることに注意
   * @throws IOException
   */
  public void update(PkgNode root) {
    this.root = root;

    /** 
     * 更新されたプロジェクトに{@link PrunedPkgs}を適合させる。
     * 更新されたプロジェクトのノードは、現在の{@link PrunedPkgs}とは異なるもの。
     * */
    PrunedPkgs old = prunedPkgs;
    prunedPkgs = new PrunedPkgs(root, old);
    prunedPkgs.bus.listen(PrunedChangedEvent.class, e->fireProjectChanged());
    focusPkg = null;
    focusPruned = false;
    fireProjectChanged();    
  }
  
  /** 着目パッケージを変更する */
  public void setFocusPkg(PkgNode node) {
    if (focusPkg == node) return;
    this.focusPkg = node;
    this.focusPruned = this.prunedPkgs.contains(focusPkg);
    firePkgFocused();
  }

  /** プロジェクト変更イベントを発行する */
  private void fireProjectChanged() {
    System.out.println("fireProjectChanged");
    bus.dispatchEvent(new ModelEvent.ProjectChanged(root, prunedPkgs));
    this.focusPkg = null;
    this.focusPruned = false;
    firePkgFocused();
  }
  
  /** フォーカスパッケージ変更イベントを発行する　*/
  private void firePkgFocused() {
    System.out.println("firePkgFocused " + focusPkg + "," + focusPruned);
    bus.dispatchEvent(new ModelEvent.PkgFocused(focusPkg, focusPruned));    
  }
}
