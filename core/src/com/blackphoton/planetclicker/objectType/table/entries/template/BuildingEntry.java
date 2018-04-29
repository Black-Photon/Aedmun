package com.blackphoton.planetclicker.objectType.table.entries.template;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuildingEntry extends TableEntry {
	public BuildingEntry(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade, ResourceBundle resources) {
		super(ResourceType.BUILDINGS, name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade, resources);
	}
}
