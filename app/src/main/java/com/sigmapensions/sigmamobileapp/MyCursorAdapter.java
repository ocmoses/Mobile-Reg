package com.sigmapensions.sigmamobileapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter{

	public MyCursorAdapter (Context context, Cursor cursor) {
		super(context, cursor);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView tv = (TextView)view.findViewById(R.id.employer_list_textView);
		tv.setText(cursor.getString(cursor.getColumnIndex("employer_name")));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.employer_list_item, parent, false);
		return view;
	}

}
