package com.sigmapensions.sigmamobileapp;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomePanel extends AppCompatActivity {

	String[] listItems = {"Register new contributor","Change password", "View all records", 
	"Upload records", "Exit"};

	ListView leftList;
	ArrayAdapter<String> adapter;

	Button logout;

	TextView user_name;
	TextView repNoTV;
	LinearLayout performanceWarningLL;
	ImageView warningLogoIV;
	TextView performanceWarningTV;
	TextView totalRegisteredValueTV;
	TextView totalPinsGeneratedValueTV;
	TextView totalWelcomeLettersValueTV;
	TextView succesfulSMSValueTV;
	TextView totalUploadedValueTV;
	TextView performanceIndexValueTV;
	TextView performanceCommentValueTV;



	MobileRegDatabase helper;

	String user;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepanel_activity);

		//let's create the database here
		helper = new MobileRegDatabase(this);

		logout = (Button)findViewById(R.id.logoutButton);
		user_name = (TextView)findViewById(R.id.user_info);
		repNoTV = (TextView)findViewById(R.id.repNoTV);
		performanceWarningLL = (LinearLayout)findViewById(R.id.performanceWarningLL);
		warningLogoIV = (ImageView)findViewById(R.id.warningLogoIV);
		performanceWarningTV = (TextView)findViewById(R.id.performanceWarningTV);
		totalRegisteredValueTV = (TextView)findViewById(R.id.totalRegisteredValueTV);
		totalPinsGeneratedValueTV = (TextView)findViewById(R.id.totalPinsGeneratedValueTV);
		totalWelcomeLettersValueTV = (TextView)findViewById(R.id.totalWelcomeLettersValueTV);
		succesfulSMSValueTV = (TextView)findViewById(R.id.succesfulSMSValueTV);
		totalUploadedValueTV = (TextView)findViewById(R.id.totalUploadedValueTV);
		performanceIndexValueTV = (TextView)findViewById(R.id.performanceIndexValueTV);
		performanceCommentValueTV = (TextView)findViewById(R.id.performanceCommentValueTV);

		leftList = (ListView)findViewById(R.id.admin_list);
		adapter = new ArrayAdapter<String>(this, R.layout.left_list_layout, listItems);		
		leftList.setAdapter(adapter);

		//Here, the app checks its database to see which user just logged in and instantiates the name
		//All the other textviews are set as well
//		Intent intent = getIntent();
//		if(intent.getExtras().getString("username").equals("admin")){
			user = "John Doe";//just for tests
//		}else{
//			user = "Jane Doe";//this one too
//		}

		user_name.setText("Welcome, " + user);

		leftList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.playClickSound(HomePanel.this);
				if(listItems[position] == "Register new contributor"){
					Intent registerIntent = new Intent(HomePanel.this, RegisterContributor.class);
					startActivity(registerIntent);
					CommonOps.enterFromRight(HomePanel.this);
					finish();
					//Normally, I would clear shared preferences
				}else if(listItems[position] == "Exit"){
					CommonOps.exitApp(HomePanel.this, CommonOps.editor);
				}

			}


		});
		
		logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.logout(HomePanel.this);
			}
			
		});
	}
}
