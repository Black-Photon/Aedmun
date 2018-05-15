package com.blackphoton.planetclicker.objectType;

import com.badlogic.gdx.graphics.Texture;

/**
 * Holds data about an era
 */
public class Era {
	private String name;
	private Long pop_req;
	private Texture texture;

	/**
	 * @param name Name of the era (to identify, show and save)
	 * @param pop_req Minimum population needed for the era to be unlockable
	 * @param imagePath Location of the image of the era text to appear under the population counter. Relative to android/assets
	 */
	public Era(String name, Long pop_req, String imagePath){
		this.name = name;
		this.pop_req = pop_req;
		texture = new Texture(imagePath);
	}

	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPop_req() {
		return pop_req;
	}
	public void setPop_req(Long pop_req) {
		this.pop_req = pop_req;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
