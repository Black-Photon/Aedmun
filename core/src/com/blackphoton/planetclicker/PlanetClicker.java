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

public class PlanetClicker extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Planet planet;
	Skin skin;
	Stage stage;
	Picture era;
	Label countLabel;
	Label populationLabel;
	Label insufficientResources;
	GlyphLayout glyphLayout;
	BitmapFont bitmapFont;
	Picture buildings;
	Picture food;
	Picture resources;
	Picture stats;
	Group resourceGroup;
	InputMultiplexer multiplexer;
	final float clickMultiplier = 0.95f;
	int buildingCount = 0;
	int foodCount = 0;
	int resourcesCount = 0;
	int populationCount = 2;
	ResourceType resourceType = ResourceType.BUILDINGS;
	Random random;
	final int peoplePerBuilding = 4;
	final int resourcesPerBuilding = 1;
	int numberOfIRThreads = 0;
	float heightScale;
	Texture buildings_tex;
	Texture food_tex;
	Texture resources_tex;
	Texture buildings_clicked;
	Texture food_clicked;
	Texture resources_clicked;
	Era thisEra;
	final int margin = 3;
	
	@Override
	public void create () {
		Data.setData();

		thisEra = Data.getEraList().get(0);

		buildings_tex = new Texture("building.png");
		food_tex = new Texture("food.png");
		resources_tex = new Texture("resources.png");
		buildings_clicked = new Texture("building_clicked.png");
		food_clicked = new Texture("food_clicked.png");
		resources_clicked = new Texture("resources_clicked.png");

		batch = new SpriteBatch();
		stage = new Stage();
		random = new Random();

		skin = new Skin(Gdx.files.internal("uiskin.json"));
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

		planet = Data.getCurrent();
		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		countLabel = new Label("", skin);
		populationLabel = new Label("", skin);
		insufficientResources = new Label("You don't have enough resources to build this", skin);
		stats = new Picture(new Texture("stats.png"), 0,0);
		glyphLayout = new GlyphLayout();
		bitmapFont = new BitmapFont();

		buildings.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				resourceType = ResourceType.BUILDINGS;
				updateResources();
				return true;
			}
		});
		food.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				resourceType = ResourceType.FOOD;
				updateResources();
				return true;
			}
		});
		resources.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				resourceType = ResourceType.RESOURCES;
				updateResources();
				return true;
			}
		});
		stats.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//TODO Switch view
				System.out.println("Will go to new page with stats about Buildings, Food, Resources and great structures");
				return true;
			}
		});

		insufficientResources.setColor(0.8f,0.8f,0.8f,0);

		updateResources();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		boolean found = false;
		for(Era era: Data.getEraList()){
			if(found){
				if(populationCount>=era.getPop_req().toInt()){
					thisEra = era;
					updateEra();
				}
			}
			if(era.equals(thisEra)){
				found = true;
			}
		}

		if (buildingCount * peoplePerBuilding > populationCount && foodCount > populationCount)
			for(int i=1;i<populationCount;i++) {
				int randomInt = random.nextInt(1000);
				if (randomInt == 42) {
					populationCount++;
				}
			}

		populationLabel.setText("Population: "+populationCount);

		batch.begin();


		stage.draw();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		planet.dispose();
		stage.dispose();
	}

	private void updateResources(){
		switch (resourceType) {
			case BUILDINGS:
				buildings.setTexture(buildings_clicked);
				food.setTexture(food_tex);
				resources.setTexture(resources_tex);
				countLabel.setText("Buildings: " + buildingCount);
				break;
			case FOOD:
				buildings.setTexture(buildings_tex);
				food.setTexture(food_clicked);
				resources.setTexture(resources_tex);
				countLabel.setText("Food: "+foodCount);
				break;
			case RESOURCES:
				buildings.setTexture(buildings_tex);
				food.setTexture(food_tex);
				resources.setTexture(resources_clicked);
				countLabel.setText("Resources: " + resourcesCount);
				break;
		}
	}

	private void updateEra(){
		era.setTexture(new Texture(thisEra.getImagePath()));
	}

	//---KEY-TESTS---

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(planet.pointInsidePlanet(screenX,screenY)) {
			planet.setMultiplier(clickMultiplier * planet.getMultiplier());
			planet.setClicked(true);
			updateResources();
			switch (resourceType) {
				case BUILDINGS:
					if(resourcesCount>=resourcesPerBuilding) {
						resourcesCount -= resourcesPerBuilding;
						buildingCount++;
					}else{
						insufficientResources.setColor(0.8f,0.8f,0.8f,1);
						new Thread(){
							@Override
							public void run() {
								numberOfIRThreads++;
								try {
									sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								float alpha = insufficientResources.getColor().a;
								while(alpha!=0){
									if(numberOfIRThreads>1){
										numberOfIRThreads--;
										return;
									}
									alpha = insufficientResources.getColor().a;
									insufficientResources.setColor(0.8f,0.8f,0.8f,alpha-0.001f);
									try {
										sleep(3);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								numberOfIRThreads--;
							}
						}.start();
					}
					break;
				case FOOD:
					foodCount++;
					break;
				case RESOURCES:
					resourcesCount++;
					break;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(planet.isClicked()) {
			planet.setMultiplier(planet.getMultiplier() / clickMultiplier);
			planet.setClicked(false);
			updateResources();
			return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void resize(int width, int height) {
		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		heightScale = ((float) Gdx.graphics.getHeight())/480f; //480 = default height
		heightScale = (float) Math.pow(1.4, heightScale-1); //Magic. Oooooohh
		resourceGroup.setScale(heightScale);
		countLabel.setFontScale(heightScale);
		populationLabel.setFontScale(heightScale);
		insufficientResources.setFontScale(heightScale);

		stage.dispose();
		stage = new Stage();
		stage.addActor(era);
		stage.addActor(planet);
		stage.addActor(countLabel);
		stage.addActor(populationLabel);
		stage.addActor(resourceGroup);
		stage.addActor(insufficientResources);
		stage.addActor(stats);

		multiplexer = new InputMultiplexer(stage, this);
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

		stats.setX(Gdx.graphics.getWidth()-stats.getWidth());
		stats.setY(Gdx.graphics.getHeight()-stats.getHeight());
	}
}
