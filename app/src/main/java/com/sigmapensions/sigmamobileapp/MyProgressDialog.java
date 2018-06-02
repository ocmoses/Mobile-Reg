package com.sigmapensions.sigmamobileapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MyProgressDialog extends ProgressDialog{

	private TextView header;
	private TextView body;
	private String headerText;
	private String bodyText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.progress_dialog_layout, null, false);
		setContentView(view);
		header = (TextView)view.findViewById(R.id.progressHeaderText);
		body = (TextView)view.findViewById(R.id.progressMessageText);
		
		if(headerText != null && bodyText != null){
			header.setText(headerText);
			body.setText(bodyText);
		}
		
	}

	public MyProgressDialog(Context context) {
		super(context);
	}
	public MyProgressDialog(Context context, String headerText, String bodyText) {
		super(context);
		this.headerText = headerText;
		this.bodyText = bodyText;
		this.setCancelable(false);
	}
	

}
