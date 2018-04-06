package com.blackphoton.planetclicker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UI {

	private Texture buildings_tex;
	private Texture food_tex;
	private Texture resources_tex;
	private Texture buildings_clicked;
	private Texture food_clicked;
	private Texture resources_clicked;
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	private Picture buildings;
	private Picture food;
	private Picture resources;
	private Picture era;
	private Group resourceGroup;
	private Label countLabel;
	private Label populationLabel;
	private final Label insufficientResources = new Label("You don't have enough resources to build this", skin);
	private GlyphLayout glyphLayout;
	private BitmapFont bitmapFont;
	private float heightScale;
	private InputMultiplexer multiplexer;
	private final int margin = 3;

	public void createUI(){
		buildings_tex = new Texture("building.png");
		food_tex = new Texture("food.png");
		resources_tex = new Texture("resources.png");
		buildings_clicked = new Texture("building_clicked.png");
		food_clicked = new Texture("food_clicked.png");
		resources_clicked = new Texture("resources_clicked.png");

		era = new Picture(new Texture("cavemen.png"),0,0);

		buildings = new Picture(buildings_tex, 0, 0);
		food = new Picture(food_tex, 0, 0);
		resources = new Picture(resources_tex, 0, 0);

		buildings.setPosition(0,food.getHeight()+resources.getHeight());
		food.setPosition(0, resources.getHeight());
		resourceGroup = new Group();
		resourceGroup.addActor(buildings);
		resourceGroup.addActor(food);
		resourceGroup.addActor(resources);
		buildings.setBounds(buildings.getX(),buildings.getY(), buildings.getWidth(), buildings.getHeight());

		Planet planet = Data.getCurrent();
		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		countLabel = new Label("", skin);
		populationLabel = new Label("", skin);
		glyphLayout = new GlyphLayout();
		bitmapFont = new BitmapFont();

		Data.getPlanetClicker().setPlanet(planet);

		buildings.addListener(new Mechanics.buildingListener());
		food.addListener(new Mechanics.foodListener());
		resources.addListener(new Mechanics.resourcesListener());

		insufficientResources.setColor(0.8f,0.8f,0.8f,0);

		multiplexer = new InputMultiplexer(Data.getPlanetClicker().getStage(), new InputDetector());
		Gdx.input.setInputProcessor(multiplexer);
	}
	public void updateUI(){
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		populationLabel.setText("Population: "+Data.getPlanetClicker().getPopulationCount());
	}

	public void updateResources(){
		switch (Data.getResourceType()) {
			case BUILDINGS:
				buildings.setTexture(buildings_clicked);
				food.setTexture(food_tex);
				resources.setTexture(resources_tex);
				countLabel.setText("Buildings: " + Data.getPlanetClicker().getBuildingCount());
				break;
			case FOOD:
				buildings.setTexture(buildings_tex);
				food.setTexture(food_clicked);
				resources.setTexture(resources_tex);
				countLabel.setText("Food: " + Data.getPlanetClicker().getFoodCount());
				break;
			case RESOURCES:
				buildings.setTexture(buildings_tex);
				food.setTexture(food_tex);
				resources.setTexture(resources_clicked);
				countLabel.setText("Resources: " + Data.getPlanetClicker().getResourcesCount());
				break;
		}
	}

	public void updateEra(Era thisEra){
		era.setTexture(new Texture(thisEra.getImagePath()));
	}

	public void resize(int width, int height) {
		Planet planet = Data.getPlanetClicker().getPlanet();

		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		heightScale = ((float) Gdx.graphics.getHeight())/480f; //480 = default height
		heightScale = (float) Math.pow(1.4, heightScale-1); //Magic. Oooooohh
		resourceGroup.setScale(heightScale);
		countLabel.setFontScale(heightScale);
		populationLabel.setFontScale(heightScale);
		insufficientResources.setFontScale(heightScale);

		Data.getPlanetClicker().getStage().dispose();
		Stage stage = new Stage();
		stage.addActor(era);
		stage.addActor(planet);
		stage.addActor(countLabel);
		stage.addActor(populationLabel);
		stage.addActor(resourceGroup);
		stage.addActor(insufficientResources);
		Data.getPlanetClicker().setStage(stage);

		multiplexer = new InputMultiplexer(stage, new InputDetector());
		Gdx.input.setInputProcessor(multiplexer);

		planet.setX(Gdx.graphics.getWidth()/2- planet.getWidth()/2);
		planet.setY(Gdx.graphics.getHeight()/2- planet.getHeight()/2);

		era.setX(Gdx.graphics.getWidth()/2-era.getWidth()/2);
		era.setY(Gdx.graphics.getHeight()/9); //Arbitrary number, just seems to work

		glyphLayout.setText(bitmapFont, countLabel.getText());
		countLabel.setX(Gdx.graphics.getWidth()/2-heightScale*glyphLayout.width/2);
		countLabel.setY(8*Gdx.graphics.getHeight()/9); //Also arbitrary

		resourceGroup.setX(0);
		resourceGroup.setY(Gdx.graphics.getHeight()/2-heightScale*(buildings.getHeight()+food.getHeight()+resources.getHeight())/2);

		glyphLayout.setText(bitmapFont, populationLabel.getText());
		populationLabel.setX(margin);
		populationLabel.setY(Gdx.graphics.getHeight()-heightScale*glyphLayout.height-margin);

		glyphLayout.setText(bitmapFont, insufficientResources.getText());
		insufficientResources.setX(Gdx.graphics.getWidth()/2-heightScale*glyphLayout.width/2);
		insufficientResources.setY(Gdx.graphics.getHeight()/2-heightScale*glyphLayout.height/2);
	}

	public void dispose(){

	}

	public Label getInsufficientResources() {
		return insufficientResources;
	}
}
