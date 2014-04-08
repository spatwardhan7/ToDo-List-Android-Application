package com.example.todo;

import java.text.ParseException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditItem extends FragmentActivity {
	
	public static int setYear = -1;
	public static int setMonth = -1;
	public static int setDay = -1;

	public static int SetDateFlag = 0;
	public static int SetTimeFlag = 0;
	private TaskDataSource datasource;
	
	private AuthenticationDataSource AdataSource; 

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			EditText datebox = (EditText) getActivity().findViewById(
					R.id.Date_EditText_EditItem);
			//String completeDate = datebox.getText().toString();

			// String yearString = completeDate.lastIndexOf(c, start);

			int year = c.get(Calendar.YEAR);
			if (setYear != -1) {
				year = setYear;
			}

			int month = c.get(Calendar.MONTH);
			System.out.println("month value = " + month);
			if (setMonth != -1) {
				month = setMonth;
			}

			int day = c.get(Calendar.DAY_OF_MONTH);
			if (setDay != -1) {
				day = setDay;
			}

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			EditText datebox = (EditText) getActivity().findViewById(
					R.id.Date_EditText_EditItem);
			datebox.setText((month+1) + "-" + day + "-" + year);
			setYear = year;
			setMonth = month;
			setDay = day;

			Button SetDateButton = (Button) getActivity().findViewById(R.id.SetDate_Button_EditItem);
			SetDateButton.setText("Change Date");

		}
	}
	
	public static int setHour = -1;
	public static int setMinutes = -1;

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			EditText timebox = (EditText) getActivity().findViewById(
					R.id.Time_EditText_EditItem);
			// String completeTime = timebox.getText().toString();

			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if (setHour != -1) {
				hour = setHour;
			}

			int minute = c.get(Calendar.MINUTE);
			if (setMinutes != -1) {
				minute = setMinutes;
			}

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user

			setHour = hourOfDay;
			setMinutes = minute;
			String min;
			String hr;
			String ampm = "AM";
			EditText timebox = (EditText) getActivity().findViewById(
					R.id.Time_EditText_EditItem);
			
			if (hourOfDay >= 12) {
				hourOfDay = hourOfDay - 12;
				ampm = "PM";
			}
			if (minute < 10) {
				min = "0" + minute;
			} else {
				min = Integer.toString(minute);
			}
			timebox.setText(hourOfDay + ":" + min + " " + ampm);
		
			Button SetTimeButton = (Button) getActivity().findViewById(R.id.SetTime_Button_EditItem);
			SetTimeButton.setText("Change Time");
		}
	}	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        
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
    	
    	
		EditText taskName = (EditText) findViewById(R.id.TaskName_EditText_EditItem);
		taskName.setText(task.getTname());
		
		EditText taskDate = (EditText) findViewById(R.id.Date_EditText_EditItem);
		if(!task.getTdate().equals("-1"))
		{
			taskDate.setText(task.getTdate());
		}
		else
		{
			
			taskDate.setText("");
		}
		
		
		EditText taskTime = (EditText) findViewById(R.id.Time_EditText_EditItem);
		if(!task.getTtime().equals("-1"))
		{
			taskTime.setText(task.getTtime());
		}
		else
		{
			
			taskTime.setText("");
		}
		
		EditText taskDesc = (EditText) findViewById(R.id.TaskDescription_EditItem);
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
			RadioButton rb = (RadioButton) findViewById(R.id.radio0_EditItem);
			rb.setChecked(true);
		}
		else if (task.getTPriority().equals("Medium"))
		{
			RadioButton rb = (RadioButton) findViewById(R.id.radio1_EditItem);
			rb.setChecked(true);
		}
		else if(task.getTPriority().equals("Low"))
		{
			RadioButton rb = (RadioButton) findViewById(R.id.radio2_EditItem);
			rb.setChecked(true);
		}
    }
    
    public void OnClickSave(View view) throws ParseException {
		// If task name is blank. Do not save.

		int ValidationResult = Validate();
		
		EditText taskName = (EditText) findViewById(R.id.TaskName_EditText_EditItem);
		String tName = taskName.getText().toString();
		
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		
		if (ValidationResult == 0) // Conditions validated
		{
			Task task = new Task();
				    	
			EditText taskDate = (EditText) findViewById(R.id.Date_EditText_EditItem);
			String tDate = taskDate.getText().toString();
			
			EditText taskTime = (EditText) findViewById(R.id.Time_EditText_EditItem);
			String tTime = taskTime.getText().toString();
			
			System.out.println("Date = "+tDate);
			System.out.println("Time = "+tTime);
			
			
			if(tDate.isEmpty())
			{
				tDate = "-1";
			}
			
			if(tTime.isEmpty())
			{
				tTime = "-1";
			}
			
			EditText taskDesc = (EditText) findViewById(R.id.TaskDescription_EditItem);
			String tDesc = taskDesc.getText().toString();
			
			if(tDesc.isEmpty())
			{
				tDesc = "-1";
			}
			System.out.println("Task date: "+tDate);
			System.out.println("Task time: "+tTime);
			System.out.println("Task desc: "+tDesc);
			
	    	
			RadioGroup r=(RadioGroup)findViewById(R.id.radioGroup2_EditItem);
			int chk_priority=r.getCheckedRadioButtonId();
			String priority = null;
			switch(chk_priority)
			{
				case R.id.radio0_EditItem:
					priority="High";
					break;
				case R.id.radio1_EditItem:
					priority="Medium";
					break;
				case R.id.radio2_EditItem:
					priority="Low";
					break;
				
			}
	
			Intent intent_fromDisplayToDo = getIntent();
	    	Task taskFromToDO = (Task)intent_fromDisplayToDo.getSerializableExtra("CurrentTask");
	    	
	    	System.out.println("gettid " +taskFromToDO.getTid());
	    	System.out.println("tName " +tName);
	    	System.out.println("getuid " +taskFromToDO.getUid());
	    	System.out.println("priority " +priority);
	    	System.out.println("CompletedFlag " +taskFromToDO.getCompletedFlag());
	    	System.out.println("tDesc " +tDesc);
	    	System.out.println("tDate " +tDate);
	    	System.out.println("tTime " +tTime);
			
			 //datasource.createTaskEntry(tName, (int) user.getUid(), priority, "0",tDesc ,tDate,tTime );
			 datasource.editTask(taskFromToDO.getTid(), tName, (int) taskFromToDO.getUid(), priority, taskFromToDO.getCompletedFlag(), tDesc, tDate, tTime);
			
			System.out.println("Done database insert");
			
			Intent displaytodo_intent = new Intent(this, DisplayToDo.class);
			
			Authenticate user1 = AdataSource.getUser(taskFromToDO.getUid());
			
			displaytodo_intent.putExtra("CurrentUserObject", user1);
			
			startActivity(displaytodo_intent);			
			
		}
		else if (ValidationResult == 1)
		{
			CharSequence text = "Cannot Save Task without Task Name. Please Enter task name and then save";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		else if (ValidationResult == 2)
		{
			CharSequence text = "Please Enter Date if you wish to use Set Time for Task";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		
	}
	
	public int Validate()
	{
		
		EditText taskName = (EditText) findViewById(R.id.TaskName_EditText_EditItem);
		String tName = taskName.getText().toString();
		
		EditText taskDate = (EditText) findViewById(R.id.Date_EditText_EditItem);
		String tDate = taskDate.getText().toString();
		
		EditText taskTime = (EditText) findViewById(R.id.Time_EditText_EditItem);
		String tTime = taskTime.getText().toString();
		
		
		if(tName.isEmpty())
			return 1;
		
		if((!tTime.isEmpty()) && (tDate.isEmpty()))
			return 2;
		
		return 0;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_item, menu);
        return true;
    }
    
	public void OnClickSetTime(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");

	}

	public void OnClickSetDate(View view) {

		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
    
	public void OnCLickCancel_EditItem(View v) 
	{
		finish();
	}     
}
