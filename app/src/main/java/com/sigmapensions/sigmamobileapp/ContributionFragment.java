package com.sigmapensions.sigmamobileapp;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

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
import android.widget.Button;
import android.widget.EditText;

public class ContributionFragment extends Fragment implements OnFocusChangeListener {

	Button c_back;
	Button c_proceed;

	EditText cFormReferenceNoET;
	EditText cEMployerTypeET;
	EditText cEmployerContributionET;
	EditText cSalaryGrade;
	EditText cSalaryStep;
	EditText cSalaryStructure;
	EditText cAnnualBasicSalaryET;
	EditText cAnnualTransportET;
	EditText cAnnualRentET;
	EditText cAnnualOtherPensionableET;
	EditText cEmployeeContribution;

	private ContributorNav cn;

	SharedPreferences cPrefs;
	SharedPreferences.Editor editor;
	
	MyDialog dialog;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			cn = (ContributorNav)activity;
		}catch(ClassCastException e){
			Log.e("Exception!!!!", e.toString() + "Didn't implement contributor nav interface");
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		cPrefs = CommonOps.getCPrefs(getActivity());
		editor = cPrefs.edit();
		setVariableValues();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.contribution_fragment, container, false);

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
		if(cPrefs.getAll().isEmpty()){
						
			SharedPreferences pdPrefs = CommonOps.getPdPrefs(getActivity());
			SharedPreferences edPrefs = CommonOps.getEdPrefs(getActivity());

			editor.putString("cFormReferenceNoET", pdPrefs.getString("controlNumberET", ""));
			editor.putString("cEMployerTypeET", edPrefs.getString("employerType", "1"));
			editor.putString("cEmployerContributionET", "2571.81");
			editor.putString("cSalaryGrade", "7");
			editor.putString("cSalaryStep", "2");
			editor.putString("cSalaryStructure", "1");
			editor.putString("cAnnualBasicSalaryET", "1");
			editor.putString("cAnnualTransportET", "1");
			editor.putString("cAnnualRentET", "1");
			editor.putString("cAnnualOtherPensionableET", "1");
			editor.putString("cEmployeeContribution", "2571.81");

			editor.commit();
		}
	}


	private void initializeVariables(View view) {
		c_back = (Button)view.findViewById(R.id.c_back);
		c_proceed  = (Button)view.findViewById(R.id.c_proceed);

		cFormReferenceNoET = (EditText)view.findViewById(R.id.cFormReferenceNoET);
		cEMployerTypeET = (EditText)view.findViewById(R.id.cEMployerTypeET);
		cEmployerContributionET = (EditText)view.findViewById(R.id.cEmployerContributionET);
		cSalaryGrade = (EditText)view.findViewById(R.id.cSalaryGrade);
		cSalaryStep = (EditText)view.findViewById(R.id.cSalaryStep);
		cSalaryStructure = (EditText)view.findViewById(R.id.cSalaryStructure);
		cAnnualBasicSalaryET = (EditText)view.findViewById(R.id.cAnnualBasicSalaryET);
		cAnnualTransportET = (EditText)view.findViewById(R.id.cAnnualTransportET);
		cAnnualRentET = (EditText)view.findViewById(R.id.cAnnualRentET);
		cAnnualOtherPensionableET = (EditText)view.findViewById(R.id.cAnnualOtherPensionableET);
		cEmployeeContribution = (EditText)view.findViewById(R.id.cEmployeeContribution);
	}


	private void presetAllViews() {
		cFormReferenceNoET.setText(cPrefs.getString("cFormReferenceNoET", ""));
		cEMployerTypeET.setText(cPrefs.getString("cEMployerTypeET", ""));
		cEmployerContributionET.setText(cPrefs.getString("cEmployerContributionET", ""));
		cSalaryGrade.setText(cPrefs.getString("cSalaryGrade", ""));
		cSalaryStep.setText(cPrefs.getString("cSalaryStep", ""));
		cSalaryStructure.setText(cPrefs.getString("cSalaryStructure", ""));
		cAnnualBasicSalaryET.setText(cPrefs.getString("cAnnualBasicSalaryET", ""));
		cAnnualTransportET.setText(cPrefs.getString("cAnnualTransportET", ""));
		cAnnualRentET.setText(cPrefs.getString("cAnnualRentET", ""));
		cAnnualOtherPensionableET.setText(cPrefs.getString("cAnnualOtherPensionableET", ""));
		cEmployeeContribution.setText(cPrefs.getString("cEmployeeContribution", ""));		
	}

	
	private void setAllViewListeners() {
		cFormReferenceNoET.setOnFocusChangeListener(this);
		cEMployerTypeET.setOnFocusChangeListener(this);
		cEmployerContributionET.setOnFocusChangeListener(this);
		cSalaryGrade.setOnFocusChangeListener(this);
		cSalaryStep.setOnFocusChangeListener(this);
		cSalaryStructure.setOnFocusChangeListener(this);
		cAnnualBasicSalaryET.setOnFocusChangeListener(this);
		cAnnualTransportET.setOnFocusChangeListener(this);
		cAnnualRentET.setOnFocusChangeListener(this);
		cAnnualOtherPensionableET.setOnFocusChangeListener(this);
		cEmployeeContribution.setOnFocusChangeListener(this);	

		c_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(getActivity());
				cn.backToEmployerData();
			}

		});

		c_proceed.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(getActivity());
				if(validate())
					cn.forwardToBiometric();
			}

		});
	}


	public interface ContributorNav{
		public void backToEmployerData();
		public void forwardToBiometric();
	}


	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if(!hasFocus){
			switch(v.getId()){

			case R.id.cFormReferenceNoET: 
				CommonOps.saveValue("cFormReferenceNoET", cFormReferenceNoET.getText().toString(), editor);
			break;

			case R.id.cEMployerTypeET: 
				CommonOps.saveValue("cEMployerTypeET", cEMployerTypeET.getText().toString(), editor);
			break;

			case R.id.cEmployerContributionET: 
				CommonOps.saveValue("cEmployerContributionET", cEmployerContributionET.getText().toString(), editor);
			break;

			case R.id.cSalaryGrade: 
				CommonOps.saveValue("cSalaryGrade", cSalaryGrade.getText().toString(), editor);
			break;

			case R.id.cSalaryStep: 
				CommonOps.saveValue("cSalaryStep", cSalaryStep.getText().toString(), editor);
			break;

			case R.id.cSalaryStructure: 
				CommonOps.saveValue("cSalaryStructure", cSalaryStructure.getText().toString(), editor);
			break;

			case R.id.cAnnualBasicSalaryET: 
				CommonOps.saveValue("cAnnualBasicSalaryET", cAnnualBasicSalaryET.getText().toString(), editor);
			break;

			case R.id.cAnnualTransportET: 
				CommonOps.saveValue("cAnnualTransportET", cAnnualTransportET.getText().toString(), editor);
			break;

			case R.id.cAnnualRentET: 
				CommonOps.saveValue("cAnnualRentET", cAnnualRentET.getText().toString(), editor);
			break;

			case R.id.cAnnualOtherPensionableET: 
				CommonOps.saveValue("cAnnualOtherPensionableET", cAnnualOtherPensionableET.getText().toString(), editor);
			break;

			case R.id.cEmployeeContribution: 
				CommonOps.saveValue("cEmployeeContribution", cEmployeeContribution.getText().toString(), editor);
			break;
			
			default: break;
			}
		}
		

	}

	
	private boolean validate(){
		String enteredValue = null;
		Drawable stop = getResources().getDrawable(R.drawable.stop);
		
		/*===========================================================================
		 *Form reference number validation
		===========================================================================*/

		enteredValue = cFormReferenceNoET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "No form reference", 
					"The form reference number field cannot be empty.", "OK");
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
			CommonOps.saveValue("cFormReferenceNoET", enteredValue, editor);
		}
		/*===========================================================================
		 *End form reference number validation
		===========================================================================*/



		/*===========================================================================
		 *Employer type validation
		===========================================================================*/

		enteredValue = cEMployerTypeET.getText().toString();
		if(enteredValue.equals("")){
			CommonOps.playErrorSound(getActivity());
			dialog = new MyDialog(getActivity(), stop, "No employer type", 
					"The employer type number field cannot be empty.", "OK");
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
			CommonOps.saveValue("cEMployerTypeET", enteredValue, editor);
		}
		/*===========================================================================
		 *End employer type validation
		===========================================================================*/




		/*===========================================================================
		 *Employer contribution validation
		===========================================================================*/

		enteredValue = cEmployerContributionET.getText().toString();

		CommonOps.saveValue("cEmployerContributionET", enteredValue, editor);

		/*===========================================================================
		 *End employer contribution validation
		===========================================================================*/


		/*===========================================================================
		 *Salary grade validation
			===========================================================================*/

		enteredValue = cSalaryGrade.getText().toString();

		CommonOps.saveValue("cSalaryGrade", enteredValue, editor);

		/*===========================================================================
		 *End salary grade validation
		===========================================================================*/

		/*===========================================================================
		 *Salary step validation
		===========================================================================*/

		enteredValue = cSalaryStep.getText().toString();

		CommonOps.saveValue("cSalaryStep", enteredValue, editor);

		/*===========================================================================
		 *End salary step validation
		===========================================================================*/
		
		
		/*===========================================================================
		 *Salary structure validation
		===========================================================================*/

		enteredValue = cSalaryStructure.getText().toString();

		CommonOps.saveValue("cSalaryStructure", enteredValue, editor);

		/*===========================================================================
		 *End salary structure validation
		===========================================================================*/
		
		
		
		/*===========================================================================
		 *Annual basic salary validation
		===========================================================================*/

		enteredValue = cAnnualBasicSalaryET.getText().toString();

		CommonOps.saveValue("cAnnualBasicSalaryET", enteredValue, editor);

		/*===========================================================================
		 *End annual basic salary validation
		===========================================================================*/
		
		
		/*===========================================================================
		 *Annual transport validation
		===========================================================================*/

		enteredValue = cAnnualTransportET.getText().toString();

		CommonOps.saveValue("cAnnualTransportET", enteredValue, editor);

		/*===========================================================================
		 *End annual transport validation
		===========================================================================*/
		
		
		
		/*===========================================================================
		 *Annual rent validation
		===========================================================================*/

		enteredValue = cAnnualRentET.getText().toString();

		CommonOps.saveValue("cAnnualRentET", enteredValue, editor);

		/*===========================================================================
		 *End annual rent validation
		===========================================================================*/
		
		
		/*===========================================================================
		 *Annual other pension validation
		===========================================================================*/

		enteredValue = cAnnualOtherPensionableET.getText().toString();

		CommonOps.saveValue("cAnnualOtherPensionableET", enteredValue, editor);

		/*===========================================================================
		 *End annual other pension validation
		===========================================================================*/
		
		
		/*===========================================================================
		 *Employee contribution validation
		===========================================================================*/

		enteredValue = cEmployeeContribution.getText().toString();

		CommonOps.saveValue("cEmployeeContribution", enteredValue, editor);

		/*===========================================================================
		 *End Employee contribution validation
		===========================================================================*/
		
		return true;
	}

}
