package com.sigmapensions.sigmamobileapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sigmapensions.sigmamobileapp.sqliteasset.SQLiteAssetHelper;

public class EmployerDatabase extends SQLiteAssetHelper{
	
	public static final String DATABASE_NAME = "Employer.db";
	private static final int DATABASE_VERSION = 1;

	public EmployerDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public SQLiteDatabase openDatabase(){
		return getWritableDatabase();
	}
	
}

