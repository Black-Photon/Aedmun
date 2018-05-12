package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.objectType.Era;

import java.util.ArrayList;

/**
 * Holds data on various info about progress in the game
 */
public class Data {
	//Class Access Points
	/**
	 * The base LibGDX class that manages connections to UI and Mechanics. Objects that are neutral to both sections go here.
	 */
	public static PlanetClicker main;
	/**
	 * The UI class containing all methods and objects that relate to the User Interface and anything graphical related.
	 */
	public static UI ui;
	/**
	 * The Mechanics class has all methods and a couple of objects that relate to the functionality of the game, including listener responses.
	 */
	public static Mechanics mechanics;

	//Era Variables
	/**
	 * List of Era's that are available in the game, in order of achieving. The next entry in the list MUST have a higher required population.
	 */
	private static ArrayList<Era> eraList;
	/**
	 * The 'active' era that is currently being used
	 */
	private static Era currentEra;

	//Table Info
	/**
	 * Contains all the table entry information for the Building Table
	 */
	private static TableInfo buildingTable;
	/**
	 * Contains all the table entry information for the Food Table
	 */
	private static TableInfo foodTable;
	/**
	 * Contains all the table entry information for the Resources Table
	 */
	private static TableInfo resourcesTable;
	/**
	 * Contains all the table entry information for the Special Table
	 */
	private static TableInfo specialTable;
	/**
	 * Contains all the table entry information for the Currently Selected Table
	 */
	private static TableInfo currentTable;

	//Other
	/**
	 * Contains the table entry info for the entry selected via clicking 'Create' or 'Upgrade'
	 */
	private static TableEntry selectedEntry;
	/**
	 * The resource type that is selected (Clicked on to open options to buy)
	 * Eg. Buildings, Food, Resources, Special
	 */
	private static ResourceType resourceType = ResourceType.NONE;

	//Counts
	/**
	 * Population support of buildings
	 */
	private static long buildingCount;
	/**
	 * Population support of food
	 */
	private static long foodCount;

	//Table visibility
	/**
	 * Whether the building purchase table is currently visible
	 */
	private static boolean buildingTableVisible = false;
	/**
	 * Whether the food purchase table is currently visible
	 */
	private static boolean foodTableVisible = false;
	/**
	 * Whether the resources purchase table is currently visible
	 */
	private static boolean resourcesTableVisible = false;
	/**
	 * Whether the special purchase table is currently visible
	 */
	private static boolean specialTableVisible = false;

	//Planets
	/**
	 * Earth planet data
	 */
	private static Planet earth;
	/**
	 * List of all loaded planets
	 */
	private static Planet[] planets;
	/**
	 * Current Planet data
	 */
	private static Planet currentPlanet;

	/**
	 * Sets all data to values to be used. Call once at start.
	 */
	public static void setData(PlanetClicker planetClicker){
		ui = new UI();
		mechanics = new Mechanics();
		main = planetClicker;

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

		buildingCount = 0;
		foodCount = 0;

		Data.ui.setTex_earth(new Texture("earth.png"));
		earth = new Planet(Data.ui.getTex_earth(), 128, 128, "01e");
		planets = new Planet[]{earth};
		currentPlanet = planets[0];

		currentEra = cavemen;
	}

	//---Getters and Setters---
	public static ArrayList<Era> getEraList() {
		return eraList;
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
	public static long getBuildingCount() {
		return buildingCount;
	}
	public static void setBuildingCount(long buildingCount) {
		Data.buildingCount = buildingCount;
	}
	public static long getFoodCount() {
		return foodCount;
	}
	public static void setFoodCount(long foodCount) {
		Data.foodCount = foodCount;
	}
	public static boolean isBuildingTableVisible() {
		return buildingTableVisible;
	}
	public static void setBuildingTableVisible(boolean buildingTableVisible) {
		Data.buildingTableVisible = buildingTableVisible;
	}
	public static boolean isFoodTableVisible() {
		return foodTableVisible;
	}
	public static void setFoodTableVisible(boolean foodTableVisible) {
		Data.foodTableVisible = foodTableVisible;
	}
	public static boolean isResourcesTableVisible() {
		return resourcesTableVisible;
	}
	public static void setResourcesTableVisible(boolean resourcesTableVisible) {
		Data.resourcesTableVisible = resourcesTableVisible;
	}
	public static boolean isSpecialTableVisible() {
		return specialTableVisible;
	}
	public static void setSpecialTableVisible(boolean specialTableVisible) {
		Data.specialTableVisible = specialTableVisible;
	}
	public static Planet getEarth() {
		return earth;
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
}
