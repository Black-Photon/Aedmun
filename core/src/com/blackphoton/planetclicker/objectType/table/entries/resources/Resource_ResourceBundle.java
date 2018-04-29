package com.blackphoton.planetclicker.objectType.table.entries.resources;

import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class Resource_ResourceBundle extends ResourceBundle {
	Resource material;
	TableEntry entry;
	private ArrayList keyList;

	public Resource_ResourceBundle(Resource material, TableEntry entry) {
		this.material = material;
		this.entry = entry;
		keyList = new ArrayList();
		keyList.add("resource");
		keyList.add("multiplier");
	}

	@Override
	protected Object handleGetObject(String key) {
		if(key.equals("resource")) return material;
		if(key.equals("multiplier")) return entry;
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
