package com.cm55.depDetect.gui.unknown;

import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;

public class UnknownPanels implements FxNode {

  FxSplitPane.Hor splitPane;
  
  @Inject
  public UnknownPanels(
    PrunedPkgPanel prunedPkgPanel,
    UnknownListPanel unknownListPanel  
  ) {
    splitPane = new FxSplitPane.Hor(
      prunedPkgPanel,
      unknownListPanel
    );
  }

  public Node node() {
    return splitPane.node();
  }
}
