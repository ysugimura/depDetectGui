package com.cm55.depDetect.gui.descend;

import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

import javafx.beans.property.*;
import javafx.beans.value.*;

/**
 * 対象とするすべてのパッケージを表示し、descendフラグをON/OFFする。
 * @author ysugimura
 */
public class DescendPanel implements FxNode  {
  
  FxTitledBorder titledBorder;
  FxTable<Row>table;
  FxObservableList<Row>rows;
  DescendSet descendSet;
  
  @Inject
  public DescendPanel(Model model) {    
    table = new FxTable<Row>();
    table.setColumns(
      new FxTable.CheckColumn<Row>("desc",  r->r.descend).setAlign(FxAlign.CENTER).setPrefWidth(30),
      new FxTable.TextColumn<Row>("パッケージ", r->FixedValue.w(r.pkgNode.getPath())).setPrefWidth(400)
    );    
    rows = table.getRows();
    model.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
    FxBorderPane.Ver borderPane = new FxBorderPane.Ver(
      null,
      table,
      new FxButton("clear", this::clear)
    );
    titledBorder = new FxTitledBorder("みなしパッケージ", new FxJustBox(borderPane));
  }

  private boolean selfEvent = false;
  
  /** 
   * プロジェクト変更時。表示をリセットする
   * @param e
   */
  void projectChanged(ModelEvent.ProjectChanged e) {
    if (selfEvent) return;
    
    descendSet = e.descendSet;
    
    // 行として表示するパッケージを決定するが、その際、
    // 1. descendモードのパッケージによって隠されているものは表示しない
    // 2.　descendモードのパッケージは、そのフラグをONにする。
    List<Row>list = new ArrayList<>();
    e.root.visitPackages(VisitOrder.PRE, pkgNode->{
      if (descendSet.isHidden(pkgNode)) return;
      list.add(new Row(pkgNode, descendSet.contains(pkgNode)));
    });
    rows.clear();    
    rows.addAll(list);
  }

  /** 
   * ある行のdescendフラグをON/OFFする
   * ONの場合、そのパッケージ以下の行を削除する。
   * OFFの場合、そのパッケージ以下の行を復活する。
   * @param targetRow
   * @param on
   */
  @SuppressWarnings("restriction")
  private void setOn(Row targetRow, boolean on) {
    
    // descendSetを操作するとイベントが発生してしまう。
    selfEvent = true;
    try {
      if (!descendSet.setOn(targetRow.pkgNode, on)) return;
    } finally {
      selfEvent = false;
    }

    int index = rows.indexOf(targetRow);
    if (index < 0) {
      // ありえない!!!
      return;
    }
    index++;
    
    // ONの場合、このパッケージ以下の行を削除する
    if (on) {
      Set<PkgNode>set = targetRow.pkgNode.childPackages(true).collect(Collectors.toSet());
      while (index < rows.size()) {
        Row r = rows.get(index);
        if (set.contains(r.pkgNode)) rows.remove(index); 
        else break;
      }
      return;
    }
    
    // OFFの場合、このパッケージ以下の行を復活する
    List<Row>list = targetRow.pkgNode.childPackages(true).map(n->new Row(n, false)).collect(Collectors.toList());
    rows.addAll(index, list);      
  }
  
  /** 全descenフラグをOFFする */
  @SuppressWarnings("restriction")
  private void clear(FxButton button) {
    descendSet.clear();
    for (int i = 0; i < rows.size(); i++) {
      rows.get(i).descend.set(false);
    }
  }

  /** 表示行 */
  public class Row {
    /** パッケージノード */
    public final PkgNode pkgNode;
    
    /** descendフラグ */
    public final SimpleBooleanProperty descend;
    
    Row(PkgNode pkgNode, boolean on) {
      this.pkgNode = pkgNode;
      descend = new SimpleBooleanProperty(false);      
      descend.set(on);
      descend.addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          //ystem.out.println("changed " + oldValue + "," + newValue);
          setOn(Row.this, newValue);
        }  
      });
    }
    
    @Override
    public String toString() {
      return pkgNode.getPath() + "," + descend.get();
    }
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
