package com.blackphoton.planetclicker.objectType.table;

import com.blackphoton.planetclicker.objectType.table.entries.*;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableInfo {
	private ResourceType type;
	private ArrayList<TableEntry> entries;

	public TableInfo(ResourceType type){
		this.type = type;
		entries = new ArrayList<TableEntry>();
	}

	public ArrayList<TableEntry> getEntries() {
		return entries;
	}

	public void addEntry(TableEntry entry) {
		entries.add(entry);
	}

	public void addEntry(String name, int value, ResourceBundle resources) {
		TableEntry entry = null;
		switch (type) {
			case BUILDINGS:
				entry = new BuildingEntry(type, name, value, resources);
				break;
			case FOOD:
				entry = new FoodEntry(type, name, value, resources);
				break;
			case RESOURCES:
				entry = new ResourcesEntry(type, name, value, resources);
				break;
			case SPECIAL:
				entry = new SpecialEntry(type, name, value, resources);
				break;
			default: return;
		}
		this.addEntry(entry);
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}
}
