package com.example.todo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DisplayToDo extends Activity {

	
	private  Activity myActivity = this;
	private TaskDataSource datasource;
	ListView myList;
    LazyAdapter adapter;
    TaskList tasks;
    Authenticate user;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        // Receive Intent from Calling Activity and store it in local object
    	Intent intent_fromMain = getIntent();
    	user = (Authenticate)intent_fromMain.getSerializableExtra("CurrentUserObject");
    	long uid = user.getUid();
    	System.out.println("UserId in DisplayTodo:" +uid);
    	
        setContentView(R.layout.activity_display_to_do);

        datasource = new TaskDataSource(this);
        datasource.open();
        
        //customized list view
        tasks=datasource.getAllTasks(user);
        myList=(ListView)findViewById(R.id.list);
        for(int i=0;i<tasks.name.length;i++)
        {
        	System.out.println(tasks.name[i]+"items"+ tasks.completedFlag[i]);
        }
        adapter=new LazyAdapter(this, tasks.name, tasks.duedate , tasks.duetime, tasks.priority, tasks.completedFlag);
        
        myList.setAdapter(adapter);


        // Handle click on list item
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				
				System.out.println("Inside on item click");
				
				ListAdapter l=myList.getAdapter();
				final int i=(Integer)myList.getItemAtPosition(pos);
				Object h=myList.getItemAtPosition(i);
				String m=h.toString();
				System.out.println(m + tasks.taskid[i]);
				
				if(tasks.completedFlag[i]=="1")
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);
					builder.setMessage("Are you sure you want to un-check this task?")
					       .setCancelable(false)
					       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
									datasource.updateTaskuncheck(tasks.taskid[i]);
									tasks.completedFlag[i]="0";
									adapter.notifyDataSetChanged();;
					           }
					       })
					       .setNegativeButton("No", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();						
				}
				else
				{
					datasource.updateTask(tasks.taskid[i]);
					tasks.completedFlag[i]="1";
					adapter.notifyDataSetChanged();
				}
			}
		});
		
		registerForContextMenu(myList);
    }
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) 
		{
			case R.id.ViewItem:
				System.out.println("Inside View menu Item");
				try{
					int i=menuInfo.position;
					Long tid=tasks.taskid[i];
					Task task=datasource.getTask(tid);
				
					Intent intent_ViewItem = new Intent(this, ViewItem.class);
					intent_ViewItem.putExtra("CurrentTask", task);
					// Call next screen to display User ToDO list
				 	startActivity(intent_ViewItem);
					
				}
				catch (Exception e){
					e.printStackTrace();
				}	
				break;	
		
			case R.id.DeleteItem:
				try{

				  AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
				  builder.setMessage("Are you sure you want to delete this task?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                
			        	   int i=menuInfo.position;
					
							datasource.deleteTask(tasks.taskid[i]);
							tasks=datasource.getAllTasks(user);
													
							adapter=new LazyAdapter(myActivity, tasks.name, tasks.duedate , tasks.duetime, tasks.priority, tasks.completedFlag);
							 myList=(ListView)findViewById(R.id.list);
							 myList.setAdapter(adapter);
							adapter.notifyDataSetChanged();
							System.out.println(menuInfo.position+"position");    
					     }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
					
						AlertDialog alert = builder.create();
						alert.show();
				 				
					System.out.println("Inside Delete menu Item");			
				
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				 
				break;
			case R.id.EditItem:
				
				System.out.println("Inside Edit menu Item");
				
				int i=menuInfo.position;
				Long tid=tasks.taskid[i];
				Task task=datasource.getTask(tid);
				
		    	System.out.println("task in displaytodo"+task.getTdate());
		    	System.out.println("task in displaytodo"+task.getTname());
		    	System.out.println("task in displaytodo"+task.getTdescription());
		    	System.out.println("task in displaytodo"+task.getTid());
		    	System.out.println("task in displaytodo"+task.getTtime());
				
				Intent intent_EditItem = new Intent(this, EditItem.class);
				intent_EditItem.putExtra("CurrentTask", task);
				// Call next screen to display User ToDO list
			 	startActivity(intent_EditItem);
		
				break;
				
		}
		return true;
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.hideTasks:
			
			System.out.println("Inside Hide Items");
			tasks=datasource.getAllIncompleteTasks(user);
	        myList=(ListView)findViewById(R.id.list);
	        adapter=new LazyAdapter(this, tasks.name, tasks.duedate , tasks.duetime, tasks.priority, tasks.completedFlag);	        
	        myList.setAdapter(adapter);		
			break;
			
		case R.id.showTasks:
		
			System.out.println("Inside Show Items");
			tasks=datasource.getAllTasks(user);
	        myList=(ListView)findViewById(R.id.list);
	        adapter=new LazyAdapter(this, tasks.name, tasks.duedate , tasks.duetime, tasks.priority, tasks.completedFlag);	        
	        myList.setAdapter(adapter);	
			break;
		
		case R.id.Logout:
			System.out.println("Inside Logout");
		
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to logout?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {

			        	   startActivity(new Intent(myActivity, Main.class));               
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
		return true;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_to_do, menu);
        return true;
    }
    
    public void OnClickAddButton(View view) 
    {    
    	
    	// Get Calling Intent to extract Extras and pass it to next activity 
    	Intent intent_fromMain = getIntent();
    	Authenticate user = (Authenticate)intent_fromMain.getSerializableExtra("CurrentUserObject");
      
		Intent intent_AddItem = new Intent(this, AddItem.class);
		intent_AddItem.putExtra("CurrentUserObject", user);
		// Call next screen to display User ToDO list
	 	startActivity(intent_AddItem);
    }  
    @Override
    public void onDestroy()
    {
        myList.setAdapter(null);
        super.onDestroy();
    }
    
    @Override
    public void onBackPressed() 
    {
	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to exit this application?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
       	   
		        	   startActivity(new Intent(myActivity, Main.class));   
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		
    	return;
   	
    }
}
