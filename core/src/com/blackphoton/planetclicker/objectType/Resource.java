package com.blackphoton.planetclicker.objectType;

import com.badlogic.gdx.graphics.Texture;

/**
 * Holds data about a specific type of resource
 */
public class Resource {
	/**
	 * Number this resource of currently
 	 */
	private long count;
	/**
	 * Texture of the resource
	 */
	private Texture texture;

	/**
	 * @param texture Location of the texture relative to android/assets
	 */
	public Resource(String texture) {
		count = 0;
		this.texture = new Texture(texture);
	}

	public void addCount(long count) {
		this.count += count;
	}

	//Getters and Setters
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
