package com.blackphoton.planetclicker.objectType.table.entries.template;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Class for Resource Entries such as Wood or Woodmill. Should use specific classes (Absolute, Multiplier ect.) unless none match.
 */
public class ResourcesEntry extends TableEntry {
	protected Resource material;
	protected ResourcesEntry multiplierEntry;
	protected int initialValue;

	/**
	 * @param name Name of resource
	 * @param value Value of 1 resource
	 * @param location Image location
	 * @param requiredEra Era needed to purchase
	 * @param resourcesNeeded Resources needed to buy
	 * @param upgradeTo Entry to turn into when upgraded
	 * @param resources ResourceEntry resources containing the material
	 */
	public ResourcesEntry(String name, final int value, String location, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(ResourceType.RESOURCES, name, value, location, requiredEra, upgradeTo, resourcesNeeded, null);
		if (name == null) return;
		initialValue = value;
		if(resources==null) return;
		material = (Resource) resources.getObject(resources.getKeys().nextElement());
		multiplierEntry = (ResourcesEntry) resources.getObject(resources.getKeys().nextElement());
	}

	//Getters and Setters
	public Resource getMaterial() {
		return material;
	}
	public void setMaterial(Resource material) {
		this.material = material;
	}
	/**
	 * Does nothing
	 */
	public void setValueLabelText() {} //This is just here to prevent the superclass updating the value label text incorrectly
}
