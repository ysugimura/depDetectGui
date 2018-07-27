package com.cm55.depDetect.gui.depends;

import java.util.stream.*;

import com.cm55.depDetect.gui.common.*;
import com.cm55.depDetect.gui.model.*;
import com.cm55.fx.*;
import com.google.inject.*;

/**
 * 枝刈り済パッケージパネル
 * @author ysugimura
 */
public class PrunedPkgPanel implements FxNode {

  FxTitledBorder titledBorder;
  PackagesPanel packagesPanel;
  @Inject private DependModel dependModel;
  
  @Inject
  public PrunedPkgPanel(Model model) {
    packagesPanel = new PackagesPanel();
    packagesPanel.setSelectionCallback(node->dependModel.setFocusPkg(node));
    titledBorder = new FxTitledBorder("対象パッケージ", new FxJustBox(packagesPanel));
    model.listen(ModelEvent.ProjectChanged.class,  this::projectChanged);    
  }
  
  void projectChanged(ModelEvent.ProjectChanged e) {
    packagesPanel.setRows(e.descendSet.getEffectivePackages(e.root).collect(Collectors.toList()));
  }

  public javafx.scene.Node node() {
    return titledBorder.node();
  }
}