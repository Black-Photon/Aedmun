package com.blackphoton.planetclicker;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * Holds data on various info about progress in the game
 */
public class Data {
	private static Texture tex_earth = new Texture("earth.png");
	private static Planet earth = new Planet(tex_earth, 128, 128, "01e");
	private final static Planet[] planets = {earth};
	private static Planet current = planets[0];

	private static ArrayList<Era> eraList;

	/**
	 * Sets all data to values to be used. Call once at start.
	 */
	public static void setData(){
		eraList = new ArrayList<Era>();
		Era cavemen = new Era("Cavemen", Exponent.toExponent(2), "caveman.png");
		Era iron = new Era("Iron", Exponent.toExponent(400), "iron.png");
		Era bronze = new Era("Bronze", Exponent.toExponent(1000), "bronze.png");
		Era ancient = new Era("Ancient", Exponent.toExponent(12000), "ancient.png");
		Era medievil = new Era("Medievil", Exponent.toExponent(200000), "medievil.png");
		Era classical = new Era("Classical", Exponent.toExponent(5000000), "classical.png");
		Era industrial = new Era("Industrial", Exponent.toExponent(100000000), "industrial.png");
		Era modern = new Era("Modern", new Exponent(7,9), "modern.png");
		Era future = new Era("Future", new Exponent(1,10), "future.png");
		eraList.add(cavemen);
		eraList.add(iron);
		eraList.add(bronze);
		eraList.add(ancient);
		eraList.add(medievil);
		eraList.add(classical);
		eraList.add(industrial);
		eraList.add(modern);
		eraList.add(future);
	}

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
