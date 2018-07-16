package com.blackphoton.planetclicker.objectType.table.entries.building;

import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.BuildingEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;

/**
 * For hthe cave. This one specifically is designed for the tutorial
 */
public class Cave extends BuildingEntry{
	public Cave(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade) {
		super(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade);
		new Thread(){
			@Override
			public void run(){
				while (!Thread.currentThread().isInterrupted()) {
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(Data.isTutorialRunning() && isCreateClicked()){
						Data.mechanics.getCollection().objectiveComplete(6);
					}
				}
			}
		}.start();
	}
}
