package com.cm55.depDetect.gui.model;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.projects.*;
import com.cm55.depDetect.impl.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

/**
 * プロジェクトモデル
 * @author ysugimura
 */
@Singleton
public class Model {

  private EventBus eventBus = new EventBus();
  
  /** 現在のプロジェクト */
  private Project project;
  
  /** 現在のルート */
  private PkgNode root;
  
  public Model() {
  }

  /** プロジェクトを設定する */
  public void setProject(Project project) throws IOException {   
    List<String>list = project.sourcePaths().collect(Collectors.toList());
    //list.forEach(System.out::println);
    
    root = TreeCreator.create(list);
    this.project = project;
    eventBus.dispatchEvent(new ModelEvent.ProjectChanged(root));
  }

  /** 現在の プロジェクトを更新する */
  public void update() throws IOException {
    if (project == null)  throw new IllegalStateException("No Current Project");
    List<String>list = project.sourcePaths().collect(Collectors.toList());
    root = TreeCreator.create(list);
    eventBus.dispatchEvent(new ModelEvent.ProjectChanged(root));
  }
  
  public PkgNode getRoot() {
    return root;
  }
  
  public <T extends ModelEvent> Unlistener<T> listen(Class<T>clazz, Consumer<T>listener) {
    return eventBus.listen(clazz,  listener);
  }
}
