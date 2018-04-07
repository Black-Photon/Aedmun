package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class FoodEntry extends TableEntry {
	private int timeLimit;

	public FoodEntry(ResourceType type, String name, int value, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ResourceBundle resources) {
		super(type, name, value, requiredEra, upgradeTo, resourcesNeeded, resources);
		timeLimit = (Integer) resources.getObject(resources.getKeys().nextElement());
		if(timeLimit>0)
			new Thread(){
				@Override
				public void run(){
					while(true){
						try {
							sleep(timeLimit);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(FoodEntry.super.numberOf>0){
							FoodEntry.super.subFromEntry();
							Data.ui.refreshTable();
							Data.mechanics.updateNumberOf();
						}
					}
				}
			}.start();
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
}
