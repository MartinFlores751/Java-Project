package com.asteroids.team;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.asteroids.team.Asteroid;
import com.asteroids.team.PlayerShip;

public class Asteroids extends ApplicationAdapter {
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1); //TEMP: Black Background

        //TODO: Spawn player ship here!
        spawnAsteroids();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //TODO: Render asteroids and ship within the batch for optimized rendering!
		batch.begin();


        batch.end();

        handleCollisions();

        //TODO/TEMP: Game ends when player hits asteroid
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        //TODO: Dispose of everything on exit
    }

	/*
	TODO: Function should spawn asteroids at LEAST ~20px away from center(player ship)
	- random velocity in +-5 range
	 */

    private void spawnAsteroids() {

    }

    /*
    TODO: Check for collisions and handle them accordingly
    - bullets <---> asteroids
    - ship <---> asteroids
     */
    private void handleCollisions() {

    }
}
