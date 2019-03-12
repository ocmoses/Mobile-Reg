package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;
import com.sigmapensions.sigmamobileapp.utils.States;

public class NOKFragment extends Fragment implements OnFocusChangeListener, OnCheckedChangeListener{

	Spinner nokTitleSpinner;
	EditText nokSurnameET;
	EditText nokFirstNameET;
	EditText nokMiddleNameET;
	EditText nokDateOfBirthET;	

	RadioGroup nok_radiogroup;
	RadioButton nokSexMale;
	RadioButton nokSexFemale;
	
	Spinner nokRelationshipSpinner;

	EditText nokPhoneNumberET;	
	EditText nokEmailET;
	EditText nokContactAddress1ET;
	EditText nokContactAddress2ET;
	Spinner nokCountrySpinner;
	Spinner nokStateSpinner;
	Spinner nokLgaSpinner;
	
	Button nok_back;
	Button nok_proceed;

	ArrayAdapter<String> nokTitleSpinnerAdapter;
	ArrayAdapter<String> nokRelationshipSpinnerAdapter;	
	ArrayAdapter<String> nokCountrySpinnerAdapter;
	ArrayAdapter<String> nokStateSpinnerAdapter;
	ArrayAdapter<String> nokLgaSpinnerAdapter;

	SharedPreferences nokPrefs;
	SharedPreferences.Editor editor;
	
	NOKNav nokn;

	View view;

	MyDialog dialog;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			nokn = (NOKNav)activity;
		}catch(ClassCastException e){
			Log.e("Exception ho!!!", e.toString());
		}	

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		nokPrefs = CommonOps.getNOKPrefs(getActivity());
		editor = nokPrefs.edit();
		setVariableValues();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.nok_fragment, container, false);

		this.view = view;

		initializeVariables(view);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presetAllViews();
		setAllViewListeners();
	}

	private void setVariableValues() {

		if(nokPrefs.getAll().isEmpty()){
			
			editor.putString("nokTitleSpinner", "MR.");
			editor.putString("nokRelationshipSpinner", "SON");
			editor.putString("nok_radiogroup", "M");
			editor.putString("nokPhoneNumberET", "08012345678");
			editor.putString("nokDateOfBirthET", "01/01/2002");
			editor.putString("nokSurnameET", "DICKENS");
			editor.putString("nokFirstNameET", "CHARLIE");
			editor.putString("nokMiddleNameET", "");
			editor.putString("nokEmailET", "me@myemail.com");
			editor.putString("nokContactAddress1ET", "NEXT_OF_KIN_ADDRESS");
			editor.putString("nokContactAddress2ET", "NEXT_OF_KIN_CONTACT_ADDRESS");
			editor.putString("nokCountrySpinner", "NIGERIA");
			editor.putString("nokStateSpinner", "KEBBI");
			editor.putString("nokLgaSpinner", "ARGUNGU");
			
			editor.commit();
		}
	}


	private void initializeVariables(View view) {
		
		nokTitleSpinner = (Spinner)view.findViewById(R.id.nokTitleSpinner);
		nokRelationshipSpinner = (Spinner)view.findViewById(R.id.nokRelationshipSpinner);

		nok_radiogroup = (RadioGroup)view.findViewById(R.id.nok_radiogroup);
		nokSexMale = (RadioButton)view.findViewById(R.id.nokSexMale);
		nokSexFemale = (RadioButton)view.findViewById(R.id.nokSexFemale);

		nokPhoneNumberET = (EditText)view.findViewById(R.id.nokPhoneNumberET);
		nokDateOfBirthET = (EditText)view.findViewById(R.id.nokDateOfBirthET);
		nokSurnameET = (EditText)view.findViewById(R.id.nokSurnameET);
		nokFirstNameET = (EditText)view.findViewById(R.id.nokFirstNameET);
		nokMiddleNameET = (EditText)view.findViewById(R.id.nokMiddleNameET);
		nokEmailET = (EditText)view.findViewById(R.id.nokEmailET);
		nokContactAddress1ET = (EditText)view.findViewById(R.id.nokContactAddress1ET);
		nokContactAddress2ET = (EditText)view.findViewById(R.id.nokContactAddress2ET);
		nokCountrySpinner = (Spinner)view.findViewById(R.id.nokCountrySpinner);
		nokStateSpinner = (Spinner)view.findViewById(R.id.nokStateSpinner);
		nokLgaSpinner = (Spinner)view.findViewById(R.id.nokLgaSpinner);
		
		nok_back = (Button)view.findViewById(R.id.nok_back);
		nok_proceed = (Button)view.findViewById(R.id.nok_proceed);

		nokTitleSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.TITLES);
		nokTitleSpinner.setAdapter(nokTitleSpinnerAdapter);

		nokRelationshipSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.RELATIONSHIPS);
		nokRelationshipSpinner.setAdapter(nokRelationshipSpinnerAdapter);

		nokCountrySpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.COUNTRIES);
		nokCountrySpinner.setAdapter(nokCountrySpinnerAdapter);

		nokCountrySpinner.post(new Runnable(){
			public void run(){
				nokCountrySpinner.setSelection(nokCountrySpinnerAdapter.getPosition("Nigeria"));
			}			
		});
		
		nokStateSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.STATES);
		nokStateSpinner.setAdapter(nokStateSpinnerAdapter);

		nokLgaSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.NA);
		nokLgaSpinner.setAdapter(nokLgaSpinnerAdapter);
	}


	private void presetAllViews() {
		
		nokTitleSpinner.post(new Runnable(){
			public void run(){
				nokTitleSpinner.setSelection(nokTitleSpinnerAdapter.getPosition(nokPrefs.getString("nokTitleSpinner", "")));
			}
		});
		
		nokRelationshipSpinner.post(new Runnable(){
			public void run(){
				nokRelationshipSpinner.setSelection(nokRelationshipSpinnerAdapter.getPosition(nokPrefs.getString("nokRelationshipSpinner", "")));
			}
		});

		if(nokSexMale.getText().toString().equals(nokPrefs.getString("nok_radiogroup", ""))){
			nokSexMale.setChecked(true);
		}else if(nokSexFemale.getText().toString().equals(nokPrefs.getString("nok_radiogroup", ""))){
			nokSexFemale.setChecked(true);
		}

		nokPhoneNumberET.setText(nokPrefs.getString("nokPhoneNumberET", ""));
		nokDateOfBirthET.setText(nokPrefs.getString("nokDateOfBirthET", ""));
		nokSurnameET.setText(nokPrefs.getString("nokSurnameET", ""));
		nokFirstNameET.setText(nokPrefs.getString("nokFirstNameET", ""));
		nokMiddleNameET.setText(nokPrefs.getString("nokMiddleNameET", ""));
		nokEmailET.setText(nokPrefs.getString("nokEmailET", ""));
		nokContactAddress1ET.setText(nokPrefs.getString("nokContactAddress1ET", ""));
		nokContactAddress2ET.setText(nokPrefs.getString("nokContactAddress2ET", ""));
		
		nokCountrySpinner.post(new Runnable(){
			public void run(){
				nokCountrySpinner.setSelection(nokCountrySpinnerAdapter.getPosition(nokPrefs.getString("nokCountrySpinnerAdapter", "Nigeria")));
			}
		});	
		
		nokStateSpinner.post(new Runnable(){
			public void run(){
				nokStateSpinner.setSelection(nokStateSpinnerAdapter.getPosition(nokPrefs.getString("nokStateSpinnerAdapter", "N/A")));
			}
		});	
		
		nokLgaSpinner.post(new Runnable(){
			public void run(){
				nokLgaSpinner.setSelection(nokLgaSpinnerAdapter.getPosition(nokPrefs.getString("nokLgaSpinnerAdapter", "N/A")));
			}
		});	
		
		
	}

	private void setAllViewListeners() {
		
		nokTitleSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("nokTitleSpinner", nokTitleSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		nokRelationshipSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("nokRelationshipSpinner", nokRelationshipSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		nok_radiogroup.setOnCheckedChangeListener(this);
		nokPhoneNumberET.setOnFocusChangeListener(this);
		nokDateOfBirthET.setOnFocusChangeListener(this);
		nokSurnameET.setOnFocusChangeListener(this);
		nokFirstNameET.setOnFocusChangeListener(this);
		nokMiddleNameET.setOnFocusChangeListener(this);
		nokEmailET.setOnFocusChangeListener(this);
		nokContactAddress1ET.setOnFocusChangeListener(this);
		nokContactAddress2ET.setOnFocusChangeListener(this);
		
		nokCountrySpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("nokCountrySpinner", nokCountrySpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		
		nokCountrySpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("nokCountrySpinner", nokCountrySpinner.getItemAtPosition(position).toString(), editor);
								
				String country = States.COUNTRIES[position];
				if(!country.equals("NIGERIA")){
					
					nokStateSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.NA);
					nokStateSpinner.setAdapter(nokStateSpinnerAdapter);
					
					nokLgaSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.NA);
					nokLgaSpinner.setAdapter(nokLgaSpinnerAdapter);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		
		nokStateSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("nokStateSpinner", nokStateSpinner.getItemAtPosition(position).toString(), editor);
				
				String[] chosenLGAs = States.LGA.get(position);
				
				nokLgaSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, chosenLGAs);
				nokLgaSpinner.setAdapter(nokLgaSpinnerAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		
		nokLgaSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("nokLgaSpinner", nokLgaSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		nok_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(getActivity());
				nokn.backToPersonalData();
			}

		});

		nok_proceed.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(getActivity());
				if(validate())
					nokn.forwardToEmployerData();
			}		
		});


	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		
		if(!hasFocus){
			switch(v.getId()){
			
			case R.id.nokPhoneNumberET: 
				CommonOps.saveValue("nokPhoneNumberET", nokPhoneNumberET.getText().toString(), editor);
				break;

			case R.id.nokDateOfBirthET: 
				CommonOps.saveValue("nokDateOfBirthET", nokDateOfBirthET.getText().toString(), editor);
				break;

			case R.id.nokSurnameET: 
				CommonOps.saveValue("nokSurnameET", nokSurnameET.getText().toString(), editor);
			break;

			case R.id.nokFirstNameET: 
				CommonOps.saveValue("nokFirstNameET", nokFirstNameET.getText().toString(), editor);
			break;

			case R.id.nokMiddleNameET: 
				CommonOps.saveValue("nokMiddleNameET", nokMiddleNameET.getText().toString(), editor);
			break;

			case R.id.nokEmailET: 
				CommonOps.saveValue("nokEmailET", nokEmailET.getText().toString(), editor);
			break;

			case R.id.nokContactAddress1ET: 
				CommonOps.saveValue("nokContactAddress1ET", nokContactAddress1ET.getText().toString(), editor);
			break;

			case R.id.nokContactAddress2ET: 
				CommonOps.saveValue("nokContactAddress2ET", nokContactAddress2ET.getText().toString(), editor);
			break;

			default: break;
			}
		}
		
	}

	public interface NOKNav{
		public void backToPersonalData();
		public void forwardToEmployerData();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton checked = (RadioButton)view.findViewById(checkedId);
		CommonOps.saveValue("nok_radiogroup", checked.getText().toString(), editor);
	}
	
	boolean titleChecked;
	private boolean validate(){
		String enteredValue = null;
		Drawable stop = getResources().getDrawable(R.drawable.stop);
		Drawable warning = getResources().getDrawable(R.drawable.warning_logo);
		
		/*===========================================================================
		 *Title validation
		===========================================================================*/
		enteredValue = nokTitleSpinner.getSelectedItem().toString();
		if(enteredValue.equals("") && !titleChecked){
			CommonOps.playWarningSound(getActivity());
			dialog = new MyDialog(getActivity(), warning, "Empty title", 
					"The title field is empty, do you want to ignore?", "No", "Yes");
			dialog.show();
			dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();
				}

			});
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();
					CommonOps.saveValue("nokTitleSpinner", 
							nokTitleSpinner.getSelectedItem().toString(), editor);
				}

			});
			if(!titleChecked){				
				titleChecked = true;
				return false;
			}
		}else{
			CommonOps.saveValue("nokTitleSpinner", enteredValue, editor);
		}
		/*===========================================================================
		 *Title validation
		===========================================================================*/
		
		
		
		
		/*===========================================================================
		 *Surname validation
		===========================================================================*/
		enteredValue =  nokSurnameET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Empty surname Field", 
					"Sorry, the surname field cannot be empty", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();					
				}

			});
			return false;
		}else if(!CommonOps.containsOnlyLetters(enteredValue)){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Invalid surname", 
					"Sorry, the surname you entered is not a valid name", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();					
				}

			});
			return false;
		}else{
			CommonOps.saveValue("nokSurnameET", enteredValue, editor);
		}

		/*===========================================================================
		 *End Surname validation
		===========================================================================*/



		/*===========================================================================
		 *First name validation
		===========================================================================*/

		enteredValue =  nokFirstNameET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Empty name Field", 
					"Sorry, the first name field cannot be empty", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();					
				}

			});
			return false;
		}else if(!CommonOps.containsOnlyLetters(enteredValue)){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Invalid name", 
					"Sorry, the name you entered as first name is not valid", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();					
				}

			});
			return false;
		}else{
			CommonOps.saveValue("nokFirstNameET", enteredValue, editor);
		}

		/*===========================================================================
		 *End first name validation
		===========================================================================*/




		/*===========================================================================
		 *Middle name validation
		===========================================================================*/

		enteredValue =  nokMiddleNameET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.saveValue("nokMiddleNameET", enteredValue, editor);
		}else if(!CommonOps.containsOnlyLetters(enteredValue)){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Invalid middle name", 
					"Sorry, the name you entered as middle name is not valid", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();					
				}

			});
			return false;
		}else{
			enteredValue = String.valueOf(enteredValue.substring(0, 1)).toUpperCase() + 
					enteredValue.substring(1).toLowerCase();
			CommonOps.saveValue("nokMiddleNameET", enteredValue, editor);
		}

		/*===========================================================================
		 *End middle name validation
		===========================================================================*/




		/*===========================================================================
		 *Date of birth validation
		===========================================================================*/
		enteredValue =  nokDateOfBirthET.getText().toString();
		if(!CommonOps.validateDate(getActivity(), enteredValue, editor, "nokDateOfBirthET"))
			return false;
		/*===========================================================================
		 *End date of birth validation
		===========================================================================*/
		

		

		/*===========================================================================
		 *Phone number validation
		===========================================================================*/
		enteredValue = nokPhoneNumberET.getText().toString();
		if(enteredValue.equals("") || !enteredValue.equals("") && enteredValue.charAt(0) == '0' 
				&& enteredValue.length() == 11){
			CommonOps.saveValue("nokPhoneNumberET", enteredValue, editor);			
		}else{
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Invalid phone number", 
					"Please check the phone number you entered.", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();					
				}

			});
			return false;
		}
		/*===========================================================================
		 *End phone number validation
		===========================================================================*/
		
		
		
		
		/*===========================================================================
		 *Email validation
		===========================================================================*/
		enteredValue =  nokEmailET.getText().toString();
		if(!CommonOps.emailValidate(getActivity(), enteredValue, editor, "nokEmailET"))
			return false;
		
		/*===========================================================================
		 *End Email validation
		===========================================================================*/
				
		
		
		
		/*===========================================================================
		 *Current contact address 1 validation
		===========================================================================*/
		
		enteredValue = nokContactAddress1ET.getText().toString();
		CommonOps.saveSQLString(enteredValue, editor, "nokContactAddress1ET");
		/*===========================================================================
		 *End current contact address 1 validation
		===========================================================================*/
		
		
		
				
		/*===========================================================================
		 *Current contact address 2 validation
		===========================================================================*/
		
		enteredValue = nokContactAddress2ET.getText().toString();
		CommonOps.saveSQLString(enteredValue, editor, "nokContactAddress2ET");
		/*===========================================================================
		 *End current contact address 2 validation
		===========================================================================*/

		return true;
	}

}
