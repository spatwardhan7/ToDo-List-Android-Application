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

public class Main extends Activity {

	private AuthenticationDataSource datasource;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		datasource = new AuthenticationDataSource(this);
		datasource.open();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void OnClickLoginButton(View view) {

		// Get username from form and store in string
		EditText userName = (EditText) findViewById(R.id.UserName);
		String uName = userName.getText().toString();
		// Debug
		System.out.println("" + uName);
		

		// Get password from form and store in string
		EditText Pwd = (EditText) findViewById(R.id.Password);
		String pwd = Pwd.getText().toString();
		// Debug
		System.out.println("" + pwd);

		// Toast for Displaying Error Message
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		if ((!uName.isEmpty()) && (!pwd.isEmpty())) 
		{
			// Validate Input
			if (datasource.checkUserEntry(uName, pwd)) 
			{
				// Get User Details to pass on to next Activity
				Authenticate user = new Authenticate();
				user = datasource.get_current_user_object(uName, pwd);
				System.out.println("User Id In Main: " + user.getUid());

				Intent intent_DisplayToDo = new Intent(this, DisplayToDo.class);
				intent_DisplayToDo.putExtra("CurrentUserObject", user);
				
				// Clear Username and password fields
				userName.setText("");
				Pwd.setText("");

				// Call next screen to display User ToDO list
				startActivity(intent_DisplayToDo);
			} 
			else 
			{
				CharSequence text = "Kindly enter a valid password.";
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}
		} 
		else 
		{
			CharSequence text = "Kindly enter a valid username and password.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
	}

	// If user clicks on NewUser button
	public void OnClickNewUser(View view) {

		Intent intent_NewUser = new Intent(this, NewUser.class);
		startActivity(intent_NewUser);
	}
	
}
