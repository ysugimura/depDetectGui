package com.cm55.depDetect.gui;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;

public class ClassesPanel implements FxNode {

  FxTable<ClsNode>table;
  FxObservableList<ClsNode>rows;
  Consumer<ClsNode>selectionCallback;
  
  @SuppressWarnings("restriction")
  public ClassesPanel() {
    table = new FxTable<ClsNode>();
    table.setColumns(new FxTable.TextColumn<ClsNode>("クラス", t->FixedValue.w(t.getPath())).setPrefWidth(600));
    rows = table.getRows();
    table.getSelectionModel().listenSelection(e-> {
      ClsNode node = null;
      if (e.value >= 0) node = rows.get(e.value);
      if (selectionCallback != null) selectionCallback.accept(node);
    });
  }
  
  public ClassesPanel setSelectionCallback(Consumer<ClsNode>callback) {
    this.selectionCallback = callback;
    return this;
  }
  
  public void clearRows() {
    rows.clear();
  }
  
  public void setRows(Stream<ClsNode>stream) {
    setRows(stream.collect(Collectors.toList()));
  }
  
  public void setRows(Collection<ClsNode>list) {
      rows.clear();
      rows.addAll(list);
  }
  
  public javafx.scene.Node node() {
    return table.node();
  }
}

