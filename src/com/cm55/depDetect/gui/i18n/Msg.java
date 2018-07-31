package com.cm55.depDetect.gui.i18n;


import java.util.*;
import java.util.regex.*;

import com.google.inject.*;

/** メッセージ */
@Singleton
public class Msg {

  private Lang lang = Lang.JA;

  /** 言語種類を変更する */
  public void setLang(Lang lang) {
    this.lang = lang;
  }
  
  /** 
   * ロケールを取得して、メッセージ言語を変更。
   * 現在は日本語と英語だけ。
   */
  public void ensureLocale() {
    Locale locale = Locale.getDefault();
    if (locale == Locale.JAPAN) {
      setLang(Lang.JA);
    } else {
      setLang(Lang.EN);
    }
  }

  /** 置換パターン */
  public static final Pattern ARG_PATTERN = Pattern.compile("\\{\\d+\\}");

  /** 現在の言語設定でのメッセージを引数無しで取得する */
  public String get(Key key) {
    return getAbstract(key);
  }
  
  /** 現在の言語設定でのメッセージを引数１個で取得する */
  public String get(Key1 key, Object arg) {
    return getAbstract(key, arg);
  }

  /** 現在の言語設定でのメッセージを引数２個で取得する */
  public String get(Key2 key, Object a, Object b) {
    return getAbstract(key, a, b);
  }

  /** 現在の言語設定のメッセージを任意個数の引数で取得する */
  public String getAbstract(AbstractKey key, Object...args) {
    String msg = key.msg[lang.ordinal()];
    return replaceArgs(msg, args);
  }
  
  /** メッセージ中の引数を置換した文字列を取得する */
  protected String replaceArgs(String msg, Object...args) {
    StringBuilder result = new StringBuilder();
    Matcher matcher = ARG_PATTERN.matcher(msg);      
    int searchStart = 0;
    while (matcher.find(searchStart)) {
      int foundStart = matcher.start();
      int foundEnd = matcher.end();
      if (searchStart < foundStart) {        
        result.append(msg.substring(searchStart, foundStart));
      }      
      int index = Integer.parseInt(msg.substring(foundStart + 1, foundEnd - 1));
      result.append(args[index]);     
      searchStart = foundEnd;
    }
    result.append(msg.substring(searchStart));
    return result.toString();     
  }  
  
  /** メッセージキー */
  public static class AbstractKey {
    
    /** 外部翻訳ファイルを使用する場合のキー */
    public final String key;
    
    /** {@link Lang}のordinal()順での現在のサポートする言語別メッセージ */
    public final String[]msg;
    
    /** キー、日本語、英語を指定する */
    protected AbstractKey(String key, String ja, String en) {
      this.key = key;
      this.msg = new String[] { ja, en };     
    }
  }
  
  public static class Key extends AbstractKey {
    private Key(String key, String ja, String en) {
      super(key, ja, en);
    }
  }
  
  public static class Key1 extends AbstractKey {
    private Key1(String key, String ja, String en) {
      super(key, ja, en);
    }
  }
  public static class Key2 extends AbstractKey {
    private Key2(String key, String ja, String en) {
      super(key, ja, en);
    }
  }

  public static final Key データベースエラー = new Key(
    "DatabaseError", 
    "データベース読み込み中に以下のエラーを検出しました",
    "While reading database, the following error detected");
  
  public static final Key ブラウザをオープンできません = new Key(
    "CouldNotOpenBrowser",
    "ブラウザをオープンできません",
    "Could not open browser"
   );
  
  public static final Key プロジェクトがありません = new Key(
    "NoCurrentProject",
    "現在のプロジェクトがありません",
    "No Current Project openning"
  );
  
  public static final Key ロード中お待ち下さい = new Key(
    "LoadingWait",
    "ロード中。お待ちください",
    "Now loading, wait for a while"
  );
  
  public static final Key エラー = new Key(
    "Error",
    "エラー：",
    "Error:" 
  );
  
  public static final Key パッケージ = new Key(
    "Package",
    "パッケージ",
    "Package"
  );
  
  public static final Key 外部パッケージ = new Key(
    "ExternalPackage",
    "外部パッケージ",
    "External Package"
  );
  
  public static final Key 新規 = new Key(
    "New",
    "新規",
    "New"
  );
  
  public static final Key 編集 = new Key(
    "Edit",
    "編集",
    "Edit"
  );
  
  public static final Key 削除 = new Key(
    "Delete",
    "削除",
    "Delete"
  );
  
  public static final Key モード = new Key(
    "Mode",
    "モード",
    "Mode"
  );
  
  public static final Key プロジェクト名 = new Key(
    "ProjectName",
    "プロジェクト名",
    "Project Name"
  );
  
  public static final Key パス数 = new Key(
    "PathCount",
    "パス数",
    "Path Count"
  );
  
  public static final Key このプロジェクトを削除します = new Key(
    "DeletingThisProject",
    "このプロジェクトを削除します",
    "Deleting this project"
  );
  
  public static final Key 循環依存 = new Key(
    "CyclicDeps",
    "循環依存",
    "Cyclic Deps"
  );
  
  public static final Key 依存先元 = new Key(
    "DepsToFrom",
    "依存先・元",
    "Deps-To/From"
   );
  
  public static final Key 外部参照 = new Key(
    "ExternalRefs",
    "外部参照",
    "External Refs"
  );
  
  public static final Key 依存先パッケージ = new Key(
    "DepsToPackages",
    "依存先パッケージ",
    "Deps-To packages"
  );
  
  public static final Key 依存ソースクラス = new Key(
    "DepsToSourceClass",
    "依存ソースクラス",
    "Deps-To source classes"
  );
  
  public static final Key 詳細 = new Key(
    "Detail",
    "詳細",
    "Detail"
  );
  
  public static final Key 被依存先パッケージ = new Key(
    "DepsFromPackages",
    "被依存先パッケージ",
    "Deps-From packages"
  );
  
  public static final Key 循環依存先パッケージ一覧 = new Key(
    "CyclicDepsToPackages",
    "循環依存先パッケージ一覧",
    "Cyclic deps-to packages"
  );
  
  public static final Key 循環依存パッケージ一覧 = new Key(
    "CyclicDepsPackages",
    "循環依存パッケージ一覧",
    "Cyclic deps packages"
  );
  
  
  public static final Key 参照先クラス = new Key(
    "RefsToClasses",
    "参照先クラス",
    "Refs-To classes"
  );
  
  public static final Key 参照元クラス = new Key(
    "RefsFromClasses",
    "参照元クラス",
    "Refs-From classes"
  );
  
  public static final Key テキスト = new Key(
      "Text",
      "テキスト",
      "Text"
  );
  
  public static final Key 見つかりません = new Key(
    "NotFound",
    "見つかりません",
    "Not found"
  );
  
  public static final Key クラス = new Key(
    "Classes",
    "クラス",
    "Classes"
  );
  
  public static final Key 刈 = new Key(
    "Pruned",
    "刈",
    "Pruned"
  );
  
  public static final Key 全パッケージ = new Key(
    "AllPackages",
    "全パッケージ",
    "All packages"
  );
  
  public static final Key HelpURL = new Key(
    "HelpURL",
    "http://www.gwtcenter.com/depDetectGui",
    "http://www.gwtcenter.com/depDetectGui-en"
  );
}

