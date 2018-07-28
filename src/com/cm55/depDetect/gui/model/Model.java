package com.cm55.depDetect.gui.model;

import java.io.*;
import java.util.*;
import java.util.function.*;
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

  private EventBus eventBus = new EventBus();
  
  /** 現在のプロジェクト */
  private Project project;
  
  /** 現在のルート */
  private PkgNode root;
  
  /** 現在の枝刈り集合 */
  private PrunedPkgs prunedPkgs;
  
  public Model() {
  }

  /** 
   * プロジェクトを設定する
   * この操作は時間がかかるのでイベントスレッド以外で実行されることに注意
   * @param project
   * @throws IOException
   */
  public void setProject(Project project) throws IOException {   
    List<String>list = project.sourcePaths().collect(Collectors.toList());
    //list.forEach(System.out::println);
    
    root = TreeCreator.create(list);
    this.project = project;
    this.prunedPkgs = new PrunedPkgs();
    prunedPkgs.bus.listen(PrunedChangedEvent.class,  this::descendChange);
    Platform.runLater(()-> {
      eventBus.dispatchEvent(new ModelEvent.ProjectChanged(root, prunedPkgs));
    });
  }

  /** 
   * 現在の プロジェクトを更新する
   * この操作は時間がかかるのでイベントスレッド以外で実行されることに注意
   * @throws IOException
   */
  public void update() throws IOException {
    if (project == null)  throw new IllegalStateException("No Current Project");
    List<String>list = project.sourcePaths().collect(Collectors.toList());
    root = TreeCreator.create(list);
    Platform.runLater(()-> {
      /** 
       * 更新されたプロジェクトに{@link PrunedPkgs}を適合させる。
       * 更新されたプロジェクトのノードは、現在の{@link PrunedPkgs}とは異なるもの。
       * */
      PrunedPkgs old = prunedPkgs;
      prunedPkgs = new PrunedPkgs(root, old);
      prunedPkgs.bus.listen(PrunedChangedEvent.class, this::descendChange);
      eventBus.dispatchEvent(new ModelEvent.ProjectChanged(root, prunedPkgs));
    });
  }

  /** カレントのPrunedPkgsが変更された */
  void descendChange(PrunedPkgs.PrunedChangedEvent e) {
    eventBus.dispatchEvent(new ModelEvent.ProjectChanged(root, e.prunedPkgs));
  }
  
  public PkgNode getRoot() {
    return root;
  }
  
  public <T extends ModelEvent> Unlistener<T> listen(Class<T>clazz, Consumer<T>listener) {
    return eventBus.listen(clazz,  listener);
  }
}
