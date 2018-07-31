package com.cm55.depDetect.gui.cyclic;

import com.cm55.depDetect.gui.common.*;
import com.cm55.depDetect.gui.i18n.*;
import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;

/**
 * 参照元ノード(from)下にあるクラスのうち、指定された参照先(to)ノードを参照しているクラスのリスト。
 * ただし、参照元ノードが枝刈りされている可能性があり、「下にあるクラス」とは直下とは限らず、下すべてのパッケージ内のクラスの可能性。
 * 
 * @author ysugimura
 */
public class FromClassesPanel implements FxNode {

  private FxTitledBorder titledBorder;
  private ClassesPanel classesPanel;
  
  @Inject
  public FromClassesPanel(CyclicModel guiEvent, ClassesPanel classesPanel, Msg msg) {
    this.classesPanel = classesPanel;
    titledBorder = new FxTitledBorder(msg.get(Msg.参照元クラス), new FxJustBox(classesPanel));
    guiEvent.bus.listen(CyclicModel.ToPkgEvent.class,  this::toPackageSelection);
  }

  /** 
   * 参照先側ノードが指定された。参照元ノード側のクラスで、この参照先ノードを参照するものを一覧する。
   * @param e
   */
  void toPackageSelection(CyclicModel.ToPkgEvent e) {
    if (e.isEmpty()) {
      classesPanel.clearRows();
      return;
    }
    
    //　fromノード下のすべてのクラスのうち、toノードを参照するものをリストする。
    // fromノードが枝刈りされている場合はfromノードの子孫にあるすべてのクラスをチェックする。
    // が、チェック対象の参照ノードとしては、指定されたノードだけでよい。
    classesPanel.setRows(
      e.fromPkgNode.childClasses(e.fromPkgPruned).filter(clsNode-> {        
        return clsNode.getDepsTo().contains(e.toPkgNode);
      })
    );
  }
  
  @Override
  public Node node() {
    return titledBorder.node();
  }  
}
