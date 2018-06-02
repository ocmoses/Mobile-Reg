package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

public class LoginActivity extends Activity implements OnFocusChangeListener{
	TableLayout table;
	EditText usernameField;
	EditText passwordField;
	TextView forgotTextView;
	TextView errorTextView;
	TextView login_exit;
	Button button;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		CommonOps.loadSounds(this);
		
		table = (TableLayout)findViewById(R.id.login_layout);
		usernameField = (EditText)findViewById(R.id.username);
		passwordField = (EditText)findViewById(R.id.password);
		
		//this is here to save me the trouble of typing always
		usernameField.setText("admin");
		passwordField.setText("admin");		
		
		forgotTextView = (TextView)findViewById(R.id.forgot_password);
		button = (Button)findViewById(R.id.login_button);
		
		login_exit = (TextView)findViewById(R.id.login_exit);
		
		forgotTextView.setPaintFlags(forgotTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		login_exit.setPaintFlags(forgotTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		//Two lines below checks if the error message exists and removes it by adding an onclick listener
		usernameField.setOnFocusChangeListener(this);
		passwordField.setOnFocusChangeListener(this);

		forgotTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(LoginActivity.this);
				forgotTextView.setTextColor(Color.RED);
				Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);		
				startActivity(intent);
				CommonOps.enterFromRight(LoginActivity.this);
				finish();
			}

		});
		
		login_exit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				login_exit.setTextColor(Color.RED);
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						login_exit.setTextColor(Color.BLACK);
					}
					
				}, 2000);				
				CommonOps.exitApp(LoginActivity.this);				
			}
			
		});
		
		inputLoginDetails();
	}

	public void login(View view){
		String username = usernameField.getText().toString();
		String password = passwordField.getText().toString();
		CommonOps.playClickSound(LoginActivity.this);

		/* Here, the method checks the entries against a database to 
		 * ensure the password and usernames match
		 */

		//Below is a makeshift login

		if(username.equalsIgnoreCase("admin") && password.equals("admin")){
			Intent intent = new Intent(this, LoginCodeActivity.class);
			intent.putExtra("username", "admin");
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
			finish();
		}else if(username.equalsIgnoreCase("admin2") && password.equals("admin2")){
			Intent intent = new Intent(this, HomePanel.class);
			intent.putExtra("username", "admin2");
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
			finish();
		}else{
			//This part of the login will remain though
			CommonOps.playErrorSound(LoginActivity.this);
			if(errorTextView == null){
				errorTextView = new TextView(LoginActivity.this);
				errorTextView.setText("Sorry, invalid username/password combination");
				errorTextView.setTextSize(20);
				errorTextView.setGravity(1);
				errorTextView.setBackgroundColor(Color.rgb(255, 255, 0));
				errorTextView.setTextColor(Color.RED);
				table.addView(errorTextView);
			}

		}//end of makeshift login
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(table.getChildAt(table.getChildCount() - 1).equals(errorTextView) && hasFocus){
			table.removeViewAt(table.getChildCount() - 1);
			errorTextView = null;
		}
	}
	//This method doesn't seem to be working for some reason???????????????
	public void inputLoginDetails(){
		Intent intent = getIntent();
		Bundle loginDetails = intent.getExtras();
		if(loginDetails != null){
			usernameField.setText(loginDetails.getString("Username"));
			passwordField.setText(loginDetails.getString("Password"));
		}
	}
}
