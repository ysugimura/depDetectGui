package com.cm55.depDetect.gui.depends;

import com.cm55.depDetect.gui.common.*;
import com.cm55.fx.*;
import com.google.inject.*;

public class DepsFromClsPanel implements FxNode {

  FxTitledBorder titledBorder;
  ClassesPanel classesPanel;
  
  @Inject
  public DepsFromClsPanel(DependModel dependModel) {
    classesPanel = new ClassesPanel();
    titledBorder = new FxTitledBorder("ソースクラス", new FxJustBox(classesPanel));
    dependModel.bus.listen(DependModel.DepFromPkgEvent.class,  this::dependFromPkgChanged);
  }
  
  void dependFromPkgChanged(DependModel.DepFromPkgEvent e) {
    if (e.pkgNode == null) {
      classesPanel.clearRows();
      return;
    }
    
    // フォーカス中のパッケージ下の全クラスについて、選択された被依存先パッケージを被依存先としてもつものを取得
    classesPanel.setRows(
      e.focusPkg.childClasses(e.descend).filter(cls->cls.getDepsTo().contains(e.pkgNode))
    );
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
