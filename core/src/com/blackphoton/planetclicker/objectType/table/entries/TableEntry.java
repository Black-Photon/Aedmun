package com.blackphoton.planetclicker.objectType.table.entries;

import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ResourceBundle;

public abstract class TableEntry {
	protected ResourceType type;
	protected String name;
	protected int value;
	protected int numberOf;
	protected ResourceBundle resources;

	public TableEntry(ResourceType type, ResourceBundle resources){
		this.type = type;
		numberOf = 0;
		this.resources = resources;
	}
	public TableEntry(ResourceType type, String name, ResourceBundle resources){
		this(type, resources);
		this.name = name;
	}
	public TableEntry(ResourceType type, String name, int value, ResourceBundle resources){
		this(type, name, resources);
		this.value = value;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getNumberOf() {
		return numberOf;
	}

	/**
	 * Adds one of this building - so you now have one more than before
	 */
	public void addToEntry(){
		numberOf++;
	}
}