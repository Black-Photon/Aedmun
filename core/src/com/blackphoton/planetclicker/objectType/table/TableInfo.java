package com.blackphoton.planetclicker.objectType.table;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.*;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Holds a list of Table Entries
 */
public class TableInfo {
	private ResourceType type;
	private ArrayList<TableEntry> entries;

	/**
	 * @param type Type of data stored
	 */
	public TableInfo(ResourceType type){
		this.type = type;
		entries = new ArrayList<TableEntry>();
	}

	/**
	 * Adds the entry to the list
	 * @param entry Entry to add
	 * @return The entry inputted
	 */
	public TableEntry addEntry(TableEntry entry) {
		entries.add(entry);
		return entry;
	}

	/**
	 * Adds a new Entry to the list. Can create BuildingEntry, FoodEntry, ResourceEntry or SpecialEntry
	 * @param name Name of resource
	 * @param value Value of 1 resource
	 * @param location Image location
	 * @param requiredEra Era needed to purchase
	 * @param upgradeTo Entry to turn into when upgraded
	 * @param resourcesNeeded Resources needed to buy
	 * @param resourcesNeededToUpgrade Resources needed to upgrade
	 * @param resources Relevant resources. See individual types for what.
	 * @return The entry created
	 */
	public TableEntry addEntry(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade, ResourceBundle resources) {
		TableEntry entry = null;
		switch (type) {
			case BUILDINGS:
				entry = new BuildingEntry(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade);
				break;
			case FOOD:
				entry = new FoodEntry(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade);
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

	/**
	 * Updates buttons of all entries in the list
	 */
	public void updateButtons(){
		for(TableEntry entry: entries){
			entry.updateButtons();
		}
	}
	/**
	 * UnClicks buttons of all entries in the list
	 */
	public void unclickAll(){
		for(TableEntry entry: entries){
			entry.unclick();
			entry.updateButtons();
		}
	}

	//Getters and Setters
	public ResourceType getType() {
		return type;
	}
	public void setType(ResourceType type) {
		this.type = type;
	}
	public ArrayList<TableEntry> getEntries() {
		return entries;
	}
}
