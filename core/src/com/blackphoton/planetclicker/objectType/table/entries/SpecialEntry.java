package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class SpecialEntry extends TableEntry {
	public SpecialEntry(ResourceType type, String name, int value, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ResourceBundle resources) {
		super(type, name, value, requiredEra, upgradeTo,resourcesNeeded, resources);
	}
}
