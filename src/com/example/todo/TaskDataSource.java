package com.example.todo;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TaskDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.TASK_ID,
      MySQLiteHelper.COLUMN_TNAME, MySQLiteHelper.USER_ID1,MySQLiteHelper.COLUMN_TASK_PRIORITY ,MySQLiteHelper.COLUMN_COMPLETED };
  

  public TaskDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public void createTaskEntry(String tname,int uid , String priority, String completedflag , String description, String date, String time) {
    ContentValues values = new ContentValues();
    
    values.put(MySQLiteHelper.COLUMN_TNAME, tname);
    values.put(MySQLiteHelper.USER_ID1, uid);
    values.put(MySQLiteHelper.COLUMN_TASK_PRIORITY, priority);
    values.put(MySQLiteHelper.COLUMN_COMPLETED, completedflag);
    values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
    values.put(MySQLiteHelper.COLUMN_DATE, date);
    values.put(MySQLiteHelper.COLUMN_TIME, time);
    
    
    
    long insertId = database.insert(MySQLiteHelper.TABLE_TASKS, null,
        values);
    
    Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
        allColumns, MySQLiteHelper.TASK_ID + " = " + insertId, null,
        null, null, null);
    
    cursor.moveToFirst();
    //Task newEntry = cursorToUser(cursor);
    cursor.close();
    //return newEntry;
  }
  
public Task getTask(Long tid)
  {
	  
	  Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_TASKS + " WHERE "+MySQLiteHelper.TASK_ID+"=?", new String[]{Long.toString(tid)});
	  cursor.moveToFirst();
	  Task task = cursorToUser(cursor);
	  return task;
  }
  
  
  public void updateTask(Long tid)
  {
	  ContentValues updateCompleted = new ContentValues();
	   updateCompleted.put(MySQLiteHelper.COLUMN_COMPLETED, "1");
	   database.update(MySQLiteHelper.TABLE_TASKS, updateCompleted,
			   MySQLiteHelper.TASK_ID + " = " + tid, null);
		  
	  
  }
  public void updateTaskuncheck(Long tid)
  {
	  ContentValues updateCompleted = new ContentValues();
	   updateCompleted.put(MySQLiteHelper.COLUMN_COMPLETED, "0");
	   database.update(MySQLiteHelper.TABLE_TASKS, updateCompleted,
			   MySQLiteHelper.TASK_ID + " = " + tid, null);
		  
	  
  }

  public void deleteTask(Long tid) {
    
    System.out.println("Task deleted with tid: " + tid);
    database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.TASK_ID
        + " = " + tid, null);
  }
  public String[] getTaskNames(Authenticate user)
  {
	  String[] name;
	  Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_TASKS + " WHERE uid1=?", new String[]{Long.toString(user.getUid())});
	  int length = cursor.getCount();
	  cursor.moveToFirst();
	  name=new String[length];
	  int i =0;
	  cursor.moveToFirst();
	  while (!cursor.isAfterLast()) {
	      Task user1 = cursorToUser(cursor);
	      name[i]=user1.getTname();
	      cursor.moveToNext();
	      i++;
	    }
	  return name;
  }
  public TaskList getAllTasks(Authenticate user) {
	    
	    System.out.println("10");
	    //Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
	       // allColumns, null, null, null, null, null);
	    Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_TASKS + " WHERE uid1=?", new String[]{Long.toString(user.getUid())});
	    //System.out.println("11");
	    int length = cursor.getCount();
		TaskList tasks=new TaskList(length);  
	    int i =0;
	    
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Task user1 = cursorToUser(cursor);
	      tasks.name[i]=user1.getTname();
	      tasks.priority[i]=user1.getTPriority();
	      tasks.completedFlag[i]=user1.getCompletedFlag();
	     
	      tasks.taskid[i]=user1.getTid();
	      tasks.description[i]=user1.getTdescription();
	      tasks.duedate[i]=user1.getTdate();
	      System.out.println("weird1"+tasks.duedate[i]);
	      tasks.duetime[i]=user1.getTtime();
	      System.out.println("weird2"+tasks.duetime[i]);
	      cursor.moveToNext();
	      i++;
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    System.out.println("13");
	    return tasks;
	  }

  private Task cursorToUser(Cursor cursor) {
    Task user = new Task();
    user.setTid(cursor.getLong(0));
    user.setTname(cursor.getString(1));
    user.setUid(cursor.getLong(2));
    user.setTPriority(cursor.getString(3));
    user.setCompletedFlag(cursor.getString(4));
    user.setTdescription(cursor.getString(5));
    user.setTdate(cursor.getString(6));
    user.setTtime(cursor.getString(7));
    
    return user;
  }
  
  public TaskList getAllIncompleteTasks(Authenticate user) {
	    
	    System.out.println("10");
	    //Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
	       // allColumns, null, null, null, null, null);
	    Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_TASKS + " WHERE uid1=? AND completedflag=?", new String[]{Long.toString(user.getUid()),"0"});
	    //System.out.println("11");
	    int length = cursor.getCount();
		TaskList tasks=new TaskList(length);  
	    int i =0;
	    
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Task user1 = cursorToUser(cursor);
	      tasks.name[i]=user1.getTname();
	      tasks.priority[i]=user1.getTPriority();
	      tasks.completedFlag[i]=user1.getCompletedFlag();
	     
	      tasks.taskid[i]=user1.getTid();
	      tasks.description[i]=user1.getTdescription();
	      tasks.duedate[i]=user1.getTdate();
	      System.out.println("weird1"+tasks.duedate[i]);
	      tasks.duetime[i]=user1.getTtime();
	      System.out.println("weird2"+tasks.duetime[i]);
	      cursor.moveToNext();
	      i++;
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    System.out.println("13");
	    return tasks;
	  }
  
  public void editTask(Long tid,String tname,int uid , String priority, String completedflag , String description, String date, String time)
  {
  	  ContentValues editCompleted = new ContentValues();
  	   editCompleted.put(MySQLiteHelper.COLUMN_COMPLETED, "0");
  	   editCompleted.put(MySQLiteHelper.COLUMN_TNAME, tname);
  	   editCompleted.put(MySQLiteHelper.COLUMN_TASK_PRIORITY, priority);
  	   editCompleted.put(MySQLiteHelper.COLUMN_DATE, date);
  	   editCompleted.put(MySQLiteHelper.COLUMN_TIME, time);
  	   editCompleted.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
  	   database.update(MySQLiteHelper.TABLE_TASKS, editCompleted,
  			   MySQLiteHelper.TASK_ID + " = " + tid, null);
  		  
  	  
  }
  
} 