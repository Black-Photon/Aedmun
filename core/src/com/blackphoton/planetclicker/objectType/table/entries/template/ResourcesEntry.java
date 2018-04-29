package com.blackphoton.planetclicker.objectType.table.entries.template;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResourcesEntry extends TableEntry {
	protected Resource material;
	protected ResourcesEntry multiplierEntry;
	protected int initialValue;

	public ResourcesEntry(String name, final int value, String location, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(ResourceType.RESOURCES, name, value, location, requiredEra, upgradeTo, resourcesNeeded, null, resources);
		if (name == null) return;
		initialValue = value;
		if(resources==null) return;
		material = (Resource) resources.getObject(resources.getKeys().nextElement());
		multiplierEntry = (ResourcesEntry) resources.getObject(resources.getKeys().nextElement());
	}

	public Resource getMaterial() {
		return material;
	}

	public void setMaterial(Resource material) {
		this.material = material;
	}

	public void setValueLabelText() { }
}
