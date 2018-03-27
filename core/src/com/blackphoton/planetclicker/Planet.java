package com.blackphoton.planetclicker;

import com.badlogic.gdx.graphics.Texture;

public class Planet {
	private Texture texture;
	private int initial_width;
	private int initial_height;
	private float x;
	private float y;
	/**
	 * Planet size multiplier - planets will be enlarged by this multiple from 128x128px
	 */
	private float multiplier = 2;

	public Planet(Texture texture, int initial_width, int initial_height){
		this.texture = texture;
		this.initial_height = initial_height;
		this.initial_width = initial_width;
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
	public void setMultiplier(float multiplier){
		this.multiplier=multiplier;
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
}
