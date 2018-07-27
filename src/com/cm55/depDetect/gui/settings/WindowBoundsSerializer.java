package com.cm55.depDetect.gui.settings;

import com.cm55.fx.winBounds.*;

/**
 * {@link WindowBounds}のセーブとロードを行う。
 * @author ysugimura
 *
 * @param <T>
 */
public class WindowBoundsSerializer<T extends WindowBounds> 
  extends H2Serializer<T> implements WindowBoundsPersister.LoadSave<T> {

  private Class<T>targetClass;
  
  public WindowBoundsSerializer(Class<T>clazz) {
    super(clazz);
    targetClass = clazz;
  }

  public Class<T>targetClass() {
    return targetClass;
  }

  @Override
  public T load() {
    return this.mayGet();
  }

  @Override
  public void save(T value) {
    this.put(value);    
  }

}
