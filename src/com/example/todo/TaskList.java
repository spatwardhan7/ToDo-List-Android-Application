package com.example.todo;

public class TaskList {
	
	public String[] name;
	public String[] priority;
	public String[] duedate;
	public String[] duetime;
	public String[] description;
	public String[] completedFlag;
	public Long[] taskid; 
	TaskList(int l)
	{
		name= new String[l];
		priority=new String[l];
		duedate=new String[l];
		duetime=new String[l];
		description=new String[l];
		completedFlag=new String[l];
		taskid= new Long[l];
	}
}
