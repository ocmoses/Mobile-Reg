package com.sigmapensions.sigmamobileapp;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class BiometricDataFragment extends Fragment implements OnClickListener{

	ImageView passport;
	ImageView leftThumb;
	ImageView rightThumb;
	ImageView signature;

	Button passportButton;
	Button leftThumbButton;
	Button rightThumbButton;
	Button signatureButton;
	Button bBack;
	Button submitButton;

	BiometricButtons bb;
	
	SharedPreferences bPrefs;
	SharedPreferences.Editor editor;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			bb = (BiometricButtons)activity;
		}catch(ClassCastException e){
			Log.e("Forgot To IMPLEMENT!!!", e.toString());
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bPrefs = CommonOps.getbPrefs(getActivity());
		editor = bPrefs.edit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.biometric_data_fragment, container, false);

		initializeVariables(view);		 	 

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presetImages();
		attachAllListeners();
	}

	private void initializeVariables(View view) {
		passport = (ImageView)view.findViewById(R.id.passport);
		leftThumb = (ImageView)view.findViewById(R.id.leftThumb);
		rightThumb = (ImageView)view.findViewById(R.id.rightThumb);
		signature = (ImageView)view.findViewById(R.id.signature);

		passportButton = (Button)view.findViewById(R.id.bCapture);
		leftThumbButton = (Button)view.findViewById(R.id.bLeftThumb);
		rightThumbButton = (Button)view.findViewById(R.id.bRightThumb);
		signatureButton = (Button)view.findViewById(R.id.bSignature);
		bBack = (Button)view.findViewById(R.id.bBack);	
		submitButton = (Button)view.findViewById(R.id.bSubmit);	

	}
		
	private void presetImages() {
		Bitmap passportBitmap = CommonOps.getPassportBitmap(getActivity());
		Bitmap leftThumbBitmap = CommonOps.getLeftThumbBitmap(getActivity());
		Bitmap rightThumbBitmap = CommonOps.getRightThumbBitmap(getActivity());
		Bitmap signatureBitmap = CommonOps.getSignatureBitmap(getActivity());
		
		if(passportBitmap != null)
			passport.setImageBitmap(passportBitmap);
		if(leftThumbBitmap != null)
			leftThumb.setImageBitmap(leftThumbBitmap);
		if(rightThumbBitmap != null)
			rightThumb.setImageBitmap(rightThumbBitmap);		
		if(signatureBitmap != null)
			signature.setImageBitmap(signatureBitmap);	

	}

	private void attachAllListeners() {
		passportButton.setOnClickListener(this);
		leftThumbButton.setOnClickListener(this);
		rightThumbButton.setOnClickListener(this);
		signatureButton.setOnClickListener(this);
		bBack.setOnClickListener(this);
		submitButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		CommonOps.playClickSound(getActivity());
		switch(v.getId()){
		case R.id.bCapture: 
			bb.handlePassport();			
		break;
		
		case R.id.bLeftThumb: 
			bb.handleLeftThumb();
		break;
		
		case R.id.bRightThumb: 
			bb.handleRightThumb();
		break;
		
		case R.id.bSignature: 
			bb.handleSignature();
		break;
		
		case R.id.bBack: 
			bb.handleBack();
		break;
		
		case R.id.bSubmit: 
			bb.handleSubmit();
		break;
		
		default: break;
		}
	}
	
	public interface BiometricButtons{
		public void handlePassport();
		public void handleLeftThumb();
		public void handleRightThumb();
		public void handleSignature();
		public void handleBack();
		public void handleSubmit();
	}

}
