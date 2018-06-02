package com.sigmapensions.sigmamobileapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;
import com.sigmapensions.sigmamobileapp.utils.PostXML;
import com.sigmapensions.sigmamobileapp.utils.SaveContributorDetails;
import com.sigmapensions.sigmamobileapp.utils.WelcomeLetterPDF;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

public class ResponsePage extends Activity {

	private String[] responseList = {"New Response", "View all contributors", "Register new contributor", "Exit"};
	ListView responseLV;
	ArrayAdapter<String> adapter;

	ImageView responsePagePassport;

	TextView idSolicitValueTV;
	TextView rsanoPinValueTV;
	TextView formReferenceNoValueTV;
	TextView valueDateValueTV;
	TextView commentValueTV;

	Button viewWelcomeLetter;

	boolean gotPin;
	boolean sentMessage;
	boolean welcomeLetter;

	LinearLayout welcomeLetterLL;
	RelativeLayout welcomeLetterRL;

	Button okButton;

	SharedPreferences pdPrefs;
	SharedPreferences.Editor editor;

	SharedPreferences nokPrefs;

	SharedPreferences edPrefs;

	SharedPreferences bPrefs;

	SharedPreferences pinDetailsPrefs;

	ScrollView right_scrollview;

	SQLiteDatabase database;

	String rsaPin;
	String contributorAccount;
	String contributorFileID;
	String contributorName;	
	String contributorPhone;
	String contributorEmail;

	Button logout;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.response_page_activity);

		pdPrefs = CommonOps.getPdPrefs(this);
		nokPrefs = CommonOps.getNOKPrefs(this);
		edPrefs = CommonOps.getEdPrefs(this);
		bPrefs = CommonOps.getbPrefs(this);
		pinDetailsPrefs = CommonOps.getPinDetailsPrefs(this);

		responseLV = (ListView)findViewById(R.id.response_list);
		adapter = new ArrayAdapter<String>(ResponsePage.this, R.layout.left_list_layout, responseList);
		responseLV.setAdapter(adapter);

		responsePagePassport = (ImageView)findViewById(R.id.responsePagePassport);
		Bitmap bitmap = CommonOps.getPassportBitmap(this);
		if(bitmap != null)
			responsePagePassport.setImageBitmap(bitmap);

		idSolicitValueTV = (TextView)findViewById(R.id.idSolicitValueTV);
		rsanoPinValueTV = (TextView)findViewById(R.id.rsanoPinValueTV);
		formReferenceNoValueTV = (TextView)findViewById(R.id.formReferenceNoValueTV);
		valueDateValueTV = (TextView)findViewById(R.id.valueDateValueTV);
		commentValueTV = (TextView)findViewById(R.id.commentValueTV);

		right_scrollview = (ScrollView)findViewById(R.id.right_scrollview);
		viewWelcomeLetter = (Button)findViewById(R.id.viewWelcomeLetter);

		welcomeLetterLL = (LinearLayout)findViewById(R.id.viewWelcomeLetterLL);
		welcomeLetterRL = (RelativeLayout)findViewById(R.id.welcomeLetterRL);

		logout = (Button)findViewById(R.id.logoutButton);
		logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ResponsePage.this);
				CommonOps.logout(ResponsePage.this);
			}

		});

		okButton = (Button)findViewById(R.id.OKButton);

		okButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ResponsePage.this);
				welcomeLetterLL.setVisibility(View.INVISIBLE);
				right_scrollview.scrollTo(0, 0);
			}

		});

		database = openOrCreateDatabase(MobileRegDatabase.DATABASE_NAME, MODE_PRIVATE, null);

		if(setResponseValues()){
			sendConfirmationMessage();
//			if(generateWelcomeLetter())
//				Toast.makeText(this, "Welcome letter successfully generated", Toast.LENGTH_LONG).show();
//			else
//				Toast.makeText(this, "Sorry, welcome letter not generated", Toast.LENGTH_LONG).show();
		}

		new Handler().postDelayed(new Runnable(){
			public void run(){
				boolean save = new SaveContributorDetails(database).save(ResponsePage.this, gotPin, sentMessage, welcomeLetter);
				if(save)
					Toast.makeText(ResponsePage.this, "Contributor details saved successfully", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(ResponsePage.this, "Sorry, there was a problem saving contributor details", Toast.LENGTH_LONG).show();
				database.close();
			}
		}, 2000);



		responseLV.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				CommonOps.playClickSound(ResponsePage.this);

				if(responseList[position].equals("Exit")){
					CommonOps.exitApp(ResponsePage.this, editor);

				}else if(responseList[position].equals("View all contributors")){
					Intent intent = new Intent(ResponsePage.this, RegisteredContributors.class);
					startActivity(intent);
					CommonOps.enterFromRight(ResponsePage.this);
					finish();
				}else if(responseList[position].equals("Register new contributor")){
					Intent intent = new Intent(ResponsePage.this, RegisterContributor.class);
					startActivity(intent);
					CommonOps.enterFromLeft(ResponsePage.this);
					finish();
				}

			}			
		});

		viewWelcomeLetter.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ResponsePage.this);
				File file = new File(CommonOps.getPrefixString(ResponsePage.this) + "_welcomeLetter.pdf");

			//if(file.exists()){
				showWelcomeLetter();		
			//}else{
			//	Toast.makeText(ResponsePage.this, 
			//			"No welcome letter for this contributor", Toast.LENGTH_LONG).show();
			//}

			}			

		});
	}

	private void showWelcomeLetter(){
		TextView dateTV = (TextView)findViewById(R.id.dateTV);
		TextView nameTV = (TextView)findViewById(R.id.nameTV);
		TextView telNoTV = (TextView)findViewById(R.id.telNoTV);
		TextView fileIdTV = (TextView)findViewById(R.id.fileIdTV);
		TextView emailTV = (TextView)findViewById(R.id.emailTV);
		TextView greetingTV = (TextView)findViewById(R.id.greetingTV);
		ImageView passportIV = (ImageView)findViewById(R.id.passportIV);
		TextView accountNoTV = (TextView)findViewById(R.id.accountNoTV);
		TextView rsaPinTV = (TextView)findViewById(R.id.rsaPinTV);
		//ImageView signatureIV = (ImageView)findViewById(R.id.signatureIV);

		setContributorDetails();

		dateTV.setText(new SimpleDateFormat("dd MMMM, yyyy").format(new Date()));
		nameTV.setText(contributorName);
		telNoTV.setText(contributorPhone);
		fileIdTV.setText(contributorFileID);
		emailTV.setText(contributorEmail);
		greetingTV.setText("Dear " + contributorName + ",");
		accountNoTV.setText("Account No: " + contributorAccount);
		rsaPinTV.setText("RSA PIN: " + rsaPin);

		Bitmap passportBitmap = CommonOps.getPassportBitmap(ResponsePage.this);
		if(passportBitmap == null){
			passportBitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.passport_place));
		}
		passportIV.setImageBitmap(passportBitmap);

		CommonOps.playClickSound(ResponsePage.this);
		welcomeLetterLL.setVisibility(View.VISIBLE);		
	}

	private boolean generateWelcomeLetter() {

		Image companyLogo = null;
		Image signature = null;
		Image passport = null;

		try {
			//for the company logo
			Drawable d = getResources().getDrawable(R.drawable.sigma_header); 
			Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] bitmapdata = stream.toByteArray();
			stream.flush();

			companyLogo = Image.getInstance(bitmapdata);

			//for the signature
			d = getResources().getDrawable(R.drawable.authorized_signature); 
			bitmap = ((BitmapDrawable)d).getBitmap();
			stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			bitmapdata = stream.toByteArray();
			stream.flush();

			signature = Image.getInstance(bitmapdata);

			//for the passport
			try{
				FileInputStream fis = openFileInput(CommonOps.getPrefixString(this) + "_passport.jpg");
				bitmapdata = new byte[fis.available()];
				fis.read(bitmapdata);
				fis.close();
			}catch(FileNotFoundException e){
				d = getResources().getDrawable(R.drawable.passport_place); 
				bitmap = ((BitmapDrawable)d).getBitmap();
				stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				bitmapdata = stream.toByteArray();
				stream.flush();
			}catch(IOException e){
				e.printStackTrace();
			}

			passport = Image.getInstance(bitmapdata);

			FileOutputStream fos = openFileOutput(CommonOps.getPrefixString(this) + "_welcomeLetter.pdf", MODE_PRIVATE);

			setContributorDetails();
			
			//for now password is unknown		
			new WelcomeLetterPDF(ResponsePage.this, fos, companyLogo, signature, passport, contributorAccount, contributorFileID, 
					contributorName, contributorPhone, contributorEmail, rsaPin).generateWelcomeLetter();
			welcomeLetter = true;
			return welcomeLetter;


		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		welcomeLetter = false;
		return welcomeLetter;
	}

	private void sendConfirmationMessage() {
		/*
		 * bulk sms api key----b40249124c0bbf8b658d9946780687dbab072306
		 *email dgenius2005@yahoo.com
		 *oche moses
		 *
		 *bulk sms api key----e5f939a10f6f28fb9ceae5ef4e7d4b91ec944b1f
		 *email ocmoses@yahoo.com
		 *moses oche
		 *
		 *bulk sms api key----a00d00c0f1819edf7e533cb1b648ad3b0d122698
		 *email mosesocho@gmail.com
		 *ocho moses
		 */
		contributorPhone = pdPrefs.getString("pdPhone1ET", "0");
		if(!contributorPhone.equals("0")){		
			new sendSMS().execute();
		}

	}

	private boolean setResponseValues() {

		if(!pinDetailsPrefs.getAll().isEmpty()){
			idSolicitValueTV.setText(pinDetailsPrefs.getString("ID_SOLICITUD", "Not available"));
			rsanoPinValueTV.setText(pinDetailsPrefs.getString("RSANO_PIN", "Not available"));
			formReferenceNoValueTV.setText(pinDetailsPrefs.getString("FORMREFERENCENO", "Not available"));
			valueDateValueTV.setText(pinDetailsPrefs.getString("VALUEDATE", "Not available"));
			commentValueTV.setText(pinDetailsPrefs.getString("COMMENT", "Not available"));

			if(!rsanoPinValueTV.getText().toString().equals("Not available")){
				gotPin = true;
				return gotPin;
			}

		}
		else{
			Toast.makeText(this, "Some error occured, sorry", Toast.LENGTH_LONG).show();
		}
		return false;
	}

	private void setContributorDetails(){
		rsaPin = pinDetailsPrefs.getString("RSANO_PIN", "");
		contributorAccount = pdPrefs.getString("controlNumberET", "");
		contributorFileID = edPrefs.getString("edFileIdNumberET", "");
		if(!pdPrefs.getString("pd_MiddleNameET", "").equals(""))
			contributorName = pdPrefs.getString("titleSpinner", "") + " " + pdPrefs.getString("pd_firstNameET", "").substring(0, 1) + 
			" " + pdPrefs.getString("pd_MiddleNameET", "").substring(0, 1) + " " + pdPrefs.getString("pd_surnameET", "");
		else
			contributorName = pdPrefs.getString("titleSpinner", "") + " " + pdPrefs.getString("pd_firstNameET", "").substring(0, 1) + 
			" " + pdPrefs.getString("pd_surnameET", "");	
		contributorPhone = pdPrefs.getString("pdPhone1ET", "");
		if(!pdPrefs.getString("pdEmailET", "").equals(""))
			contributorEmail = pdPrefs.getString("pdEmailET", "");
		else
			contributorEmail = "NIL";
	}

	class sendSMS extends AsyncTask<String, Void, String>{

		String username;
		String apikey;
		String sendername;
		String message;
		String[] recipients = new String[1];
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			setContributorDetails();
			
			username = "mosesocho@gmail.com";
			apikey = "a00d00c0f1819edf7e533cb1b648ad3b0d122698";
			sendername = "SPensions";
			message = "Welcome, you have been registered! \nThese are your details: \nbla bla bla...";
			recipients[0] = contributorPhone;//Normally, we'll send this from the shared preferences phone1
		}


		@Override
		protected String doInBackground(String... params) {
			return PostXML.send(username, apikey, sendername, message, recipients);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result.contains("SUCCESS")){
				sentMessage = true;
				Toast.makeText(ResponsePage.this, "Message sent successfully!", Toast.LENGTH_LONG).show();
			}else{
				sentMessage = false;
				Toast.makeText(ResponsePage.this, "Message sending failed", Toast.LENGTH_LONG).show();

			}
			Log.e("The PostXML response", result);
		}


	}
}
