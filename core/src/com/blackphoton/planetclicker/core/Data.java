package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.graphics.Texture;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
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
	private static com.blackphoton.planetclicker.objectType.Planet earth = new com.blackphoton.planetclicker.objectType.Planet(tex_earth, 128, 128, "01e");
	private final static com.blackphoton.planetclicker.objectType.Planet[] planets = {earth};
	private static com.blackphoton.planetclicker.objectType.Planet current = planets[0];

	private static ArrayList<com.blackphoton.planetclicker.objectType.Era> eraList;

	public static com.blackphoton.planetclicker.core.UI ui;
	public static com.blackphoton.planetclicker.core.Mechanics mechanics;
	private static ResourceType resourceType = ResourceType.NONE;

	private static TableInfo buildingTable;
	private static TableInfo foodTable;
	private static TableInfo resourcesTable;
	private static TableInfo specialTable;

	/**
	 * Sets all data to values to be used. Call once at start.
	 */
	public static void setData(PlanetClicker planetClicker){
		eraList = new ArrayList<com.blackphoton.planetclicker.objectType.Era>();
		com.blackphoton.planetclicker.objectType.Era cavemen = new com.blackphoton.planetclicker.objectType.Era("Cavemen", com.blackphoton.planetclicker.objectType.Exponent.toExponent(2), "caveman.png");
		com.blackphoton.planetclicker.objectType.Era iron = new com.blackphoton.planetclicker.objectType.Era("Iron", com.blackphoton.planetclicker.objectType.Exponent.toExponent(40), "iron.png");
		com.blackphoton.planetclicker.objectType.Era bronze = new com.blackphoton.planetclicker.objectType.Era("Bronze", com.blackphoton.planetclicker.objectType.Exponent.toExponent(1000), "bronze.png");
		com.blackphoton.planetclicker.objectType.Era ancient = new com.blackphoton.planetclicker.objectType.Era("Ancient", com.blackphoton.planetclicker.objectType.Exponent.toExponent(12000), "ancient.png");
		com.blackphoton.planetclicker.objectType.Era medievil = new com.blackphoton.planetclicker.objectType.Era("Medievil", com.blackphoton.planetclicker.objectType.Exponent.toExponent(200000), "medievil.png");
		com.blackphoton.planetclicker.objectType.Era classical = new com.blackphoton.planetclicker.objectType.Era("Classical", com.blackphoton.planetclicker.objectType.Exponent.toExponent(5000000), "classical.png");
		com.blackphoton.planetclicker.objectType.Era industrial = new com.blackphoton.planetclicker.objectType.Era("Industrial", com.blackphoton.planetclicker.objectType.Exponent.toExponent(100000000), "industrial.png");
		com.blackphoton.planetclicker.objectType.Era modern = new com.blackphoton.planetclicker.objectType.Era("Modern", new com.blackphoton.planetclicker.objectType.Exponent(7,9), "modern.png");
		com.blackphoton.planetclicker.objectType.Era future = new com.blackphoton.planetclicker.objectType.Era("Future", new Exponent(1,10), "future.png");
		eraList.add(cavemen);
		eraList.add(iron);
		eraList.add(bronze);
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

	public static com.blackphoton.planetclicker.objectType.Planet getEarth() {
		return earth;
	}

	public static void setEarth(com.blackphoton.planetclicker.objectType.Planet earth) {
		Data.earth = earth;
	}

	public static com.blackphoton.planetclicker.objectType.Planet[] getPlanets() {
		return planets;
	}

	public static com.blackphoton.planetclicker.objectType.Planet getCurrent() {
		return current;
	}

	public static void setCurrent(Planet current) {
		Data.current = current;
	}

	public static ArrayList<com.blackphoton.planetclicker.objectType.Era> getEraList() {
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
}
