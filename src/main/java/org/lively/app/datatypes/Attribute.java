package org.lively.app.datatypes;

import java.util.HashMap;
import java.util.Map;

public class Attribute extends HashMap {
	
	/**
	 * creates a hashmap of string attributes
	 */
	
	private static final long serialVersionUID = 1L;
	protected static Map<String, String> subcollection;
	protected String name;
	
	public Attribute() {
		subcollection = new HashMap<String, String>();
		name = "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, String> getMap() {
		return Attribute.subcollection;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}