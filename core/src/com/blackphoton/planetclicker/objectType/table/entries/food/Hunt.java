package com.blackphoton.planetclicker.objectType.table.entries.food;

import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;

/**
 * For hunting food. This one specifically is designed for the tutorial
 */
public class Hunt extends Timeout {
	public Hunt(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade, final int timeLimit) {
		super(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade, timeLimit);
		new Thread(){
			@Override
			public void run(){
				while (!Thread.currentThread().isInterrupted()) {
					try {
						sleep(timeLimit);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(Data.isTutorialRunning() && isCreateClicked()){
						Data.mechanics.getCollection().objectiveComplete(3); //Kept in case tutorial re-run
					}
				}
			}
		}.start();
	}

}
