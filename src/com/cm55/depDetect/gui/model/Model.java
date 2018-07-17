package com.cm55.depDetect.gui.model;

import java.io.*;
import java.util.function.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.projects.*;
import com.cm55.depDetect.impl.*;
import com.cm55.eventBus.*;
import com.google.inject.*;

@Singleton
public class Model {

  private EventBus eventBus = new EventBus();
  
  /** 現在のプロジェクト */
  private Project project;
  
  /** 現在のルート */
  private PkgNode root;
  
  public Model() {
  }

  public void setProject(Project project) throws IOException {   
    root = TreeCreator.create(project.sourcePaths().collect(Collectors.toList()));
    this.project = project;
    eventBus.dispatchEvent(new ModelEvent.ProjectChanged(root));
  }

  public PkgNode getRoot() {
    return root;
  }
  
  public <T extends ModelEvent> Unlistener<T> listen(Class<T>clazz, Consumer<T>listener) {
    return eventBus.listen(clazz,  listener);
  }
}
