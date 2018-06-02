package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

public class LoginCodeActivity extends Activity {
	
	private EditText codeET;
	private Button login_button;
	private TextView exitTV;
	
	private ImageView loginCodeBackIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_code_layout);
		
		CommonOps.loadSounds(this);
		
		codeET = (EditText)findViewById(R.id.loginCode);
		login_button = (Button)findViewById(R.id.login_code_button);
		exitTV = (TextView)findViewById(R.id.login_code_exit);
		
		loginCodeBackIV = (ImageView)findViewById(R.id.loginCodeBackIV);
		
		login_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(LoginCodeActivity.this);
				if(codeET.getText().toString().equals("1234")){
					Intent intent = new Intent(LoginCodeActivity.this, HomePanel.class);
					startActivity(intent);
					CommonOps.enterFromRight(LoginCodeActivity.this);
				}else{
					//we would say what to do here later...
				}
			}
			
		});
		
		exitTV.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				CommonOps.playClickSound(LoginCodeActivity.this);
				
				exitTV.setTextColor(Color.RED);
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						exitTV.setTextColor(Color.BLACK);
					}
					
				}, 2000);				
				CommonOps.exitApp(LoginCodeActivity.this);				
			}
			
		});
		
		loginCodeBackIV.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				CommonOps.playClickSound(LoginCodeActivity.this);
				Intent intent = new Intent(LoginCodeActivity.this, LoginActivity.class);
				startActivity(intent);
				CommonOps.enterFromLeft(LoginCodeActivity.this);
				finish();			
			}
			
		});
	}

}
