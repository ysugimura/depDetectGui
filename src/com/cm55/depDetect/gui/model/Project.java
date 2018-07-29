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

  private int mode;
    
  /** 枝刈されたパッケージ名称集合 */
  private Set<String>prunedPkgs;
  
  public Project() {    
  }

  public Mode getMode() {
    return Mode.values()[mode];
  }
  
  public void setMode(Mode mode) {
    this.mode = mode.ordinal();
  }
  
  public Project(Mode mode, String name, String...paths) {
    this.mode = mode.ordinal();
    this.name = name;
    sourcePaths = new ArrayList<>(Arrays.asList(paths));
  }
  
  public Project(Mode mode, String name, List<String>paths) {
    this.mode = mode.ordinal();
    this.name = name;
    sourcePaths = new ArrayList<>(paths);
  }
  
  /** ソースフォルダのパスを取得する */
  public Stream<String>sourcePaths() {
    return sourcePaths.stream();
  }
  
  /** 枝刈りパッケージ集合を取得する */
  public Stream<String>getPrunedPkgs() {
    //ystem.out.println("getPrunedPkgs " + toString());
    if (prunedPkgs != null)
      return prunedPkgs.stream();
    else 
      return new HashSet<String>().stream();
  }
  
  /** 枝刈りパッケージ集合を設定する */
  public void setPrunedPkgs(Stream<String>stream) {
    prunedPkgs = new HashSet<>(stream.collect(Collectors.toSet()));
    //ystem.out.println(prunedPkgs.size() + "setPrunedPkgs " + toString());
  }

  /** デバッグ用文字列化 */
  @Override
  public String toString() {
    String msg =  name + ":" + 
        sourcePaths.stream().collect(Collectors.joining(",")) + ":";
    if (prunedPkgs != null)
        prunedPkgs.stream().collect(Collectors.joining(","));
    return msg;
  }
}
