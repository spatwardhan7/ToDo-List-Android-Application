package com.example.todo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AuthenticationDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.USER_ID,
      MySQLiteHelper.COLUMN_UNAME, MySQLiteHelper.COLUMN_PASSWORD };

  public AuthenticationDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Authenticate createUserEntry(String uname,String password) {
    ContentValues values = new ContentValues();
    
    values.put(MySQLiteHelper.COLUMN_UNAME, uname);
    values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
    long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
        values);
    
    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
        allColumns, MySQLiteHelper.USER_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Authenticate newEntry = cursorToUser(cursor);
    cursor.close();
    return newEntry;
  }
  
  public boolean checkUserEntry(String uname,String password) {
	    
	  Cursor mCursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_USERS + " WHERE uname=? AND password=?", new String[]{uname,password});  
	  if (mCursor != null) {  
          if(mCursor.getCount() > 0)  
          {  
              return true;  
          }  
      }  
   return false; 
	  }
  public boolean checkuname(String uname) {
	    
	  Cursor mCursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_USERS + " WHERE uname=? ", new String[]{uname});  
	  if (mCursor != null) {  
          if(mCursor.getCount() > 0)  
          {  
              return true;  
          }  
      }  
   return false; 
	  }
  public Authenticate get_current_user_object(String uname,String password){
	  Authenticate user=new Authenticate();
	  Cursor mCursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_USERS + " WHERE uname=? AND password=?", new String[]{uname,password});  
	  mCursor.moveToFirst();
	  
	  user.setUid(mCursor.getLong(0));
	  user.setUname(mCursor.getString(1));
	  user.setPassword(mCursor.getString(2));
	  
	  return user;
  }

  public void deleteComment(Authenticate task) {
    long uid = task.getUid();
    System.out.println("Comment deleted with id: " + uid);
    database.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.USER_ID
        + " = " + uid, null);
  }

  public List<Authenticate> getAllUsers() {
    List<Authenticate> Users = new ArrayList<Authenticate>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Authenticate user = cursorToUser(cursor);
      Users.add(user);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return Users;
  }

  private Authenticate cursorToUser(Cursor cursor) {
    Authenticate user = new Authenticate();
    user.setUid(cursor.getLong(0));
    user.setUname(cursor.getString(1));
    user.setPassword(cursor.getString(2));
    return user;
  }
  
  public Authenticate getUser(Long uid){
	  Authenticate user=new Authenticate();
	  Cursor mCursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_USERS + " WHERE "+MySQLiteHelper.USER_ID+"=? ", new String[]{Long.toString(uid)});  
	  mCursor.moveToFirst();
	  
	  user.setUid(mCursor.getLong(0));
	  user.setUname(mCursor.getString(1));
	  user.setPassword(mCursor.getString(2));
	  
	  return user;
  }
} 