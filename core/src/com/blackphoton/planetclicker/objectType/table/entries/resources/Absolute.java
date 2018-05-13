package com.blackphoton.planetclicker.objectType.table.entries.resources;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.objectType.table.entries.template.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * For a base resource. Keeps the resource updated, so the number of the resource is the same as the actual recorded amount eg. When resources spent
 */
public class Absolute extends ResourcesEntry{
	/**
	 * @param name Name of resource
     * @param value Value of 1 resource
	 * @param resource The resource the entry represents
	 * @param requiredEra Era needed to purchase
	 * @param upgradeTo Entry to turn into when upgraded
	 * @param resourcesNeeded Resources needed to buy
	 * @param resources ResourceEntry resources containing the material
	 */
	public Absolute(String name, int value, Resource resource, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(name, value, "empty.png", requiredEra, resourcesNeeded, upgradeTo, resources);
		final Thread absolute = new Thread() {
			@Override
			public synchronized void run() {
				while (!Thread.currentThread().isInterrupted()) {
					//Updates the value depending on the multiplier
					if(multiplierEntry!=null){
						setValue(initialValue*(multiplierEntry.getNumberOf()+1));
					}

					//Updates the count 100 times a second (if needed)
					try {
						if(material.getCount() != numberOf)
							numberOf = material.getCount();
						sleep(10);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		absolute.start();
		setTexture(resource.getTexture());
		setImage(new Image(texture));
		image.setScaling(Scaling.fit);
		material = resource;
	}

	@Override
	public void onClick() {
		super.onClick();
		Data.mechanics.addResource(getMaterial(), getValue());
	}

	//Getters and Setters
	@Override
	public void setValueLabelText() {
		this.valueLabel.setText(Long.toString(value));
	}
}
