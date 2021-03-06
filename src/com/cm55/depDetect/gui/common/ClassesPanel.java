package com.cm55.depDetect.gui.common;

import java.util.function.*;
import java.util.stream.*;

import com.cm55.depDetect.*;
import com.cm55.depDetect.gui.i18n.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

/**
 * クラス一覧を表示スルパネル
 * @author ysugimura
 */
public class ClassesPanel implements FxNode {

  FxTable<ClsNode>table;
  FxObservableList<ClsNode>rows;
  Consumer<ClsNode>selectionCallback;
  @Inject private ShowClassDetail showClassDetail;
  
  @Inject
  @SuppressWarnings("restriction")
  public ClassesPanel(Msg msg) {
    table = new FxTable<ClsNode>();
    table.setColumns(
      new FxTable.ButtonColumn<ClsNode>(msg.get(Msg.詳細),  msg.get(Msg.詳細), this::showDetail).setAlign(FxAlign.CENTER).setPrefWidth(50),
      new FxTable.TextColumn<ClsNode>(msg.get(Msg.クラス), t->FixedValue.w(t.getPath())).setPrefWidth(400)
    );
    rows = table.getRows();
    table.getSelectionModel().listenSelection(e-> {
      ClsNode node = null;
      if (e.value >= 0) node = rows.get(e.value);
      if (selectionCallback != null) selectionCallback.accept(node);
    });
  }
  
  @SuppressWarnings("restriction")
  private void showDetail(int index) {
    showClassDetail.show(table, rows.get(index));  
  }
  
  /** クラスが選択されたときにコールバックする */
  public ClassesPanel setSelectionCallback(Consumer<ClsNode>callback) {
    this.selectionCallback = callback;
    return this;
  }

  /** 行をクリア */
  public void clearRows() {
    rows.clear();
  }
  
  /** 行を設定 */
  public void setRows(Stream<ClsNode>stream) {
    rows.clear();
    rows.addAll(stream.collect(Collectors.toList()));
  }
  
  /** ノード取得 */
  public javafx.scene.Node node() {
    return table.node();
  }
}

