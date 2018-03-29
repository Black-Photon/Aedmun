package com.blackphoton.planetclicker;

import com.badlogic.gdx.graphics.Texture;

/**
 * Holds data on various info about progress in the game
 */
public class Data {
	private static Texture tex_earth = new Texture("earth.png");
	private static Planet earth = new Planet(tex_earth, 128, 128, "01e");
	private final static Planet[] planets = {earth};
	private static Planet current = planets[0];

	//---Getters and Setters---

	public static Texture getTex_earth() {
		return tex_earth;
	}

	public static void setTex_earth(Texture tex_earth) {
		Data.tex_earth = tex_earth;
	}

	public static Planet getEarth() {
		return earth;
	}

	public static void setEarth(Planet earth) {
		Data.earth = earth;
	}

	public static Planet[] getPlanets() {
		return planets;
	}

	public static Planet getCurrent() {
		return current;
	}

	public static void setCurrent(Planet current) {
		Data.current = current;
	}
}
