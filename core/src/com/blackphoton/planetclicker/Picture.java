package com.blackphoton.planetclicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Picture extends Actor {
	private Texture texture;
	public Picture(Texture texture, float x, float y){
		this.texture = texture;
		setX(x);
		setY(y);
		setWidth(texture.getWidth());
		setHeight(texture.getHeight());
	}
	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(texture,getX(), getY());
	}
}
