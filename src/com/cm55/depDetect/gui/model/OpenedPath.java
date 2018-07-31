package com.cm55.depDetect.gui.model;

import com.cm55.depDetect.gui.settings.*;
import com.cm55.miniSerial.*;

/**
 * 最後にオープンされたパスを覚えておく。
 * @author ysugimura
 */
@Serialized(key=7329658451255252584L)
public class OpenedPath {
  
  private String path;
  
  public OpenedPath() {}
  public OpenedPath(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return path;
  }
  
  public static class Serializer extends H2Serializer<OpenedPath> {
    public Serializer() {
      super(OpenedPath.class);
    }    
  }
}