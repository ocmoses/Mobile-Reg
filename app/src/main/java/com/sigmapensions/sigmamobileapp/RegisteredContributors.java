package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

public class RegisteredContributors extends AppCompatActivity {
	
	SharedPreferences pdPrefs;
	
	String[] leftListItems = {"All registered", "Newly registered", "Register new contributor", "Exit"};
	
	ListView leftList;
	ListView rightList;
	
	ArrayAdapter<String> adapter;
	RegisteredCursorAdapter cursorAdapter;
	SQLiteDatabase database;
	Cursor cursor;
	
	Button logout;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registered_contributors_activity);
		
		leftList = (ListView)findViewById(R.id.registered_list);
		adapter = new ArrayAdapter<String>(this, R.layout.left_list_layout, leftListItems);
		leftList.setAdapter(adapter);		
		
		database = openOrCreateDatabase(MobileRegDatabase.DATABASE_NAME, MODE_PRIVATE, null);	
		cursor = database.rawQuery("SELECT * FROM " + MobileRegDatabase.CONTRIBUTOR_TABLE_NAME, null);
		
		rightList = (ListView)findViewById(R.id.right_listview);
		cursorAdapter = new RegisteredCursorAdapter(this, cursor);
		rightList.setAdapter(cursorAdapter);
		
		logout = (Button)findViewById(R.id.logoutButton);
		
		logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(RegisteredContributors.this);
				CommonOps.logout(RegisteredContributors.this);
			}
			
		});
		
		pdPrefs = CommonOps.getPdPrefs(this);
		
		leftList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.playClickSound(RegisteredContributors.this);
				if(leftListItems[position].equals("Exit")){
					CommonOps.exitApp(RegisteredContributors.this, pdPrefs.edit());

				}else if(leftListItems[position].equals("Register new contributor")){
					Intent intent = new Intent(RegisteredContributors.this, RegisterContributor.class);
					startActivity(intent);
					CommonOps.enterFromLeft(RegisteredContributors.this);
					finish();
				}
				
			}
			
		});
		
	}
}
