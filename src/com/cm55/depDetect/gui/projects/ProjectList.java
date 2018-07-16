package com.cm55.depDetect.gui.projects;

import java.util.*;

import com.cm55.depDetect.gui.settings.*;
import com.cm55.miniSerial.*;

/**
 * プロジェクトリスト
 * @author ysugimura
 */
@Serialized(key=5509783832435459038L)
public class ProjectList {

  List<Project>list = new ArrayList<>();
  
  public ProjectList() {
  }

  
  public static void main(String[]args) {
    Random r = new Random();
    for (int i = 0; i < 10; i++) {
      System.out.println("" + r.nextLong());
    }
    /*
     
3236454494238683284
-6650581244027825805
6834922208167482827
-1872413569820196254
2513142317446635469
-5047439190659496360
6578217862138482125
-3672305739862928943
979205695659151291

     */
  }
}
