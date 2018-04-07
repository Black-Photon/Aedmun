package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ResourceBundle;

public class FoodEntry extends TableEntry {
	private int timeLimit;

	public FoodEntry(ResourceType type, String name, int value, Era requiredEra, TableEntry upgradeTo, ResourceBundle resources) {
		super(type, name, value, requiredEra, upgradeTo, resources);
		timeLimit = (Integer) resources.getObject(resources.getKeys().nextElement());
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
}
