package com.cm55.depDetect.gui.cyclic;

import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;

public class CyclicPanels implements FxNode{

  FxSplitPane.Hor splitPane;
  
  @Inject
  public CyclicPanels(
      FromPkgsPanel fromPkgsPanel,
      ToPkgsPanel toPkgsPanel,
      FromClassesPanel fromClassesPanel,
      ToClassesPanel toClassesPanel
  ) {
    FxSplitPane.Ver leftPanel = new FxSplitPane.Ver(
      fromPkgsPanel,
      fromClassesPanel
    );
    FxSplitPane.Ver rightPanel = new FxSplitPane.Ver(
      toPkgsPanel,
      toClassesPanel
    );
    splitPane = new FxSplitPane.Hor(leftPanel, rightPanel);

  }

  public Node node() {
    return splitPane.node();
  }
}
