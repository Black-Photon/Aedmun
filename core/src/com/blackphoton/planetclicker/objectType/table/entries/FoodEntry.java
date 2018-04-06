package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ResourceBundle;

public class FoodEntry extends TableEntry {
	private int timeLimit;

	public FoodEntry(ResourceType type, ResourceBundle resources) {
		super(type, resources);
		timeLimit = (Integer) resources.getObject(resources.getKeys().nextElement());
	}

	public FoodEntry(ResourceType type, String name, ResourceBundle resources) {
		super(type, name, resources);
	}

	public FoodEntry(ResourceType type, String name, int value, ResourceBundle resources) {
		super(type, name, value, resources);
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
}
