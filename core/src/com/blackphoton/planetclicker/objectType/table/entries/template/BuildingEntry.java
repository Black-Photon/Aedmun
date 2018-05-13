package com.blackphoton.planetclicker.objectType.table.entries.template;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Class for Building Entries
 */
public class BuildingEntry extends TableEntry {
	/**
	 * @param name Name of resource
	 * @param value Value of 1 resource
	 * @param location Image location
	 * @param requiredEra Era needed to purchase
	 * @param upgradeTo Entry to turn into when upgraded
	 * @param resourcesNeeded Resources needed to buy
	 * @param resourcesNeededToUpgrade Resources needed to upgrade
	 */
	public BuildingEntry(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade) {
		super(ResourceType.BUILDINGS, name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade);
	}
}
