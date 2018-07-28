package com.cm55.depDetect.gui.allPkgs;

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
 * 対象とするすべてのパッケージを表示し、枝刈りフラグをON/OFFする。
 * 選択されたパッケージを{@link Model}側に通知する。
 * @author ysugimura
 */
public class AllPkgsPanel implements FxNode  {
  
  FxTitledBorder titledBorder;
  FxTable<Row>table;
  FxObservableList<Row>rows;
  PrunedPkgs prunedPkgs;
  @Inject private Model model;
  
  @SuppressWarnings("restriction")
  @Inject
  public AllPkgsPanel(Model model) {    
    table = new FxTable<Row>();
    table.setColumns(
      new FxTable.CheckColumn<Row>("刈",  r->r.pruned).setAlign(FxAlign.CENTER).setPrefWidth(30),
      new FxTable.TextColumn<Row>("パッケージ", r->FixedValue.w(r.pkgNode.getPath())).setPrefWidth(400)
    );    
    rows = table.getRows();
    table.getSelectionModel().listenSelection(e-> {
      if (e.value >= 0) 
        rowSelection(rows.get(e.value));
      else 
        rowSelection(null);
    });
    model.bus.listen(ModelEvent.ProjectChanged.class, this::projectChanged);
    FxBorderPane.Ver borderPane = new FxBorderPane.Ver(
      null,
      table,
      new FxButton("clear", this::clear)
    );
    titledBorder = new FxTitledBorder("全パッケージ", new FxJustBox(borderPane));
  }

  /** 行選択時 */
  private void rowSelection(Row row) {
    model.setFocusPkg(row != null? row.pkgNode:null);
  }
  
  private boolean selfEvent = false;
  
  /** 
   * プロジェクト変更時。表示をリセットする
   * @param e
   */
  void projectChanged(ModelEvent.ProjectChanged e) {
    if (selfEvent) return;
    
    prunedPkgs = e.prunedPkgs;
    
    // 行として表示するパッケージを決定するが、その際、
    // 1. 枝刈りされているパッケージによって隠されているものは表示しない
    // 2.　枝刈りされているパッケージは、そのフラグをONにする。
    List<Row>list = new ArrayList<>();
    e.root.visitPackages(VisitOrder.PRE, pkgNode->{
      if (prunedPkgs.isHidden(pkgNode)) return;
      list.add(new Row(pkgNode, prunedPkgs.contains(pkgNode)));
    });
    rows.clear();    
    rows.addAll(list);
  }

  /** 
   * ある行の枝刈りフラウをON/OFFする
   * ONの場合、そのパッケージ以下の行を削除する。
   * OFFの場合、そのパッケージ以下の行を復活する。
   * @param targetRow
   * @param on
   */
  @SuppressWarnings("restriction")
  private void setOn(Row targetRow, boolean on) {
    
    // prunedPkgsを操作するとイベントが発生してしまう。
    selfEvent = true;
    try {
      if (!prunedPkgs.setOn(targetRow.pkgNode, on)) return;
    } finally {
      selfEvent = false;
    }

    int targetIndex = rows.indexOf(targetRow);
    if (targetIndex < 0) {
      // ありえない!!!
      return;
    }
    int nextIndex = targetIndex + 1;
    
    // ONの場合、このパッケージ以下の行を削除する
    if (on) {
      Set<PkgNode>set = targetRow.pkgNode.childPackages(true).collect(Collectors.toSet());
      while (nextIndex < rows.size()) {
        Row r = rows.get(nextIndex);
        if (set.contains(r.pkgNode)) rows.remove(nextIndex); 
        else break;
      }
      table.getSelectionModel().select(targetIndex);
      model.setFocusPkg(targetRow.pkgNode);
      return;
    }
    
    // OFFの場合、このパッケージ以下の行を復活する
    List<Row>list = targetRow.pkgNode.childPackages(true).map(n->new Row(n, false)).collect(Collectors.toList());
    rows.addAll(nextIndex, list);
    table.getSelectionModel().select(targetIndex);
    model.setFocusPkg(targetRow.pkgNode);
  }
  
  /** 全descenフラグをOFFする */
  @SuppressWarnings("restriction")
  private void clear(FxButton button) {
    prunedPkgs.clear();
    for (int i = 0; i < rows.size(); i++) {
      rows.get(i).pruned.set(false);
    }
  }

  /** 表示行 */
  public class Row {
    /** パッケージノード */
    public final PkgNode pkgNode;
    
    /** prunedフラグ */
    public final SimpleBooleanProperty pruned;
    
    Row(PkgNode pkgNode, boolean on) {
      this.pkgNode = pkgNode;
      pruned = new SimpleBooleanProperty(false);      
      pruned.set(on);
      pruned.addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          //ystem.out.println("changed " + oldValue + "," + newValue);
          setOn(Row.this, newValue);
        }  
      });
    }
    
    @Override
    public String toString() {
      return pkgNode.getPath() + "," + pruned.get();
    }
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
