package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ResourceBundle;

public class BuildingEntry extends TableEntry {
	public BuildingEntry(ResourceType type, String name, int value, Era requiredEra, TableEntry upgradeTo, ResourceBundle resources) {
		super(type, name, value, requiredEra, upgradeTo, resources);
	}
}
