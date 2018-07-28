package com.cm55.depDetect.gui.model;

import java.util.*;
import java.util.stream.*;

import com.cm55.miniSerial.*;

/**
 * プロジェクトデータ
 * @author ysugimura
 */
@Serialized(key=3236454494238683284L)
public class Project {

  /** プロジェクトの名称 */
  public String name = "";
  
  /** プロジェクトの複数のソースパス。.javaソースツリーフォルダ  */
  public List<String>sourcePaths = new ArrayList<>();

  /** 枝刈されたパッケージ名称集合 */
  private Set<String>prunedPkgs;
  
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
  
  /** ソースフォルダのパスを取得する */
  public Stream<String>sourcePaths() {
    return sourcePaths.stream();
  }
  
  /** 枝刈りパッケージ集合を取得する */
  public Set<String>getPrunedPkgs() {
    if (prunedPkgs != null) return prunedPkgs;
    return new HashSet<>();
  }
  
  /** 枝刈りパッケージ集合を設定する */
  public void setPrunedPkgs(Stream<String>stream) {
    prunedPkgs = new HashSet<>(stream.collect(Collectors.toSet()));
  }

  /** デバッグ用文字列化 */
  @Override
  public String toString() {
    return name + ":" + sourcePaths.stream().collect(Collectors.joining(","));
  }
}
