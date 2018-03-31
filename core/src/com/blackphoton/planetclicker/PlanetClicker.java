package com.blackphoton.planetclicker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import javafx.concurrent.Task;

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
	Group resourceGroup;
	InputMultiplexer multiplexer;
	final float clickMultiplier = 0.95f;
	//Height and Width of window. Used to check for resize event.
	float previousWidth;
	float previousHeight;
	int buildingCount = 0;
	int foodCount = 0;
	int resourcesCount = 0;
	int populationCount = 2;
	ResourceType resourceType = ResourceType.BUILDINGS;
	Random random;
	final int peoplePerBuilding = 4;
	final int resourcesPerBuilding = 1;
	int numberOfIRThreads = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();
		random = new Random();

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		era = new Picture(new Texture("cavemen.png"),0,0);

		buildings = new Picture(new Texture("building.png"), 0, 0);
		food = new Picture(new Texture("food.png"), 0, 0);
		resources = new Picture(new Texture("resources.png"), 0, 0);
		buildings.setPosition(0,food.getHeight()+resources.getHeight());
		food.setPosition(0, resources.getHeight());
		resourceGroup = new Group();
		resourceGroup.addActor(buildings);
		resourceGroup.addActor(food);
		resourceGroup.addActor(resources);
		buildings.setBounds(buildings.getX(),buildings.getY(), buildings.getWidth(), buildings.getHeight());

		planet = Data.getCurrent();
		previousHeight = Gdx.graphics.getHeight();
		previousWidth = Gdx.graphics.getWidth();
		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		countLabel = new Label("", skin);
		populationLabel = new Label("", skin);
		insufficientResources = new Label("You don't have enough resources to build this", skin);
		glyphLayout = new GlyphLayout();
		bitmapFont = new BitmapFont();

		buildings.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				resourceType = ResourceType.BUILDINGS;
				return true;
			}
		});
		food.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				resourceType = ResourceType.FOOD;
				return true;
			}
		});
		resources.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				resourceType = ResourceType.RESOURCES;
				return true;
			}
		});
		insufficientResources.setColor(0.8f,0.8f,0.8f,0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int randomInt = random.nextInt(100);
		if(randomInt==42){
			if(buildingCount*peoplePerBuilding>populationCount && foodCount>populationCount) populationCount++;
		}

		switch (resourceType) {
			case BUILDINGS:
				countLabel.setText("Buildings: " + buildingCount);
				break;
			case FOOD:
				countLabel.setText("Food: "+foodCount);
				break;
			case RESOURCES:
				countLabel.setText("Resources: " + resourcesCount);
				break;
		}
		populationLabel.setText("Population: "+populationCount);

		stage = new Stage();
		stage.addActor(era);
		stage.addActor(planet);
		stage.addActor(countLabel);
		stage.addActor(populationLabel);
		stage.addActor(resourceGroup);
		stage.addActor(insufficientResources);

		multiplexer = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(multiplexer);

		if(Gdx.graphics.getHeight()!=previousHeight || Gdx.graphics.getWidth()!=previousWidth) {
			planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
			previousWidth = Gdx.graphics.getWidth();
			previousHeight = Gdx.graphics.getHeight();
		}

		batch.begin();
		planet.setX(Gdx.graphics.getWidth()/2- planet.getWidth()/2);
		planet.setY(Gdx.graphics.getHeight()/2- planet.getHeight()/2);

		era.setX(Gdx.graphics.getWidth()/2-era.getWidth()/2);
		era.setY(Gdx.graphics.getHeight()/9); //Arbitrary number, just seems to work

		glyphLayout.setText(bitmapFont, countLabel.getText());
		countLabel.setX(Gdx.graphics.getWidth()/2-glyphLayout.width/2);
		countLabel.setY(8*Gdx.graphics.getHeight()/9); //Also arbitrary

		resourceGroup.setX(0);
		resourceGroup.setY(Gdx.graphics.getHeight()/2-buildings.getHeight()/2-food.getHeight()/2-resources.getHeight()/2);

		final int margin = 3;
		glyphLayout.setText(bitmapFont, populationLabel.getText());
		populationLabel.setX(margin);
		populationLabel.setY(Gdx.graphics.getHeight()-glyphLayout.height-margin);

		glyphLayout.setText(bitmapFont, insufficientResources.getText());
		insufficientResources.setX(Gdx.graphics.getWidth()/2-glyphLayout.width/2);
		insufficientResources.setY(Gdx.graphics.getHeight()/2-glyphLayout.height/2);

		stage.draw();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		planet.dispose();
		stage.dispose();
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
}
