package com.sigmapensions.sigmamobileapp.utils;

import java.sql.Date;

import com.sigmapensions.sigmamobileapp.MobileRegDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;


public class SaveContributorDetails {
	private SQLiteDatabase database;

	public SaveContributorDetails(SQLiteDatabase database){
		this.database = database;
	}

	public boolean save(Context context, boolean gotPin, boolean sentSMS, boolean welcomeLetter){

		SharedPreferences pdPref = CommonOps.getPdPrefs(context);
		SharedPreferences NOKPref = CommonOps.getNOKPrefs(context);
		SharedPreferences edPref = CommonOps.getEdPrefs(context);		
		SharedPreferences cPref = CommonOps.getCPrefs(context);
		SharedPreferences bPref = CommonOps.getbPrefs(context);
		SharedPreferences pinDetailsPref = CommonOps.getPinDetailsPrefs(context);

		ContentValues cv = new ContentValues();
		cv.put(MobileRegDatabase.PD_CONTROL_NUMBER_COLUMN, pdPref.getString("controlNumberET", ""));
		cv.put(MobileRegDatabase.PD_ACCOUNT_NUMBER_COLUMN, pdPref.getString("accountNumberET", ""));
		cv.put(MobileRegDatabase.PD_TITLE_COLUMN, pdPref.getString("titleSpinner", ""));
		cv.put(MobileRegDatabase.PD_DATE_OF_BIRTH_COLUMN, pdPref.getString("dateOfBirthET", ""));
		cv.put(MobileRegDatabase.PD_SURNAME_COLUMN, pdPref.getString("pd_surnameET", ""));
		cv.put(MobileRegDatabase.PD_FIRST_NAME_COLUMN, pdPref.getString("pd_firstNameET", ""));
		cv.put(MobileRegDatabase.PD_MIDDLE_NAME_COLUMN, pdPref.getString("pd_MiddleNameET", ""));
		cv.put(MobileRegDatabase.PD_MARITAL_STATUS_COLUMN, pdPref.getString("maritalStatus", ""));
		cv.put(MobileRegDatabase.PD_SEX_COLUMN, pdPref.getString("pd_sexRG", ""));
		cv.put(MobileRegDatabase.PD_PLACE_OF_BIRTH_COLUMN, pdPref.getString("placeOfBirthSpinner", ""));
		cv.put(MobileRegDatabase.PD_NATIONALITY_COLUMN, pdPref.getString("nationalitySpinner", ""));
		cv.put(MobileRegDatabase.PD_STATE_OF_ORIGIN_COLUMN, pdPref.getString("stateOfOriginSpinner", ""));
		cv.put(MobileRegDatabase.PD_LGA_OF_ORIGIN_COLUMN, pdPref.getString("lgaOfOriginSpinner", ""));
		cv.put(MobileRegDatabase.PD_EMAIL_COLUMN, pdPref.getString("pdEmailET", ""));
		cv.put(MobileRegDatabase.PD_PHONE1_COLUMN, pdPref.getString("pdPhone1ET", ""));
		cv.put(MobileRegDatabase.PD_PHONE2_COLUMN, pdPref.getString("pdPhone2ET", ""));
		cv.put(MobileRegDatabase.PD_PRESENT_ADDRESS_COLUMN, pdPref.getString("pdPresentAddressET", ""));
		cv.put(MobileRegDatabase.PD_PERMANENT_HOME_ADDRESS_COLUMN, pdPref.getString("pdPermanentHomeAddressET", ""));
		cv.put(MobileRegDatabase.PD_STATE_OF_PERMANENT_HOME_ADDRESS_COLUMN, pdPref.getString("stateOfpermanentAddressSpinner", ""));
		cv.put(MobileRegDatabase.PD_LGA_OF_PERMANENT_HOME_ADDRESS_COLUMN, pdPref.getString("lgaOfpermanentAddressSpinner", ""));
		cv.put(MobileRegDatabase.PD_CURRENT_CONTENT_ADDRESS1_COLUMN, pdPref.getString("pdCurrentContactAddress1ET", ""));
		cv.put(MobileRegDatabase.PD_CURRENT_CONTENT_ADDRESS2_COLUMN, pdPref.getString("pdCurrentContactAddress2ET", ""));
		cv.put(MobileRegDatabase.PD_STATE_ADDRESS_COLUMN, pdPref.getString("pdStateAddressSpinner", ""));
		cv.put(MobileRegDatabase.PD_LGA_ADDRESS_COLUMN, pdPref.getString("pdLgaAddressSpinner", ""));


		cv.put(MobileRegDatabase.NOK_TITLE_COLUMN, NOKPref.getString("nokTitleSpinner", ""));
		cv.put(MobileRegDatabase.NOK_SURNAME_COLUMN, NOKPref.getString("nokSurnameET", ""));
		cv.put(MobileRegDatabase.NOK_FIRST_NAME_COLUMN, NOKPref.getString("nokFirstNameET", ""));
		cv.put(MobileRegDatabase.NOK_MIDDLE_NAME_COLUMN, NOKPref.getString("nokMiddleNameET", ""));
		cv.put(MobileRegDatabase.NOK_DATE_OF_BIRTH_COLUMN, NOKPref.getString("nokDateOfBirthET", ""));
		cv.put(MobileRegDatabase.NOK_SEX_COLUMN, NOKPref.getString("nok_radiogroup", ""));
		cv.put(MobileRegDatabase.NOK_RELATIONSHIP_COLUMN, NOKPref.getString("nokRelationshipSpinner", ""));
		cv.put(MobileRegDatabase.NOK_PHONE_COLUMN, NOKPref.getString("nokPhoneNumberET", ""));
		cv.put(MobileRegDatabase.NOK_EMAIL_COLUMN, NOKPref.getString("nokEmailET", ""));
		cv.put(MobileRegDatabase.NOK_HOME_ADDRESS_COLUMN, NOKPref.getString("nokContactAddress1ET", ""));
		cv.put(MobileRegDatabase.NOK_CONTACT_ADDRESS_COLUMN, NOKPref.getString("nokContactAddress2ET", ""));
		cv.put(MobileRegDatabase.NOK_COUNTRY_COLUMN, NOKPref.getString("nokCountrySpinner", ""));
		cv.put(MobileRegDatabase.NOK_STATE_OF_ORIGIN_COLUMN, NOKPref.getString("nokStateSpinner", ""));
		cv.put(MobileRegDatabase.NOK_LGA_OF_ORIGIN_COLUMN, NOKPref.getString("nokLgaSpinner", ""));


		cv.put(MobileRegDatabase.EMPLOYER_CODE_COLUMN, edPref.getString("edEmployerCodeET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_NAME_OF_ORGANIZATION_COLUMN, edPref.getString("edNameOfOrganizationET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_OFFICE_ADDRESS1_COLUMN, edPref.getString("edOfficeAddress1ET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_OFFICE_ADDRESS2_COLUMN, edPref.getString("edOfficeAddress2ET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_STATE_COLUMN, edPref.getString("edStateSpinner", ""));
		cv.put(MobileRegDatabase.EMPLOYER_LGA_COLUMN, edPref.getString("edLgaSpinner", ""));
		cv.put(MobileRegDatabase.EMPLOYER_PHONE_COLUMN, edPref.getString("edPhoneNumberET", ""));
		cv.put(MobileRegDatabase.PROFESSION_COLUMN, edPref.getString("edProfessionET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_FILE_ID_COLUMN, edPref.getString("edFileIdNumberET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_DESIGNATION_COLUMN, edPref.getString("edDesignationET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_DATE_OF_FIRST_EMPLOYMENT_COLUMN, edPref.getString("edDateOfFirstEmploymentET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_DATE_OF_CONFIRMATION_COLUMN, edPref.getString("edDateOfConfirmationET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_BRANCH_OF_POSTING_COLUMN, edPref.getString("edBranchOrLocationOfPostingET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_EMAIL_COLUMN, edPref.getString("edEmailET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_PRIVATE_PUBLIC_COLUMN, edPref.getString("private_public", ""));
		cv.put(MobileRegDatabase.EMPLOYER_PRIVATE_PUBLIC_COLUMN, edPref.getString("sector_radiogroup", ""));
		cv.put(MobileRegDatabase.EMPLOYER_SECTOR_COLUMN, edPref.getString("private_public", ""));


		cv.put(MobileRegDatabase.FORM_REFERENCE_NO_COLUMN, cPref.getString("cFormReferenceNoET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_TYPE_COLUMN, cPref.getString("cEMployerTypeET", ""));
		cv.put(MobileRegDatabase.EMPLOYER_CONTRIBUTION_COLUMN, cPref.getString("cEmployerContributionET", ""));
		cv.put(MobileRegDatabase.SALARY_GRADE_COLUMN, cPref.getString("cSalaryGrade", ""));
		cv.put(MobileRegDatabase.SALARY_STEP_COLUMN, cPref.getString("cSalaryStep", ""));		
		cv.put(MobileRegDatabase.SALARY_STRUCTURE_COLUMN, cPref.getString("cSalaryStructure", ""));
		cv.put(MobileRegDatabase.ANNUAL_BASIC_SALARY_COLUMN, cPref.getString("cAnnualBasicSalaryET", ""));
		cv.put(MobileRegDatabase.ANNUAL_TRANSPORT_COLUMN, cPref.getString("cAnnualTransportET", ""));
		cv.put(MobileRegDatabase.ANNUAL_RENT_COLUMN, cPref.getString("cAnnualRentET", ""));
		cv.put(MobileRegDatabase.ANNUAL_OTHER_PENSIONABLE_COLUMN, cPref.getString("cAnnualOtherPensionableET", ""));
		cv.put(MobileRegDatabase.EMPLOYEE_CONTRIBUTION_COLUMN, cPref.getString("cEmployeeContribution", ""));


		cv.put(MobileRegDatabase.PASSPORT, bPref.getString("PASSPORT", ""));
		cv.put(MobileRegDatabase.LEFT_THUMB, bPref.getString("LEFT_THUMB", ""));
		cv.put(MobileRegDatabase.RIGHT_THUMB, bPref.getString("RIGHT_THUMB", ""));
		cv.put(MobileRegDatabase.SIGNATURE, bPref.getString("SIGNATURE", ""));


		if(gotPin){
			cv.put(MobileRegDatabase.ID_SOLICITUD, pinDetailsPref.getString("ID_SOLICITUD", "Not Available"));
			cv.put(MobileRegDatabase.RSANO_PIN, pinDetailsPref.getString("RSANO_PIN", "Not Available"));
			cv.put(MobileRegDatabase.FORMREFERENCENO, pinDetailsPref.getString("FORMREFERENCENO", "Not Available"));
			cv.put(MobileRegDatabase.VALUEDATE, pinDetailsPref.getString("VALUEDATE", "Not Available"));
			cv.put(MobileRegDatabase.COMMENT, pinDetailsPref.getString("COMMENT", "Not Available"));
		}else{
			cv.put(MobileRegDatabase.ID_SOLICITUD, "Not Available");
			cv.put(MobileRegDatabase.RSANO_PIN, "Not Available");
			cv.put(MobileRegDatabase.FORMREFERENCENO, "Not Available");
			cv.put(MobileRegDatabase.VALUEDATE, "Not Available");
			cv.put(MobileRegDatabase.COMMENT, "Not Available");
		}


		cv.put(MobileRegDatabase.WELCOME_LETTER, (sentSMS)?"Y":"N");
		cv.put(MobileRegDatabase.BACKED_UP, "N");
		cv.put(MobileRegDatabase.SMS, (sentSMS)?"Y":"N");
		cv.put(MobileRegDatabase.DATE, new Date(System.currentTimeMillis()).toString());

		int success = (int) database.insert(MobileRegDatabase.CONTRIBUTOR_TABLE_NAME, null, cv);
		if(success != -1)
			return true;

		return false;
	}

}
