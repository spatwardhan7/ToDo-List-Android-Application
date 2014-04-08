package com.example.todo;

import java.io.Serializable;

public class Task implements Serializable{
	
	private String tname , tpri,completed,tdescription,tdate,ttime;
	  private long uid,tid;

	  
	  
	  
	  
	  public void setCompletedFlag(String id) {
		    this.completed = id;
		  }
	  public String getCompletedFlag() {
		    return completed;
		  }
	  
	  
	  
	  public long getUid() {
		    return uid;
		  }
	  public void setUid(long id) {
		    this.uid = id;
		  }

	  
	  
	  
	  public long getTid() {
		    return tid;
		  }
	  public void setTid(long id) {
		    this.tid = id;
		  }

	  
	  public String getTname() {
	    return tname;
	  }

	  public void setTname(String tname) {
	    this.tname = tname;
	  }
	  
	  
	  
	  public String getTdate() 
	  {
		    return tdate;
	  }

	  public void setTdate(String tdate) 
	  {
		  this.tdate = tdate;
	  }
	  public String getTtime()
	  {
			    return ttime;
	  }

	  public void setTtime(String ttime)
	  {
			    this.ttime = ttime;
	  }
	  public String getTdescription() 
	  {
		    return tdescription;
	  }

	  public void setTdescription(String tdescription1) 
	  {
		    this.tdescription = tdescription1;
	  }
	  
	  public String getTPriority() {
		    return tpri;
		  }

		  public void setTPriority(String tpri) {
		    this.tpri = tpri;
		  }
	  
	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
		  String mix=tname+"  "+tpri+"  "+completed;
	    return mix;
	  }

}
