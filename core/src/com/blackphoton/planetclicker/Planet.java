package com.blackphoton.planetclicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Planet extends Actor {
	private Texture texture;
	private int initial_width;
	private int initial_height;
	private float x;
	private float y;
	private boolean clicked = false;
	/**
	 * Id for planet, to distinguish between them
	 * 01e: Earth
	 */
	private String id;
	/**
	 * Planet size multiplier - planets will be enlarged by this multiple from 128x128px
	 */
	private float multiplier = 1;

	public Planet(Texture texture, int initial_width, int initial_height, String id){
		this.texture = texture;
		this.initial_height = initial_height;
		this.initial_width = initial_width;
		this.id = id;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public float getWidth(){
		return initial_width* multiplier;
	}
	public float getHeight(){
		return initial_height* multiplier;
	}

	public int getInitial_width() {
		return initial_width;
	}

	public int getInitial_height() {
		return initial_height;
	}

	public String getId() {
		return id;
	}

	public void setMultiplier(float multiplier){
		this.multiplier=multiplier;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void dispose(){
		texture.dispose();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	/**
	 * Checks to see if the point is within the planet (A circle)
	 * @param pointX The point x co-ordinate
	 * @param pointY The point y co-ordinate
	 * @return true if point within planet, false otherwise
	 */
	public boolean pointInsidePlanet(float pointX, float pointY){
		float radius = getWidth()/2;
		if(radius!=getHeight()/2) throw new NotSymmetricalException("Texture not a square");
		float relativeX = pointX-x-radius;
		float relativeY = pointY-y-radius;
		if(Math.abs(relativeX)>radius||Math.abs(relativeY)>radius) return false;
		float distanceFromCentre = (float) Math.sqrt((double) relativeX*relativeX + relativeY*relativeY);
		if(distanceFromCentre>radius) return false;
		return true;
	}

	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
	}
}
