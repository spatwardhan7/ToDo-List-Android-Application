package com.example.todo;

import java.io.Serializable;

public class Authenticate implements Serializable{
	  private String uname,password;
	  private long uid;

	  
	  public long getUid() {
		    return uid;
		  }


	  public void setUid(long id) {
		    this.uid = id;
		  }

	  public String getUname() {
	    return uname;
	  }

	  public void setUname(String uname) {
	    this.uname = uname;
	  }
	  
	  public String getPassword() {
		    return password;
		  }

		  public void setPassword(String password) {
		    this.password = password;
		  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return uname;
	  }
	} 
