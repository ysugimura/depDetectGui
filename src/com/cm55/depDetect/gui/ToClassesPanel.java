package com.cm55.depDetect.gui;

import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;

/**
 * 指定された参照先ノード下にあるクラスのうち、参照元ノードを参照しているクラスのリスト。
 * @author ysugimura
 */
public class ToClassesPanel implements FxNode {

  private FxTitledBorder titledBorder;
  private ClassesPanel classesPanel;
  
  @Inject
  public ToClassesPanel(GuiEvent guiEvent) {
    classesPanel = new ClassesPanel();
    titledBorder = new FxTitledBorder("参照先クラス", new FxJustBox(classesPanel));
    guiEvent.bus.listen(GuiEvent.ToPackageSelection.class,  this::toPackageSelection);
  }

  /** 
   * 参照先側ノードが指定された。
   * @param e
   */
  void toPackageSelection(GuiEvent.ToPackageSelection e) {
    if (e.toPkgNode == null) {
      classesPanel.clearRows();
      return;
    }
    classesPanel.setRows(
      e.toPkgNode.childClasses(false).filter(clsNode->clsNode.getDepsTo().contains(e.fromPkgNode))
    );
  }
  
  @Override
  public Node node() {
    return titledBorder.node();
  }  
}
