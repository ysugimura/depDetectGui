package com.cm55.depDetect.gui.depends;

import com.cm55.depDetect.gui.common.*;
import com.cm55.depDetect.gui.model.*;
import com.google.inject.*;

/**
 * 依存先パッケージ一覧
 * @author ysugimura
 */
public class DepsToPkgPanel {

  PackagesPanel packagesPanel;

  @Inject
  public DepsToPkgPanel(Model model) {    
    packagesPanel = new PackagesPanel();
    model.listen(ModelEvent.ProjectChanged.class, this::modelChanged);
  }
  
  void modelChanged(ModelEvent.ProjectChanged e) {
    
  }

}
