package com.sigmapensions.sigmamobileapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MobileRegDatabase {

	public static final String DATABASE_NAME = "MOBILE_REG_DATABASE";
	public static final int DATABASE_VERSION = 2;
	public static final String CONTRIBUTOR_TABLE_NAME = "CONTRIBUTOR_TABLE";
	public static final String EMPLOYER_TABLE_NAME = "EMPLOYER_TABLE";

	public static final String _ID_COLUMN = "_id";

	public static final String PD_CONTROL_NUMBER_COLUMN = "PD_CONTROL_NUMBER";
	public static final String PD_ACCOUNT_NUMBER_COLUMN = "PD_ACCOUNT_NUMBER";
	public static final String PD_TITLE_COLUMN = "PD_TITLE";
	public static final String PD_DATE_OF_BIRTH_COLUMN = "PD_DATE_OF_BIRTH";
	public static final String PD_SURNAME_COLUMN = "PD_SURNAME";
	public static final String PD_FIRST_NAME_COLUMN = "PD_FIRST_NAME";
	public static final String PD_MIDDLE_NAME_COLUMN = "PD_MIDDLE_NAME";
	public static final String PD_MARITAL_STATUS_COLUMN = "PD_MARITAL_STATUS";
	public static final String PD_SEX_COLUMN = "PD_SEX";
	public static final String PD_STATE_OF_ORIGIN_COLUMN = "PD_STATE_OF_ORIGIN";
	public static final String PD_PLACE_OF_BIRTH_COLUMN = "PD_PLACE_OF_BIRTH";
	public static final String PD_NATIONALITY_COLUMN = "PD_NATIONALITY";
	public static final String PD_LGA_OF_ORIGIN_COLUMN = "PD_LGA_OF_ORIGIN";
	public static final String PD_EMAIL_COLUMN = "PD_EMAIL_ORIGIN";
	public static final String PD_PHONE1_COLUMN = "PD_PHONE1";
	public static final String PD_PHONE2_COLUMN = "PD_PHONE2";
	public static final String PD_PRESENT_ADDRESS_COLUMN = "PD_PRESENT_ADDRESS";
	public static final String PD_PERMANENT_HOME_ADDRESS_COLUMN = "PD_PERMANENT_HOME_ADDRESS";
	public static final String PD_STATE_OF_PERMANENT_HOME_ADDRESS_COLUMN = "PD_STATE_OF_PERMANENT_HOME_ADDRESS1";
	public static final String PD_LGA_OF_PERMANENT_HOME_ADDRESS_COLUMN = "PD_LGA_OF_PERMANENT_HOME_ADDRESS1";
	public static final String PD_CURRENT_CONTENT_ADDRESS1_COLUMN = "PD_CURRENT_CONTENT_ADDRESS1";
	public static final String PD_CURRENT_CONTENT_ADDRESS2_COLUMN = "PD_CURRENT_CONTENT_ADDRESS2";
	public static final String PD_STATE_ADDRESS_COLUMN = "PD_STATE_ADDRESS_COLUMN";
	public static final String PD_LGA_ADDRESS_COLUMN = "PD_LGA_ADDRESS_COLUMN";

	public static final String NOK_TITLE_COLUMN = "NOK_TITLE";
	public static final String NOK_RELATIONSHIP_COLUMN = "NOK_RELATIONSHIP";
	public static final String NOK_SEX_COLUMN = "NOK_SEX";
	public static final String NOK_PHONE_COLUMN = "NOK_PHONE";
	public static final String NOK_DATE_OF_BIRTH_COLUMN = "NOK_DATE_OF_BIRTH";
	public static final String NOK_SURNAME_COLUMN = "NOK_SURNAME";
	public static final String NOK_FIRST_NAME_COLUMN = "NOK_FIRST_NAME";
	public static final String NOK_MIDDLE_NAME_COLUMN = "NOK_MIDDLE_NAME";
	public static final String NOK_EMAIL_COLUMN = "NOK_EMAIL";
	public static final String NOK_HOME_ADDRESS_COLUMN = "NOK_HOME_ADDRESS";
	public static final String NOK_CONTACT_ADDRESS_COLUMN = "NOK_CONTACT_ADDRESS";
	public static final String NOK_COUNTRY_COLUMN = "NOK_COUNTRY";
	public static final String NOK_STATE_OF_ORIGIN_COLUMN = "NOK_STATE_OF_ORIGIN";
	public static final String NOK_LGA_OF_ORIGIN_COLUMN = "NOK_LGA_OF_ORIGIN";

	public static final String EMPLOYER_CODE_COLUMN = "EMPLOYER_CODE";
	public static final String EMPLOYER_NAME_OF_ORGANIZATION_COLUMN = "EMPLOYER_NAME_OF_ORGANIZATION";
	public static final String EMPLOYER_OFFICE_ADDRESS1_COLUMN = "EMPLOYER_OFFICE_ADDRESS1";
	public static final String EMPLOYER_OFFICE_ADDRESS2_COLUMN = "EMPLOYER_OFFICE_ADDRESS2";
	public static final String EMPLOYER_STATE_COLUMN = "EMPLOYER_STATE";
	public static final String EMPLOYER_LGA_COLUMN = "EMPLOYER_LGA";	
	public static final String EMPLOYER_PHONE_COLUMN = "EMPLOYER_PHONE";
	public static final String EMPLOYER_FILE_ID_COLUMN = "EMPLOYER_FILE_ID";
	public static final String PROFESSION_COLUMN = "PROFESSION";
	public static final String EMPLOYER_DESIGNATION_COLUMN = "EMPLOYER_DESIGNATION";
	public static final String EMPLOYER_DATE_OF_FIRST_EMPLOYMENT_COLUMN = "EMPLOYER_DATE_OF_FIRST_EMPLOYMENT";
	public static final String EMPLOYER_DATE_OF_CONFIRMATION_COLUMN = "EMPLOYER_DATE_OF_CONFIRMATION_COLUMN";
	public static final String EMPLOYER_BRANCH_OF_POSTING_COLUMN = "EMPLOYER_BRANCH_OF_POSTING";
	public static final String EMPLOYER_EMAIL_COLUMN = "EMPLOYER_EMAIL";
	public static final String EMPLOYER_PRIVATE_PUBLIC_COLUMN = "EMPLOYER_PRIVATE_PUBLIC";
	public static final String EMPLOYER_SECTOR_COLUMN = "EMPLOYER_SECTOR";

	public static final String 	FORM_REFERENCE_NO_COLUMN = "FORM_REFERENCE_NO";
	public static final String 	EMPLOYER_TYPE_COLUMN = "EMPLOYER_TYPE";
	public static final String 	EMPLOYER_CONTRIBUTION_COLUMN = "EMPLOYER_CONTRIBUTION";
	public static final String 	SALARY_GRADE_COLUMN = "SALARY_GRADE";
	public static final String 	SALARY_STEP_COLUMN = "SALARY_STEP";
	public static final String 	SALARY_STRUCTURE_COLUMN = "SALARY_STRUCTURE";
	public static final String 	ANNUAL_BASIC_SALARY_COLUMN = "ANNUAL_BASIC_SALARY";
	public static final String 	ANNUAL_TRANSPORT_COLUMN = "ANNUAL_TRANSPORT";
	public static final String 	ANNUAL_RENT_COLUMN = "ANNUAL_RENT";
	public static final String 	ANNUAL_OTHER_PENSIONABLE_COLUMN = "ANNUAL_OTHER_PENSIONABLE";
	public static final String 	EMPLOYEE_CONTRIBUTION_COLUMN = "EMPLOYEE_CONTRIBUTION";

	public static final String PASSPORT = "PASSPORT";
	public static final String LEFT_THUMB = "LEFT_THUMB";
	public static final String RIGHT_THUMB = "RIGHT_THUMB";
	public static final String SIGNATURE = "SIGNATURE";

	public static final String ID_SOLICITUD = "ID_SOLICITUD";
	public static final String RSANO_PIN = "RSANO_PIN";
	public static final String FORMREFERENCENO = "FORMREFERENCENO";
	public static final String VALUEDATE = "VALUEDATE";
	public static final String COMMENT = "COMMENT";

	public static final String WELCOME_LETTER = "WELCOME_LETTER";
	public static final String BACKED_UP = "BACKED_UP";
	public static final String SMS = "SMS";
	public static final String DATE = "DATE";


	MobileRegDatabaseHelper helper;
	Context context;

	public MobileRegDatabase(Context context){
		this.context = context;
		openDatabase();
		closeDatabase();
	}

	public void closeDatabase() {
		helper.close();
	}

	public void openDatabase() {
		helper = new MobileRegDatabaseHelper(this.context);
		helper.getWritableDatabase();
	}

	class MobileRegDatabaseHelper extends SQLiteOpenHelper{

		public MobileRegDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS " + CONTRIBUTOR_TABLE_NAME +
					" (" + _ID_COLUMN + " INTEGER PRIMARY KEY, " +
					PD_CONTROL_NUMBER_COLUMN + " TEXT, " + 
					PD_ACCOUNT_NUMBER_COLUMN + " TEXT, " + 
					PD_TITLE_COLUMN + " TEXT, " +
					PD_DATE_OF_BIRTH_COLUMN + " TEXT, " +
					PD_SURNAME_COLUMN + " TEXT, " +
					PD_FIRST_NAME_COLUMN + " TEXT, " +
					PD_MIDDLE_NAME_COLUMN + " TEXT, " +
					PD_MARITAL_STATUS_COLUMN + " TEXT, " +
					PD_SEX_COLUMN + " TEXT, " +
					PD_PLACE_OF_BIRTH_COLUMN + " TEXT, " +
					PD_NATIONALITY_COLUMN + " TEXT, " +
					PD_STATE_OF_ORIGIN_COLUMN + " TEXT, " +
					PD_LGA_OF_ORIGIN_COLUMN + " TEXT, " +
					PD_EMAIL_COLUMN + " TEXT, " +
					PD_PHONE1_COLUMN + " TEXT, " +
					PD_PHONE2_COLUMN + " TEXT, " +
					PD_PRESENT_ADDRESS_COLUMN + " TEXT, " +
					PD_PERMANENT_HOME_ADDRESS_COLUMN + " TEXT, " +
					PD_STATE_OF_PERMANENT_HOME_ADDRESS_COLUMN + " TEXT, " +
					PD_LGA_OF_PERMANENT_HOME_ADDRESS_COLUMN + " TEXT, " +
					PD_CURRENT_CONTENT_ADDRESS1_COLUMN + " TEXT, " +
					PD_CURRENT_CONTENT_ADDRESS2_COLUMN + " TEXT, " +
					PD_STATE_ADDRESS_COLUMN + " TEXT, " +
					PD_LGA_ADDRESS_COLUMN + " TEXT, " +

					NOK_TITLE_COLUMN + " TEXT, " +
					NOK_RELATIONSHIP_COLUMN + " TEXT, " +
					NOK_SEX_COLUMN + " TEXT, " +
					NOK_PHONE_COLUMN + " TEXT, " +
					NOK_DATE_OF_BIRTH_COLUMN + " TEXT, " +
					NOK_SURNAME_COLUMN + " TEXT, " +
					NOK_FIRST_NAME_COLUMN + " TEXT, " +
					NOK_MIDDLE_NAME_COLUMN + " TEXT, " +
					NOK_EMAIL_COLUMN + " TEXT, " +
					NOK_HOME_ADDRESS_COLUMN + " TEXT, " +
					NOK_CONTACT_ADDRESS_COLUMN + " TEXT, " +
					NOK_COUNTRY_COLUMN + " TEXT, " +
					NOK_STATE_OF_ORIGIN_COLUMN + " TEXT, " +
					NOK_LGA_OF_ORIGIN_COLUMN + " TEXT, " +

					EMPLOYER_CODE_COLUMN + " TEXT, " +
					EMPLOYER_NAME_OF_ORGANIZATION_COLUMN + " TEXT, " +
					EMPLOYER_OFFICE_ADDRESS1_COLUMN + " TEXT, " +
					EMPLOYER_OFFICE_ADDRESS2_COLUMN + " TEXT, " +
					EMPLOYER_STATE_COLUMN + " TEXT, " +
					EMPLOYER_LGA_COLUMN + " TEXT, " +	
					EMPLOYER_PHONE_COLUMN + " TEXT, " +
					EMPLOYER_FILE_ID_COLUMN + " TEXT, " +
					EMPLOYER_DESIGNATION_COLUMN + " TEXT, " +
					EMPLOYER_DATE_OF_FIRST_EMPLOYMENT_COLUMN + " TEXT, " +
					EMPLOYER_DATE_OF_CONFIRMATION_COLUMN + " TEXT, " +
					EMPLOYER_BRANCH_OF_POSTING_COLUMN + " TEXT, " +
					EMPLOYER_EMAIL_COLUMN + " TEXT, " +
					EMPLOYER_PRIVATE_PUBLIC_COLUMN + " TEXT, " +
					EMPLOYER_SECTOR_COLUMN + " TEXT, " +

					FORM_REFERENCE_NO_COLUMN + " TEXT, " + 
					PROFESSION_COLUMN + " TEXT, " + 
					EMPLOYER_TYPE_COLUMN + " CHAR(1) NOT NULL DEFAULT '1', " + 
					EMPLOYER_CONTRIBUTION_COLUMN + " TEXT, " + 
					SALARY_GRADE_COLUMN + " TEXT, " + 
					SALARY_STEP_COLUMN + " TEXT, " + 
					SALARY_STRUCTURE_COLUMN + " TEXT, " + 
					ANNUAL_BASIC_SALARY_COLUMN + " TEXT, " + 
					ANNUAL_TRANSPORT_COLUMN + " TEXT, " + 
					ANNUAL_RENT_COLUMN + " TEXT, " + 
					ANNUAL_OTHER_PENSIONABLE_COLUMN + " TEXT, " + 
					EMPLOYEE_CONTRIBUTION_COLUMN + " TEXT, " + 

					PASSPORT + " TEXT, " +
					LEFT_THUMB + " TEXT, " +
					RIGHT_THUMB + " TEXT, " +
					SIGNATURE + " TEXT, " +

					ID_SOLICITUD + " TEXT, " +
					RSANO_PIN + " TEXT, " +
					FORMREFERENCENO + " TEXT, " +
					VALUEDATE + " TEXT, " +
					COMMENT + " TEXT, " +

					WELCOME_LETTER + " CHAR(1) NOT NULL DEFAULT 'N', " +
					BACKED_UP + " CHAR(1) NOT NULL DEFAULT 'N', " +
					SMS + " CHAR(1) NOT NULL DEFAULT 'N', " +
					DATE + " DATE NOT NULL " +

					" )");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP  TABLE IF EXISTS " + CONTRIBUTOR_TABLE_NAME );
			onCreate(db);
		}

	}

}
