package com.cm55.depDetect.gui.settings;

import com.cm55.miniSerial.*;

public class H2Serializer<T> extends DataSerializer<T> {

  public H2Serializer(Class<T>clazz) {
    super(H2Data.getInstance(), clazz);
  }

}
