package com.sigmapensions.sigmamobileapp.utils;


import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class DataClass extends Vector<NewContributor>implements KvmSerializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2267101698832948498L;

	@Override
	public Object getProperty(int arg0) {
		return this.get(arg0);
	}

	@Override
	public int getPropertyCount() {
		return this.size();
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		arg2.type = NewContributor.class;
		arg2.name = "NewContributor";
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		this.add((NewContributor)arg1);
	}

}
