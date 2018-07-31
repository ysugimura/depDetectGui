package com.cm55.depDetect.gui.i18n;


/** サポートする言語種類 */
public enum Lang {
  JA("Japanese"),
  EN("English");
  public final String desc;
  private Lang(String desc) {
    this.desc = desc;
  }
}