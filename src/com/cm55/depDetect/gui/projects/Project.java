package com.cm55.depDetect.gui.projects;

import java.util.*;
import java.util.stream.*;

import com.cm55.miniSerial.*;

/**
 * プロジェクトデータ
 * @author ysugimura
 */
@Serialized(key=3236454494238683284L)
public class Project {

  String name = "";
  List<String>sourcePaths = new ArrayList<>();
  
  public Project() {    
  }
  
  public Project(String name, String...paths) {
    this.name = name;
    sourcePaths = new ArrayList<>(Arrays.asList(paths));
  }
  
  public Project(String name, List<String>paths) {
    this.name = name;
    sourcePaths = new ArrayList<>(paths);
  }
  
  public Stream<String>sourcePaths() {
    return sourcePaths.stream();
  }

  /** デバッグ用文字列化 */
  @Override
  public String toString() {
    return name + ":" + sourcePaths.stream().collect(Collectors.joining(","));
  }
}
