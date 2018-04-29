package com.blackphoton.planetclicker.objectType;

import com.badlogic.gdx.graphics.Texture;

public class Resource {
	private long count;
	private Texture texture;

	public Resource(String texture) {
		count = 0;
		this.texture = new Texture(texture);
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void addCount(long count) {
		this.count += count;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
