package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_USERS = "usernametable";
  public static final String USER_ID = "_uid";
  public static final String COLUMN_UNAME = "uname";
  public static final String COLUMN_PASSWORD = "password";
  private static final String DATABASE_NAME = "users.db";
  private static final int DATABASE_VERSION = 3;
  
  public static final String TABLE_TASKS = "tasktable";
  public static final String TASK_ID = "_tid";
  public static final String COLUMN_TNAME = "tname";
  public static final String USER_ID1 = "uid1";
  public static final String COLUMN_TASK_PRIORITY = "task_priority";
  public static final String COLUMN_COMPLETED = "completedflag";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_DATE = "date";
  public static final String COLUMN_TIME = "time";


  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_USERS + "(" + USER_ID
      + " integer primary key autoincrement, " + COLUMN_UNAME
      + " text not null, "+COLUMN_PASSWORD +" text not null "+");";

  private static final String DATABASE_CREATE1 = "create table "
	      + TABLE_TASKS + "(" 
		  + TASK_ID
	      + " integer primary key autoincrement, " 
		  + COLUMN_TNAME
	      + " text not null, "
		  + USER_ID1 
		  + " integer, "
	      + COLUMN_TASK_PRIORITY 
	      + " text not null, "
	      + COLUMN_COMPLETED
	      + " integer, "
	      + COLUMN_DESCRIPTION
	      + " text , "
	      + COLUMN_DATE
	      + " text, "
	      + COLUMN_TIME
	      + " text "
	      + ");";

  
  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
    database.execSQL(DATABASE_CREATE1);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
    onCreate(db);
  }

} 