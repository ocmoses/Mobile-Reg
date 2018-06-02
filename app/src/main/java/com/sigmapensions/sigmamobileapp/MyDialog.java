package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialog extends Dialog {

	private Drawable drawableImage;
	private ImageView iconImage;
	private String header; 
	private String body;
	private String negativeText;
	private String positiveText;
	private TextView headerText;
	private TextView messageText;
	private Button negativeButton;
	private Button positiveButton;
	
	public MyDialog(Context context) {
		super(context);
	}
	
	public MyDialog(Activity activity) {
		super(activity);
	}
	
	private MyDialog(Context context, Drawable drawableImage, String header, String body) {
		super(context);
		this.drawableImage = drawableImage;
		this.header = header;
		this.body = body;
	}
	
	public MyDialog(Context context, Drawable drawableImage, String header, String body, String positiveText) {
		super(context);
		this.drawableImage = drawableImage;
		this.header = header;
		this.body = body;
		this.positiveText = positiveText;
		this.negativeText = null;
	}
	public MyDialog(Context context, Drawable drawableImage, String header, String body, String negativeText, String positiveText) {
		super(context);
		this.drawableImage = drawableImage;
		this.header = header;
		this.body = body;
		this.negativeText = negativeText;
		this.positiveText = positiveText;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_layout, null, false);
		setContentView(view);
				
		iconImage = (ImageView)view.findViewById(R.id.iconImage);
		headerText = (TextView)view.findViewById(R.id.headerText);
		messageText = (TextView)view.findViewById(R.id.messageText);
		negativeButton = (Button)view.findViewById(R.id.dialogNegativeButton);
		positiveButton = (Button)view.findViewById(R.id.dialogPositiveButton);
		
		if(drawableImage != null)
			iconImage.setImageDrawable(drawableImage);
		headerText.setText(header);
		messageText.setText(body);
		
		if(negativeText == null)
			negativeButton.setVisibility(View.GONE);
		else{
			negativeButton.setText(negativeText);
		}
		
		if(positiveText != null){
			positiveButton.setText(positiveText);
		}
		
	}
	
	public TextView getHeaderTextView(){
		return headerText;
	}
	
	public TextView getMessageTextView(){
		return messageText;
	}
	
	public void setIcon(Drawable d){
		iconImage.setImageDrawable(d);
	}
	public void setHeader(String s){
		headerText.setText(s);
	}
	public void setMessage(String s){
		messageText.setText(s);
	}
	
	public Button getNegativeButton(){
		return negativeButton;
	}
	public Button getPositiveButton(){
		return positiveButton;
	}

}
