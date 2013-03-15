package org.onesy.Beans;

import java.util.Map.Entry;

public class KVContainer implements Entry {
	
	private Object key = null;
	
	private Object value = null;
	
	public KVContainer(Object key,Object val){
		this.key = key;
		this.value = val;
	}

	@Override
	public Object getKey() {
		// TODO Auto-generated method stub
		return this.key;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public Object setValue(Object value) {
		// TODO Auto-generated method stub
		return this.value = value;
	}

}
