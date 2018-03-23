package com.asteroids.team.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.asteroids.team.Asteroids;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Asteroids";
        config.width = 640;
        config.height = 480;
		new LwjglApplication(new Asteroids(), config);
	}
}
