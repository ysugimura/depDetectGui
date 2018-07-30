package com.cm55.depDetect.gui.depends;

import com.cm55.depDetect.gui.common.*;
import com.cm55.fx.*;
import com.google.inject.*;

public class DepsToClsPanel implements FxNode {

  FxTitledBorder titledBorder;
  ClassesPanel classesPanel;
  
  @Inject
  public DepsToClsPanel(DependModel dependModel, ClassesPanel classesPanel) {
    this.classesPanel = classesPanel;
    titledBorder = new FxTitledBorder("ソースクラス", new FxJustBox(classesPanel));
    dependModel.bus.listen(DependModel.DepToPkgEvent.class,  this::dependToPkgChanged);
  }
  
  void dependToPkgChanged(DependModel.DepToPkgEvent e) {
    if (e.isEmpty()) {
      classesPanel.clearRows();
      return;
    }
    
    // フォーカス中のパッケージ下の全クラスについて、選択された依存先パッケージを依存先としてもつものを取得
    classesPanel.setRows(
      e.focusPkg.childClasses(e.focusPruned).filter(cls->cls.getDepsTo().contains(e.getDepToPkg()))
    );
  }
  
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
