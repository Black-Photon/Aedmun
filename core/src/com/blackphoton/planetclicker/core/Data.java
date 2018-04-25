package com.blackphoton.planetclicker.core;

import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.objectType.Era;

import java.util.ArrayList;

/**
 * Holds data on various info about progress in the game
 */
public class Data {
	public static com.blackphoton.planetclicker.core.PlanetClicker main;

	private static ArrayList<Era> eraList;
	private static Era currentEra;

	public static com.blackphoton.planetclicker.core.UI ui;
	public static com.blackphoton.planetclicker.core.Mechanics mechanics;
	private static ResourceType resourceType = ResourceType.NONE;

	private static TableInfo buildingTable;
	private static TableInfo foodTable;
	private static TableInfo resourcesTable;
	private static TableInfo specialTable;
	private static TableInfo currentTable;

	private static TableEntry selectedEntry;

	/**
	 * Sets all data to values to be used. Call once at start.
	 */
	public static void setData(PlanetClicker planetClicker){
		eraList = new ArrayList<Era>();
		Era cavemen = new Era("Cavemen", 2L, "cavemen.png");
		Era bronze = new Era("Bronze", 400L, "bronze.png");
		Era iron = new Era("Iron", 2000L, "iron.png");
		Era ancient = new Era("Ancient", 12000L, "ancient.png");
		Era medievil = new Era("Medievil", 200000L, "medievil.png");
		Era classical = new Era("Classical", 5000000L, "classical.png");
		Era industrial = new Era("Industrial",100000000L, "industrial.png");
		Era modern = new Era("Modern",7000000000L, "modern.png");
		Era future = new Era("Future", 25000000000L, "future.png");
		Era endgame = new Era("Future", 50000000000L, "future.png");
		eraList.add(cavemen);
		eraList.add(bronze);
		eraList.add(iron);
		eraList.add(ancient);
		eraList.add(medievil);
		eraList.add(classical);
		eraList.add(industrial);
		eraList.add(modern);
		eraList.add(future);
		eraList.add(endgame);

		ui = new UI();
		mechanics = new Mechanics();
		main = planetClicker;

		currentEra = cavemen;
	}

	//---Getters and Setters---

	public static ArrayList<Era> getEraList() {
		return eraList;
	}

	public static void setEraList(ArrayList<Era> eraList) {
		Data.eraList = eraList;
	}

	public static ResourceType getResourceType() {
		return resourceType;
	}

	public static void setResourceType(ResourceType resourceType) {
		Data.resourceType = resourceType;
		switch (resourceType){
			case BUILDINGS:
				currentTable = buildingTable;
				break;
			case FOOD:
				currentTable = foodTable;
				break;
			case RESOURCES:
				currentTable = resourcesTable;
				break;
			case SPECIAL:
				currentTable = specialTable;
				break;
			case NONE:
				currentTable = null;
				break;
		}
	}

	public static void setTableInfo(TableInfo building, TableInfo food, TableInfo resources, TableInfo special){
		buildingTable = building;
		foodTable = food;
		resourcesTable = resources;
		specialTable = special;
	}

	public static TableInfo getBuildingTable() {
		return buildingTable;
	}

	public static TableInfo getFoodTable() {
		return foodTable;
	}

	public static TableInfo getResourcesTable() {
		return resourcesTable;
	}

	public static TableInfo getSpecialTable() {
		return specialTable;
	}

	public static Era getCurrentEra() {
		return currentEra;
	}

	public static void setCurrentEra(Era currentEra) {
		Data.currentEra = currentEra;
	}

	public static TableEntry getSelectedEntry() {
		return selectedEntry;
	}

	public static void setSelectedEntry(TableEntry selectedEntry) {
		Data.selectedEntry = selectedEntry;
	}

	public static TableInfo getCurrentTable() {
		return currentTable;
	}

	public static void setCurrentTable(TableInfo currentTable) {
		Data.currentTable = currentTable;
	}
}
