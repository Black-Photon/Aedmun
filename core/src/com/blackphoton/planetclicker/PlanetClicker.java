package com.blackphoton.planetclicker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlanetClicker extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Planet planet;
	Skin skin;
	Stage stage;
	Picture era;
	final float clickMultiplier = 0.95f;
	//Height and Width of window. Used to check for resize event.
	float previousWidth;
	float previousHeight;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		era = new Picture(new Texture("cavemen.png"),0,0);
		planet = Data.getCurrent();
		previousHeight = Gdx.graphics.getHeight();
		previousWidth = Gdx.graphics.getWidth();
		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage = new Stage();
		stage.addActor(era);
		stage.addActor(planet);

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
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(planet.isClicked()) {
			planet.setMultiplier(planet.getMultiplier() / clickMultiplier);
			planet.setClicked(false);
		}
		return true;
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
