package com.cm55.depDetect.gui.descend;

import java.util.*;

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
    titledBorder = new FxTitledBorder("Includes descends", new FxJustBox(borderPane));
  }

  /** 
   * プロジェクト変更時。表示をリセットする
   * @param e
   */
  void projectChanged(ModelEvent.ProjectChanged e) {
    descendSet = e.descendSet;
    List<Row>list = new ArrayList<>();
    e.root.visitPackages(VisitOrder.PRE, pkgNode->{
      list.add(new Row(pkgNode, e.descendSet.contains(pkgNode)));
    });
    rows.clear();    
    rows.addAll(list);
  }

  /** ある行のdescendフラグをON/OFFする */
  @SuppressWarnings("restriction")
  private void setOn(PkgNode pkgNode, boolean on) {
    descendSet.setOn(pkgNode, on);   
    
    if (!on) return;
    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.get(i);
      if (!row.descend.get()) continue;
      if (!descendSet.contains(row.pkgNode))
        row.descend.set(false);
    }        
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
          setOn(pkgNode, newValue);
        }  
      });
    }
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
