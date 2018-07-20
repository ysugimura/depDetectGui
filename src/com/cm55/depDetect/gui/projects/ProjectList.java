package com.cm55.depDetect.gui.projects;

import java.util.*;
import java.util.stream.*;

import com.cm55.depDetect.gui.settings.*;
import com.cm55.miniSerial.*;

/**
 * プロジェクトリスト
 * @author ysugimura
 */
@Serialized(key=5509783832435459038L)
public class ProjectList {

  private List<Project>list = new ArrayList<>();
  
  public ProjectList() {
  }
  
  public ProjectList(List<Project>list) {
    setList(list);
  }
  
  public void setList(List<Project>list) {
    this.list = new ArrayList<Project>(list);
  }
  
  public Stream<Project>stream() {
    return list.stream();
  }

  public static class Serializer extends H2Serializer<ProjectList> {
    public Serializer() {
      super(ProjectList.class);
    }    
  }
  
  /** 文字列化。デバッグ用 */
  @Override
  public String toString() {
    return stream().map(p->p.toString()).collect(Collectors.joining("\n"));
  }

}
