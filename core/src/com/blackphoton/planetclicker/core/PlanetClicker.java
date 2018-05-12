package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.resources.ResourceType;

/**
 * Main class extending ApplicationAdapter for game-wide events that aren't necessarily more mechanics, data or UI. Also has variables that must be non-static
 */
public class PlanetClicker extends ApplicationAdapter{
	/**
	 * Main batch to draw with
	 */
	private SpriteBatch batch;
	/**
	 * Main stage for clicking planet screen
	 */
	private Stage stage;

	//Resources
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
		for(Planet planet:Data.getPlanets()){
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
	public SpriteBatch getBatch() {
		return batch;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}