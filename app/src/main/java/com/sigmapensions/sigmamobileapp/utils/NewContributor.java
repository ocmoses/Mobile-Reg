package com.sigmapensions.sigmamobileapp.utils;


import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class NewContributor implements KvmSerializable{

	public String ID;
	public String MEMBER_FIRSTNAME;
	public String MEMBER_LAST_NAME;
	public String MEMBER_OTHER_NAME;
	public String PRESENT_ADDRESS;
	public String MEMBER_TITLE;
	public String MEMBER_PHONE;
	public String MEMBER_Mobile;
	public String MEMBER_EMAIL;
	public String PLACE_OF_BIRTH;
	public String DATE_OF_BIRTH;
	public String PERMANENT_HOME_ADDRESS;
	public String MARITAL_STATUS;
	public String SEX;
	public String NATIONALITY;
	public String STATE_OF_ORIGIN;
	public String LGA;

	public String NEXT_OF_KIN_LAST_NAME;
	public String NEXT_OF_KIN_FIRST_NAME;
	public String NEXT_OF_KIN_OTHER_NAMES;
	public String NEXT_OF_KIN_SEX;
	public String NEXT_OF_KIN_ADDRESS;
	public String NEXT_OF_KIN_EMAIL;
	public String NEXT_OF_KIN_PHONE;
	public String NEXT_OF_KIN_TITLE;
	public String NEXT_OF_KIN_Town;
	public String NEXT_OF_KIN_State;
	public String NEXT_OF_KIN_Country;
	public String NEXT_OF_KIN_RELATIONSHIP;
	public String NEXT_OF_KIN_CONTACT_ADDRESS;
	public String DATE_OF_EMPLOYMENT;
	public String STAFF_NUMBER;
	public String DATE_OF_REGISTRATION;
	public String PROFESSION;

	public String PICTURE_DATA;
	public String PICTURE_TYPE;
	public String SIGNATURE_DATA;
	public String SIGNATURE_TYPE;
	public String LEFT_THUMB_DATA;
	public String RIGHT_THUMB_DATA;
	public String THUMB_TYPE;

	public String EMPLOYER_Code;
	public String EMPLOYER_NAME;
	public String EMPLOYER_Address;
	public String EMPLOYER_Town;
	public String EMPLOYER_State;
	public String EMPLOYER_Phone;	
	public String Designation;
	public String StateofPosting;
	public String FileNo;
	public String DateOfFirstEmployement;
	public String DateOfConfirmation;
	public String EMPLOYEE_Contribution;
	public String EMPLOYER_Contribution;
	public String EMPLOYER_TYPE;

	public String SALARY_GRADE_LEVEL;
	public String SALARY_STEP;
	public String SALARY_STRUCTURE;
	public String ANNUAL_BASIC_SALARY;
	public String ANNUAL_TRANSPORT;
	public String ANNUAL_RENT;
	public String ANNUAL_OTHER_PENSIONABLE;
	public String PFAID;
	public String Comment;
	public String FormReferenceNo;

	@Override
	public Object getProperty(int pr) {
		switch(pr){
		case 0:
			return ID;
		case 1:
			return MEMBER_FIRSTNAME;
		case 2:
			return MEMBER_LAST_NAME;
		case 3:
			return MEMBER_OTHER_NAME;
		case 4:
			return PRESENT_ADDRESS;
		case 5:
			return MEMBER_TITLE;
		case 6:
			return MEMBER_PHONE;
		case 7:
			return MEMBER_Mobile;
		case 8:
			return MEMBER_EMAIL;
		case 9:
			return PLACE_OF_BIRTH;
		case 10:
			return DATE_OF_BIRTH;
		case 11:
			return PERMANENT_HOME_ADDRESS;
		case 12:
			return MARITAL_STATUS;
		case 13:
			return SEX;

		case 14:
			return NATIONALITY;

		case 15:
			return STATE_OF_ORIGIN;

		case 16:
			return LGA;

		case 17:
			return NEXT_OF_KIN_LAST_NAME;

		case 18:
			return NEXT_OF_KIN_FIRST_NAME;

		case 19:
			return NEXT_OF_KIN_OTHER_NAMES;


		case 20:
			return NEXT_OF_KIN_SEX;

		case 21:
			return NEXT_OF_KIN_ADDRESS;

		case 22:
			return NEXT_OF_KIN_EMAIL;

		case 23:
			return NEXT_OF_KIN_PHONE;

		case 24:
			return NEXT_OF_KIN_TITLE;

		case 25:
			return NEXT_OF_KIN_Town;

		case 26:
			return NEXT_OF_KIN_State;

		case 27:
			return NEXT_OF_KIN_Country;

		case 28:
			return NEXT_OF_KIN_RELATIONSHIP;

		case 29:
			return NEXT_OF_KIN_CONTACT_ADDRESS;

		case 30:
			return DATE_OF_EMPLOYMENT;

		case 31:
			return STAFF_NUMBER;

		case 32:
			return DATE_OF_REGISTRATION;

		case 33:
			return PROFESSION;

		case 34:
			return PICTURE_DATA;

		case 35:
			return PICTURE_TYPE;

		case 36:
			return SIGNATURE_DATA;

		case 37:
			return SIGNATURE_TYPE;

		case 38:
			return LEFT_THUMB_DATA;

		case 39:
			return RIGHT_THUMB_DATA;

		case 40:
			return THUMB_TYPE;


		case 41:
			return EMPLOYER_Code;

		case 42:
			return EMPLOYER_NAME;

		case 43:
			return EMPLOYER_Address;

		case 44:
			return EMPLOYER_Town;

		case 45:
			return EMPLOYER_State;

		case 46:
			return EMPLOYER_Phone;

		case 47:
			return Designation;

		case 48:
			return StateofPosting;

		case 49:
			return FileNo;

		case 50:
			return DateOfFirstEmployement;


		case 51:
			return DateOfConfirmation;

		case 52:
			return EMPLOYEE_Contribution;

		case 53:
			return EMPLOYER_Contribution;

		case 54:
			return EMPLOYER_TYPE;

		case 55:
			return SALARY_GRADE_LEVEL;

		case 56:
			return SALARY_STEP;

		case 57:
			return  SALARY_STRUCTURE;

		case 58:
			return ANNUAL_BASIC_SALARY;

		case 59:
			return ANNUAL_TRANSPORT;

		case 60:
			return ANNUAL_RENT;


		case 61:
			return ANNUAL_OTHER_PENSIONABLE;

		case 62:
			return  PFAID;

		case 63:
			return Comment;

		case 64:
			return FormReferenceNo;
		
		default:
			break;

		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 65;
	}

	@Override
	public void getPropertyInfo(int pr, Hashtable arg1, PropertyInfo pi) {
		switch(pr){
		case 0:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "ID";
			break;
		case 1:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MEMBER_FIRSTNAME";
			break;
		case 2:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MEMBER_LAST_NAME";
			break;
		case 3:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MEMBER_OTHER_NAME";
			break;
		case 4:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "PRESENT_ADDRESS";
			break;
		case 5:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MEMBER_TITLE";
			break;
		case 6:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MEMBER_PHONE";
			break;
		case 7:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MEMBER_Mobile";
			break;
		case 8:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MEMBER_EMAIL";
			break;
		case 9:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "PLACE_OF_BIRTH";
			break;
		case 10:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "DATE_OF_BIRTH";
			break;
		case 11:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "PERMANENT_HOME_ADDRESS";
			break;
		case 12:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "MARITAL_STATUS";
			break;
		case 13:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "SEX";
			break;
		case 14:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NATIONALITY";
			break;
		case 15:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "STATE_OF_ORIGIN";
			break;
		case 16:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "LGA";
			break;
		case 17:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_LAST_NAME";
			break;
		case 18:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_FIRST_NAME";
			break;
		case 19:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_OTHER_NAMES";
			break;

		case 20:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_SEX";
			break;
		case 21:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_ADDRESS";
			break;
		case 22:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_EMAIL";
			break;
		case 23:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_PHONE";
			break;
		case 24:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_TITLE";
			break;
		case 25:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_Town";
			break;
		case 26:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_State";
			break;
		case 27:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_Country";
			break;
		case 28:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_RELATIONSHIP";
			break;
		case 29:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "NEXT_OF_KIN_CONTACT_ADDRESS";
			break;
		case 30:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "DATE_OF_EMPLOYMENT";
			break;
		case 31:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "STAFF_NUMBER";
			break;
		case 32:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "DATE_OF_REGISTRATION";
			break;
		case 33:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "PROFESSION";
			break;
			//This is where I declare the type for the picture data
		case 34:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "PICTURE_DATA";
			break;
		case 35:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "PICTURE_TYPE";
			break;

			//This is where I declare the type for the signature data
		case 36:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "SIGNATURE_DATA";
			break;
		case 37:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "SIGNATURE_TYPE";
			break;

			//This is where I declare the type for the left thumb data
		case 38:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "LEFT_THUMB_DATA";
			break;

			//This is where I declare the type for the right thumb data
		case 39:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "RIGHT_THUMB_DATA";
			break;
		case 40:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "THUMB_TYPE";
			break;

		case 41:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_Code";
			break;
		case 42:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_NAME";
			break;
		case 43:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_Address";
			break;
		case 44:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_Town";
			break;
		case 45:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_State";
			break;
		case 46:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_Phone";
			break;
		case 47:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "Designation";
			break;
		case 48:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "StateofPosting";
			break;
		case 49:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "FileNo";
			break;
		case 50:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "DateOfFirstEmployement";
			break;

		case 51:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "DateOfConfirmation";
			break;
		case 52:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYEE_Contribution";
			break;
		case 53:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_Contribution";
			break;
		case 54:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "EMPLOYER_TYPE";
			break;
		case 55:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "SALARY_GRADE_LEVEL";
			break;
		case 56:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "SALARY_STEP";
			break;
		case 57:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "SALARY_STRUCTURE";
			break;
		case 58:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "ANNUAL_BASIC_SALARY";
			break;
		case 59:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "ANNUAL_TRANSPORT";
			break;
		case 60:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "ANNUAL_RENT";
			break;

		case 61:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "ANNUAL_OTHER_PENSIONABLE";
			break;
		case 62:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "PFAID";
			break;
		case 63:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "Comment";
			break;
		case 64:
			pi.type = PropertyInfo.STRING_CLASS;
			pi.name = "FormReferenceNo";
			break;
		}

	}

	@Override
	public void setProperty(int pr, Object arg1) {
		switch(pr){
		case 0:
			this.ID = arg1.toString(); break;
		case 1:
			this.MEMBER_FIRSTNAME = arg1.toString(); break;
		case 2:
			this.MEMBER_LAST_NAME = arg1.toString(); break;
		case 3:
			this.MEMBER_OTHER_NAME = arg1.toString(); break;
		case 4:
			this.PRESENT_ADDRESS = arg1.toString(); break;
		case 5:
			this.MEMBER_TITLE = arg1.toString(); break;
		case 6:
			this.MEMBER_PHONE = arg1.toString(); break;
		case 7:
			this.MEMBER_Mobile = arg1.toString(); break;
		case 8:
			this.MEMBER_EMAIL = arg1.toString(); break;
		case 9:
			this.PLACE_OF_BIRTH = arg1.toString(); break;
		case 10:
			this.DATE_OF_BIRTH = arg1.toString(); break;
		case 11:
			this.PERMANENT_HOME_ADDRESS = arg1.toString(); break;
		case 12:
			this.MARITAL_STATUS = arg1.toString(); break;
		case 13:
			this.SEX = arg1.toString(); break;

		case 14:
			this.NATIONALITY = arg1.toString(); break;

		case 15:
			this.STATE_OF_ORIGIN = arg1.toString(); break;

		case 16:
			this.LGA = arg1.toString(); break;

		case 17:
			this.NEXT_OF_KIN_LAST_NAME = arg1.toString(); break;

		case 18:
			this.NEXT_OF_KIN_FIRST_NAME = arg1.toString(); break;

		case 19:
			this.NEXT_OF_KIN_OTHER_NAMES = arg1.toString(); break;


		case 20:
			this.NEXT_OF_KIN_SEX = arg1.toString(); break;

		case 21:
			this.NEXT_OF_KIN_ADDRESS = arg1.toString(); break;

		case 22:
			this.NEXT_OF_KIN_EMAIL = arg1.toString(); break;

		case 23:
			this.NEXT_OF_KIN_PHONE = arg1.toString(); break;

		case 24:
			this.NEXT_OF_KIN_TITLE = arg1.toString(); break;

		case 25:
			this.NEXT_OF_KIN_Town = arg1.toString(); break;

		case 26:
			this.NEXT_OF_KIN_State = arg1.toString(); break;

		case 27:
			this.NEXT_OF_KIN_Country = arg1.toString(); break;

		case 28:
			this.NEXT_OF_KIN_RELATIONSHIP = arg1.toString(); break;

		case 29:
			this.NEXT_OF_KIN_CONTACT_ADDRESS = arg1.toString(); break;

		case 30:
			this.DATE_OF_EMPLOYMENT = arg1.toString(); break;

		case 31:
			this.STAFF_NUMBER = arg1.toString(); break;

		case 32:
			this.DATE_OF_REGISTRATION = arg1.toString(); break;

		case 33:
			this.PROFESSION = arg1.toString(); break;

		case 34:
			this.PICTURE_DATA = arg1.toString(); break;

		case 35:
			this.PICTURE_TYPE = arg1.toString(); break;

		case 36:
			this.SIGNATURE_DATA = arg1.toString(); break;

		case 37:
			this.SIGNATURE_TYPE = arg1.toString(); break;

		case 38:
			this.LEFT_THUMB_DATA = arg1.toString(); break;

		case 39:
			this.RIGHT_THUMB_DATA = arg1.toString(); break;

		case 40:
			this.THUMB_TYPE = arg1.toString(); break;


		case 41:
			this.EMPLOYER_Code = arg1.toString(); break;

		case 42:
			this.EMPLOYER_NAME = arg1.toString(); break;

		case 43:
			this.EMPLOYER_Address = arg1.toString(); break;

		case 44:
			this.EMPLOYER_Town = arg1.toString(); break;

		case 45:
			this.EMPLOYER_State = arg1.toString(); break;

		case 46:
			this.EMPLOYER_Phone = arg1.toString(); break;

		case 47:
			this.Designation = arg1.toString(); break;

		case 48:
			this.StateofPosting = arg1.toString(); break;

		case 49:
			this.FileNo = arg1.toString(); break;

		case 50:
			this.DateOfFirstEmployement = arg1.toString(); break;


		case 51:
			this.DateOfConfirmation = arg1.toString(); break;

		case 52:
			this.EMPLOYEE_Contribution = arg1.toString(); break;

		case 53:
			this.EMPLOYER_Contribution = arg1.toString(); break;

		case 54:
			this.EMPLOYER_TYPE = arg1.toString(); break;

		case 55:
			this.SALARY_GRADE_LEVEL = arg1.toString(); break;

		case 56:
			this.SALARY_STEP = arg1.toString(); break;

		case 57:
			this. SALARY_STRUCTURE = arg1.toString(); break;

		case 58:
			this.ANNUAL_BASIC_SALARY = arg1.toString(); break;

		case 59:
			this.ANNUAL_TRANSPORT = arg1.toString(); break;

		case 60:
			this.ANNUAL_RENT = arg1.toString(); break;


		case 61:
			this.ANNUAL_OTHER_PENSIONABLE = arg1.toString(); break;

		case 62:
			this.PFAID = arg1.toString(); break;

		case 63:
			this.Comment = arg1.toString(); break;

		case 64:
			this.FormReferenceNo = arg1.toString(); break;
		
		default:
			break;
		}
	}

}
