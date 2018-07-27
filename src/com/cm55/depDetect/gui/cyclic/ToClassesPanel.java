package com.cm55.depDetect.gui.cyclic;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.common.*;
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
  public ToClassesPanel(CyclicModel guiEvent) {
    classesPanel = new ClassesPanel();
    titledBorder = new FxTitledBorder("参照先クラス", new FxJustBox(classesPanel));
    guiEvent.bus.listen(CyclicModel.ToPkgEvent.class,  this::toPackageSelection);
  }

  /** 
   * 参照先側ノードが指定された。
   * @param e
   */
  void toPackageSelection(CyclicModel.ToPkgEvent e) {
    if (e.isEmpty()) {
      classesPanel.clearRows();
      return;
    }
    
    // toノード直下のクラスのみを調べ、fromノードを参照するクラスをリストする。
    // ただし、fromノードがdescendモードの場合はfromの子孫ノードもチェック対象に含める
    classesPanel.setRows(
      e.toPkgNode.childClasses(false).filter(clsNode-> {
        Refs refs = clsNode.getDepsTo();
        if (e.fromPkgDescend) 
          return refs.containsUnder(e.fromPkgNode);
        else
          return refs.contains(e.fromPkgNode);
      })
    );
  }
  
  @Override
  public Node node() {
    return titledBorder.node();
  }  
}
