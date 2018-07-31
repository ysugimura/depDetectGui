package com.cm55.depDetect.gui.model;

import com.cm55.depDetect.gui.settings.*;
import com.cm55.miniSerial.*;

/**
 * jdepsのパス設定
 * @author ysugimura
 */
@Serialized(key=8521479632525252584L)
public class JDepsPath {
  
  /** jdepsのパス。nullの場合はjdepsのみが指定され、その場合はPATHに存在しなければならない */
  public String path;
  
  public JDepsPath() {    
  }
  
  public JDepsPath(String path) {
    this.path = path;
  }
  
  public JDepsPath duplicate() {
    return new JDepsPath(path);
  }
  
  @Override
  public String toString() {
    return "jdepsPath:" + path;
  }
  
  public static class Serializer extends H2Serializer<JDepsPath> {
    public Serializer() {
      super(JDepsPath.class);
    }    
  }
}
