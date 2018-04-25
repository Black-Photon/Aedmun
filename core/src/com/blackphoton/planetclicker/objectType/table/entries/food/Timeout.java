package com.blackphoton.planetclicker.objectType.table.entries.food;

import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.FoodEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;

public class Timeout extends FoodEntry{
	public Timeout(String name, int value, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade, final int timeLimit) {
		super(name, value, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade, null);
		new Thread(){
			@Override
			public void run(){
				while (!Thread.currentThread().isInterrupted()) {
					try {
						sleep(timeLimit);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(numberOf>0){
						subFromEntry();
						Data.mechanics.updateNumberOf();
					}
				}
			}
		}.start();
	}
}
