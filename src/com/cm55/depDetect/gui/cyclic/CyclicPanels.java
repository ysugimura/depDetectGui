package com.cm55.depDetect.gui.cyclic;

import com.cm55.fx.*;
import com.google.inject.*;

import javafx.scene.*;

public class CyclicPanels implements FxNode{

  FxTitledBorder titledBorder;
  
  @Inject
  public CyclicPanels(
      AllCyclicsPanel allCyclicsPanel,
      AllCyclicToPanel allCyclicToPanel,
      FromClassesPanel fromClassesPanel,
      ToClassesPanel toClassesPanel
  ) {
    FxSplitPane.Ver leftPanel = new FxSplitPane.Ver(
      allCyclicsPanel,
      allCyclicToPanel
    );
    FxSplitPane.Ver rightPanel = new FxSplitPane.Ver(
      fromClassesPanel,
      toClassesPanel
    );
    FxSplitPane.Hor hor = new FxSplitPane.Hor(leftPanel, rightPanel);
    titledBorder = new FxTitledBorder("Cyclics detection", hor);
  }

  public Node node() {
    return titledBorder.node();
  }
}
