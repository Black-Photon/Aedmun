package com.blackphoton.planetclicker.objectType.table.entries.resources;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Multiplier Resource Entry type. Each increases the amount of the resource you get per click
 */
public class Multiplier extends ResourcesEntry{
	final ArrayList<RequiredResource> initialCost = new ArrayList<RequiredResource>();
	/**
	 * @param name Name of resource
	 * @param value Value of 1 resource
	 * @param location Image location
	 * @param requiredEra Era needed to purchase
	 * @param resourcesNeeded Resources needed to buy
	 * @param upgradeTo Entry to turn into when upgraded
	 * @param resources ResourceEntry resources containing the material
	 */
	public Multiplier(String name, int value, String location, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(name, value, location, requiredEra, resourcesNeeded, upgradeTo, resources);
		if(getResourcesNeeded()!=null) {
			for (int i = 0; i < getResourcesNeeded().size(); i++)
				initialCost.add(new RequiredResource(getResourcesNeeded().get(i).getMaterial(), getResourcesNeeded().get(i).getNumberRequired()));
		}
		Thread multiplier = new Thread() {
			@Override
			public void run() {

				while (!Thread.currentThread().isInterrupted()) {
					try { //Increases the cost by a multiple every time bought
						if (Multiplier.super.resourcesNeeded != null)
							for (int i = 0; i < Multiplier.super.resourcesNeeded.size(); i++) {
								Multiplier.super.resourcesNeeded.get(i).setNumberRequired(initialCost.get(i).getNumberRequired() * (numberOf + 1));
							}
						sleep(100);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		multiplier.start();
	}

	//Getters and Setters
	/**
	 * Set's the value text to be the multiplier: eg. "x6"
	 */ @Override public void setValueLabelText() {
		this.valueLabel.setText("x"+Long.toString(numberOf+1));
	}
}
