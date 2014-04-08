package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends Activity {

	private AuthenticationDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);

		// Database
		datasource = new AuthenticationDataSource(this);
		datasource.open();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_new_user, menu);
		return true;
	}

	public void OnClickNewUserSubmit(View view) {
		// Add to Database
		//
		// DisplayToDo intent by passing new username and password

		Authenticate user = new Authenticate();
		EditText userName = (EditText) findViewById(R.id.New_UserName);
		EditText passWord = (EditText) findViewById(R.id.New_Password);
		EditText repassWord = (EditText) findViewById(R.id.New_Re_Password);

		String uName = userName.getText().toString();
		String password = passWord.getText().toString();
		String repassword = repassWord.getText().toString();
		
		// To Display Error Message
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		if(uName.isEmpty() || password.isEmpty() || repassword.isEmpty())
		{
			
			CharSequence text = "Kindly enter a valid username and password.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		else if(!password.equals(repassword))
		{
			CharSequence text = "Passwords do not match. Please enter password again.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			
		}
		else
		{
			if(datasource.checkuname(uName))
			{
				CharSequence text = "Username already exists. Please enter different username";
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
				
			}
			else
			{
				// Save the new User entry to the database
				user = datasource.createUserEntry(uName, password);
				
				Authenticate newuser = new Authenticate();
				newuser = datasource.get_current_user_object(uName, password);
				System.out.println("User Id In NewUser: " + newuser.getUid());
				
				Intent intent_DisplayToDo = new Intent(this, DisplayToDo.class);	
				intent_DisplayToDo.putExtra("CurrentUserObject",newuser);
				startActivity(intent_DisplayToDo);
			}
		} 
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}
