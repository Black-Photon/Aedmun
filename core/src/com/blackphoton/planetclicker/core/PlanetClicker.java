package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.blackphoton.planetclicker.file.SavegameFile;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.table.entries.SpecialEntry;
import com.blackphoton.planetclicker.objectType.table.entries.TableEntry;

import static com.badlogic.gdx.Application.ApplicationType.Android;

public class PlanetClicker extends ApplicationAdapter{
	private SpriteBatch batch;
	private Stage stage;
	private int buildingCount;
	private int foodCount;
	private int woodCount;
	private int stoneCount;
	private int bronzeCount;
	private int ironCount;
	private long populationCount;
	private Planet planet;
	private boolean buildingTableVisible = false;
	private boolean foodTableVisible = false;
	private boolean resourcesTableVisible = false;
	private boolean specialTableVisible = false;

	private Texture tex_earth;
	private Planet earth;
	private Planet[] planets;
	private Planet currentPlanet;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_INFO);

		Data.setData(this);
		Data.mechanics.createTables();

		buildingCount = 0;
		foodCount = 0;
		woodCount = 0;
		stoneCount = 0;
		bronzeCount = 0;
		ironCount = 0;
		populationCount = 2;

		tex_earth = new Texture("earth.png");
		earth = new Planet(tex_earth, 128, 128, "01e");
		planets = new Planet[]{earth};
		currentPlanet = planets[0];

		Gdx.input.setCatchBackKey(true);

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

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

	public int getStoneCount() {
		return stoneCount;
	}

	public void setStoneCount(int stoneCount) {
		this.stoneCount = stoneCount;
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

	public void addBuildingCount(int buildingCount) {
		this.buildingCount += buildingCount;
	}

	public void addFoodCount(int foodCount) {
		this.foodCount += foodCount;
	}

	public void addWoodCount(int woodCount) {
		this.woodCount += woodCount;
	}

	public void addStoneCount(int stoneCount) {
		this.stoneCount += stoneCount;
	}

	public void addBronzeCount(int bronzeCount) {
		this.bronzeCount += bronzeCount;
	}

	public void addIronCount(int ironCount) {
		this.ironCount += ironCount;
	}

	public long getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(long populationCount) {
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

	public Texture getTex_earth() {
		return tex_earth;
	}

	public void setTex_earth(Texture tex_earth) {
		this.tex_earth = tex_earth;
	}

	public Planet getEarth() {
		return earth;
	}

	public void setEarth(Planet earth) {
		this.earth = earth;
	}

	public Planet[] getPlanets() {
		return planets;
	}

	public Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public void setCurrentPlanet(Planet currentPlanet) {
		this.currentPlanet = currentPlanet;
	}

}


/*  ----- Template - These take way to long manually -----
			switch(material){
				case WOOD:

					break;
				case STONE:

					break;
				case IRON:

					break;
				case BRONZE:

					break;
			}

 */