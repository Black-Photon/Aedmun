package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.graphics.Texture;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.TableEntry;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.Exponent;
import com.blackphoton.planetclicker.objectType.Planet;

import java.util.ArrayList;

/**
 * Holds data on various info about progress in the game
 */
public class Data {
	public static com.blackphoton.planetclicker.core.PlanetClicker main;

	private static Texture tex_earth = new Texture("earth.png");
	private static Planet earth = new Planet(tex_earth, 128, 128, "01e");
	private final static Planet[] planets = {earth};
	private static Planet currentPlanet = planets[0];

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
		Era cavemen = new Era("Cavemen", Exponent.toExponent(2), "caveman.png");
		Era bronze = new Era("Bronze", Exponent.toExponent(400), "bronze.png");
		Era iron = new Era("Iron", Exponent.toExponent(2000), "iron.png");
		Era ancient = new Era("Ancient", Exponent.toExponent(12000), "ancient.png");
		Era medievil = new Era("Medievil", Exponent.toExponent(200000), "medievil.png");
		Era classical = new Era("Classical", Exponent.toExponent(5000000), "classical.png");
		Era industrial = new Era("Industrial", Exponent.toExponent(100000000), "industrial.png");
		Era modern = new Era("Modern", new Exponent(7,9), "modern.png");
		Era future = new Era("Future", new Exponent(1,10), "future.png");
		eraList.add(cavemen);
		eraList.add(bronze);
		eraList.add(iron);
		eraList.add(ancient);
		eraList.add(medievil);
		eraList.add(classical);
		eraList.add(industrial);
		eraList.add(modern);
		eraList.add(future);

		ui = new UI();
		mechanics = new Mechanics();
		main = planetClicker;
	}

	//---Getters and Setters---

	public static Texture getTex_earth() {
		return tex_earth;
	}

	public static void setTex_earth(Texture tex_earth) {
		Data.tex_earth = tex_earth;
	}

	public static Planet getEarth() {
		return earth;
	}

	public static void setEarth(Planet earth) {
		Data.earth = earth;
	}

	public static Planet[] getPlanets() {
		return planets;
	}

	public static Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public static void setCurrentPlanet(Planet currentPlanet) {
		Data.currentPlanet = currentPlanet;
	}

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
