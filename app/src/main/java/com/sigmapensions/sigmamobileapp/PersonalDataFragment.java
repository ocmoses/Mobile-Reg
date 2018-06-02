package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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

public class PersonalDataFragment extends Fragment implements OnFocusChangeListener, 
OnCheckedChangeListener{

	EditText controlNumberET;
	EditText accountNumberET;
	Spinner titleSpinner;
	EditText dateOfBirthET;
	EditText pd_surnameET;
	EditText pd_firstNameET;
	EditText pd_MiddleNameET;
	Spinner maritalStatus;

	RadioGroup pd_sexRG;
	RadioButton pd_sexMale;
	RadioButton pd_sexFemale;

	Spinner placeOfBirthSpinner;
	Spinner nationalitySpinner;
	Spinner stateOfOriginSpinner;
	Spinner lgaOfOriginSpinner;
	EditText pdEmailET;
	EditText pdPhone1ET;
	EditText pdPhone2ET;
	EditText pdPresentAddressET;
	EditText pdPermanentHomeAddressET;
	Spinner stateOfpermanentAddressSpinner;
	Spinner lgaOfpermanentAddressSpinner;
	EditText pdCurrentContactAddress1ET;
	EditText pdCurrentContactAddress2ET;
	Spinner pdStateAddressSpinner;
	Spinner pdLgaAddressSpinner;

	Button pd_back;
	Button proceed;

	ArrayAdapter<String>titleSpinnerAdapter;
	ArrayAdapter<String> placeOfBirthSpinnerAdapter;
	ArrayAdapter<String> nationalitySpinnerAdapter;
	ArrayAdapter<String> maritalStatusSpinnerAdapter;
	ArrayAdapter<String> stateOfOriginSpinnerAdapter;
	ArrayAdapter<String> lgaOfOriginSpinnerAdapter;	
	ArrayAdapter<String> stateOfpermanentAddressSpinnerAdapter;
	ArrayAdapter<String> lgaOfpermanentAddressSpinnerAdapter;
	ArrayAdapter<String> pdStateAddressSpinnerAdapter;
	ArrayAdapter<String> pdLgaAddressSpinnerAdapter;

	SharedPreferences pdPrefs;
	SharedPreferences.Editor editor;

	ChangeToNOK ctnok;

	String chosenState;
	String[] chosenLGAs;
	String chosenLGA;

	View view;

	States state = new States();

	SQLiteDatabase database;

	MyDialog dialog;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			ctnok = (ChangeToNOK)activity;
		}catch(ClassCastException e){
			Log.e("Exception!!!!", e.toString() + "Didn't implement interface");
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

		pdPrefs = CommonOps.getPdPrefs(getActivity());
		editor = pdPrefs.edit();
		setVariableValues();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.personal_data_fragment, container, false);
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

	private void initializeVariables(final View view){

		controlNumberET = (EditText)view.findViewById(R.id.controlNumberET);
		accountNumberET = (EditText)view.findViewById(R.id.accountNumberET);
		titleSpinner = (Spinner)view.findViewById(R.id.titleSpinner);
		dateOfBirthET = (EditText)view.findViewById(R.id.dateOfBirthET);
		pd_surnameET = (EditText)view.findViewById(R.id.pd_surnameET);
		pd_firstNameET = (EditText)view.findViewById(R.id.pd_firstNameET);
		pd_MiddleNameET = (EditText)view.findViewById(R.id.pd_MiddleNameET);

		maritalStatus = (Spinner)view.findViewById(R.id.maritalStatus);

		pd_sexRG = (RadioGroup)view.findViewById(R.id.pd_sexRG);
		pd_sexMale = (RadioButton)view.findViewById(R.id.pd_sexMale);
		pd_sexFemale = (RadioButton)view.findViewById(R.id.pd_sexFemale);

		placeOfBirthSpinner = (Spinner)view.findViewById(R.id.placeOfBirthSpinner);
		nationalitySpinner = (Spinner)view.findViewById(R.id.nationalitySpinner);
		stateOfOriginSpinner = (Spinner)view.findViewById(R.id.stateOfOriginSpinner);
		lgaOfOriginSpinner = (Spinner)view.findViewById(R.id.lgaOfOriginSpinner);

		pdEmailET = (EditText)view.findViewById(R.id.pdEmailET);
		pdPhone1ET = (EditText)view.findViewById(R.id.pdPhone1ET);
		pdPhone2ET = (EditText)view.findViewById(R.id.pdPhone2ET);
		pdPresentAddressET = (EditText)view.findViewById(R.id.pdPresentAddressET);
		pdPermanentHomeAddressET = (EditText)view.findViewById(R.id.pdPermanentHomeAddressET);

		stateOfpermanentAddressSpinner = (Spinner)view.findViewById(R.id.stateOfpermanentAddressSpinner);

		lgaOfpermanentAddressSpinner = (Spinner)view.findViewById(R.id.lgaOfpermanentAddressSpinner);

		pdCurrentContactAddress1ET = (EditText)view.findViewById(R.id.pdCurrentContactAddress1ET);
		pdCurrentContactAddress2ET = (EditText)view.findViewById(R.id.pdCurrentContactAddress2ET);

		pdStateAddressSpinner = (Spinner)view.findViewById(R.id.pdStateAddressSpinner);

		pdLgaAddressSpinner = (Spinner)view.findViewById(R.id.pdLgaAddressSpinner);

		proceed = (Button)view.findViewById(R.id.proceed);

		titleSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.TITLES);
		titleSpinner.setAdapter(titleSpinnerAdapter);

		placeOfBirthSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.STATES);
		placeOfBirthSpinner.setAdapter(placeOfBirthSpinnerAdapter);

		nationalitySpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.COUNTRIES);
		nationalitySpinner.setAdapter(nationalitySpinnerAdapter);

		maritalStatusSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.MARRIED);
		maritalStatus.setAdapter(maritalStatusSpinnerAdapter);

		stateOfOriginSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.STATES);
		stateOfOriginSpinner.setAdapter(stateOfOriginSpinnerAdapter);

		lgaOfOriginSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.NA);
		lgaOfOriginSpinner.setAdapter(lgaOfOriginSpinnerAdapter);

		stateOfpermanentAddressSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.STATES);
		stateOfpermanentAddressSpinner.setAdapter(stateOfpermanentAddressSpinnerAdapter);

		lgaOfpermanentAddressSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.NA);
		lgaOfpermanentAddressSpinner.setAdapter(lgaOfpermanentAddressSpinnerAdapter);

		pdStateAddressSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.STATES);
		pdStateAddressSpinner.setAdapter(pdStateAddressSpinnerAdapter);

		pdLgaAddressSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.NA);
		pdLgaAddressSpinner.setAdapter(pdLgaAddressSpinnerAdapter);

	}

	public void setVariableValues(){

		if(pdPrefs.getAll().isEmpty()){
			
			editor.putString("controlNumberET", "710607");
			editor.putString("accountNumberET", "2946531");
			editor.putString("titleSpinner", "MR.");
			editor.putString("placeOfBirthSpinner", "KEBBI");
			editor.putString("nationalitySpinner", "NIGERIA");
			editor.putString("dateOfBirthET", "01/02/1982");
			editor.putString("pd_surnameET", "ISAH");
			editor.putString("pd_firstNameET", "KABIRU");
			editor.putString("pd_MiddleNameET", "");
			editor.putString("maritalStatus", "MARRIED");
			editor.putString("pd_sexRG", "M");
			editor.putString("stateOfOriginSpinner", "KEBBI");
			editor.putString("lgaOfOriginSpinner", "ARGUNGU");
			editor.putString("pdEmailET", "myself@myemail.com");
			editor.putString("pdPhone1ET", "08082617399");
			editor.putString("pdPhone2ET", "08160945909");
			editor.putString("pdPresentAddressET", "GWAZANGE AREA ARGUNGU OPP LAUWAL TARGET HOUSE");
			editor.putString("pdPermanentHomeAddressET", "GWAZANGE AREA ARGUNGU OPP LAUWAL TARGET HOUSE");
			editor.putString("stateOfpermanentAddressSpinner", "KEBBI");
			editor.putString("lgaOfpermanentAddressSpinner", "ARGUNGU");
			editor.putString("pdCurrentContactAddress1ET", "");
			editor.putString("pdCurrentContactAddress2ET", "");
			editor.putString("pdStateAddressSpinner", "N/A");
			editor.putString("pdLgaAddressSpinner", "N/A");

			editor.commit();
		}

	}

	private void presetAllViews() {
		controlNumberET.setText(pdPrefs.getString("controlNumberET", ""));
		RegisterContributor.controlNumber = pdPrefs.getString("controlNumberET", "N/A");
		RegisterContributor.prefix = CommonOps.getPrefixString(getActivity());
		accountNumberET.setText(pdPrefs.getString("accountNumberET", ""));
		titleSpinner.post(new Runnable(){
			public void run(){
				titleSpinner.setSelection(titleSpinnerAdapter.getPosition(pdPrefs.getString("titleSpinner", "N/A")));
			}
		});
		placeOfBirthSpinner.post(new Runnable(){
			public void run(){
				placeOfBirthSpinner.setSelection(placeOfBirthSpinnerAdapter.getPosition(pdPrefs.getString("placeOfBirthSpinner", "N/A")));
			}
		});
		nationalitySpinner.post(new Runnable(){
			public void run(){
				nationalitySpinner.setSelection(nationalitySpinnerAdapter.getPosition(pdPrefs.getString("nationalitySpinner", "N/A")));
			}
		});
		dateOfBirthET.setText(pdPrefs.getString("dateOfBirthET", ""));
		pd_surnameET.setText(pdPrefs.getString("pd_surnameET", ""));
		pd_firstNameET.setText(pdPrefs.getString("pd_firstNameET", ""));
		pd_MiddleNameET.setText(pdPrefs.getString("pd_MiddleNameET", ""));

		maritalStatus.post(new Runnable(){
			public void run(){
				maritalStatus.setSelection(maritalStatusSpinnerAdapter.getPosition(pdPrefs.getString("maritalStatus", "Married")));
			}
		});

		if(pd_sexMale.getText().toString().equals(pdPrefs.getString("pd_sexRG", ""))){
			pd_sexMale.setChecked(true);
		}else if(pd_sexFemale.getText().toString().equals(pdPrefs.getString("pd_sexRG", ""))){
			pd_sexFemale.setChecked(true);
		}

		if(nationalitySpinner.getSelectedItem().toString().equals("NIGERIA")){
			stateOfOriginSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, 
					States.STATES));
			stateOfOriginSpinner.post(new Runnable(){
				public void run(){		
					stateOfOriginSpinner.setSelection(stateOfOriginSpinnerAdapter.getPosition(pdPrefs.getString("stateOfOriginSpinner", "")));
				}
			});
		}


		if(!stateOfOriginSpinner.getSelectedItem().toString().equals("N/A")){
			lgaOfOriginSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, 
					States.LGA.get(stateOfOriginSpinner.getSelectedItemPosition())));
			lgaOfOriginSpinner.post(new Runnable(){
				public void run(){
					lgaOfOriginSpinner.setSelection(lgaOfOriginSpinnerAdapter.getPosition(pdPrefs.getString("lgaOfOriginSpinner", "")));
				}
			});
		}

		pdEmailET.setText(pdPrefs.getString("pdEmailET", ""));
		pdPhone1ET.setText(pdPrefs.getString("pdPhone1ET", ""));
		pdPhone2ET.setText(pdPrefs.getString("pdPhone2ET", ""));
		pdPresentAddressET.setText(pdPrefs.getString("pdPresentAddressET", ""));
		pdPermanentHomeAddressET.setText(pdPrefs.getString("pdPermanentHomeAddressET", ""));

		stateOfpermanentAddressSpinner.post(new Runnable(){
			public void run(){			
				stateOfpermanentAddressSpinner.setSelection(stateOfpermanentAddressSpinnerAdapter.getPosition(pdPrefs.getString("stateOfpermanentAddressSpinner", "")));
			}
		});

		if(stateOfpermanentAddressSpinner.getSelectedItemPosition() != 0){
			lgaOfpermanentAddressSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, 
					States.LGA.get(stateOfpermanentAddressSpinner.getSelectedItemPosition())));
			lgaOfpermanentAddressSpinner.post(new Runnable(){
				public void run(){	
					lgaOfpermanentAddressSpinner.setSelection(lgaOfpermanentAddressSpinnerAdapter.getPosition(pdPrefs.getString("lgaOfpermanentAddressSpinner", "")));
				}
			});
		}

		pdCurrentContactAddress1ET.setText(pdPrefs.getString("pdCurrentContactAddress1ET", ""));
		pdCurrentContactAddress2ET.setText(pdPrefs.getString("pdCurrentContactAddress2ET", ""));

		pdStateAddressSpinner.post(new Runnable(){
			public void run(){
				pdStateAddressSpinner.setSelection(pdStateAddressSpinnerAdapter.getPosition(pdPrefs.getString("pdStateAddressSpinner", "")));
			}
		});

		if(pdStateAddressSpinner.getSelectedItemPosition() != 0){
			pdLgaAddressSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, 
					States.LGA.get(pdStateAddressSpinner.getSelectedItemPosition())));
			pdLgaAddressSpinner.post(new Runnable(){
				public void run(){
					pdLgaAddressSpinner.setSelection(pdLgaAddressSpinnerAdapter.getPosition(pdPrefs.getString("pdLgaAddressSpinner", "")));
				}
			});
		}

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if(!hasFocus){
			switch(v.getId()){

			case R.id.controlNumberET: 
				CommonOps.saveValue("controlNumberET", controlNumberET.getText().toString(), editor);
				RegisterContributor.controlNumber = pdPrefs.getString("controlNumberET", "N/A");
				RegisterContributor.prefix = CommonOps.getPrefixString(getActivity());
				break;

			case R.id.accountNumberET: 
				CommonOps.saveValue("accountNumberET", accountNumberET.getText().toString(), editor);
				break;

			case R.id.dateOfBirthET: 
				CommonOps.saveValue("dateOfBirthET", dateOfBirthET.getText().toString(), editor);
				break;

			case R.id.pd_surnameET: 
				CommonOps.saveValue("pd_surnameET", pd_surnameET.getText().toString(), editor);
				break;

			case R.id.pd_firstNameET: 
				CommonOps.saveValue("pd_surnameET", pd_surnameET.getText().toString(), editor);
				break;

			case R.id.pd_MiddleNameET: 
				CommonOps.saveValue("pd_MiddleNameET", pd_MiddleNameET.getText().toString(), editor);
				break;

			case R.id.pdEmailET: 
				CommonOps.saveValue("pdEmailET", pdEmailET.getText().toString(), editor);
				break;

			case R.id.pdPhone1ET: 
				CommonOps.saveValue("pdPhone1ET", pdPhone1ET.getText().toString(), editor);
				break;

			case R.id.pdPhone2ET: 
				CommonOps.saveValue("pdPhone2ET", pdPhone2ET.getText().toString(), editor);
				break;

			case R.id.pdPresentAddressET: 
				CommonOps.saveValue("pdPresentAddressET", pdPresentAddressET.getText().toString(), editor);
				break;

			case R.id.pdPermanentHomeAddressET: 
				CommonOps.saveValue("pdPermanentHomeAddressET", pdPermanentHomeAddressET.getText().toString(), editor);
				break;

			case R.id.pdCurrentContactAddress1ET: 
				CommonOps.saveValue("pdCurrentContactAddress1ET", pdCurrentContactAddress1ET.getText().toString(), editor);
				break;

			case R.id.pdCurrentContactAddress2ET: 
				CommonOps.saveValue("pdCurrentContactAddress2ET", pdCurrentContactAddress2ET.getText().toString(), editor);
				break;

			default: break;

			}
		}		

	}

	private void setAllViewListeners() {

		controlNumberET.setOnFocusChangeListener(this);
		accountNumberET.setOnFocusChangeListener(this);
		titleSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("titleSpinner", titleSpinner.getItemAtPosition(position).toString(), editor);				

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		dateOfBirthET.setOnFocusChangeListener(this);
		pd_surnameET.setOnFocusChangeListener(this);
		pd_firstNameET.setOnFocusChangeListener(this);
		pd_MiddleNameET.setOnFocusChangeListener(this);

		maritalStatus.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("maritalStatus", maritalStatus.getItemAtPosition(position).toString(), editor);
		
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		pd_sexRG.setOnCheckedChangeListener(this);

		placeOfBirthSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("placeOfBirthSpinner", placeOfBirthSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		nationalitySpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CommonOps.saveValue("nationalitySpinner", nationalitySpinner.getItemAtPosition(position).toString(), editor);
			
				String country = States.COUNTRIES[position];
				if(country.equals("NIGERIA")){
					stateOfOriginSpinnerAdapter = new ArrayAdapter<String>(getActivity(), 
							android.R.layout.simple_spinner_dropdown_item, States.STATES);
				}else{
					stateOfOriginSpinnerAdapter = new ArrayAdapter<String>(getActivity(), 
							android.R.layout.simple_spinner_dropdown_item, States.NA);
				}
				stateOfOriginSpinner.setAdapter(stateOfOriginSpinnerAdapter);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		stateOfOriginSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				chosenState = stateOfOriginSpinner.getItemAtPosition(position).toString();

				CommonOps.saveValue("stateOfOriginSpinner", stateOfOriginSpinner.getItemAtPosition(position).toString(), editor);
				
				chosenLGAs = States.LGA.get(position);
				lgaOfOriginSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, chosenLGAs);
				lgaOfOriginSpinner.setAdapter(lgaOfOriginSpinnerAdapter);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		lgaOfOriginSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				chosenLGA = lgaOfOriginSpinner.getItemAtPosition(position).toString();

				CommonOps.saveValue("lgaOfOriginSpinner", lgaOfOriginSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		stateOfpermanentAddressSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				chosenState = stateOfpermanentAddressSpinner.getItemAtPosition(position).toString();
				
				CommonOps.saveValue("stateOfpermanentAddressSpinner", stateOfpermanentAddressSpinner.getItemAtPosition(position).toString(), editor);
				
				chosenLGAs = States.LGA.get(position);
				lgaOfpermanentAddressSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, chosenLGAs);
				lgaOfpermanentAddressSpinner.setAdapter(lgaOfpermanentAddressSpinnerAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		lgaOfpermanentAddressSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				chosenLGA = lgaOfpermanentAddressSpinner.getItemAtPosition(position).toString();

				CommonOps.saveValue("lgaOfpermanentAddressSpinner", lgaOfpermanentAddressSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		pdStateAddressSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				chosenState = pdStateAddressSpinner.getItemAtPosition(position).toString();
				
				CommonOps.saveValue("pdStateAddressSpinner", pdStateAddressSpinner.getItemAtPosition(position).toString(), editor);
				
				chosenLGAs = States.LGA.get(position);
				pdLgaAddressSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, chosenLGAs);
				pdLgaAddressSpinner.setAdapter(pdLgaAddressSpinnerAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		pdLgaAddressSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				chosenLGA = pdLgaAddressSpinner.getItemAtPosition(position).toString();
				
				CommonOps.saveValue("pdLgaAddressSpinner", pdLgaAddressSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		pdEmailET.setOnFocusChangeListener(this);
		pdPhone1ET.setOnFocusChangeListener(this);
		pdPhone2ET.setOnFocusChangeListener(this);
		pdPresentAddressET.setOnFocusChangeListener(this);
		pdPermanentHomeAddressET.setOnFocusChangeListener(this);

		pdCurrentContactAddress1ET.setOnFocusChangeListener(this);
		pdCurrentContactAddress2ET.setOnFocusChangeListener(this);

		proceed.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new Handler().postDelayed(new Runnable(){
					public void run(){
						CommonOps.playClickSound(getActivity());
						if(validate())
							ctnok.changeToNOKForm();		
					}
				}, 300);		
			}			
		});

	}

	public interface ChangeToNOK{
		public void changeToNOKForm();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {		

		int id = pd_sexRG.getCheckedRadioButtonId();
		RadioButton radio = (RadioButton)getActivity().findViewById(id);
		String text = radio.getText().toString();
		CommonOps.saveValue("pd_sexRG", text, editor);			
	}

	boolean titleChecked;
	public boolean validate(){
		String enteredValue = null;
		Drawable stop = getResources().getDrawable(R.drawable.stop);
		Drawable warning = getResources().getDrawable(R.drawable.warning_logo);

		/*===========================================================================
		 *To validate control number
		===========================================================================*/
		enteredValue = controlNumberET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Hold it!", 
					"Control Number field cannot be empty", "OK");			
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();
				}

			});
			return false;
		}else if(enteredValue.length() < 6){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Hold it!", 
					"Invalid Entry in control number field", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();
				}

			});
			return false;
		}//else if the control number already exists...
		else{
			RegisterContributor.controlNumber = pdPrefs.getString("controlNumberET", "N/A");
			RegisterContributor.prefix = CommonOps.getPrefixString(getActivity());
			CommonOps.saveValue("controlNumberET", enteredValue, editor);
		}

		/*===========================================================================
		 *End control number validation
		===========================================================================*/




		/*===========================================================================
		 *now to validate account number
		===========================================================================*/
		enteredValue = accountNumberET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Hold it!", 
					"Account number field cannot be empty", "OK");			
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();
				}

			});
			return false;
		}else if(enteredValue.length() < 7){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Hold it!", 
					"Invalid Entry in account number field", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();
				}

			});
			return false;
		}//else if the account number already exists...
		else{
			CommonOps.saveValue("accountNumberET", enteredValue, editor);
		}

		/*===========================================================================
		 *End account number validation
		===========================================================================*/



		/*===========================================================================
		 *title validation
		===========================================================================*/
		enteredValue =  titleSpinner.getSelectedItem().toString();
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
					CommonOps.saveValue("titleSpinner", 
							titleSpinner.getSelectedItem().toString(), editor);
				}

			});
			if(!titleChecked){				
				titleChecked = true;
				return false;
			}

		}else{
			CommonOps.saveValue("titleSpinner", enteredValue, editor);
		}

		/*===========================================================================
		 *End title validation
		===========================================================================*/



		/*===========================================================================
		 *Surname validation
		===========================================================================*/
		enteredValue =  pd_surnameET.getText().toString();
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
			CommonOps.saveValue("pd_surnameET", enteredValue, editor);
		}

		/*===========================================================================
		 *End Surname validation
		===========================================================================*/



		/*===========================================================================
		 *First name validation
		===========================================================================*/

		enteredValue =  pd_firstNameET.getText().toString();
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
			enteredValue = String.valueOf(enteredValue.substring(0, 1)).toUpperCase() + 
					enteredValue.substring(1).toLowerCase();
			CommonOps.saveValue("pd_firstNameET", enteredValue, editor);
		}

		/*===========================================================================
		 *End first name validation
		===========================================================================*/




		/*===========================================================================
		 *Middle name validation
		===========================================================================*/

		enteredValue =  pd_MiddleNameET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.saveValue("pd_MiddleNameET", enteredValue, editor);
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
			CommonOps.saveValue("pd_MiddleNameET", enteredValue, editor);
		}

		/*===========================================================================
		 *End middle name validation
		===========================================================================*/



		/*===========================================================================
		 *Date of birth validation
		===========================================================================*/
		enteredValue =  dateOfBirthET.getText().toString();
		if(!CommonOps.validateDate(getActivity(), enteredValue, editor, "dateOfBirthET"))
			return false;		
		/*===========================================================================
		 *End date of birth validation
		===========================================================================*/



		/*===========================================================================
		 *Email validation
		===========================================================================*/
		enteredValue =  pdEmailET.getText().toString();
		if(!CommonOps.emailValidate(getActivity(), enteredValue, editor, "pdEmailET"))
			return false;

		/*===========================================================================
		 *End Email validation
		===========================================================================*/




		/*===========================================================================
		 *phone number validation
		===========================================================================*/

		enteredValue =  pdPhone1ET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Empty phone field", 
					"The Phone 1 field cannot be empty.", "OK");
			dialog.show();
			dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					CommonOps.playClickSound(getActivity());
					dialog.dismiss();					
				}

			});
			return false;
		}else if(enteredValue.charAt(0) == '0' && enteredValue.length() == 11){
			CommonOps.saveValue("pdPhone1ET", enteredValue, editor);
		}else{
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Invalid phone number", 
					"Please check the phone number you entered in Phone 1.", "OK");
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
		 *Phone number 2 validation
		===========================================================================*/
		enteredValue = pdPhone2ET.getText().toString();
		if(enteredValue.equals("") || !enteredValue.equals("") && enteredValue.charAt(0) == '0' 
				&& enteredValue.length() == 11){
			CommonOps.saveValue("pdPhone2ET", enteredValue, editor);			
		}else{
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Invalid phone number", 
					"Please check the phone number you entered in Phone 2.", "OK");
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
		 *End phone number 2 validation
		===========================================================================*/




		/*===========================================================================
		 *Present address validation
		===========================================================================*/
		enteredValue = pdPresentAddressET.getText().toString();
		CommonOps.saveSQLString(enteredValue, editor, "pdPresentAddressET");
		/*===========================================================================
		 *End present address validation
		===========================================================================*/



		/*===========================================================================
		 *Permanent address validation
		===========================================================================*/

		enteredValue = pdPermanentHomeAddressET.getText().toString();
		CommonOps.saveSQLString(enteredValue, editor, "pdPermanentHomeAddressET");
		/*===========================================================================
		 *End permanent address validation
		===========================================================================*/


		/*=========================================================================
		 *Current contact address 1 validation
		===========================================================================*/

		enteredValue = pdCurrentContactAddress1ET.getText().toString();
		CommonOps.saveSQLString(enteredValue, editor, "pdCurrentContactAddress1ET");
		/*===========================================================================
		 *End current contact address 1 validation
		===========================================================================*/




		/*===========================================================================
		 *Current contact address 2 validation
		===========================================================================*/

		enteredValue = pdCurrentContactAddress2ET.getText().toString();
		CommonOps.saveSQLString(enteredValue, editor, "pdCurrentContactAddress2ET");
		/*===========================================================================
		 *Current contact address 2 validation
		===========================================================================*/



		return true;
	}	
}
