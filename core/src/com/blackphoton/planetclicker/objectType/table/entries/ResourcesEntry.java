package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.resources.ResourceMaterial;

import java.util.ResourceBundle;

public class ResourcesEntry extends TableEntry {
	private ResourceMaterial material;

	public ResourcesEntry(ResourceType type, String name, int value, Era requiredEra, TableEntry upgradeTo, ResourceBundle resources) {
		super(type, name, value, requiredEra, upgradeTo, null, null, resources);
		material = (ResourceMaterial) resources.getObject(resources.getKeys().nextElement());
	}

	public ResourceMaterial getMaterial() {
		return material;
	}

	public void setMaterial(ResourceMaterial material) {
		this.material = material;
	}
}
