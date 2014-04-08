package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class ViewItem extends Activity {
	private TaskDataSource datasource;
	
	private AuthenticationDataSource AdataSource; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        
        datasource = new TaskDataSource(this);
		datasource.open();
        
		AdataSource = new AuthenticationDataSource(this);
		AdataSource.open();
        
		Intent intent_fromMain = getIntent();
    	Task task = (Task)intent_fromMain.getSerializableExtra("CurrentTask");
    	
    	System.out.println(""+task.getTdate());
    	System.out.println(""+task.getTname());
    	System.out.println(""+task.getTdescription());
    	System.out.println(""+task.getTid());
    	System.out.println(""+task.getTtime());
    	
    	
		EditText taskName = (EditText) findViewById(R.id.TaskName_EditText_ViewItem);
		taskName.setText(task.getTname());
		
		EditText taskDate = (EditText) findViewById(R.id.Date_EditText_ViewItem);
		if(!task.getTdate().equals("-1"))
		{
			taskDate.setText(task.getTdate());
		}
		else
		{
			
			taskDate.setText("");
		}
		
		
		EditText taskTime = (EditText) findViewById(R.id.Time_EditText_ViewItem);
		if(!task.getTtime().equals("-1"))
		{
			taskTime.setText(task.getTtime());
		}
		else
		{
			
			taskTime.setText("");
		}
		
		EditText taskDesc = (EditText) findViewById(R.id.TaskDescription_ViewItem);
		if(!task.getTdescription().equals("-1"))
		{
			taskDesc.setText(task.getTdescription());
		}
		else
		{
			
			taskDesc.setText("");
		}
		

		if(task.getTPriority().equals("High"))
		{
			RadioButton rb = (RadioButton) findViewById(R.id.radio0_ViewItem);
			rb.setChecked(true);
		}
		else if (task.getTPriority().equals("Medium"))
		{
			RadioButton rb = (RadioButton) findViewById(R.id.radio1_ViewItem);
			rb.setChecked(true);
		}
		else if(task.getTPriority().equals("Low"))
		{
			RadioButton rb = (RadioButton) findViewById(R.id.radio2_ViewItem);
			rb.setChecked(true);
		}
    }
    
	public void OnClickClose(View v) 
	{
		finish();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_item, menu);
        return true;
    }
}
