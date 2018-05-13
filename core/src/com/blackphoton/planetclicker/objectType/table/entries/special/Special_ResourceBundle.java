package com.blackphoton.planetclicker.objectType.table.entries.special;

import com.blackphoton.planetclicker.core.Data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * ResourceBundle for default Special properties
 */
public class Special_ResourceBundle extends ResourceBundle{
	private int era;
	private ArrayList keyList;

	public Special_ResourceBundle(int era) {
		this.era = era;
		keyList = new ArrayList();
		keyList.add("pop");
	}

	@Override
	protected Object handleGetObject(String key) {
		if(key.equals("pop")) return Data.getEraList().get(era).getPop_req().intValue();
		return null;
	}

	int count = 0;

	@Override
	public Enumeration<String> getKeys() {
		Enumeration<String> e = new Enumeration<String>() {
			ArrayList<String> elements = keyList;

			@Override
			public boolean hasMoreElements() {
				return count<elements.size();
			}

			@Override
			public String nextElement() {
				count++;
				return elements.get(count-1);
			}
		};
		return e;
	}
}
