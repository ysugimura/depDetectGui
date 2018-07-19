package com.cm55.depDetect.gui;

import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

/**
 * 循環依存クラス一覧パネル
 * @author ysugimura
 */
public class CyclicClassesPanel implements FxNode {

  FxTitledBorder titledBorder;
  FxTable<ClsNode>classTable;
  FxObservableList<ClsNode>classRows;
  
  @Inject
  public CyclicClassesPanel(GuiEvent guiEvent) {
    classTable = new FxTable<ClsNode>();
    classTable.setColumns(
      new FxTable.TextColumn<ClsNode>("クラス", t->FixedValue.w(t.getName())).setPrefWidth(300)
    );      
    classRows = classTable.getRows();
    titledBorder = new FxTitledBorder("循環参照クラス一覧", new FxJustBox(
      classTable
    ));    
    guiEvent.bus.listen(GuiEvent.PackageSelection.class, this::packageSelection);
  }

  /** 
   * パッケージが選択された
   * このパッケージ中の循環依存を起こしているクラスをリストアップする。
   * @param e
   */
  private void packageSelection(GuiEvent.PackageSelection e) {
    classRows.clear();
    PkgNode pkgNode = e.node;
    if (pkgNode == null) return;
    Refs cyclics = pkgNode.getCyclics(false);    
    List<ClsNode>list = pkgNode.classStream().filter(clsNode->clsNode.getDepsTo().intersects(cyclics))
        .collect(Collectors.toList());
    System.out.println("" + list.size());
    classRows.addAll(list);
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
