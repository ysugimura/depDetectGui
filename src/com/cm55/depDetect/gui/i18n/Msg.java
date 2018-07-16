package com.cm55.depDetect.gui.i18n;


import java.util.regex.*;

import com.google.inject.*;

@Singleton
public class Msg {

  public static enum Lang {
    JA("Japanese"),
    EN("English");
    public final String desc;
    private Lang(String desc) {
      this.desc = desc;
    }
  }
  
  private Lang lang = Lang.JA;
  
  public void setLang(Lang lang) {
    this.lang = lang;
  }
  
  public static class AbstractKey {
    public final String key;
    public final String[]msg;
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
  
  public static final Key お待ちください = new Key(
      "PleaseWait",
      "お待ちください",
      "Please wait"
  );
  
  public static final Key クロームドライバ = new Key(
      "ChromeDriver",
      "chromedriver",
      "chromedriver"
  );
  
  
  public static final Key エンコーディング指定の必要なケースはまれ = new Key(
      "SpecifyingEncodingIsRare",
      "現代ではエンコーディング指定の必要なケースは、まれです（デフォルトUTF-8)",      
      "Specifying encoding is very nowadays (default UTF-8)"
  );
  
  public static final Key 秒1あたり最大文字数 = new Key(
      "CharsPerSecond",
      "1秒あたり最大文字数",
      "Chars per second"
  );
  
  public static final Key 出力ファイル名 = new Key(
     "OutputFileNames",
     "出力ファイル名",
     "Output file name(s)"
  );
  
  
  public static final Key 変更 = new Key(
      "Change",
      "変更",
      "Change");
  
  public static final Key 字幕要素を1秒縮小 = new Key(
      "ShrinkElem1Sec",
      "字幕要素を1秒縮小",
      "Shrink Elem 1 Sec"
  );
  public static final Key 字幕要素を1秒拡大 = new Key(
      "ExpandElem1Sec",
      "字幕要素を1秒拡大",
      "Expand Elem 1 Sec"
  );
  
  public static final Key 字幕要素を1秒左に = new Key(
      "ElemBackward1Sec",
      "字幕要素を1秒左に",
      "Elem Backward 1 Sec"
  );
  public static final Key 字幕要素を1秒右に = new Key(
      "ElemFoward1Sec",
      "字幕要素を1秒右に",
      "Elem Foward 1 Sec"
  );
  public static final Key 前の字幕要素にフォーカス = new Key(
      "FocusPreviousElem",
      "前の字幕要素にフォーカス",
      "Focus Previous Elem"
  );
  public static final Key 後の字幕要素にフォーカス = new Key(
      "FocusNextElem",
      "後の字幕要素にフォーカス",
      "Focus Next Elem"
  );
  public static final Key 無し = new Key("None", "無し", "None");
  public static final Key 動画を最大化 = new Key(
      "MaximizeMovie",
      "動画を最大化",
      "Maximize Movie"
  );
  public static final Key 秒1戻る = new Key("Sec1Backword", "1秒戻る", "Backward 1 Sec");
  public static final Key 秒5戻る = new Key("Sec5Backword", "5秒戻る", "Backward 5 Sec");
  public static final Key 秒1進む = new Key("Sec1Foward", "1秒進む", "Foward 1 Sec");
  public static final Key 秒5進む = new Key("Sec5Foward", "5秒進む", "Forward 5 Sec");
  
  public static final Key1 続行するには入力 = new Key1(
      "InputToProceed",
      "続行するには {0} を入力してください",
      "Enter {0} to proceed"
  );
  
  public static final Key ライセンス未取得 = new Key(
      "LicenseNotAcquired",
      "ライセンス未取得",
      "License not acquired"
  );
  
  public static final Key ヘルプ = new Key(
      "Help",
      "ヘルプ",
      "Help"
  );
  
  public static final Key 表示言語 = new Key(
      "DisplayLanguage",
      "表示言語",
      "Display Language"
  );
  
  public static final Key 言語選択後に本ソフトを再起動してください = new Key(
      "ReinvokeAfterLangSelect",
      "言語選択後に本ソフトを再起動してください",
      "Reinvoke this software after language selection"
  );
  
  
  public static final Key1 書き込めませんでした = new Key1(
      "CouldNotWrite",
      "書き込めませんでした： {0}",
      "Could not write: {0}"
  );
  
  public static final Key 出力先フォルダ = new Key(
      "DestinationFolder",
      "出力先フォルダ",
      "Destination Folder"
  );
  
  public static final Key ベースファイル名 = new Key(
      "BaseFilename",
      "ベースファイル名",
      "Base Filename"
  );
  
  public static final Key 字幕ファイルカット = new Key(
    "SubFileCut",
    "字幕ファイルカット",
    "Subtitle File Cutting"
  );
  
  public static final Key 番号桁数 = new Key(
      "NumberDigits",
      "番号桁数",
      "Number digits"
  );
  
  public static final Key ナンバリングオフセット = new Key(
      "NumberingOffset",
      "ナンバリングオフセット",
      "Numbering offset"
  );
  
  public static final Key 書き込み先フォルダ指定 = new Key(
      "SpecifyDestinationFolder",
      "書き込み先フォルダ指定",
      "Specify Destination Folder"
  );
  
  public static final Key 出力設定 = new Key(
      "OutputSetting",
      "出力設定",
      "Output Setting"
  );
  
  
  public static final Key 停止待ちです = new Key(
      "WaitingTermination",
      "停止待ちです",
      "Waiting termination"
  );
  
  
  public static final Key 分割動画書き出し中 = new Key(
      "WritingCutMovies",
      "分割動画書き出し中",
      "Writing cut movies"
  );
  
  public static final Key カットポイントセーブファイルの指定 = new Key(
      "SpecifyCutPointsSaveFile",      
      "カットポイント設定セーブファイルの指定",
      "Specify cut points save file"
  );
  
  public static final Key2 ファイルが読めません = new Key2(
      "CouldNotReadFile",
      "ファイル {0} が読めません ... {1}",
      "Could not read file {0} ... {1}"
  );
  
  public static final Key1 MP4ファイルではありません = new Key1(
      "NotMP4File",
      "MP4ファイルではありません",
      "Not a MP4 File"
  );
  
  public static final Key1 ロード中 = new Key1(
     "Loading",
     "ロード中... {0}",
     "Loading... {0}"
  );
  
  public static final Key ライセンス設定 = new Key(
      "LicenseSetting",
      "ライセンス設定",
      "License Setting"
  );
  
  public static final Key ライセンシー = new Key(
      "Licensee",
      "ライセンシー",
      "Licensee"
  );
  
  public static final Key 以前の翻訳編集状態を再現しますか = new Key(
      "RecoverLastTranslationEditingState",
      "n以前の翻訳編集状態を再現しますか？",
      "Recover last translation editing state?"
  );
  
  public static final Key これらの変更を無視しますか = new Key(
      "IgnoreTheseChanges",
      "これらの変更を無視しますか?",
      "Ignore these changes?"
  );
  
  public static final Key 結合結果字幕ファイルが未セーブです = new Key(
      "ModifiedSubtitleFileUnsaved",
      "結合結果字幕ファイルが未セーブです。",
      "Modified destination file unsaved"
  );
  
  public static final Key1 個の未セーブのソース字幕ファイルがあります = new Key1(
      "ModifiedSubFiles",
      "{0} 個の変更済未セーブのソース字幕ファイルがあります",
      "There's unsaved {0} modified source files"

  );
  
  public static final Key ビデオトラックを取得できません = new Key(
      "CouldNotGetVideoTrack",
      "ビデオトラックを取得できません",
      "Could not get video track"
  );
  
  public static final Key1 ファイルを解析中です = new Key1(
      "AnalyzingFile",
      "ファイルを解析中です：{0}",
      "Analyzing... {0}"
  );
  
  public static final Key このファイルタイプはサポートされていません = new Key(
      "FileTypeNotSupported",
      "このファイルタイプはサポートされていません",
      "File type not supported"
  );
  
  public static final Key 上書きされます = new Key(
      "OverwriteOk",
      "上書きされます",
      "Overwrite OK?"
  );
  
  
  public static final Key 出力フォルダを指定してください = new Key(
      "SpecifyOutputFolder",
      "出力フォルダを指定してください",
      "Specify output folder"
  );
  
  public static final Key すべてのカットポイントを除去します = new Key(
      "RemoveAllCutPoints",
      "すべてのカットポイントを除去します(最初を除く)。",
      "Remove all cut points (except first one)"
  );
  public static final Key カット動画を書き出し中 = new Key(
      "WritingCutMovies",
      "カット動画を書き出し中、お待ちください",
      "Now writing cut movie files, please wait"
    );
  public static final Key 動画がありません = new Key(
      "NoMovie",
      "動画がありません",
      "There's no movie");
  
  public static final Key ライセンスが無効です = new Key(
      "LicenseInvalid",
      "ライセンスが無効です",
      "License Invalid"
  );
  
  public static final Key フリーバージョンです = new Key(
      "FreeVersionMsg",
      "フリーバージョンです。書き込み時に毎回このメッセージが表示されます。ご了承ください。",
      "Free Version .. Every time you'll see this message when you write some file."
  );
  
  public static final Key 動画ファイルの解析中です = new Key(
      "AnalyzingMovieFile",
      "動画ファイルの解析中です",
      "Analyzing movie file"
  );
  
  public static final Key この動画ファイルを除去します = new Key(
      "RemoveThisMovieFile",
      "この動画ファイルを除去します",
      "Remove this movie file from the list"
  );
  
  public static final Key すべての動画ファイルを除去します = new Key(
      "RemoveAllMovieFiles",
      "すべての動画ファイルを除去します",
      "Remove all movie files from the list。"
  );
  
  
  public static final Key 書き込めませんおそらくビデオのフォーマットが異なります = new Key(
      "CouldNotWriteVideoFormatUnmatched",
     "書き込めません。おそらくビデオのフォーマットが異なります",
      "Could not write. Maybe video formats unmatched"
  );
  
  public static final Key 結合動画書き出し中 = new Key(
      "WritingConcatMovie",
      "結合動画書き出し中",
      "Writing concatenated movie"
  );
  
  public static final Key 結合動画書き出し先指定 = new Key(
      "SpecifyConcatMovieDestination",
      "結合動画書き出し先指定",
      "Specify Destination File"
  );

  public static final Key 一時ファイルフォルダをデフォルトに戻しますか = new Key(
      "RestoreTempFolderToDefault",
      "一時ファイル・ディレクトリをデフォルトに戻しますか?",
      "Restore temporary file folder to default?"

   );
  
  public static final Key 書き込み先ファイル選択 = new Key(
      "SelectFileToSave",
      "書き込み先ファイル選択",
      "Select file to save"
  );
  
  public static final Key この字幕ファイルを除去します = new Key(
      "RemoveThisSubtitleFile",
      "この字幕ファイルを除去します。",
      "Remove this subtitle file from the list"
  );
  
  public static final Key すべての字幕ファイルを除去します = new Key(
      "RemoveAllSubtileFiles", 
      "すべての字幕ファイルを除去します",
      "Remove all subtitle files in the list"
   );
  
  public static final Key メディアがありません = new Key(
      "NoMedia",
      "メデイアがありません",
      "No media"
      );
  
  public static final Key 総時間 = new Key(
      "TotalTime",
      "総時間",
      "Total Time"
  );
  
  public static final Key 連結動画書き出し = new Key(
      "WriteConcatenatedMovie",
      "連結動画書き出し",
      "Write concatenated movie");
  
  public static final Key 以前の連結対象動画リストを再現しますか = new Key(
      "RecoverPreviousMoveListToConcat",
      "以前の連結対象動画リストを再現しますか?",
  "Recover previous movie list to concatenate?"
      );
  
  public static final Key 動画ファイル複数 = new Key(
      "MovieFiles",
      "動画ファイル",
      "Movie files"
  );
  
  
  public static final Key ここに動画ファイルmp4をドロップしてください = new Key(
      "DropMovieMp4FilesHere",
      "ここに動画ファイル(mp4)をドロップしてください。",
      "Drop movie(mp4) files here."
    );
  
  public static final Key メモ = new Key("Memo", "メモ", "Memo");
  
  public static final Key 最初のものは削除できません = new Key(
      "CantRemoveFirstOne",
      "最初のものは削除できません",
      "You can't remove the first one."
  );
  
  public static final Key 番号短 = new Key("NumberShort", "番", "no");
  public static final Key 出力短 = new Key("OutputShort", "出", "out");
  
  
  public static final Key カットポイントをクリア = new Key("ClearCutPoints", "カットポイントをクリア", "Clear cut points");
  
  public static final Key 分割動画を書き出し = new Key("WriteCutMovies", "分割動画を書き出し", "Write cut movies");
  
  public static final Key 以前のカット位置編集状態を再生しますか = new Key(
      "RecoverLastCutPointsEditingState",
      "以前のカット位置編集状態を再生しますか?",
      "Recover last cut points editing state?"
  );
  
  public static final Key1 字幕セーブファイル指定 = new Key1(
   "SpecifyFileToSaveAs",
   "{0}字幕セーブファイル指定", 
   "Specify file to save as {0}"
  );
  
  public static final Key カットポイント複数 = new Key("CutPoints", "カットポイント", "Cut Points");
  
  public static final Key 出力マップ = new Key("OutputMap", "出力マップ", "Output Map");
  
  public static final Key 未セーブの変更がありますが無視しますか = new Key("IgnoreUnsavedModifications",
      "未セーブの変更がありますが、無視しますか？",
      "There's unsaved modifications, ignore it?");
  
  public static final Key カットメモ = new Key("CutMemo", "カットメモ", "Cut Memo");
  
  
  public static final Key 字幕ファイル = new Key("SubtitleFiles", "字幕ファイル", "Subtitle Files");

  public static final Key 変更済 = new Key("Modified", "変更済", "Modified");
  public static final Key ファイル名 = new Key("FileName", "ファイル名", "File Name");
  public static final Key 開始時刻 = new Key("StartTime", "開始時刻", "Start Time");
  public static final Key 時間 = new Key("Duration", "時間", "Duration");
  
  public static final Key 波形 = new Key("Waveform", "波形", "Waveform");
  
  public static final Key ここに字幕ファイルをドロップしてください = new Key(
    "DropSubtitleFilesHere",
    "ここに字幕ファイル(sbv, srt)をドロップしてください。",
    "Drop sbv or srt files here."
  );
  public static final Key ここに編集対象の字幕ファイルをドロップしてください = new Key(
    "DropSubtitleFileToEditHere",
    "ここに編集対象の字幕ファイル(sbv, srt)をドロップしてください。",
    "Drop sbv or srt files here for edit."
  );
  public static final Key ここに参照用の字幕ファイルをドロップしてください = new Key(
    "DropSubtitleFileToReferHere",
    "ここに参照用の字幕ファイル(sbv, srt)をドロップしてください。",
    "Drop sbv or srt files here for reference."
  );
  
  public static final Key 字幕マップ = new Key("SubtitlesMap", "字幕マップ", "Subtitles Map");
  
  public static final Key ファイル形式選択 = new Key("FileFormatSelection", "ファイル形式選択", "File Format Selection");
  public static final Key 出力ファイル形式を選択 = new Key(
    "ChooseFileFormatToSave",
    "出力ファイル形式を選択",
    "Choose file format to save"
  );
  
      
  
  public static final Key 機能複数 = new Key("Functions",
    "機能",
    "Functions");  
  public static final Key 動画プレイヤー = new Key("MoviePlayer", 
    "動画プレイヤー", 
    "Movie Player");
  public static final Key 字幕エディタ = new Key("SubtitleEditor",
      "字幕エディタ",
      "Subtitle Editor");
  public static final Key 字幕結合 = new Key("SubtitleConcatenator", 
      "字幕結合",
      "Subtitle Concatenator");
  public static final Key MP4動画カッター = new Key("MP4Cutter",
      "MP4動画カッター",
      "MP4 Movie Cutter");
  public static final Key MP4動画結合 = new Key("MP4Concatenator",
      "MP4動画結合",
      "MP4 Movie Concatenator");
  public static final Key 設定 = new Key("Settings", 
      "設定",
      "Settings");
      
  public static final Key アプリ設定フォルダ = new Key("AppSettingFolder",
      "アプリ設定フォルダ（変更不可)",
      "App Setting Folder (Can't be changed)");
      
  public static final Key 一時ファイルフォルダ = new Key("TempFileFolder",
      "一時ファイルフォルダ",
      "Temporary File Folder"
   );
  
  public static final Key 字幕編集領域フォント大きさ = new Key("FontSizeOfEditorField",
      "字幕編集領域フォント大きさ",
      "Font Size of Subtitles Editor Field");
  
  public static final Key 出力テキストファイル改行コード = new Key("OutputTextFileLineSeparator",
      "出力テキストファイル改行コード",
      "Output Text File Line-Separator");
  
  public static final Key スクリーンセーバーブロック = new Key("ScreenSaverBlocking",
      "スクリーンセーバーブロック",
      "Screen Saver Blocking");
  
  public static final Key ブロックしない = new Key("NonBlocking",
      "ブロックしない",
      "Non Blocking");
  
  public static final Key 再生中はブロックする = new Key("BlockWhilePlayback",
      "再生中はブロックする",
      "Block While Playback");
  
  public static final Key 実行環境 = new Key("ExecutionEnvironment",
      "実行環境",
      "Execution Environment");
  
  
  public static final Key データベースエラー = new Key("DatabaseError", 
    "データベース読み込み中に以下のエラーを検出しました",
    "While reading database, the following error detected");

  public static final Key フォルダを表示する = new Key("ShowFolder",
      "フォルダを表示する",
      "Show Folder");
  
  public static final Key 設定変更 = new Key("ChangeSetting",
      "設定変更",
      "Change Setting");
  
  public static final Key デフォルトに戻す = new Key("RestoreDefault", 
      "デフォルトに戻す",
      "Restore Default");
  
  public static final Key フォルダを変更 = new Key("Change Folder",
      "フォルダを変更",
      "Change Folder");
  
  public static final Key 波形ファイルサイズ最大量 = new Key("TotalWaveformSizeLimitation",
      "波形ファイルサイズ最大量(Mバイト)",
      "Total Waveform File Size Limitation (M bytes)");
  
  public static final Key アプリ設定 = new Key("ApplicationSettings",
      "アプリ設定",
      "Application Settings");
  
  public static final Key 音量 = new Key("SoundVolume",
      "音量",
      "Sound Volume");
  
  public static final Key 設定短 = new Key("SettingShort", "設定", "Set");
  
  public static final Key 再生停止 = new Key("PlayPause", "再生/停止", "Play/Pause");

  public static final Key 再生速度 = new Key("PlaybackSpeed", "再生速度", "Playback Speed");
  public static final Key 再生位置 = new Key("PlayPosition", "再生位置", "Play Position");
  public static final Key タイムライン = new Key("Timeline", "タイムライン", "Timeline");
  public static final Key スピード選択肢 = new Key("SpeedOptions", "スピード選択肢", "Speed Options");
  public static final Key コンマ区切りの再生速度リストを入力 = new Key(      
    "EnterCommaSeparatedSpeedList",
    "コンマ区切りの再生速度リストを入力(20～200、100は必須)",
    "Enter comma separated speed list (20 to 200, 100 is mandatory)"
   );
  
  public static final Key コンマ区切りの時間リストを入力 = new Key(
    "EnterCommaSeparatedDurationList",
    "コンマ区切りの時間リストを入力(最小 1s から 最大 30m)",
    "Enter comma separated duration list (1s min to 30m max)"
  );
  
  public static final Key 詳細時間範囲 = new Key("PreciseRange", 
      "詳細時間範囲",
      "Precise Range");
  
  public static final Key 値が不適当ですデフォルトに戻します = new Key(
      "InvalidValuesRestoringDefaults",
      "値が不適当です、デフォルトに戻します",
      "Invalid values, restoring defaults.");
  public static final Key 以前の再生状態を復帰しますか = new Key(
      "RecoverPreviousStateOfPlayingMovie",
      "以前の再生状態を復帰しますか？",
      "Recover previous state of playing movie?"
  );
  
  public static final Key 以前の編集状態を再現しますか = new Key(
      "RecoverPreviousEditingState",
      "以前の編集状態を再現しますか?",
      "Recover previous editing state?"
  );
  
  public static final Key 検索エンジン辞書 = new Key("SearchEngineDirectionary", 
      "検索エンジン / 辞書",
      "Seach Engine / Dictionary");
  
  public static final Key 名前 = new Key("Name", "名前", "Name");
  
  public static final Key クリア = new Key("Clear", "クリア", "Clear");
  public static final Key 検索 = new Key("Search", "検索", "Search");
  public static final Key マージン秒数 = new Key("MarginSeconds", "マージン秒数", "Margin Seconds");
  
  public static final Key 全要素クリア = new Key("ClearAllElements", "全要素クリア", "Clear All Elements");
  public static final Key 参照字幕 = new Key("ReferenceSubtitles", "参照字幕", "Reference Subtitles");
  public static final Key 編集字幕 = new Key("EditingSubtitles", "編集字幕", "Editing Subtitles");
  public static final Key 参照字幕を検索 = new Key("SearchInReferenceSubtitles", "参照字幕を検索", "Search in Reference Subtitles");
  public static final Key 編集字幕を検索 = new Key("SearchInEditingSubtitles", "編集字幕を検索", "Search in Editing Subtitles");
  
  public static final Key ブラウザ選択 = new Key("BrowserSelection", "ブラウザ選択", "Browser Selection");
  public static final Key デフォルトブラウザ = new Key("DefaultBrowser", "デフォルトブラウザ", "Default Browser");
  public static final Key ChromeDriver = new Key("ChromeDriver", "Chrome Driver", "Chrome Driver");
  
  public static final Key 検索エンジン辞書指定がありません = new Key(
      "NoSearchEnginesDirectionaries",
      "検索エンジン・辞書指定がありません",
      "No search engines/directionaries"
  );
  
  public static final Key 音声データ取得中です = new Key(
      "AcquiringAudioSamples",
      "音声データ取得中です、お待ちください",
      "Acquiring audio samples, please wait"
  );
  
  public static final Key ブラウザのコントロールが得られません = new Key(
      "CouldNotGetBrowserControl",
      "ブラウザのコントロールが得られません。\nブラウザの選択を変更してください",
      "Could not get browser control.\nPlease change browser selection."
  );
  
  public static final Key ffmpegの処理に問題があります = new Key(
      "ProblemsInFFmpegProcessing",
      "ffmpegの処理に問題があります",
      "There's some problems in ffmepg processing"
  );
  
  public static final Key 検索文字列を入力しENTERを押してください = new Key(
      "EnterWordHitEnter",
      "検索文字列を入力し、ENTERを押してください。",
      "Enter a word and hit ENTER key."
  );
  
  public static final Key 空白区切りの文字列を入力しENTERを押してください = new Key(
    "EnterSpaceSeparatedWords",
    "空白区切りの文字列を入力し、ENTERを押してください",
    "Enter space separated words and hit ENTER key"
  );
  public static final Key 時刻 = new Key("Time", "時刻", "Time");
  public static final Key テキスト = new Key("Text", "テキスト", "Text");
  public static final Key 上書き = new Key("Overwrite", "上書き", "Overwrite");
  public static final Key 参照短 = new Key("ReferenceShort", "参照", "Ref");
  
  public static final Key ドラッグドロップで順序変更ができます = new Key("DragDropToOrder",
      "ドラッグドロップで順序変更ができます",
      "You can dragn' drop to change order");
  
  public static final Key 新規 = new Key("New", "新規", "New");
  public static final Key 編集 = new Key("Edit", "編集", "Edit");
  public static final Key 削除 = new Key("Delete", "削除", "Delete");
  public static final Key 名称 = new Key("Name", "名称", "Name");
  public static final Key エンコーディング = new Key("Encoding", "エンコーディング", "Encoding");
  public static final Key ウェブ検索 = new Key("WebSearch", "ウェブ検索", "Web Search");
  public static final Key エディター設定 = new Key("EditorSetting", "エディター設定", "Editor Setting");
  public static final Key ファンクションキー = new Key("FunctionKeys", "ファンクションキー", "Function Keys");
  public static final Key 要素設定 = new Key("ElementSetting", "要素設定", "Element Setting");
  public static final Key 字幕要素初期秒数 = new Key(
      "InitialSubtitleElementSeconds",
      "字幕要素初期秒数",
      "Initial Subtitle Element Seconds"
  );
  public static final Key キー = new Key("Key", "キー", "Key");
  public static final Key 機能単数 = new Key("Function", "機能", "Function");
  public static final Key デフォルトに戻します = new Key("RestoringDefaults", "デフォルトに戻します", "Restoring Defaults");
  public static final Key 名前を付けて保存 = new Key("SaveAsNew", "名前を付けて保存", "Save as New");
  public static final Key 上書き保存先 = new Key("FileToOverwrite", "上書き保存先", "File to overwrite");
  
  @Inject
  private Msg() {
    /*
    Arrays.stream(LangMsg.class.getDeclaredFields()).forEach(f-> {
      int m = f.getModifiers();
      if ((m & Modifier.STATIC) != 0) 
    });
    */
  }

  public static final Pattern ARG_PATTERN = Pattern.compile("\\{\\d+\\}");

  
  public String get(Key key) {
    return getAbstract(key);
  }
  
  public String get(Key1 key, Object arg) {
    return getAbstract(key, arg);
  }
  
  public String get(Key2 key, Object a, Object b) {
    return getAbstract(key, a, b);
  }
  
  public String getAbstract(AbstractKey key, Object...args) {
    String msg = key.msg[lang.ordinal()];
    return replaceArgs(msg, args);
  }
  
  protected static String replaceArgs(String msg, Object...args) {
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
}