package com.sigmapensions.sigmamobileapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class EmployerDataFragment extends Fragment implements OnFocusChangeListener{

	EditText edEmployerCodeET;
	EditText edNameOfOrganizationET;
	EditText edOfficeAddress1ET;
	EditText edOfficeAddress2ET;
	Spinner edStateSpinner;
	Spinner edLgaSpinner;
	EditText edProfessionET;
	EditText edPhoneNumberET;
	EditText edFileIdNumberET;
	EditText edDesignationET;
	EditText edDateOfFirstEmploymentET;
	EditText edDateOfConfirmationET;
	EditText edBranchOrLocationOfPostingET;
	EditText edEmailET;
	
	RadioGroup private_public;
	RadioButton private_sector_employeeRB;
	RadioButton public_sector_employeeRB;
    
	RadioGroup sector_radiogroup;
	RadioButton public_federalRB;
	RadioButton public_localRB;
	RadioButton public_stateRB;
	RadioButton mdasRB;
	RadioButton private_sectorRB;
	RadioButton informalRB;
	
	Button ed_back;
	Button ed_proceed;

	ArrayAdapter<String> edStateSpinnerAdapter;
	ArrayAdapter<String> edLgaSpinnerAdapter;

	SharedPreferences edPrefs;
	SharedPreferences.Editor editor;
	
	EmployerNav en;
	EmployerListInterface eli;

	MyDialog dialog;
	
	View view;


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			en = (EmployerNav)activity;
			eli = (EmployerListInterface)activity;
		}catch(ClassCastException e){
			Log.e("Exception not good!!!", e.toString());
		}	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		edPrefs = CommonOps.getEdPrefs(getActivity());
		editor = edPrefs.edit();
		setVariableValues();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.employer_data_fragment, container, false);
		initializeVariables(view);
		this.view = view;

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presetAllViews();
		setAllViewListeners();
	}

	private void setVariableValues() {

		if(edPrefs.getAll().isEmpty()){
			
			editor.putString("edEmployerCodeET", "PU000042B005");
			editor.putString("edNameOfOrganizationET", "NATIONAL DIRECTORATE OF EMPLOYMENT (NDE)");
			editor.putString("edOfficeAddress1ET", "NATIONAL DIRECTORATE OF EMPLOYMENT (NDE)");
			editor.putString("edOfficeAddress2ET", "");
			editor.putString("edStateSpinner", "KEBBI");
			editor.putString("edLgaSpinner", "KEBBI");
			editor.putString("edProfessionET", "DRIVER");
			editor.putString("edPhoneNumberET", "08000000000");
			editor.putString("edFileIdNumberET", "6269");
			editor.putString("edDesignationET", "DESIGNATION");
			editor.putString("edDateOfFirstEmploymentET", "16/09/2015");
			editor.putString("edDateOfConfirmationET", "16/09/2015");
			editor.putString("edEmailET", "employer@email.com");
			editor.putString("private_public", "");
			editor.putString("sector_radiogroup", "");
			editor.putString("employerType", "");
			
			editor.commit();

		}
	}

	private void initializeVariables(View view){
		edEmployerCodeET = (EditText)view.findViewById(R.id.edEmployerCodeET);
		edNameOfOrganizationET = (EditText)view.findViewById(R.id.edNameOfOrganizationET);
		edOfficeAddress1ET = (EditText)view.findViewById(R.id.edOfficeAddress1ET);
		edOfficeAddress2ET = (EditText)view.findViewById(R.id.edOfficeAddress2ET);
		edStateSpinner = (Spinner)view.findViewById(R.id.edStateSpinner);
		edLgaSpinner = (Spinner)view.findViewById(R.id.edLgaSpinner);
		edProfessionET = (EditText)view.findViewById(R.id.edProfessionET);
		edPhoneNumberET = (EditText)view.findViewById(R.id.edPhoneNumberET);
		edFileIdNumberET = (EditText)view.findViewById(R.id.edFileIdNumberET);
		edDesignationET = (EditText)view.findViewById(R.id.edDesignationET);
		edDateOfFirstEmploymentET = (EditText)view.findViewById(R.id.edDateOfFirstEmploymentET);
		edDateOfConfirmationET = (EditText)view.findViewById(R.id.edDateOfConfirmationET);
		edBranchOrLocationOfPostingET = (EditText)view.findViewById(R.id.edBranchOrLocationOfPostingET);
		edEmailET = (EditText)view.findViewById(R.id.edEmailET);
		
		private_public = (RadioGroup)view.findViewById(R.id.private_public);
		private_sector_employeeRB = (RadioButton)view.findViewById(R.id.private_sector_employeeRB);
		public_sector_employeeRB = (RadioButton)view.findViewById(R.id.public_sector_employeeRB);
		
		sector_radiogroup = (RadioGroup)view.findViewById(R.id.sector_radiogroup);
		public_federalRB = (RadioButton)view.findViewById(R.id.public_federalRB);
		public_localRB = (RadioButton)view.findViewById(R.id.public_localRB);
		public_stateRB = (RadioButton)view.findViewById(R.id.public_stateRB);
		mdasRB = (RadioButton)view.findViewById(R.id.mdasRB);
		private_sectorRB = (RadioButton)view.findViewById(R.id.private_sectorRB);
		informalRB = (RadioButton)view.findViewById(R.id.informalRB);
		
		ed_back = (Button)view.findViewById(R.id.ed_back);
		ed_proceed = (Button)view.findViewById(R.id.ed_proceed);

		edStateSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.STATES);
		edStateSpinner.setAdapter(edStateSpinnerAdapter);
		edLgaSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, States.NA);
		edLgaSpinner.setAdapter(edLgaSpinnerAdapter);


	}

	private void setAllViewListeners() {
		edEmployerCodeET.setEnabled(false);
		edNameOfOrganizationET.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String string = edNameOfOrganizationET.getText().toString();
				if(!string.equals(""))
					eli.showEmployerList(string);
				else{
					edEmployerCodeET.setText("");
					editor.remove("edNameOfOrganizationET");
					editor.putString("edNameOfOrganizationET", "");
					editor.remove("edEmployerCodeET");
					editor.putString("edEmployerCodeET", "");
					editor.commit();					
				}
					
			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}
			
		});
		edOfficeAddress1ET.setOnFocusChangeListener(this);
		edOfficeAddress2ET.setOnFocusChangeListener(this);

		edStateSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String[] chosenLGAs = States.LGA.get(position);
				
				CommonOps.saveValue("edStateSpinner", edStateSpinner.getItemAtPosition(position).toString(), editor);
				
				edLgaSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, chosenLGAs);
				edLgaSpinner.setAdapter(edLgaSpinnerAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		edLgaSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				CommonOps.saveValue("edLgaSpinner", edLgaSpinner.getItemAtPosition(position).toString(), editor);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		edProfessionET.setOnFocusChangeListener(this);
		edPhoneNumberET.setOnFocusChangeListener(this);
		edFileIdNumberET.setOnFocusChangeListener(this);
		edDesignationET.setOnFocusChangeListener(this);
		edDateOfFirstEmploymentET.setOnFocusChangeListener(this);
		edDateOfConfirmationET.setOnFocusChangeListener(this);
		edBranchOrLocationOfPostingET.setOnFocusChangeListener(this);
		edEmailET.setOnFocusChangeListener(this);

		private_public.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton checkedButton = (RadioButton)view.findViewById(checkedId);
				CommonOps.saveValue("private_public", checkedButton.getText().toString(), editor);
							
			}

		});

		sector_radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton checkedButton = (RadioButton)view.findViewById(checkedId);
				CommonOps.saveValue("sector_radiogroup", checkedButton.getText().toString(), editor);
						
			}

		});

		ed_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(getActivity());
				en.goBackwardToNOK();
			}

		});

		ed_proceed.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(getActivity());
				if(validate())
					en.goForwardToContribution();
			}			
		});

	}

	private void presetAllViews() {
		edEmployerCodeET.setText(edPrefs.getString("edEmployerCodeET", ""));
		edNameOfOrganizationET.setText(edPrefs.getString("edNameOfOrganizationET", ""));
		edOfficeAddress1ET.setText(edPrefs.getString("edOfficeAddress1ET", ""));
		edOfficeAddress2ET.setText(edPrefs.getString("edOfficeAddress2ET", ""));
		
		edStateSpinner.post(new Runnable(){
			public void run(){
				edStateSpinner.setSelection(edStateSpinnerAdapter.getPosition(edPrefs.getString("edStateSpinner", "N/A")));
			}
		});
		if(edStateSpinner.getSelectedItemPosition() != 0){
			edLgaSpinner.post(new Runnable(){
				public void run(){
					edLgaSpinner.setSelection(edLgaSpinnerAdapter.getPosition(edPrefs.getString("edLgaSpinner", "N/A")));
				}
			});
		}		
		
		edProfessionET.setText(edPrefs.getString("edProfessionET", ""));
		edPhoneNumberET.setText(edPrefs.getString("edPhoneNumberET", ""));
		edFileIdNumberET.setText(edPrefs.getString("edFileIdNumberET", ""));
		edDesignationET.setText(edPrefs.getString("edDesignationET", ""));
		edDateOfFirstEmploymentET.setText(edPrefs.getString("edDateOfFirstEmploymentET", ""));
		edDateOfConfirmationET.setText(edPrefs.getString("edDateOfConfirmationET", ""));
		edBranchOrLocationOfPostingET.setText(edPrefs.getString("edBranchOrLocationOfPostingET", ""));
		edEmailET.setText(edPrefs.getString("edEmailET", ""));
		
		if(private_sector_employeeRB.getText().toString().equals(edPrefs.getString("private_public", ""))){
			private_sector_employeeRB.setChecked(true);
		}else if(public_sector_employeeRB.getText().toString().equals(edPrefs.getString("private_public", ""))){
			public_sector_employeeRB.setChecked(true);
		}
		
		if(public_federalRB.getText().toString().equals(edPrefs.getString("sector_radiogroup", ""))){
			public_federalRB.setChecked(true);
		}else if(public_localRB.getText().toString().equals(edPrefs.getString("sector_radiogroup", ""))){
			public_localRB.setChecked(true);
		}else if(public_stateRB.getText().toString().equals(edPrefs.getString("sector_radiogroup", ""))){
			public_stateRB.setChecked(true);
		}else if(mdasRB.getText().toString().equals(edPrefs.getString("sector_radiogroup", ""))){
			mdasRB.setChecked(true);
		}else if(private_sectorRB.getText().toString().equals(edPrefs.getString("sector_radiogroup", ""))){
			private_sectorRB.setChecked(true);
		}else if(private_sectorRB.getText().toString().equals(edPrefs.getString("sector_radiogroup", ""))){
			private_sectorRB.setChecked(true);
		}
		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		
		if(!hasFocus){
			switch(v.getId()){
			case R.id.edEmployerCodeET: 
				CommonOps.saveValue("edEmployerCodeET", edEmployerCodeET.getText().toString(), editor);
			break;

			case R.id.edOfficeAddress1ET: 
				CommonOps.saveValue("edOfficeAddress1ET", edOfficeAddress1ET.getText().toString(), editor);
			break;

			case R.id.edOfficeAddress2ET: 
				CommonOps.saveValue("edOfficeAddress2ET", edOfficeAddress2ET.getText().toString(), editor);
			break;
			
			case R.id.edProfessionET: 
				CommonOps.saveValue("edProfessionET", edProfessionET.getText().toString(), editor);
			break;

			case R.id.edPhoneNumberET: 
				CommonOps.saveValue("edPhoneNumberET", edPhoneNumberET.getText().toString(), editor);
			break;

			case R.id.edFileIdNumberET: 
				CommonOps.saveValue("edFileIdNumberET", edFileIdNumberET.getText().toString(), editor);
			break;

			case R.id.edDesignationET: 
				CommonOps.saveValue("edDesignationET", edDesignationET.getText().toString(), editor);
			break;

			case R.id.edDateOfFirstEmploymentET: 
				CommonOps.saveValue("edDateOfFirstEmploymentET", edDateOfFirstEmploymentET.getText().toString(), editor);
			break;

			case R.id.edDateOfConfirmationET: 
				CommonOps.saveValue("edDateOfConfirmationET", edDateOfConfirmationET.getText().toString(), editor);
			break;

			case R.id.edBranchOrLocationOfPostingET: 
				CommonOps.saveValue("edBranchOrLocationOfPostingET", edBranchOrLocationOfPostingET.getText().toString(), editor);
			break;

			case R.id.edEmailET: 
				CommonOps.saveValue("edEmailET", edEmailET.getText().toString(), editor);
			break;

			default: break;
			}
		}
		
	}

	public interface EmployerNav{
		public void goForwardToContribution();
		public void goBackwardToNOK();
	}
	
	public interface EmployerListInterface{
		public void showEmployerList(String string);
	}
	
	private boolean validate(){
		String enteredValue = null;
		Drawable stop = getResources().getDrawable(R.drawable.stop);
		
		/*===========================================================================
		 *Organization name validation
		===========================================================================*/
		
		enteredValue = edNameOfOrganizationET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "No organization name", 
					"You haven't selected the name of your organization. "
							+ "This field cannot be empty", "OK");
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
			CommonOps.saveValue("edNameOfOrganizationET", enteredValue, editor);
		}
		/*===========================================================================
		 *End organization name validation
		===========================================================================*/

		
		
		
		/*===========================================================================
		 *EmployerCode validation
		===========================================================================*/
		
		enteredValue = edEmployerCodeET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "No Employer Code", 
					"The Employer code field cannot be empty.", "OK");
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
			CommonOps.saveValue("edEmployerCodeET", enteredValue, editor);
		}
		/*===========================================================================
		 *End EmployerCode validation
		===========================================================================*/
		
		
		
		
		/*===========================================================================
		 *Office address validation
		===========================================================================*/
		
		enteredValue = edOfficeAddress1ET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "No office address", 
					"The office address field (1) cannot be empty.", "OK");
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
			CommonOps.saveValue("edOfficeAddress1ET", CommonOps.convertToSQLString(enteredValue), editor);
		}
		/*===========================================================================
		 *End office address validation
		===========================================================================*/	
		
		
		
		
		/*===========================================================================
		 *Office address 2 validation
		===========================================================================*/
		
		enteredValue = edOfficeAddress2ET.getText().toString();
		if(!enteredValue.equals("")){
			CommonOps.saveValue("edOfficeAddress2ET", CommonOps.convertToSQLString(enteredValue), editor);		
		}else{
				CommonOps.saveValue("edOfficeAddress2ET", enteredValue, editor);
			
		}
		/*===========================================================================
		 *End office address 2 validation
		===========================================================================*/
		
//		Spinner edStateSpinner;
//		Spinner edLgaSpinner;
		
		
		/*===========================================================================
		 *Profession validation
		===========================================================================*/
		
		enteredValue = edProfessionET.getText().toString();
		if(!enteredValue.equals("")){
			CommonOps.saveValue("edProfessionET", CommonOps.convertToSQLString(enteredValue), editor);		
		}else{
			CommonOps.saveValue("edProfessionET", enteredValue, editor);		
		}
		/*===========================================================================
		 *End profession validation
		===========================================================================*/
		
		
		
		
		/*===========================================================================
		 *Phone number validation
		===========================================================================*/
		enteredValue = edPhoneNumberET.getText().toString();
		if(enteredValue.equals("") || !enteredValue.equals("") && enteredValue.charAt(0) == '0' 
				&& enteredValue.length() == 11){
			CommonOps.saveValue("edPhoneNumberET", enteredValue, editor);			
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
		 *Phone number validation
		===========================================================================*/
		enteredValue = edFileIdNumberET.getText().toString();
		if(!enteredValue.equals("")){
			CommonOps.saveValue("edFileIdNumberET", enteredValue, editor);			
		}else{
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Empty file ID", 
					"The file ID field cannot be empty. \nPlease check.", "OK");
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
		 *Designation validation
		===========================================================================*/
		enteredValue = edDesignationET.getText().toString();
		CommonOps.saveSQLString(enteredValue, editor, "edDesignationET");
		/*===========================================================================
		 *End designation validation
		===========================================================================*/
		
		
		
		
		/*===========================================================================
		 *Date of first employment validation
		===========================================================================*/
		enteredValue = edDateOfFirstEmploymentET.getText().toString();
		if(!CommonOps.validateDate(getActivity(), enteredValue, editor, "edDateOfFirstEmploymentET"))
			return false;	
		/*===========================================================================
		 *End date of first employment validation
		===========================================================================*/
				
		
		
		
		/*===========================================================================
		 *Date of confirmation validation
		===========================================================================*/
		
		enteredValue = edDateOfConfirmationET.getText().toString();
		if(!CommonOps.validateDate(getActivity(), enteredValue, editor, "edDateOfConfirmationET"))
			return false;	
		/*===========================================================================
		 *End date of confirmation validation
		===========================================================================*/
		
		
		
		
		
		/*===========================================================================
		 *Branch of posting validation validation
		===========================================================================*/
		enteredValue = edBranchOrLocationOfPostingET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "Empty branch field", 
					"The field: branch of posting cannot be empty.", "OK");
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
			CommonOps.saveValue("edBranchOrLocationOfPostingET", CommonOps.convertToSQLString(enteredValue), editor);	
		}
		/*===========================================================================
		 *End branch of posting validation
		===========================================================================*/
		
		
		/*===========================================================================
		 *Email validation
		===========================================================================*/
		enteredValue =  edEmailET.getText().toString();
		if(!CommonOps.emailValidate(getActivity(), enteredValue, editor, "edEmailET"))
			return false;
		/*===========================================================================
		 *End Email validation
		===========================================================================*/
		

		return true;
	}
	
}
