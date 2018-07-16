package com.cm55.depDetect.gui.settings;

import java.io.*;
import java.sql.*;

import com.cm55.miniSerial.*;

/**
 * H2データベースハンドリング
 * 
 * @author admin
 */
public class H2Data implements DataIO {

  static H2Data instance = new H2Data();
  
  public static void create(File settingDir) throws Exception {
    instance.setup(settingDir);
  }
  
  public static H2Data getInstance() {
    return instance;
  }
  
  private static File settingDir;
  public static File getSettingDir() {
    return settingDir;
  }
  
  public static final String DATABASE_NAME = "settings";
  public static final int DATABASE_VERSION = 1;

  private File databaseFile;
  private Connection connection;
  private Statement statement;


  /** オープンする */
  private void setup(File settingDir) throws Exception {
    H2Data.settingDir = settingDir;
    databaseFile = new File(settingDir, DATABASE_NAME);    
    Class.forName("org.h2.Driver").newInstance();    
    String path = databaseFile.getAbsolutePath();
    connection = DriverManager.getConnection("jdbc:h2:" + path, "sa", "");
    statement = connection.createStatement();
    ensureTables();
  }
  
  /** テーブル構造の確認  */
  private void ensureTables() throws Exception {
    ResultSet r = null;
    try {
      r = statement.executeQuery("select * from tbl_version");
      if (!r.next())
        throw new RuntimeException();
      int version = r.getInt(2);
      //ystem.out.println("version " + version);
    } catch (Exception ex) {
      createTables();
    } finally {
      if (r != null)
        try {
          r.close();
        } catch (Exception ex) {
        }
    }
  }

  /** 設定値を取得する */
  public <T> byte[] getData(long settingKey) {
    ResultSet r = null;
    try {
      r = statement.executeQuery(String.format("select * from tbl_settings where s_key=%d", settingKey));
      if (!r.next())
        throw new RuntimeException();
      return r.getBytes(2);
    } catch (Exception ex) {
    } finally {
      if (r != null)
        try {
          r.close();
        } catch (Exception ex) {
        }
    }
    return null;
  }

  
  /** 設定値を格納する */
  public void putData(long settingKey, byte[] value) {
    try {
      ResultSet rs = statement.executeQuery("select count(*) from tbl_settings where s_key=" + settingKey);
      rs.next();
      boolean exists = rs.getInt(1) == 1;
      rs.close();
      if (exists) {
        //ystem.out.println("update " + settingKey);
        PreparedStatement prest = connection.prepareStatement(
          "update tbl_settings set s_value=? where s_key=?");
        prest.setBytes(1, value);
        prest.setLong(2, settingKey);
        prest.execute();
        prest.close();
      } else {
        //ystem.out.println("insert " + settingKey);
        PreparedStatement prest = connection.prepareStatement(
            "insert into tbl_settings (s_key, s_value) values (?, ?)");      
        prest.setLong(1, settingKey);
        prest.setBytes(2, value);
        prest.execute();
        prest.close();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  /** エラーの格納 */
  public void appendError(byte[]value) {   
    try {
      PreparedStatement prest = connection.prepareStatement(
        "insert into tbl_errors (e_time, e_value) values (?, ?)");      
      prest.setLong(1, System.currentTimeMillis());
      prest.setBytes(2, value);
      prest.execute();
      prest.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** データベースをクローズする */
  public void close() {
    if (statement != null)
      try {
        statement.close();
        statement = null;
      } catch (Exception ex) {
      }
    if (connection != null)
      try {
        connection.close();
        connection = null;
      } catch (Exception ex) {
      }
  }

  /** テーブルを作成する */
  private void createTables() throws Exception {
    // データベースバージョン
    statement.execute(
      "create table tbl_version (v_key integer not null primary key, v_value integer not null)");    
    statement.execute(
      String.format("insert into tbl_version(v_key, v_value) values (1, %d)", DATABASE_VERSION));

    // 設定テーブル
    statement.execute(
      "create table tbl_settings (s_key bigint not null primary key, s_value blob not null)");
    
    /* version 2で追加
    // エラーログテーブル
    statement.execute(
      "create table tbl_errors (e_time bigint not null primary key, e_value blob not null)");
    */
  }



}
