package com.sigmapensions.sigmamobileapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sigmapensions.sigmamobileapp.BiometricDataFragment.BiometricButtons;
import com.sigmapensions.sigmamobileapp.ContributionFragment.ContributorNav;
import com.sigmapensions.sigmamobileapp.EmployerDataFragment.EmployerListInterface;
import com.sigmapensions.sigmamobileapp.EmployerDataFragment.EmployerNav;
import com.sigmapensions.sigmamobileapp.NOKFragment.NOKNav;
import com.sigmapensions.sigmamobileapp.PersonalDataFragment.ChangeToNOK;
import com.sigmapensions.sigmamobileapp.utils.CommonOps;

public class RegisterContributor extends Activity implements ChangeToNOK, 
NOKNav, EmployerNav, BiometricButtons, ContributorNav, EmployerListInterface, OnClickListener{

	private final static int PASSPORT_INT = 1;
	private final static int LEFT_THUMB_INT = 2;
	private final static int RIGHT_THUMB_INT = 3;
	private final static int SIGNATURE_INT = 4;

	private final static int PERSONAL_FRAGMENT = 1;
	private final static int NOK_FRAGMENT = 2;
	private final static int EMPLOYER_FRAGMENT = 3;
	private final static int CONTRIBUTION_FRAGMENT = 4;
	private final static int BIOMETRIC_FRAGMENT = 5;

	Fragment currentFragment;
	int CURRENT_FRAGMENT = PERSONAL_FRAGMENT;

	String[] contributorAreas = {"Personal data", "Next of kin", 
			"Employment data", "Contribution", "Biometrics", "Exit"};

	String[] maritalStatus = {"Single", "Married", "Divorced", "Widowed"};

	Button logout;

	LinearLayout employerLinearLayout;
	ListView contributorAreasList;
	ArrayAdapter<String> adapter;

	ScrollView right_scrollview;

	TableLayout personalData;
	TableLayout nokData;
	TableLayout employerData;
	RelativeLayout biometricData;

	FragmentManager fm;
	FragmentTransaction transact;

	PersonalDataFragment pdf;
	NOKFragment nokf;
	EmployerDataFragment edf;
	BiometricDataFragment bdf;
	ContributionFragment cf;

	MyDialog dialog;

	EmployerDatabase edb;

	SQLiteDatabase database;
	Cursor cursor;
	MyCursorAdapter cursorAdapter;

	ListView employerListView;	

	SignatureView sv;
	RelativeLayout signatureRL;
	Button clear;
	Button submit;

	ImageView cancelSignature;
	ImageView cancelEmployerList;
	
	ImageView bottomShadow;

	SharedPreferences edPrefs;
	SharedPreferences.Editor edPrefsEditor;

	SharedPreferences pdPrefs;
	public static String controlNumber = "";

	SharedPreferences bPrefs;
	SharedPreferences.Editor bPrefsEditor;

	public static String prefix;

	static String passport_directory;
	static String left_thumb_directory;
	static String right_thumb_directory;
	static String signature_directory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_contributor_activity);

		contributorAreasList = (ListView)findViewById(R.id.contributor_list);
		adapter = new ArrayAdapter<String>(this, R.layout.left_list_layout, contributorAreas);
		contributorAreasList.setAdapter(adapter);

		logout = (Button)findViewById(R.id.logoutButton);

		employerLinearLayout = (LinearLayout)findViewById(R.id.employerLinearLayout);
		employerListView = (ListView)findViewById(R.id.employerListView);

		sv = (SignatureView)findViewById(R.id.signature_view);
		signatureRL = (RelativeLayout)findViewById(R.id.signatureRL);

		clear = (Button)findViewById(R.id.SAclear);
		submit = (Button)findViewById(R.id.SAsubmit);

		clear.setOnClickListener(this);
		submit.setOnClickListener(this);

		pdf = new PersonalDataFragment();
		nokf = new NOKFragment();
		edf = new EmployerDataFragment();
		cf = new ContributionFragment();
		bdf = new BiometricDataFragment();

		cancelSignature = (ImageView)findViewById(R.id.cancelImage);
		cancelEmployerList = (ImageView)findViewById(R.id.cancelEmployerList);
		

		edb = new EmployerDatabase(this);


		fm = getFragmentManager();
		transact = fm.beginTransaction();
		transact.add(R.id.fragment_holder, pdf);
		transact.commit();

		right_scrollview = (ScrollView)findViewById(R.id.right_scrollview);		

		contributorAreasList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.playClickSound(RegisterContributor.this);
				if(contributorAreas[position].equals("Exit")){
					CommonOps.exitApp(RegisterContributor.this, pdPrefs.edit());
				}else
					changeForm(position);	
			}					
		});

		employerListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				CommonOps.playClickSound(RegisterContributor.this);
				cursor = (Cursor)employerListView.getItemAtPosition(position);
				String companyName = cursor.getString(cursor.getColumnIndex("employer_name"));
				String companyCode = cursor.getString(cursor.getColumnIndex("employer_code"));
				String companySector = cursor.getString(cursor.getColumnIndex("employer_sector"));
				edf.edNameOfOrganizationET.setText(companyName);
				edf.edEmployerCodeET.setText(companyCode);
				employerLinearLayout.setVisibility(View.INVISIBLE);

				edPrefs = CommonOps.getEdPrefs(RegisterContributor.this);
				edPrefsEditor = edPrefs.edit();
				edPrefsEditor.remove("edNameOfOrganizationET");
				edPrefsEditor.putString("edNameOfOrganizationET", companyName);
				edPrefsEditor.remove("edEmployerCodeET");
				edPrefsEditor.putString("edEmployerCodeET", companyCode);
				edPrefsEditor.commit();
				edPrefsEditor.remove("employerType");
				edPrefsEditor.putString("employerType", companySector);
				edPrefsEditor.commit();


			}					
		});

		cancelSignature.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(RegisterContributor.this);
				signatureRL.setVisibility(View.INVISIBLE);
			}

		});

		cancelEmployerList.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(RegisterContributor.this);
				database = openOrCreateDatabase(EmployerDatabase.DATABASE_NAME, MODE_PRIVATE, null);
				cursor = database.rawQuery("SELECT _id as _id, employer_name FROM Employer_table WHERE employer_name = '" + 
						edf.edNameOfOrganizationET.getText() + "'", null);
				if(!cursor.moveToFirst())
					edf.edEmployerCodeET.setText("");
				database.close();	
				employerLinearLayout.setVisibility(View.INVISIBLE);
			}

		});

		logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(RegisterContributor.this);
				CommonOps.logout(RegisterContributor.this);
			}

		});



		pdPrefs = CommonOps.getPdPrefs(this);

		bPrefs = CommonOps.getbPrefs(this);
		bPrefsEditor = bPrefs.edit();

	}

	public void changeForm(int position){
		fm = getFragmentManager();
		transact = fm.beginTransaction();

		if(contributorAreas[position].equals("Personal data") 
				&& CURRENT_FRAGMENT != PERSONAL_FRAGMENT){
			currentFragment = pdf;
			transact.replace(R.id.fragment_holder, pdf);
			CURRENT_FRAGMENT = PERSONAL_FRAGMENT;
		}else if(contributorAreas[position].equals("Next of kin") 
				&& CURRENT_FRAGMENT != NOK_FRAGMENT){
			currentFragment = nokf;
			transact.replace(R.id.fragment_holder, nokf);
			CURRENT_FRAGMENT = NOK_FRAGMENT;
		}else if(contributorAreas[position].equals("Employment data") 
				&& CURRENT_FRAGMENT != EMPLOYER_FRAGMENT){
			currentFragment = edf;
			transact.replace(R.id.fragment_holder, edf);
			CURRENT_FRAGMENT = EMPLOYER_FRAGMENT;
		}else if(contributorAreas[position].equals("Contribution") 
				&& CURRENT_FRAGMENT != CONTRIBUTION_FRAGMENT){
			currentFragment = cf;
			transact.replace(R.id.fragment_holder, cf);
			CURRENT_FRAGMENT = CONTRIBUTION_FRAGMENT;
		}else if(contributorAreas[position].equals("Biometrics") 
				&& CURRENT_FRAGMENT != BIOMETRIC_FRAGMENT){
			currentFragment = bdf;
			transact.replace(R.id.fragment_holder, bdf);
			CURRENT_FRAGMENT = BIOMETRIC_FRAGMENT;
		}
		
		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
		right_scrollview.scrollTo(0, 0);

		dialog = new MyDialog(this);
		
	}

	//*******************************************************************************************
	//handling navingation starts here
	//********************************************************************************************

	@Override
	public void changeToNOKForm(){

		transact = fm.beginTransaction();

		currentFragment = nokf;
		transact.replace(R.id.fragment_holder, nokf);
		CURRENT_FRAGMENT = NOK_FRAGMENT;	

		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();

	}

	@Override
	public void backToPersonalData() {
		transact = fm.beginTransaction();

		currentFragment = pdf;
		transact.replace(R.id.fragment_holder, pdf);
		CURRENT_FRAGMENT = PERSONAL_FRAGMENT;	

		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
	}

	@Override
	public void forwardToEmployerData() {
		transact = fm.beginTransaction();

		currentFragment = edf;
		transact.replace(R.id.fragment_holder, edf);
		CURRENT_FRAGMENT = EMPLOYER_FRAGMENT;	

		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
	}

	@Override
	public void backToEmployerData() {
		transact = fm.beginTransaction();

		currentFragment = edf;
		transact.replace(R.id.fragment_holder, edf);
		CURRENT_FRAGMENT = EMPLOYER_FRAGMENT;	

		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
	}

	@Override
	public void forwardToBiometric() {
		transact = fm.beginTransaction();

		currentFragment = bdf;
		transact.replace(R.id.fragment_holder, bdf);
		CURRENT_FRAGMENT = BIOMETRIC_FRAGMENT;	
		
		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
	}

	@Override
	public void goForwardToContribution() {
		transact = fm.beginTransaction();

		currentFragment = cf;
		transact.replace(R.id.fragment_holder, cf);
		CURRENT_FRAGMENT = CONTRIBUTION_FRAGMENT;	

		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
	}

	@Override
	public void goBackwardToNOK() {
		transact = fm.beginTransaction();

		currentFragment = nokf;
		transact.replace(R.id.fragment_holder, nokf);
		CURRENT_FRAGMENT = NOK_FRAGMENT;	

		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
	}

	//*******************************************************************************************
	//handling navingation ends here
	//********************************************************************************************

	//*******************************************************************************************
	//The Biometric buttons 
	//********************************************************************************************

	@Override
	public void handlePassport() {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, PASSPORT_INT);		
	}

	@Override
	public void handleLeftThumb() {
		dialog = new MyDialog(RegisterContributor.this, null, "Thumb Print Scanner", 
				"This should accept input from the biometric fingerprint scanner\nFor now, it will set a fingerprint in place", 
				"OK");
		dialog.show();
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(RegisterContributor.this);
				dialog.dismiss();
				//This sets a file stored in the assets as a thumb print provisionally
				if(!controlNumber.equals("N/A"))
					getLeftThumb(prefix + "_leftThumb.jpg");
				else{
					CommonOps.complainAboutControlNumber(RegisterContributor.this);
				}
			}			
		});		


	}

	@Override
	public void handleRightThumb() {
		dialog = new MyDialog(RegisterContributor.this, null, "Thumb Print Scanner", 
				"This should also accept input from the biometric fingerprint scanner\nFor now, it will set a fingerprint in place", 
				"OK");
		dialog.show();
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(RegisterContributor.this);
				dialog.dismiss();
				//This sets a file stored in the assets as a thumb print provisionally
				if(!controlNumber.equals("N/A"))
					getRightThumb(prefix + "_rightThumb.jpg");
				else{
					CommonOps.complainAboutControlNumber(RegisterContributor.this);
				}
			}			
		});	

	}

	@Override
	public void handleSignature() {
		TextView signHereTV = (TextView)findViewById(R.id.signHereTV);
		Typeface freescpt = Typeface.createFromAsset(getAssets(), "fonts/FREESCPT.TTF");
		signHereTV.setTypeface(freescpt);
		signatureRL.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void handleBack() {
		transact = fm.beginTransaction();

		currentFragment = cf;
		transact.replace(R.id.fragment_holder, cf);
		CURRENT_FRAGMENT = CONTRIBUTION_FRAGMENT;	

		transact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transact.addToBackStack(null);
		transact.commit();
	}
	
	@Override
	public void handleSubmit() {
		//Lets do it like this for now
		Intent intent = new Intent(RegisterContributor.this, ConfirmationPage.class);
		startActivity(intent);
		CommonOps.enterFromRight(RegisterContributor.this);
		finish();
	}

	//*******************************************************************************************
	//The Biometric buttons ends here
	//********************************************************************************************

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == PASSPORT_INT){
			try{
				Toast.makeText(this, "Passport successfully captured...", Toast.LENGTH_LONG).show();
				Bitmap passport = (Bitmap)(data.getExtras().get("data"));
				Log.e("BITMAP.WIDTH", passport.getWidth() + "");
				Log.e("BITMAP.HEIGHT", passport.getHeight() + "");
				//				Then we process the image before setting it as passport etc.
				//				Matrix matrix = new Matrix();
				//				int scaleWidth = 150/passport.getWidth();
				//				int scaleHeight = 200/passport.getHeight();
				//
				//				matrix.postScale(scaleWidth, scaleHeight);
				//				passport = Bitmap.createBitmap(passport, 0, 0, 150, 200, matrix, true);


				//Then open a file output stream to save the image
				if(saveImage(passport, prefix + "_passport.jpg")){
					bdf.passport.setImageBitmap(passport);
					passport_directory = prefix + "_passport.jpg";
					CommonOps.saveValue("PASSPORT", passport_directory, bPrefsEditor);
				}else{
					Toast.makeText(this, "Sorry, couldn't save passport image", Toast.LENGTH_LONG).show();
				}
			}catch(Exception e){
				dialog = new MyDialog(this, null, "Sorry", "Failed to resize and save passport", "OK");
				dialog.show();
				dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						CommonOps.playClickSound(RegisterContributor.this);
						dialog.dismiss();
					}

				});
				
			}	
		}
	}

	@Override
	public void showEmployerList(String string) {
		database = edb.getWritableDatabase();
		cursor = database.rawQuery("SELECT _id as _id, employer_code, " + "employer_name".replace("'", "\'") + ", employer_sector FROM Employer_table WHERE employer_name LIKE '" + 
				string + "%' order by employer_name", null);
		cursorAdapter = new MyCursorAdapter(this, cursor);
		employerListView.setAdapter(cursorAdapter);
		if(employerLinearLayout.getVisibility() == View.INVISIBLE)
			employerLinearLayout.setVisibility(View.VISIBLE);
		database.close();
	}

	@Override
	public void onClick(View v) {
		CommonOps.playClickSound(RegisterContributor.this);
		switch(v.getId()){

		case R.id.SAclear: 
			sv.clear();		
			break;

		case R.id.SAsubmit:

			if(sv.canSave){
				if(controlNumber.equals("N/A"))
					CommonOps.complainAboutControlNumber(RegisterContributor.this);
				else{
					if(saveImage(sv.surfaceBitmap, prefix + "_signature.jpg")){
						signature_directory = prefix + "_signature.jpg";
						CommonOps.saveValue("SIGNATURE", signature_directory, bPrefsEditor);					
					}else{
						Toast.makeText(this, "Sorry, couldn't save signature", Toast.LENGTH_LONG).show();
					}
					sv.clear();
					signatureRL.setVisibility(View.INVISIBLE);
					try{
						Bitmap signature = getSavedSignature(prefix + "_signature.jpg");
						if(signature != null){
							bdf.signature.setImageBitmap(signature);
						}else{
							dialog = new MyDialog(RegisterContributor.this, null, "Sorry", 
									"couldn't retrieve signature image", "OK"); 
							dialog.show();
							dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View arg0) {
									CommonOps.playClickSound(RegisterContributor.this);
									dialog.dismiss();
								}					
							});
							
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
				dialog = new MyDialog(this, null, "Hold it!", "You haven't signed, you know?", "OK");

				dialog.show();
				dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						CommonOps.playClickSound(RegisterContributor.this);
						dialog.dismiss();
					}					
				});
			}			

			break;

		default: break;
		}
	}

	//*******************************************************************************************
	//The Image handling methods
	//********************************************************************************************

	public Bitmap getSavedSignature(String fileName){
		Bitmap signature;
		try {
			FileInputStream fis = openFileInput(fileName);
			signature = BitmapFactory.decodeStream(fis);
			return signature;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void getLeftThumb(String filePath){
		Bitmap leftThumb;
		FileInputStream fis;
		try {
			fis = openFileInput(filePath);
			leftThumb = BitmapFactory.decodeStream(fis);
			bdf.leftThumb.setImageBitmap(leftThumb);
		} catch (FileNotFoundException e) {
			try {
				InputStream is = getAssets().open("images/LEFT.bmp");
				leftThumb = BitmapFactory.decodeStream(is);
				if(saveImage(leftThumb, prefix + "_leftThumb.jpg")){
					bdf.leftThumb.setImageBitmap(leftThumb);
					left_thumb_directory = prefix + "_leftThumb.jpg";
					CommonOps.saveValue("LEFT_THUMB", left_thumb_directory, bPrefsEditor);					
				}else{
					Toast.makeText(this, "Sorry, couldn't save left thumb image", Toast.LENGTH_LONG).show();
				}


			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void getRightThumb(String filePath){
		Bitmap rightThumb;
		FileInputStream fis;
		try {
			fis = openFileInput(filePath);
			rightThumb = BitmapFactory.decodeStream(fis);
			bdf.rightThumb.setImageBitmap(rightThumb);
		} catch (FileNotFoundException e) {
			try {
				InputStream is = getAssets().open("images/RIGHT.bmp");
				rightThumb = BitmapFactory.decodeStream(is);
				if(saveImage(rightThumb, prefix + "_rightThumb.jpg")){
					bdf.rightThumb.setImageBitmap(rightThumb);
					right_thumb_directory = prefix + "_rightThumb.jpg";
					CommonOps.saveValue("RIGHT_THUMB", right_thumb_directory, bPrefsEditor);
				}else{
					Toast.makeText(this, "Sorry, couldn't save right thumb image", Toast.LENGTH_LONG).show();
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public boolean saveImage(Bitmap bitmap, String fileName){
		
		if(!controlNumber.equals("N/A")){
			try {				
				FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);				
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//complain
			dialog = new MyDialog(this, null, "Hey!", "Trying to save image while control number is not set", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					CommonOps.playClickSound(RegisterContributor.this);
					dialog.dismiss();
				}

			});
		}
		return false;
	}

	//********************************************************************************************
	//The Image handling methods end here
	//********************************************************************************************

	
}
