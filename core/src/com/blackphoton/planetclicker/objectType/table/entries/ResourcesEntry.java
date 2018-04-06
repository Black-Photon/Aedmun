package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.resources.ResourceMaterial;

import java.util.ResourceBundle;

public class ResourcesEntry extends TableEntry {
	private ResourceMaterial material;

	public ResourcesEntry(ResourceType type, ResourceBundle resources) {
		super(type, resources);
		material = (ResourceMaterial) resources.getObject(resources.getKeys().nextElement());
	}

	public ResourcesEntry(ResourceType type, String name, ResourceBundle resources) {
		super(type, name, resources);
	}

	public ResourcesEntry(ResourceType type, String name, int value, ResourceBundle resources) {
		super(type, name, value, resources);
	}
}
