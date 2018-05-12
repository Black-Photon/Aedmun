package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.resources.ResourceType;

public class PlanetClicker extends ApplicationAdapter{
	/**
	 * Main batch to draw with
	 */
	private SpriteBatch batch;
	/**
	 * Main stage for clicking planet screen
	 */
	private Stage stage;
	/**
	 * Population support of buildings
	 */
	private long buildingCount;
	/**
	 * Population support of food
	 */
	private long foodCount;
	/**
	 * Whether the building purchase table is currently visible
	 */
	private boolean buildingTableVisible = false;
	/**
	 * Whether the food purchase table is currently visible
	 */
	private boolean foodTableVisible = false;
	/**
	 * Whether the resources purchase table is currently visible
	 */
	private boolean resourcesTableVisible = false;
	/**
	 * Whether the special purchase table is currently visible
	 */
	private boolean specialTableVisible = false;

	/**
	 * Earth texture
	 */
	private Texture tex_earth;
	/**
	 * Earth planet data
	 */
	private Planet earth;
	/**
	 * List of all loaded planets
	 */
	private Planet[] planets;
	/**
	 * Current Planet data
	 */
	private Planet currentPlanet;

	public Resource WOOD;
	public Resource STONE;
	public Resource BRONZE;
	public Resource IRON;
	public Resource CLAY;
	public Resource BRICK;
	public Resource CONCRETE;
	public Resource STEEL;
	public Resource GEMS;
	public Resource CARBON;
	public Resource POPULATION;

	@Override
	public void create() {
		//Creates all the resources
		WOOD = new Resource("wood.png");
		STONE = new Resource("stone.png");
		BRONZE = new Resource("bronze_bar.png");
		IRON = new Resource("iron_bar.png");
		CLAY = new Resource("clay.png");
		BRICK = new Resource("brick.png");
		CONCRETE = new Resource("concrete.png");
		STEEL = new Resource("steel.png");
		GEMS = new Resource("gems.png");
		CARBON = new Resource("carbon.png");
		POPULATION = new Resource("population.png");
		POPULATION.addCount(2);

		Data.setData(this);
		Data.mechanics.createTables();

		buildingCount = 0;
		foodCount = 0;

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
		for(Planet planet:planets){
			planet.dispose();
		}
		Data.ui.dispose();
		Data.mechanics.dispose();
	}

	@Override
	public void resize(int width, int height) {
		Data.ui.resize(width, height);
	}

	/**
	 * Called before exiting
	 * Turns off tables to avoid potential glitches
	 */
	public void exit(){
		Data.setResourceType(ResourceType.NONE);
		Data.ui.refreshBuildingTable();
		Data.ui.refreshFoodTable();
		Data.ui.refreshResourcesTable();
		Data.ui.refreshSpecialTable();
		Gdx.app.exit();
	}

	//Getters and Setters
	public long getBuildingCount() {
		return buildingCount;
	}
	public void setBuildingCount(long buildingCount) {
		this.buildingCount = buildingCount;
	}
	public long getFoodCount() {
		return foodCount;
	}
	public void setFoodCount(long foodCount) {
		this.foodCount = foodCount;
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
	public Planet getEarth() {
		return earth;
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
	public SpriteBatch getBatch() {
		return batch;
	}
}