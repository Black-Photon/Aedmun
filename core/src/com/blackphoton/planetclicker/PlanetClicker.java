package com.blackphoton.planetclicker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlanetClicker extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Planet earth;
	Skin skin;
	Stage stage;
	Picture era;
	final float clickMultiplier = 0.95f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tex_earth = new Texture("earth.png");
		earth = new Planet(tex_earth, 128, 128);
		Gdx.input.setInputProcessor(this);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		era = new Picture(new Texture("cavemen.png"),0,0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage = new Stage();
		stage.addActor(era);
		stage.addActor(earth);

		batch.begin();
		earth.setX(Gdx.graphics.getWidth()/2-earth.getWidth()/2);
		earth.setY(Gdx.graphics.getHeight()/2-earth.getHeight()/2);

		era.setX(Gdx.graphics.getWidth()/2-era.getWidth()/2);
		era.setY(Gdx.graphics.getHeight()/8); //Arbitrary number, just seems to work
		stage.draw();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		earth.dispose();
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
		if(earth.pointInsidePlanet(screenX,screenY))
		earth.setMultiplier(clickMultiplier*earth.getMultiplier());
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		earth.setMultiplier(earth.getMultiplier()/clickMultiplier);
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
