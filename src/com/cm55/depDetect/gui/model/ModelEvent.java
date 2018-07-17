package com.cm55.depDetect.gui.model;

import com.cm55.depDetect.*;

public class ModelEvent {

  public static class ProjectChanged extends ModelEvent {
    public final PkgNode root;
    ProjectChanged(PkgNode root) {
      this.root = root;
    }
  }

}
