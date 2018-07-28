package com.cm55.depDetect.gui.model;

import com.cm55.depDetect.*;

public class ModelEvent {

  /** プロジェクト変更イベント */
  public static class ProjectChanged extends ModelEvent {
    public final PkgNode root;
    public final PrunedPkgs prunedPkgs;
    ProjectChanged(PkgNode root, PrunedPkgs prunedPkgs) {
      this.root = root;
      this.prunedPkgs = prunedPkgs;
    }
  }

  /** フォーカスパッケージ変更イベント */
  public static class PkgFocused extends ModelEvent{
    public final PkgNode focusPkg;
    PkgFocused(PkgNode focusPkg) {
      this.focusPkg = focusPkg;
    }
  }
}
