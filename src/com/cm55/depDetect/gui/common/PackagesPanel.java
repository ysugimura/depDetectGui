package com.cm55.depDetect.gui.common;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.i18n.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

/**
 * パッケージ一覧を表示するパネル
 * @author ysugimura
 */
public class PackagesPanel implements FxNode {

  FxTable<PkgNode>table;
  FxObservableList<PkgNode>rows;
  Consumer<PkgNode>selectionCallback;
  
  @Inject
  @SuppressWarnings("restriction")
  public PackagesPanel(Msg msg) {
    table = new FxTable<PkgNode>();
    table.setColumns(new FxTable.TextColumn<PkgNode>(msg.get(Msg.パッケージ), t->FixedValue.w(t.getPath())).setPrefWidth(400));
    rows = table.getRows();
    table.getSelectionModel().listenSelection(e-> {
      PkgNode node = null;
      if (e.value >= 0) node = rows.get(e.value);
      if (selectionCallback != null) selectionCallback.accept(node);
    });
  }
  
  public PackagesPanel setSelectionCallback(Consumer<PkgNode>callback) {
    this.selectionCallback = callback;
    return this;
  }

  public FxTable<PkgNode>getTable() {
    return table;
  }
  
  public FxObservableList<PkgNode>getRows() {
    return rows;
  }
  
  public void clearRows() {
    rows.clear();
  }
  
  public void setRows(Stream<PkgNode>stream) {
    setRows(stream.collect(Collectors.toList()));
  }
  
  public void setRows(Collection<PkgNode>list) {
      rows.clear();
      rows.addAll(list);
  }
  
  public javafx.scene.Node node() {
    return table.node();
  }
}
