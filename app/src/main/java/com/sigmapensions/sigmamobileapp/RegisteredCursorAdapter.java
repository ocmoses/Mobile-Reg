package com.sigmapensions.sigmamobileapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisteredCursorAdapter extends CursorAdapter{

	public RegisteredCursorAdapter(Context context, Cursor c) {
		super(context, c);		
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String passportDirectory = cursor.getString(cursor.getColumnIndex(MobileRegDatabase.PASSPORT));
		try {
			FileInputStream fis = context.openFileInput(passportDirectory);
			Bitmap passportBitmap = BitmapFactory.decodeStream(fis);
			ImageView passportImageView = (ImageView)view.findViewById(R.id.rResponsePagePassport);
			passportImageView.setImageBitmap(passportBitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		TextView rIdSolicitValueTV = (TextView)view.findViewById(R.id.rIdSolicitValueTV);
		rIdSolicitValueTV.setText(cursor.getString(cursor.getColumnIndex(MobileRegDatabase.ID_SOLICITUD)));
		
		TextView rRsanoPinValueTV = (TextView)view.findViewById(R.id.rRsanoPinValueTV);
		rRsanoPinValueTV.setText(cursor.getString(cursor.getColumnIndex(MobileRegDatabase.RSANO_PIN)));
		
		TextView rFormReferenceNoValueTV = (TextView)view.findViewById(R.id.rFormReferenceNoValueTV);
		rFormReferenceNoValueTV.setText(cursor.getString(cursor.getColumnIndex(MobileRegDatabase.FORMREFERENCENO)));
		
		TextView rValueDateValueTV = (TextView)view.findViewById(R.id.rValueDateValueTV);
		rValueDateValueTV.setText(cursor.getString(cursor.getColumnIndex(MobileRegDatabase.VALUEDATE)));
		
		TextView rCommentValueTV = (TextView)view.findViewById(R.id.rCommentValueTV);
		rCommentValueTV.setText(cursor.getString(cursor.getColumnIndex(MobileRegDatabase.COMMENT)));
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.registered_table, parent, false);
		return view;
	}

}
