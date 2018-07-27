package com.cm55.depDetect.gui.cyclic;

import com.cm55.depDetect.gui.*;
import com.cm55.depDetect.gui.GuiEvent.*;
import com.cm55.depDetect.gui.common.*;
import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;

/**
 * 参照元ノード下にあるクラスのうち、指定された参照先ノードを参照しているクラスのリスト。
 * @author ysugimura
 */
public class FromClassesPanel implements FxNode {

  private FxTitledBorder titledBorder;
  private ClassesPanel classesPanel;
  
  @Inject
  public FromClassesPanel(GuiEvent guiEvent) {
    classesPanel = new ClassesPanel();
    titledBorder = new FxTitledBorder("参照元クラス", new FxJustBox(classesPanel));
    guiEvent.bus.listen(GuiEvent.ToPackageSelection.class,  this::toPackageSelection);
  }

  /** 
   * 参照先側ノードが指定された。参照元ノード側のクラスで、この参照先ノードを参照するものを一覧する。
   * @param e
   */
  void toPackageSelection(GuiEvent.ToPackageSelection e) {
    if (e.toPkgNode == null) {
      classesPanel.clearRows();
      return;
    }
    classesPanel.setRows(
      e.fromPkgNode.childClasses(false).filter(clsNode->clsNode.getDepsTo().contains(e.toPkgNode))
    );
  }
  
  @Override
  public Node node() {
    return titledBorder.node();
  }  
}
