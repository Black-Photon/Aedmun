package com.blackphoton.planetclicker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blackphoton.planetclicker.core.PlanetClicker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Planet Clicker";
		config.useGL30 = false;
		new LwjglApplication(new PlanetClicker(), config);
	}
}
