package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ResourceBundle;

public class SpecialEntry extends TableEntry {
	public SpecialEntry(ResourceType type, ResourceBundle resources) {
		super(type, resources);
	}

	public SpecialEntry(ResourceType type, String name, ResourceBundle resources) {
		super(type, name, resources);
	}

	public SpecialEntry(ResourceType type, String name, int value, ResourceBundle resources) {
		super(type, name, value, resources);
	}
}
