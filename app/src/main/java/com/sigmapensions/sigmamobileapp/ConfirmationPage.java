package com.sigmapensions.sigmamobileapp;


import java.io.IOException;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;
import com.sigmapensions.sigmamobileapp.utils.DataClass;
import com.sigmapensions.sigmamobileapp.utils.NewContributor;
import com.sigmapensions.sigmamobileapp.utils.ParseResponse;

public class ConfirmationPage extends Activity {

	final static String NAMESPACE = "http://localhost/wspencomsystem/wspencom/wsReceptionResquestPencom";
	final static String METHOD_NAME = "wmReceptionResquestNEW";
	final static String URL = "https://cras.pencom.gov.ng/wspencomsystem/wsPencom/wsReceptionResquestPencom.asmx?WSDL";
	final static String SOAP_ACTION = "http://localhost/wspencomsystem/wspencom/wsReceptionResquestPencom/wmReceptionResquestNEW";

	SoapObject request;

	DataClass dc;
	NewContributor nc;

	MyDialog dialog;
	MyProgressDialog progressDialog;

	HttpTransportSE tp;
	SoapSerializationEnvelope env;

	boolean gotResponse;	
	HashMap<String, String> responseMap;

	ImageView passport_confirm;
	ImageView left_thumb_confirm;
	ImageView right_thumb_confirm;
	ImageView signature_confirm;

	Button back;
	Button save;

	TextView control_no_value;
	TextView account_no_value;
	TextView title_value;
	TextView surname_value;
	TextView first_name_value;
	TextView middle_name_value;
	TextView date_of_birth_value;
	TextView marital_status_value;
	TextView sex_value;
	TextView place_of_birth_value;
	TextView nationality_value;
	TextView state_of_origin_value;
	TextView lga_of_origin_value;
	TextView pd_email_value;
	TextView tel_l_value;
	TextView tel_2_value;
	TextView present_address_value;
	TextView permanent_home_address_value;
	TextView state_of_permanent_address_value;
	TextView lga_of_permanent_address_value;
	TextView current_contact_address_1_value;
	TextView current_contact_address_2_value;
	TextView state_value;
	TextView lga_value;

	TextView nok_title_value;
	TextView nok_relationship_value;
	TextView nok_sex_value;
	TextView nok_phone_no_value;
	TextView nok_date_of_birth_value;
	TextView nok_surname_value;
	TextView nok_first_name_value;
	TextView nok_middle_name_value;
	TextView nok_email_value;
	TextView nok_contact_address_pobox_1_value;
	TextView nok_contact_address_pobox_2_value;
	TextView nok_country_value;
	TextView nok_state_value;
	TextView nok_lga_value;

	TextView ed_employer_code_value;
	TextView ed_name_of_organization_value;
	TextView ed_office_address_1_value;
	TextView ed_office_address_2_value;
	TextView ed_state_value;
	TextView ed_lga_value;
	TextView ed_profession_value;
	TextView ed_phone_no_value;
	TextView ed_file_id_no_value;
	TextView ed_designation_value;
	TextView ed_dofe_value;
	TextView ed_doc_value;
	TextView ed_bolop_value;
	TextView ed_email_value;
	TextView private_public_value;
	TextView ed_sector2_value;

	TextView c_form_reference_no_value;
	TextView c_employer_type_value;
	TextView c_employer_contribution_value;
	TextView c_salary_grade_value;
	TextView c_salary_step_value;
	TextView c_salary_structure_value;
	TextView c_annual_basic_salary_value;
	TextView c_annual_transport_value;
	TextView c_annual_rent_value;
	TextView c_annual_other_pensionable_value;
	TextView c_employee_contribution_value;


	SharedPreferences pdPrefs;
	SharedPreferences nokPrefs;
	SharedPreferences edPrefs;
	SharedPreferences cPrefs;
	SharedPreferences bPrefs;
	SharedPreferences pinDetailsPrefs;

	SharedPreferences.Editor editor;
	
	String pictureData;
	String leftThumbData;
	String rightThumbData;
	String signatureData; 

	Button logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation_page_activity);

		pdPrefs = CommonOps.getPdPrefs(this);
		nokPrefs = CommonOps.getNOKPrefs(this);
		edPrefs = CommonOps.getEdPrefs(this);
		cPrefs = CommonOps.getCPrefs(this);
		bPrefs = CommonOps.getbPrefs(this);
		pinDetailsPrefs = CommonOps.getPinDetailsPrefs(this);
		
		
		initializeVariables();
		setCollectedValues();

	}

	private void initializeVariables(){
		back = (Button)findViewById(R.id.review);
		save = (Button)findViewById(R.id.bSave);

		passport_confirm = (ImageView)findViewById(R.id.passport_confirm);
		left_thumb_confirm = (ImageView)findViewById(R.id.left_thumb_confirm);
		right_thumb_confirm = (ImageView)findViewById(R.id.right_thumb_confirm);
		signature_confirm = (ImageView)findViewById(R.id.signature_confirm);

		control_no_value = (TextView)findViewById(R.id.control_no_value);
		account_no_value = (TextView)findViewById(R.id.account_no_value);
		title_value = (TextView)findViewById(R.id.title_value);
		surname_value = (TextView)findViewById(R.id.surname_value);
		first_name_value = (TextView)findViewById(R.id.first_name_value);
		middle_name_value = (TextView)findViewById(R.id.middle_name_value);
		date_of_birth_value = (TextView)findViewById(R.id.date_of_birth_value);
		marital_status_value = (TextView)findViewById(R.id.marital_status_value);
		sex_value = (TextView)findViewById(R.id.sex_value);
		place_of_birth_value = (TextView)findViewById(R.id.place_of_birth_value);
		nationality_value = (TextView)findViewById(R.id.nationality_value);
		state_of_origin_value = (TextView)findViewById(R.id.state_of_origin_value);
		lga_of_origin_value = (TextView)findViewById(R.id.lga_of_origin_value);
		pd_email_value = (TextView)findViewById(R.id.pd_email_value);
		tel_l_value = (TextView)findViewById(R.id.tel_l_value);
		tel_2_value = (TextView)findViewById(R.id.tel_2_value);
		present_address_value = (TextView)findViewById(R.id.present_address_value);
		permanent_home_address_value = (TextView)findViewById(R.id.permanent_home_address_value);
		state_of_permanent_address_value = (TextView)findViewById(R.id.state_of_permanent_address_value);
		lga_of_permanent_address_value = (TextView)findViewById(R.id.lga_of_permanent_address_value);
		current_contact_address_1_value = (TextView)findViewById(R.id.current_contact_address_1_value);
		current_contact_address_2_value = (TextView)findViewById(R.id.current_contact_address_2_value);
		state_value = (TextView)findViewById(R.id.state_value);
		lga_value = (TextView)findViewById(R.id.lga_value);

		nok_title_value = (TextView)findViewById(R.id.nok_title_value);
		nok_relationship_value = (TextView)findViewById(R.id.nok_relationship_value);
		nok_sex_value = (TextView)findViewById(R.id.nok_sex_value);
		nok_phone_no_value = (TextView)findViewById(R.id.nok_phone_no_value);
		nok_date_of_birth_value = (TextView)findViewById(R.id.nok_date_of_birth_value);
		nok_surname_value = (TextView)findViewById(R.id.nok_surname_value);
		nok_first_name_value = (TextView)findViewById(R.id.nok_first_name_value);
		nok_middle_name_value = (TextView)findViewById(R.id.nok_middle_name_value);
		nok_email_value = (TextView)findViewById(R.id.nok_email_value);
		nok_contact_address_pobox_1_value = (TextView)findViewById(R.id.nok_contact_address_pobox_1_value);
		nok_contact_address_pobox_2_value = (TextView)findViewById(R.id.nok_contact_address_pobox_2_value);
		nok_country_value = (TextView)findViewById(R.id.nok_country_value);
		nok_state_value = (TextView)findViewById(R.id.nok_state_value);
		nok_lga_value = (TextView)findViewById(R.id.nok_lga_value);

		ed_employer_code_value = (TextView)findViewById(R.id.ed_employer_code_value);
		ed_name_of_organization_value = (TextView)findViewById(R.id.ed_name_of_organization_value);
		ed_office_address_1_value = (TextView)findViewById(R.id.ed_office_address_1_value);
		ed_office_address_2_value = (TextView)findViewById(R.id.ed_office_address_2_value);
		ed_state_value = (TextView)findViewById(R.id.ed_state_value);
		ed_lga_value = (TextView)findViewById(R.id.ed_lga_value);
		ed_profession_value = (TextView)findViewById(R.id.ed_profession_value);
		ed_phone_no_value = (TextView)findViewById(R.id.ed_phone_no_value);
		ed_file_id_no_value = (TextView)findViewById(R.id.ed_file_id_no_value);
		ed_designation_value = (TextView)findViewById(R.id.ed_designation_value);
		ed_dofe_value = (TextView)findViewById(R.id.ed_dofe_value);
		ed_doc_value = (TextView)findViewById(R.id.ed_doc_value);
		ed_bolop_value = (TextView)findViewById(R.id.ed_bolop_value);
		ed_email_value = (TextView)findViewById(R.id.ed_email_value);
		private_public_value = (TextView)findViewById(R.id.private_public_value);
		ed_sector2_value = (TextView)findViewById(R.id.ed_sector2_value);

		c_form_reference_no_value = (TextView)findViewById(R.id.c_form_reference_no_value);
		c_employer_type_value = (TextView)findViewById(R.id.c_employer_type_value);
		c_employer_contribution_value = (TextView)findViewById(R.id.c_employer_contribution_value);
		c_salary_grade_value = (TextView)findViewById(R.id.c_salary_grade_value);
		c_salary_step_value = (TextView)findViewById(R.id.c_salary_step_value);
		c_salary_structure_value = (TextView)findViewById(R.id.c_salary_structure_value);
		c_annual_basic_salary_value = (TextView)findViewById(R.id.c_annual_basic_salary_value);
		c_annual_transport_value = (TextView)findViewById(R.id.c_annual_transport_value);
		c_annual_rent_value = (TextView)findViewById(R.id.c_annual_rent_value);
		c_annual_other_pensionable_value = (TextView)findViewById(R.id.c_annual_other_pensionable_value);
		c_employee_contribution_value = (TextView)findViewById(R.id.c_employee_contribution_value);

		logout = (Button)findViewById(R.id.logoutButton);
		
		logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ConfirmationPage.this);
				CommonOps.logout(ConfirmationPage.this, true);
			}
			
		});
		
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ConfirmationPage.this);
				Intent intent = new Intent(ConfirmationPage.this, RegisterContributor.class);
				startActivity(intent);
				CommonOps.enterFromLeft(ConfirmationPage.this);
				finish();
			}
		});
		
		save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ConfirmationPage.this);
				submitToPencom(getBaseContext());
			}
		});

	}

	private void setCollectedValues() {

		//first the biometric		
		Bitmap passportBitmap = CommonOps.getPassportBitmap(this);
		Bitmap leftThumbBitmap = CommonOps.getLeftThumbBitmap(this);
		Bitmap rightThumbBitmap = CommonOps.getRightThumbBitmap(this);
		Bitmap signatureBitmap = CommonOps.getSignatureBitmap(this);

		if(passportBitmap != null)
			passport_confirm.setImageBitmap(passportBitmap);
		else{
			passportBitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.passport_place));
			passport_confirm.setImageBitmap(passportBitmap);
		}
		if(leftThumbBitmap != null)
			left_thumb_confirm.setImageBitmap(leftThumbBitmap);
		if(rightThumbBitmap != null)
			right_thumb_confirm.setImageBitmap(rightThumbBitmap);		
		if(signatureBitmap != null)
			signature_confirm.setImageBitmap(signatureBitmap);	


		//The personal data values
		if(pdPrefs != null){
			control_no_value.setText(pdPrefs.getString("controlNumberET", ""));
			account_no_value.setText(pdPrefs.getString("accountNumberET", ""));
			title_value.setText(pdPrefs.getString("titleSpinner", ""));
			date_of_birth_value.setText(pdPrefs.getString("dateOfBirthET", ""));
			place_of_birth_value.setText(pdPrefs.getString("placeOfBirthSpinner", ""));
			nationality_value.setText(pdPrefs.getString("nationalitySpinner", ""));
			surname_value.setText(pdPrefs.getString("pd_surnameET", ""));
			first_name_value.setText(pdPrefs.getString("pd_firstNameET", ""));
			middle_name_value.setText(pdPrefs.getString("pd_MiddleNameET", ""));
			marital_status_value.setText(pdPrefs.getString("maritalStatus", ""));
			sex_value.setText(pdPrefs.getString("pd_sexRG", ""));
			state_of_origin_value.setText(pdPrefs.getString("stateOfOriginSpinner", ""));
			lga_of_origin_value.setText(pdPrefs.getString("lgaOfOriginSpinner", ""));
			pd_email_value.setText(pdPrefs.getString("pdEmailET", ""));
			tel_l_value.setText(pdPrefs.getString("pdPhone1ET", ""));
			tel_2_value.setText(pdPrefs.getString("pdPhone2ET", ""));
			present_address_value.setText(pdPrefs.getString("pdPresentAddressET", ""));
			permanent_home_address_value.setText(pdPrefs.getString("pdPermanentHomeAddressET", ""));
			state_of_permanent_address_value.setText(pdPrefs.getString("stateOfpermanentAddressSpinner", ""));
			lga_of_permanent_address_value.setText(pdPrefs.getString("lgaOfpermanentAddressSpinner", ""));
			current_contact_address_1_value.setText(pdPrefs.getString("pdCurrentContactAddress1ET", ""));
			current_contact_address_2_value.setText(pdPrefs.getString("pdCurrentContactAddress2ET", ""));
			state_value.setText(pdPrefs.getString("pdStateAddressSpinner", ""));
			lga_value.setText(pdPrefs.getString("pdLgaAddressSpinner", ""));
		}


		//nok_data values
		if(nokPrefs != null){
			nok_title_value.setText(nokPrefs.getString("nokTitleSpinner", "Text Value"));
			nok_relationship_value.setText(nokPrefs.getString("nokRelationshipSpinner", ""));
			nok_sex_value.setText(nokPrefs.getString("nok_radiogroup", ""));
			nok_phone_no_value.setText(nokPrefs.getString("nokPhoneNumberET", ""));
			nok_date_of_birth_value.setText(nokPrefs.getString("nokDateOfBirthET", ""));
			nok_surname_value.setText(nokPrefs.getString("nokSurnameET", ""));
			nok_first_name_value.setText(nokPrefs.getString("nokFirstNameET", ""));
			nok_middle_name_value.setText(nokPrefs.getString("nokMiddleNameET", ""));
			nok_email_value.setText(nokPrefs.getString("nokEmailET", ""));
			nok_contact_address_pobox_1_value.setText(nokPrefs.getString("nokContactAddress1ET", ""));
			nok_contact_address_pobox_2_value.setText(nokPrefs.getString("nokContactAddress2ET", ""));
			nok_country_value.setText(nokPrefs.getString("nokCountrySpinner", ""));
			nok_state_value.setText(nokPrefs.getString("nokStateSpinner", ""));
			nok_lga_value.setText(nokPrefs.getString("nokLgaSpinner", ""));
		}


		//for the employer data values
		if(edPrefs != null){
			ed_employer_code_value.setText(edPrefs.getString("edEmployerCodeET", ""));
			ed_name_of_organization_value.setText(edPrefs.getString("edNameOfOrganizationET", ""));
			ed_office_address_1_value.setText(edPrefs.getString("edOfficeAddress1ET", ""));
			ed_office_address_2_value.setText(edPrefs.getString("edOfficeAddress2ET", ""));
			ed_state_value.setText(edPrefs.getString("edStateSpinner", ""));
			ed_lga_value.setText(edPrefs.getString("edLgaSpinner", ""));
			ed_profession_value.setText(edPrefs.getString("edProfessionET", ""));
			ed_phone_no_value.setText(edPrefs.getString("edPhoneNumberET", ""));
			ed_file_id_no_value.setText(edPrefs.getString("edFileIdNumberET", ""));
			ed_designation_value.setText(edPrefs.getString("edDesignationET", ""));
			ed_dofe_value.setText(edPrefs.getString("edDateOfFirstEmploymentET", ""));
			ed_doc_value.setText(edPrefs.getString("edDateOfConfirmationET", ""));
			ed_bolop_value.setText(edPrefs.getString("edBranchOrLocationOfPostingET", ""));
			ed_email_value.setText(edPrefs.getString("edEmailET", ""));
			private_public_value.setText(edPrefs.getString("private_public", ""));
			ed_sector2_value.setText(edPrefs.getString("sector_radiogroup", ""));	
		}

		//for the contribution data values
		if(cPrefs != null){

			c_form_reference_no_value.setText(cPrefs.getString("cFormReferenceNoET", ""));
			c_employer_type_value.setText(cPrefs.getString("cEMployerTypeET", ""));
			c_employer_contribution_value.setText(cPrefs.getString("cEmployerContributionET", ""));
			c_salary_grade_value.setText(cPrefs.getString("cSalaryGrade", ""));
			c_salary_step_value.setText(cPrefs.getString("cSalaryStep", ""));
			c_salary_structure_value.setText(cPrefs.getString("cSalaryStructure", ""));
			c_annual_basic_salary_value.setText(cPrefs.getString("cAnnualBasicSalaryET", ""));
			c_annual_transport_value.setText(cPrefs.getString("cAnnualTransportET", ""));
			c_annual_rent_value.setText(cPrefs.getString("cAnnualRentET", ""));
			c_annual_other_pensionable_value.setText(cPrefs.getString("cAnnualOtherPensionableET", ""));
			c_employee_contribution_value.setText(cPrefs.getString("cEmployeeContribution", ""));			
		}

	}

	public void submitToPencom(Context context){

		pictureData = ((CommonOps.getEncodedPassport(this) == null)?CommonOps.getBase64String("passport", context):CommonOps.getEncodedPassport(this));
		leftThumbData = ((CommonOps.getEncodedLeftThumb(this) == null)?CommonOps.getBase64String("leftThumb", context):CommonOps.getEncodedLeftThumb(this));
		rightThumbData = ((CommonOps.getEncodedRightThumb(this) == null)?CommonOps.getBase64String("rightThumb", context):CommonOps.getEncodedRightThumb(this));
		signatureData = ((CommonOps.getEncodedSignature(this) == null)?CommonOps.getBase64String("signature", context):CommonOps.getEncodedSignature(this));

		dialog = new MyDialog(this, getResources().getDrawable(R.drawable.warning_logo), "Attention please!", 
				"This will send the details of the contributor just registered to the server for RSA Pin generation." + 
		"\nDo you wish to continue?", "No", "Yes");
		dialog.show();
		dialog.getNegativeButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ConfirmationPage.this);
				dialog.dismiss();
				CommonOps.displaySaveDialogToSaveContributorDetails(ConfirmationPage.this);
			}
			
		});
		dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CommonOps.playClickSound(ConfirmationPage.this);
				dialog.dismiss();
				new SendAsync().execute();
			}
			
		});
		

	}

	class SendAsync extends AsyncTask<String, Void, HashMap<String, String>>{


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new MyProgressDialog(ConfirmationPage.this, "Sending Request", 
					"Hold on, \nConnecting to server...");
			CommonOps.playWarningSound(ConfirmationPage.this);
			progressDialog.show();
			
			//*************************************************************************
			//Actual setting of collected details starts here
			//*************************************************************************

			request = new SoapObject(NAMESPACE, METHOD_NAME);

			nc = new NewContributor();
			
			try{
				nc.setProperty(0, account_no_value.getText().toString());//ID-- account number
				nc.setProperty(1, first_name_value.getText().toString()); //MEMBER_FIRSTNAME;
				nc.setProperty(2, surname_value.getText().toString());//MEMBER_LAST_NAME;
				nc.setProperty(3, middle_name_value.getText().toString()); //MEMBER_OTHER_NAME this would be middle name
				nc.setProperty(4, present_address_value.getText().toString()); //PRESENT_ADDRESS;
				nc.setProperty(5, title_value.getText().toString());//MEMBER_TITLE;	
				nc.setProperty(6, tel_l_value.getText().toString());//MEMBER_PHONE;
				nc.setProperty(7, tel_2_value.getText().toString());//MEMBER_Mobile;
				nc.setProperty(8, pd_email_value.getText().toString());// MEMBER_EMAIL;
				nc.setProperty(9, place_of_birth_value.getText().toString());//PLACE_OF_BIRTH;
				nc.setProperty(10, date_of_birth_value.getText().toString());//DATE_OF_BIRTH;
				nc.setProperty(11, permanent_home_address_value.getText().toString());//PERMANENT_HOME_ADDRESS;
				nc.setProperty(12, marital_status_value.getText().toString());//MARITAL_STATUS;
				nc.setProperty(13, sex_value.getText().toString());//SEX
				nc.setProperty(14, nationality_value.getText().toString());//NATIONALITY;
				nc.setProperty(15, state_of_origin_value.getText().toString());//STATE_OF_ORIGIN;
				nc.setProperty(16, lga_of_origin_value.getText().toString());//LGA;
				nc.setProperty(17, nok_surname_value.getText().toString());//NEXT_OF_KIN_LAST_NAME;
				nc.setProperty(18, nok_first_name_value.getText().toString());//"NEXT_OF_KIN_FIRST_NAME"
				nc.setProperty(19, nok_middle_name_value.getText().toString());//NEXT_OF_KIN_OTHER_NAMES
				nc.setProperty(20, nok_sex_value.getText().toString());//NEXT_OF_KIN_SEX
				nc.setProperty(21, nok_contact_address_pobox_1_value.getText().toString());//NEXT_OF_KIN_ADDRESS
				nc.setProperty(22, nok_email_value.getText().toString());//NEXT_OF_KIN_EMAIL
				nc.setProperty(23, nok_phone_no_value.getText().toString());//NEXT_OF_KIN_PHONE
				nc.setProperty(24, nok_title_value.getText().toString());//NEXT_OF_KIN_TITLE
				nc.setProperty(25, nok_lga_value.getText().toString());//NEXT_OF_KIN_TOWN
				nc.setProperty(26, nok_state_value.getText().toString());//NEXT_OF_KIN_STATE
				nc.setProperty(27, nok_country_value.getText().toString());//NEXT_OF_KIN_Country
				nc.setProperty(28, nok_relationship_value.getText().toString());//NEXT_OF_KIN_RELATIONSHIP
				nc.setProperty(29, nok_contact_address_pobox_2_value.getText().toString());//NEXT_OF_KIN_CONTACT_ADDRESS
				nc.setProperty(30, ed_dofe_value.getText().toString());//DATE_OF_EMPLOYMENT;
				nc.setProperty(31, ed_file_id_no_value.getText().toString());//STAFF_NUMBER;
				nc.setProperty(32, CommonOps.getDate());//DATE_OF_REGISTRATION;
				nc.setProperty(33, ed_profession_value.getText().toString());//PROFESSION;
				nc.setProperty(34, /*pictureData*/"abezyd==");//PICTURE_DATA;
				nc.setProperty(35, "JPEG");//PICTURE_TYPE;
				nc.setProperty(36, /*signatureData*/"abezyd==");//SIGNATURE_DATA;
				nc.setProperty(37, "JPEG");//SIGNATURE_TYPE;
				nc.setProperty(38, /*leftThumbData*/"abezyd==");//LEFT_THUMB_DATA;
				nc.setProperty(39, /*rightThumbData*/"abezyd==");//RIGHT_THUMB_DATA;
				nc.setProperty(40, "JPEG");//THUMB_TYPE;	
				nc.setProperty(41, ed_employer_code_value.getText().toString());//EMPLOYER_Code;
				nc.setProperty(42, ed_name_of_organization_value.getText().toString());//EMPLOYER_NAME;
				nc.setProperty(43, ed_office_address_1_value.getText().toString());//EMPLOYER_Address;
				nc.setProperty(44, ed_lga_value.getText().toString());//EMPLOYER_Town;
				nc.setProperty(45, ed_state_value.getText().toString());//EMPLOYER_State;
				nc.setProperty(46, ed_phone_no_value.getText().toString());//EMPLOYER_Phone;
				nc.setProperty(47, ed_designation_value.getText().toString());//Designation;
				nc.setProperty(48, ed_bolop_value.getText().toString());//StateofPosting;
				nc.setProperty(49, ed_file_id_no_value.getText().toString());//FileNo;
				nc.setProperty(50, ed_dofe_value.getText().toString());//DateOfFirstEmployement;
				nc.setProperty(51, ed_doc_value.getText().toString());// DateOfConfirmation;
				nc.setProperty(52, c_employee_contribution_value.getText().toString());//EMPLOYEE_Contribution;
				nc.setProperty(53, c_employer_contribution_value.getText().toString());//EMPLOYER_Contribution;
				nc.setProperty(54, c_employer_type_value.getText().toString());//EMPLOYER_TYPE;
				nc.setProperty(55, c_salary_grade_value.getText().toString());//SALARY_GRADE_LEVEL;
				nc.setProperty(56, c_salary_step_value.getText().toString());//SALARY_STEP;
				nc.setProperty(57, c_salary_structure_value.getText().toString());//SALARY_STRUCTURE;
				nc.setProperty(58, c_annual_basic_salary_value.getText().toString());//ANNUAL_BASIC_SALARY;
				nc.setProperty(59, c_annual_transport_value.getText().toString());//ANNUAL_TRANSPORT;
				nc.setProperty(60, c_annual_rent_value.getText().toString());// ANNUAL_RENT;
				nc.setProperty(61, c_annual_other_pensionable_value.getText().toString());//ANNUAL_OTHER_PENSIONABLE;
				nc.setProperty(62, "24");//PFAID;
				nc.setProperty(63, "Comment");//Comment;
				nc.setProperty(64, c_form_reference_no_value.getText().toString());//FormReferenceNo----control number;
			}catch(Exception e){
				Log.e("FIELD FAILED", "Couldn't set all fields");
				return;
			}

			//************************************************************************
			//Setting of collected details ends here
			//************************************************************************

			dc = new DataClass();
			dc.add(nc);

			PropertyInfo pi = new PropertyInfo();
			pi.setName("data");
			pi.setType(dc.getClass());
			pi.setValue(dc);

			request.addProperty(pi);
			request.addProperty("usu", "username");
			request.addProperty("fun", "funny name");

			env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			env.dotNet = true;
			env.implicitTypes = true;
			env.setAddAdornments(false);
			env.setOutputSoapObject(request);

			env.addMapping(NAMESPACE, "data", new DataClass().getClass());

			tp = new HttpTransportSE(URL);
			tp.debug = true;

		}

		@Override
		protected HashMap<String, String> doInBackground(String... params) {
			try {

				tp.call(SOAP_ACTION, env);
				Log.e("Request", tp.requestDump);
				Log.e("Response", tp.responseDump);
				gotResponse = true;

				//well, this can happen here
				if(tp.responseDump != null){
					responseMap = new ParseResponse(tp.responseDump).extractPencomResponse();
					return responseMap;
				}


			}catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(HashMap<String, String> result) {
			super.onPostExecute(result);

			progressDialog.dismiss();
			CommonOps.playWarningSound(ConfirmationPage.this);

			if(result != null){

				Toast.makeText(ConfirmationPage.this, "Got Response!", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ConfirmationPage.this, ResponsePage.class);
				editor = pinDetailsPrefs.edit();
				editor.clear();
				if(responseMap != null && responseMap.containsKey("RSANO_PIN")){

					editor.putString("ID_SOLICITUD", responseMap.get("ID_SOLICITUD"));
					editor.putString("RSANO_PIN", responseMap.get("RSANO_PIN"));
					editor.putString("FORMREFERENCENO", responseMap.get("FORMREFERENCENO"));
					editor.putString("VALUEDATE", responseMap.get("VALUEDATE"));
					editor.putString("COMMENT", responseMap.get("COMMENT"));
					editor.commit();

					startActivity(intent);
					CommonOps.enterFromRight(ConfirmationPage.this);
					finish();

				}else{
					String errorString = responseMap.get("faultstring").substring(responseMap.get(
							"faultstring").indexOf("System.Exception:"), responseMap.get("faultstring").indexOf(" at") - 2);

					dialog = new MyDialog(ConfirmationPage.this, null, "Server error response", errorString, "OK");
					dialog.show();
					dialog.getPositiveButton().setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							CommonOps.playClickSound(ConfirmationPage.this);
							dialog.dismiss();
						}

					});

				}

			}else{
				Toast.makeText(ConfirmationPage.this, "Sorry, couldn't get response", Toast.LENGTH_LONG).show();

				Intent intent = new Intent(ConfirmationPage.this, ResponsePage.class);
				startActivity(intent);
				CommonOps.enterFromRight(ConfirmationPage.this);
				finish();
				//				
				//				dialog = new MyDialog(ConfirmationPage.this, null, "Network error", 
				//						"Didn't get any response,\nPlease check your network connection", "OK");	
				//				dialog.show();
				//				dialog.getPositiveButton().setOnClickListener(new OnClickListener(){
				//
				//					@Override
				//					public void onClick(View arg0) {
				//CommonOps.playClickSound(ConfirmationPage.this);
				//						dialog.dismiss();
				//					}
				//					
				//				});
			}
		}
	}
}
