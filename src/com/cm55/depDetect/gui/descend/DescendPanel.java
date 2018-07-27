package com.cm55.depDetect.gui.descend;

import java.util.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.cm55.fx.FxBorderPane.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

import javafx.beans.property.*;
import javafx.beans.value.*;

public class DescendPanel implements FxNode  {
  
  FxTitledBorder titledBorder;
  FxTable<Row>table;
  FxObservableList<Row>rows;
  DescendSet descendSet;

  public class Row {
    public final PkgNode pkgNode;
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
  
  @Inject
  public DescendPanel(Model model) {    
    table = new FxTable<Row>();
    table.setColumns(
      new FxTable.CheckColumn<Row>("descend",  r->r.descend).setAlign(FxAlign.CENTER).setPrefWidth(50),
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

  /** プロジェクト変更時 */
  void projectChanged(ModelEvent.ProjectChanged e) {
    descendSet = e.descendSet;
    List<Row>list = new ArrayList<>();
    e.root.visitPackages(VisitOrder.PRE, pkgNode->{
      list.add(new Row(pkgNode, e.descendSet.contains(pkgNode)));
    });
    rows.clear();    
    rows.addAll(list);
  }
  
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
  
  private void clear(FxButton button) {
    descendSet.clear();
    for (int i = 0; i < rows.size(); i++) {
      rows.get(i).descend.set(false);
    }
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
