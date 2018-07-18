package com.cm55.depDetect.gui;

import com.cm55.depDetect.*;
import com.google.inject.*;

/**
 * 循環依存パネル
 * @author ysugimura
 */
public class CyclicsPanel extends RefsPanel {  
  @Inject
  public CyclicsPanel(GuiEvent guiEvent) {
    super(guiEvent, "循環依存一覧");
  }
  protected Refs getRefs(PkgNode node, boolean descend) {
    return node.getCyclics(descend);
  }
}
