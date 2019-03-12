package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

public class RecoverPasswordActivity extends AppCompatActivity implements OnFocusChangeListener{

	EditText phone;
	Button recoverPassButton;
	TableLayout table;
	TextView errorTextView;
	TextView exit;
	ImageView back;
	String phoneNumber;
	
	ConnectivityManager cm;
	NetworkInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recover_password_activity);

		phone = (EditText)findViewById(R.id.phoneNo);
		recoverPassButton = (Button)findViewById(R.id.get_password_button);
		table = (TableLayout)findViewById(R.id.recoverLogin_layout);
		exit = (TextView)findViewById(R.id.get_password_exit);
		back = (ImageView)findViewById(R.id.backIV);
		
		phone.setOnFocusChangeListener(this);
		
		exit.setPaintFlags(exit.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		exit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.exitApp(RecoverPasswordActivity.this, null);				
			}
			
		});
		
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(RecoverPasswordActivity.this);
				Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
				startActivity(intent);
				CommonOps.enterFromLeft(RecoverPasswordActivity.this);
				finish();
			}
			
		});
		
	}

	/* This method connects to the online database and retrieves the password
	 * The user gets it as an sms message on his phone
	 * 
	 * If no network is available, it checks if the user was the last person to login inside the system database
	 * If he is, he is given the password and username, if not... too bad. Actually, it does this first
	 * In this case, the username and password is displayed below the button
	 */
	public void getLogin(View view){
		CommonOps.playClickSound(RecoverPasswordActivity.this);
		if(phoneIsValid()){
			//connect to the database and compare who signed in last
			
			if(isLastUser()){
				//present him with the user name and password
				table.removeAllViews();
				TableRow row1 = new TableRow(RecoverPasswordActivity.this);
				TableRow row2 = new TableRow(RecoverPasswordActivity.this);
				TableRow row3 = new TableRow(RecoverPasswordActivity.this);
				
				TextView usernameTV = new TextView(RecoverPasswordActivity.this);
				TextView passwordTV = new TextView(RecoverPasswordActivity.this);
				Button proceedButton = new Button(RecoverPasswordActivity.this);
				
				usernameTV.setText("Username: " + "admin"); //this is just a test scenario
				usernameTV.setTextSize(20);
				usernameTV.setTextColor(Color.WHITE);
				usernameTV.setLeft(10);
				row1.setGravity(1);
				row1.addView(usernameTV);
				
				
				passwordTV.setText("Password: " + "admin");//and this too
				passwordTV.setTextSize(20);
				passwordTV.setTextColor(Color.WHITE);
				passwordTV.setLeft(10);
				row2.setGravity(1);
				row2.addView(passwordTV);
				
				
				proceedButton.setText("Proceed to login");
				proceedButton.setTextColor(Color.WHITE);
				proceedButton.setTextSize(20);
				proceedButton.setLeft(10);
				proceedButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttons_click));
				row3.setGravity(1);
				row3.addView(proceedButton);
				
				table.addView(row1);
				row1.setBottom(5);
				table.addView(row2);
				row2.setBottom(5);
				table.addView(row3);
				row3.setBottom(5);
				
				proceedButton.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
						//perhaps it will be good for the user not to be bothered 
						//with typing the username and password anymore
						Bundle loginDetails = new Bundle();
						loginDetails.putString("Username", "admin");//here too, it's just for tests
						loginDetails.putString("Password", "admin");//here too, it's just for tests
						intent.putExtra("LoginDetails", loginDetails);
						startActivity(intent);
						CommonOps.enterFromLeft(RecoverPasswordActivity.this);
						finish();
					}					
				});
				
			}else{
				if(!networkAvailable()){
					//Then the connection begins...
				}else{
					//create a dialog and inform the user there is no network
					//advising him to connect
					//If he refuses, app exits...
					
					createShowDialog();
				}
			}			
		}else{
			CommonOps.playErrorSound(RecoverPasswordActivity.this);
			if(errorTextView == null){
				errorTextView = new TextView(this);
				errorTextView.setText("Sorry, invalid phone number");
				errorTextView.setTextSize(20);
				errorTextView.setGravity(1);
				errorTextView.setBackgroundColor(Color.argb(50, 255, 255, 255));
				errorTextView.setTextColor(Color.RED);
				table.addView(errorTextView);
			}
		}
	}

	public boolean phoneIsValid(){
		phoneNumber = phone.getText().toString();
		
		if(phoneNumber.equals("") || phoneNumber == null)
			return false;
		
		switch(phoneNumber.charAt(0)){
		case '+': 
			if(phoneNumber.substring(1, 4).equals("234")){
				if(phoneNumber.charAt(4) == '0' && phoneNumber.length() == 15){
					phoneNumber = phoneNumber.substring(4);
					Log.e("PhoneNumber", phoneNumber);
					return true;
				}else if(phoneNumber.charAt(4) == '8' && phoneNumber.length() == 14){
					phoneNumber = "0" + phoneNumber.substring(4);
					Log.e("PhoneNumber", phoneNumber);
					return true;
				}				
			}
			break;
		case '0':
			if(phoneNumber.length() == 11){
				Log.e("PhoneNumber", phoneNumber);
				return true;
			}
			break;

		case '2':
			if(phoneNumber.substring(0, 3).equals("234")){
				if(phoneNumber.charAt(3) == '0' && phoneNumber.length() == 14){
					phoneNumber = phoneNumber.substring(3);
					Log.e("PhoneNumber", phoneNumber);
					return true;
				}else if(phoneNumber.charAt(3) == '8' && phoneNumber.length() == 13){
					phoneNumber = "0" + phoneNumber.substring(3);
					Log.e("PhoneNumber", phoneNumber);
					return true;
				}
			}
			break;
		default:
			return false;
			
		}
		return false;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(table.getChildAt(table.getChildCount() - 1).equals(errorTextView) && hasFocus){
			table.removeViewAt(table.getChildCount() - 1);
			errorTextView = null;
		}		
	}
	
	public boolean networkAvailable(){
		cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		info = cm.getActiveNetworkInfo();
		if(info != null && info.isConnected())
			return true;
		return false;
	}
	
	public boolean isLastUser(){
		//this is the method that connects to the database to find out if current user is 
		//the same as last user 
		
		return true;
	}
	
	//perhaps if I'm in the mood, I'll create a custom dialog
	public void createShowDialog(){
		AlertDialog dialog = new AlertDialog.Builder(RecoverPasswordActivity.this).create();
		dialog.setIcon(R.drawable.no_network_symbol);
		dialog.setMessage("No network Connection \nWould you like to connect to a network?");
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//This is where it should launch the settings activity
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setClassName("com.android.phone","com.android.phone.NetworkSetting");
				CommonOps.enterFromRight(RecoverPasswordActivity.this);
				startActivity(intent);
			}			
		});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
			
		});					
		dialog.show();
	}
}
