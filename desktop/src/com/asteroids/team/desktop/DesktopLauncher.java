package com.asteroids.team.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.asteroids.team.Asteroids;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Asteroids";
        config.width = 800;
        config.height = 800;
        config.foregroundFPS = 60;
        config.backgroundFPS = 0;
		new LwjglApplication(new Asteroids(), config);
	}
}
