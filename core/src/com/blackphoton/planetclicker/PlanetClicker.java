package com.blackphoton.planetclicker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

public class PlanetClicker extends ApplicationAdapter{
	private SpriteBatch batch;
	private Stage stage;
	private int buildingCount = 0;
	private int foodCount = 0;
	private int resourcesCount = 0;
	private int populationCount = 2;
	private Planet planet;

	private UI ui;
	private Mechanics mechanics;

	@Override
	public void create() {
		Data.setData(this);

		batch = new SpriteBatch();
		stage = new Stage();

		ui = Data.getUi();
		mechanics = Data.getMechanics();

		mechanics.create();
		ui.createUI();

		ui.updateResources();
	}

	@Override
	public void render() {
		mechanics.update();
		ui.updateUI();

		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		planet.dispose();
		ui.dispose();
		mechanics.dispose();
	}

	@Override
	public void resize(int width, int height) {
		Data.getUi().resize(width, height);
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

	public int getResourcesCount() {
		return resourcesCount;
	}

	public void setResourcesCount(int resourcesCount) {
		this.resourcesCount = resourcesCount;
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
}