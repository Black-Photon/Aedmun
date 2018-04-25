package com.blackphoton.planetclicker.objectType.table.entries.template;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.resources.ResourceMaterial;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResourcesEntry extends TableEntry {
	protected ResourceMaterial material;
	protected ResourcesEntry multiplierEntry;
	protected int initialValue;

	public ResourcesEntry(String name, final int value, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(ResourceType.RESOURCES, name, value, requiredEra, upgradeTo, resourcesNeeded, null, resources);
		if (name == null) return;
		material = (ResourceMaterial) resources.getObject(resources.getKeys().nextElement());
		multiplierEntry = (ResourcesEntry) resources.getObject(resources.getKeys().nextElement());
		initialValue = value;
	}

	public ResourceMaterial getMaterial() {
		return material;
	}

	public void setMaterial(ResourceMaterial material) {
		this.material = material;
	}

	public void setValueLabelText() { }
}
