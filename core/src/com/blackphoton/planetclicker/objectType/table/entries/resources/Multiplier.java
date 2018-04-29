package com.blackphoton.planetclicker.objectType.table.entries.resources;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Multiplier extends ResourcesEntry{
	final ArrayList<RequiredResource> initialCost = new ArrayList<RequiredResource>();

	public Multiplier(String name, int value, String location, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(name, value, location, requiredEra, resourcesNeeded, upgradeTo, resources);
		if(getResourcesNeeded()!=null) {
			for (int i = 0; i < getResourcesNeeded().size(); i++)
				initialCost.add(new RequiredResource(getResourcesNeeded().get(i).getMaterial(), getResourcesNeeded().get(i).getNumberRequired()));
		}
		multiplier.start();
	}
	public Thread multiplier = new Thread() {
		@Override
		public void run() {

			while (!Thread.currentThread().isInterrupted()) {
				try {
					if(resourcesNeeded!=null)
						for(int i=0; i<resourcesNeeded.size(); i++){
							resourcesNeeded.get(i).setNumberRequired(initialCost.get(i).getNumberRequired()*(numberOf+1));
						}
					sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	};

	@Override
	public void setValueLabelText() {
		this.valueLabel.setText("x"+Long.toString(numberOf+1));
	}
}
