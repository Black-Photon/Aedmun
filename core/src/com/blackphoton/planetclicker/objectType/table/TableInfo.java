package com.blackphoton.planetclicker.objectType.table;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.*;
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

	public TableEntry addEntry(TableEntry entry) {
		entries.add(entry);
		return entry;
	}

	public TableEntry addEntry(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade, ResourceBundle resources) {
		TableEntry entry = null;
		switch (type) {
			case BUILDINGS:
				entry = new BuildingEntry(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade, resources);
				break;
			case FOOD:
				entry = new FoodEntry(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade, resources);
				break;
			case RESOURCES:
				entry = new ResourcesEntry(name, value, location, requiredEra, resourcesNeeded, upgradeTo, resources);
				break;
			case SPECIAL:
				entry = new SpecialEntry(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resources);
				break;
			default: return null;
		}
		this.addEntry(entry);
		return entry;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public void updateButtons(){
		for(TableEntry entry: entries){
			entry.updateButtons();
		}
	}

	public void unclickAll(){
		for(TableEntry entry: entries){
			entry.unclick();
			entry.updateButtons();
		}
	}
}
