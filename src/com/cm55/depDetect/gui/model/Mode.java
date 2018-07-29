package com.cm55.depDetect.gui.model;

import java.util.*;
import java.util.stream.*;

public enum Mode {
  SRC("ソース(.java)"),
  BIN("バイナリ(.class)");
  public final String desc;
  private Mode(String desc) {
    this.desc = desc;
  }
  public static final String[]descs
    = Arrays.stream(values()).map(m->m.desc).collect(Collectors.toList()).toArray(new String[0]);
  
}
