package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.blackphoton.planetclicker.objectType.Planet;

public class PlanetClicker extends ApplicationAdapter{
	private SpriteBatch batch;
	private Stage stage;
	private int buildingCount = 0;
	private int foodCount = 0;
	private int woodCount = 0;
	private int bronzeCount = 0;
	private int ironCount = 0;
	private int populationCount = 500; //TODO change back to 2
	private Planet planet;
	private boolean buildingTableVisible = false;
	private boolean foodTableVisible = false;
	private boolean resourcesTableVisible = false;
	private boolean specialTableVisible = false;

	@Override
	public void create() {
		Data.setData(this);
		Data.mechanics.createTables();

		batch = new SpriteBatch();
		stage = new Stage();

		Data.mechanics.create();
		Data.ui.createUI();

		Data.ui.updateResources();
	}

	@Override
	public void render() {
		Data.mechanics.update();
		Data.ui.updateUI();

		batch.begin();
		Data.ui.drawBackground(batch);
		batch.end();
		stage.draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		planet.dispose();
		Data.ui.dispose();
		Data.mechanics.dispose();
	}

	@Override
	public void resize(int width, int height) {
		Data.ui.resize(width, height);
	}

	//Getters and Setters

	public int getBuildingCount() {
		return buildingCount;
	}

	public void setBuildingCount(int buildingCount) {
		this.buildingCount = buildingCount;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}

	public int getWoodCount() {
		return woodCount;
	}

	public void setWoodCount(int woodCount) {
		this.woodCount = woodCount;
	}

	public int getBronzeCount() {
		return bronzeCount;
	}

	public void setBronzeCount(int bronzeCount) {
		this.bronzeCount = bronzeCount;
	}

	public int getIronCount() {
		return ironCount;
	}

	public void setIronCount(int ironCount) {
		this.ironCount = ironCount;
	}

	public int getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(int populationCount) {
		this.populationCount = populationCount;
	}

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public boolean isBuildingTableVisible() {
		return buildingTableVisible;
	}

	public void setBuildingTableVisible(boolean buildingTableVisible) {
		this.buildingTableVisible = buildingTableVisible;
	}

	public boolean isFoodTableVisible() {
		return foodTableVisible;
	}

	public void setFoodTableVisible(boolean foodTableVisible) {
		this.foodTableVisible = foodTableVisible;
	}

	public boolean isResourcesTableVisible() {
		return resourcesTableVisible;
	}

	public void setResourcesTableVisible(boolean resourcesTableVisible) {
		this.resourcesTableVisible = resourcesTableVisible;
	}

	public boolean isSpecialTableVisible() {
		return specialTableVisible;
	}

	public void setSpecialTableVisible(boolean specialTableVisible) {
		this.specialTableVisible = specialTableVisible;
	}
}