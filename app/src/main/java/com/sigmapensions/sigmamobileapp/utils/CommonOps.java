package com.sigmapensions.sigmamobileapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.sigmapensions.sigmamobileapp.LoginActivity;
import com.sigmapensions.sigmamobileapp.MobileRegDatabase;
import com.sigmapensions.sigmamobileapp.MyDialog;
import com.sigmapensions.sigmamobileapp.R;
import com.sigmapensions.sigmamobileapp.RegisterContributor;


public class CommonOps {

	public static final String personalDataString = "personal data";
	public static SharedPreferences pdPrefs;

	public static final String nokString = "nok prefs";
	public static SharedPreferences nokPrefs;

	public static final String edString = "ed String";
	public static SharedPreferences edPrefs;

	public static final String pinDetails = "pin details";
	public static SharedPreferences pinDetailsPrefs;

	public static final String cString = "c String";
	public static SharedPreferences cPrefs;

	public static final String biometricString = "biometric String";
	public static SharedPreferences bPrefs;

	public static SharedPreferences.Editor editor;

	public static MyDialog dialog;

	public static SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	public static HashMap<String, Integer> soundMap = new HashMap<String, Integer>();
	public static final String CLICK = "CLICK_SOUND";
	public static final String ERROR = "ERROR_SOUND";
	public static final String WARNING = "WARNING_SOUND";


	//Drawable warning = context.getResources().getDrawable(R.drawable.warning_logo);

	/*==================================================================================
	 * Obtaining the shared preferences methods
	 *==================================================================================*/

	public static SharedPreferences getPdPrefs(Context context){
		return context.getSharedPreferences(personalDataString, 0);
	}

	public static SharedPreferences getNOKPrefs(Context context){
		return context.getSharedPreferences(nokString, 0);
	}	

	public static SharedPreferences getEdPrefs(Context context){
		return context.getSharedPreferences(edString, 0);
	}

	public static SharedPreferences getCPrefs(Context context){
		return context.getSharedPreferences(cString, 0);
	}

	public static SharedPreferences getbPrefs(Context context){
		return context.getSharedPreferences(biometricString, 0);
	}

	public static SharedPreferences getPinDetailsPrefs(Context context){
		return context.getSharedPreferences(pinDetails, 0);
	}

	/*==================================================================================
	 * End of obtaining the shared preferences methods
	 *==================================================================================*/



	/*==================================================================================
	 * Validation methods
	 *==================================================================================*/

	public static boolean containsOnlyLetters(String string){
		for(int i = 0; i < string.length();i++){
			if(!Character.isLetter(string.charAt(i)))
				return false;
		}
		return true;
	}

	public static String convertToSQLString(String string){
		String result = string.replace("[=\"]", "");
		result = result.replace("'", "\'");
		return result;
	}

	public static void saveValue(String location, String value, SharedPreferences.Editor editor){
		editor.remove(location);
		editor.putString(location, value);
		editor.commit();
	}

	public static void declareInvalidDate(final Context context){
		playErrorSound(context);
		Drawable stop = context.getResources().getDrawable(R.drawable.stop);
		dialog = new MyDialog(context, stop, "Invalid date", 
				"The date you entered is invalid, please check", "OK");
		dialog.show();
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				playClickSound(context);
				dialog.dismiss();
			}

		});
	}

	public static boolean validateDate(final Context context, String dateString, 
			SharedPreferences.Editor editor, String saveLocation){
		if(dateString.equals("")){
			CommonOps.playErrorSound(context);
			Drawable stop = context.getResources().getDrawable(R.drawable.stop);
			if(saveLocation.equals("dateOfBirthET") || saveLocation.equals("nokDateOfBirthET"))
				dialog = new MyDialog(context, stop, "Empty date field", 
						"The date of birth field cannot be empty", "OK");
			else if(saveLocation.equals("edDateOfFirstEmploymentET"))
				dialog = new MyDialog(context, stop, "No date of employment?", 
						"The date of employment field cannot be empty, please", "OK");
			else if(saveLocation.equals("edDateOfConfirmationET"))
				dialog = new MyDialog(context, stop, "No date of confirmation?", 
						"The date of confirmation field cannot be empty, please", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					playClickSound(context);
					dialog.dismiss();
				}

			});
			return false;
		}else if(dateString.matches("\\d{2}/\\d{2}/\\d{4}") || dateString.matches("\\d{1}/\\d{1}/\\d{4}") 
				|| dateString.matches("\\d{1}/\\d{2}/\\d{4}") || dateString.matches("\\d{2}/\\d{1}/\\d{4}")){
			String[] tokens = dateString.split("/");
			String day = tokens[0];
			String month = tokens[1];
			String year = tokens[2];
			try{
				if(Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31){
					CommonOps.declareInvalidDate(context);
					return false;
				}else if(Integer.parseInt(month) == 2 && Integer.parseInt(day) > 29){
					CommonOps.declareInvalidDate(context);
					return false;
				}else if((Integer.parseInt(month) == 4 || Integer.parseInt(month) == 6
						|| Integer.parseInt(month) == 9 || Integer.parseInt(month) == 11) 
						&& Integer.parseInt(day) > 30){
					CommonOps.declareInvalidDate(context);
					return false;
				}else if(Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12){
					CommonOps.declareInvalidDate(context);
					return false;
				}else if((Integer.parseInt(year) < 1946 || Integer.parseInt(year) > 1998) 
						&& saveLocation.equals("dateOfBirthET")){
					CommonOps.declareInvalidDate(context);
					return false;
				}else{
					if(day.length() == 1)
						day = "0" + day;
					if(month.length() == 1)
						month = "0" + month;
					CommonOps.saveValue(saveLocation, day + "/" + month + "/" + year, editor);				
				}
			}catch(Exception e){
				CommonOps.declareInvalidDate(context);
				return false;
			}

		}else{
			CommonOps.declareInvalidDate(context);
			return false;
		}
		return true;
	}

	public static boolean emailValidate(final Context context, String email, 
			SharedPreferences.Editor editor, String saveLocation){
		Drawable stop = context.getResources().getDrawable(R.drawable.stop);
		if(!email.equals("") ){
			if(email.contains("@") && email.contains(".") 
					&& email.indexOf('@') < email.indexOf('.')){
				CommonOps.saveValue(saveLocation, email, editor);
			}else{
				CommonOps.playErrorSound(context);
				dialog = new MyDialog(context, stop, "Invalid email", 
						"Please check the email address you entered.", "OK");
				dialog.show();
				dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						playClickSound(context);
						dialog.dismiss();					
					}

				});
				return false;
			}
		}else{
			CommonOps.saveValue(saveLocation, email, editor);
		}
		return true;
	}

	public static void saveSQLString(String entry, SharedPreferences.Editor editor, String saveLocation){
		if(!entry.equals("")){
			CommonOps.saveValue(saveLocation, CommonOps.convertToSQLString(entry), editor);
		}else{
			CommonOps.saveValue(saveLocation, "", editor);
		}		
	}

	/*==================================================================================
	 * End of validation methods
	 *==================================================================================*/




	/*==================================================================================
	 * Utility methods
	 *==================================================================================*/

	public static String getDate(){
		return (new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString();
	}

	public static String getDateString(){
		return (new SimpleDateFormat("ddMMyyyy").format(new Date())).toString();
	}

	public static String getPrefixString(Context context){
		return getDateString() + getPdPrefs(context).getString("controlNumberET", "N/A");
	}

	/*==================================================================================
	 * End of Utility methods
	 *==================================================================================*/




	/*==================================================================================
	 *Encoding images methods 
	 *==================================================================================*/

	public static String getEncodedPassport(Context context){
		if(getImageEncodedString(context, "passport") == null){
			Bitmap passportBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.passport_place);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			passportBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] bitmapdata = stream.toByteArray();
			try {
				stream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String encoded = new String(Base64.encodeBytes(bitmapdata));
			Log.e("PASSPORT_PLACE_BECAUSE", "yes image was null");
			Log.e("PASSPORT_PLACE_STRING", encoded);
			return encoded;
		}

		return getImageEncodedString(context, "passport");
	}

	public static String getEncodedLeftThumb(Context context){
		return getImageEncodedString(context, "leftThumb");
	}

	public static String getEncodedRightThumb(Context context){
		return getImageEncodedString(context, "rightThumb");
	}

	public static String getEncodedSignature(Context context){
		return getImageEncodedString(context, "signature");
	}

	/*==================================================================================
	 *End of encoding images methods 
	 *==================================================================================*/




	/*==================================================================================
	 *Get images bitmap methods 
	 *==================================================================================*/

	public static Bitmap getPassportBitmap(Context context){
		return getBitmap(context, "passport");
	}

	public static Bitmap getLeftThumbBitmap(Context context){
		return getBitmap(context, "leftThumb");
	}

	public static Bitmap getRightThumbBitmap(Context context){
		return getBitmap(context, "rightThumb");
	}

	public static Bitmap getSignatureBitmap(Context context){
		return getBitmap(context, "signature");
	}

	/*==================================================================================
	 *End of get images bitmap methods 
	 *==================================================================================*/



	private static String getImageEncodedString(Context context, String image){
		String encoded = null;
		try{
			Log.e("PATH", getPrefixString(context) + "_" + image + ".jpg");
			FileInputStream fis = context.openFileInput(getPrefixString(context) + "_" + image + ".jpg");

			byte[] bitmapdata = new byte[fis.available()];
			fis.read(bitmapdata);
			encoded = new String(Base64.encodeBytes(bitmapdata));

			fis.close();
			Log.e("Encoded " + image, encoded);
			return encoded;

		}catch(FileNotFoundException e){
			Log.e("FOE ", getPrefixString(context) + "_" + image + ".jpg" + " wasn't found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	private static Bitmap getBitmap(Context context, String image){
		Bitmap bitmap = null;
		try{
			Log.e("PATH", getPrefixString(context) + "_" + image + ".jpg");
			FileInputStream fis = context.openFileInput(getPrefixString(context) + "_" + image + ".jpg");
			bitmap = BitmapFactory.decodeStream(fis);
			fis.close();
			if(bitmap == null)
				Log.e("Is Bitmap null?", "Yes");
			return bitmap;
		}catch(FileNotFoundException e){

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getBase64String(String image, Context context){
		if(image.equals("passport")){
			return context.getResources().getString(R.string.passportString);
		}else if(image.equals("leftThumb")){
			return context.getResources().getString(R.string.leftThumbString);
		}else if(image.equals("rightThumb")){
			return context.getResources().getString(R.string.rightThumbString);
		}else if(image.equals("signature")){
			return context.getResources().getString(R.string.signatureString);
		}
		return null;

	}




	/*==================================================================================
	 *Exiting app method
	 *==================================================================================*/

	public static void exitApp(final Activity activity, SharedPreferences.Editor editor){

		new Handler().postDelayed(new Runnable(){
			public void run(){
				playWarningSound(activity);
			}
		}, 500);
		dialog = new MyDialog(activity, null, "Quit App?", "Are you sure you want to quit the application?" + 
				"\nYou will be automatically logged out as well.", "No", "Yes");
		dialog.show();
		dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				playClickSound(activity);
				dialog.dismiss();
			}

		});
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				playClickSound(activity);
				clearPreferences(activity);
				activity.finish();
				dialog.dismiss();
			}

		});

	}
	
	public static void exitApp(final Activity activity){

		new Handler().postDelayed(new Runnable(){
			public void run(){
				playWarningSound(activity);
			}
		}, 500);
		dialog = new MyDialog(activity, null, "Quit App?", "Are you sure you want to quit the application?", 
				"No", "Yes");
		dialog.show();
		dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				playClickSound(activity);
				dialog.dismiss();
			}

		});
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				playClickSound(activity);
				activity.finish();
				dialog.dismiss();
			}

		});

	}
	/*==================================================================================
	 *End of exiting app method
	 *==================================================================================*/




	/*==================================================================================
	 *Logging out methods
	 *==================================================================================*/

	public static void logout(final Activity activity){

		new Handler().postDelayed(new Runnable(){
			public void run(){
				playWarningSound(activity);
			}
		}, 500);
		dialog = new MyDialog(activity, null, "Logging out?", "Are you sure you want to logout?", "No", "Yes");
		dialog.show();
		dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				playClickSound(activity);
				dialog.dismiss();
			}

		});
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				playClickSound(activity);
				clearPreferences(activity);
				Intent intent = new Intent(activity, LoginActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}

		});

	}

	public static void logout(final Activity activity, boolean save){

		new Handler().postDelayed(new Runnable(){
			public void run(){
				playWarningSound(activity);
			}
		}, 500);
		dialog = new MyDialog(activity, null, "Logging out?", "Are you sure you want to logout?", "No", "Yes");
		dialog.show();
		dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				playClickSound(activity);
				dialog.dismiss();
			}

		});
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				playClickSound(activity);
				dialog.dismiss();

				playWarningSound(activity);
				dialog = new MyDialog(activity, null, "Save?", "Do you wish to save contributor details before logout?" + 
						"\nDetails will be cleared on logout.", "No", "Yes");
				dialog.show();
				dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						playClickSound(activity);
						dialog.dismiss();
						clearPreferences(activity);
						Intent intent = new Intent(activity, LoginActivity.class);
						activity.startActivity(intent);
						enterFromLeft(activity);
						activity.finish();
					}

				});
				dialog.getPositiveButton().setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						playClickSound(activity);
						dialog.dismiss();
						SQLiteDatabase database = activity.openOrCreateDatabase(MobileRegDatabase.DATABASE_NAME, activity.getBaseContext().MODE_PRIVATE, null);
						boolean save = new SaveContributorDetails(database).save(activity, false, false, false);
						if(save){
							Toast.makeText(activity, "The contributor details was saved successfully.", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(activity, "Sorry, failed to contributor details.", Toast.LENGTH_LONG).show();
						}
						database.close();
						clearPreferences(activity);
						Intent intent = new Intent(activity, LoginActivity.class);
						activity.startActivity(intent);
						enterFromLeft(activity);
						activity.finish();
					}

				});

			}

		});

	}
	
	public static void displaySaveDialogToSaveContributorDetails(final Activity activity){
		playWarningSound(activity);
		dialog = new MyDialog(activity, null, "Save?", "Do you wish to save contributor details?", "No", "Yes");
		dialog.show();
		dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				playClickSound(activity);
				dialog.dismiss();
				clearPreferences(activity);
				Intent intent = new Intent(activity, RegisterContributor.class);
				activity.startActivity(intent);
				enterFromLeft(activity);
				activity.finish();
			}

		});
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				playClickSound(activity);
				dialog.dismiss();
				SQLiteDatabase database = activity.openOrCreateDatabase(MobileRegDatabase.DATABASE_NAME, activity.getBaseContext().MODE_PRIVATE, null);
				boolean save = new SaveContributorDetails(database).save(activity, false, false, false);
				if(save){
					Toast.makeText(activity, "The contributor details was saved successfully.", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(activity, "Sorry, failed to contributor details.", Toast.LENGTH_LONG).show();
				}
				database.close();
				clearPreferences(activity);
				Intent intent = new Intent(activity, RegisterContributor.class);
				activity.startActivity(intent);
				enterFromLeft(activity);
				activity.finish();
			}

		});

	}
	/*==================================================================================
	 *End of logging out methods
	 *==================================================================================*/




	/*==================================================================================
	 *Clearing preferences method
	 *==================================================================================*/

	public static void clearPreferences(Activity activity){
		if(activity != null){
			SharedPreferences.Editor editor = getPdPrefs(activity.getBaseContext()).edit();
			editor.clear();
			editor.commit();
			editor =  getNOKPrefs(activity.getBaseContext()).edit();
			editor.clear();
			editor.commit();
			editor = getEdPrefs(activity.getBaseContext()).edit();
			editor.clear();
			editor.commit();
			editor = getbPrefs(activity.getBaseContext()).edit();
			editor.clear();
			editor.commit();
			editor = getPinDetailsPrefs(activity.getBaseContext()).edit();
			editor.clear();
			editor.commit();
		}		
	}
	/*==================================================================================
	 *End of clearing preferences method
	 *==================================================================================*/

	
	//This method complains if there is no control number
	public static void complainAboutControlNumber(final Context context){
		dialog = new MyDialog(context, null, "Hey!", "What do you mean?\nThe contributor has no control number?" + 
				"\nCan't save without Control Number.\nSorry...", "OK");
		dialog.show();
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				playClickSound(context);
				dialog.dismiss();
			}

		});
	}

	/*==================================================================================
	 *SoundPool methods
	 *==================================================================================*/

	public static void loadSounds(Context context){
		if(soundMap.isEmpty()){
			soundMap.put(CLICK, soundPool.load(context, context.getResources().getIdentifier("clicksound", "raw", context.getPackageName()), 1));
			soundMap.put(ERROR, soundPool.load(context, context.getResources().getIdentifier("errorsound", "raw", context.getPackageName()), 1));
			soundMap.put(WARNING, soundPool.load(context, context.getResources().getIdentifier("warningsound", "raw", context.getPackageName()), 1));
		}
	}

	public static void playClickSound(Context context){
		loadSounds(context);
		soundPool.play(soundMap.get(CLICK), 1, 1, 1, 0, 1.0f);
	}
	public static void playWarningSound(Context context){
		loadSounds(context);
		soundPool.play(soundMap.get(WARNING), 1, 1, 1, 0, 1.0f);
	}
	public static void playErrorSound(Context context){
		loadSounds(context);
		soundPool.play(soundMap.get(ERROR), 1, 1, 1, 0, 1.0f);
	}

	/*==================================================================================
	 *End of soundPool methods
	 *==================================================================================*/

	
	
	
	/*==================================================================================
	 *Activity transition methods
	 *==================================================================================*/

	public static void enterFromRight(Activity activity){
		activity.overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
	}

	public static void enterFromLeft(Activity activity){
		activity.overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
	}

	/*==================================================================================
	 *End of Activity transition methods
	 *==================================================================================*/
}
