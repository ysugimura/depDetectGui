package com.cm55.depDetect.gui.depends;

import com.cm55.depDetect.*;
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
  
  /** 
   * 依存元パッケージの変更。 
   * このパッケージ内のクラスのうち、着目対象パッケージに依存するものを列挙する。
   * @param e
   */
  void dependFromPkgChanged(DependModel.DepFromPkgEvent e) {
    //ystem.out.println("" + e);
    
    // 依存元パッケージがない
    if (e.isEmpty()) {
      classesPanel.clearRows();
      return;
    }
    
    // フォーカス中のパッケージ下の全クラスについて、選択された被依存先パッケージを被依存先としてもつものを取得
    // ただし、着目パッケージの下のパッケージに依存している可能性がある。
    classesPanel.setRows(
      e.getDepFromPkg().childClasses(false).filter(cls-> {        
        Refs refs = cls.getDepsTo();
        if (e.focusPruned) return refs.containsUnder(e.focusPkg);
        return refs.contains(e.focusPkg); 
      })
    );
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
