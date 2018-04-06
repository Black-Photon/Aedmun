package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ResourceBundle;

public class BuildingEntry extends TableEntry {
	public BuildingEntry(ResourceType type, ResourceBundle resources) {
		super(type, resources);
	}

	public BuildingEntry(ResourceType type, String name, ResourceBundle resources) {
		super(type, name, resources);
	}

	public BuildingEntry(ResourceType type, String name, int value, ResourceBundle resources) {
		super(type, name, value, resources);
	}
}
