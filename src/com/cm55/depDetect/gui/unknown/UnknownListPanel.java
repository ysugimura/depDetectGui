package com.cm55.depDetect.gui.unknown;

import java.util.stream.*;

import com.cm55.depDetect.gui.i18n.*;
import com.cm55.fx.*;
import com.cm55.fx.FxTable.*;
import com.google.inject.*;

public class UnknownListPanel implements FxNode {

  FxTitledBorder titledBorder;
  FxTable<String>table;
  FxObservableList<String>rows;

  @Inject
  public UnknownListPanel(UnknownModel model, Msg msg) {
    table = new FxTable<String>();
    table.setColumns(new FxTable.TextColumn<String>(msg.get(Msg.パッケージ), t->FixedValue.w(t)).setPrefWidth(400));
    rows = table.getRows();
    titledBorder = new FxTitledBorder(msg.get(Msg.外部パッケージ), new FxJustBox(table));
    model.bus.listen(UnknownModel.FocusPkgEvent.class, this::focusPkgSelection);
  }
  
  void focusPkgSelection(UnknownModel.FocusPkgEvent e) {
    rows.clear();
    if (e.isEmpty()) return;      
    rows.setAll(e.focusPkg.getUnknowns(e.focusPruned).stream().collect(Collectors.toList()));    
  }
  
  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}
