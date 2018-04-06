package com.blackphoton.planetclicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Picture extends Actor {
	private Texture texture;
	public Picture(Texture texture, float x, float y){
		this.texture = texture;
		setX(x);
		setY(y);
		setWidth(texture.getWidth());
		setHeight(texture.getHeight());
		setBounds(super.getX(), super.getY(), getWidth(), getHeight());
	}
	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(texture,getX(), getY(),getWidth(),getHeight());
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	@Override
	public void setScaleX(float scaleX) {
		super.setScaleX(scaleX);
		setWidth(texture.getWidth()*scaleX);
	}

	@Override
	public void setScaleY(float scaleY) {
		super.setScaleY(scaleY);
		setHeight(texture.getHeight()*scaleY);
	}

	@Override
	public void setScale(float scaleXY) {
		super.setScale(scaleXY);
		setWidth(texture.getWidth()*scaleXY);
		setHeight(texture.getHeight()*scaleXY);
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		super.setScale(scaleX, scaleY);
		setWidth(texture.getWidth()*scaleX);
		setHeight(texture.getHeight()*scaleY);
	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		setBounds(super.getX(), super.getY(), getWidth(), getHeight());
	}

	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		setBounds(super.getX(), super.getY(), getWidth(), getHeight());
	}
}
