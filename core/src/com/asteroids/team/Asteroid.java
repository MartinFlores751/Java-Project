package com.asteroids.team;

import com.badlogic.gdx.graphics.Texture;

/*
TODO: Fill in all of the details for the asteroid class
- Texture
- AI ?
 */

class Asteroid {
    Texture mAsteroid;
    int mXVel;
    int mYVel;
    boolean notDestroyed;

    Asteroid() {
        mAsteroid = new Texture("images.jpg");
        mXVel = mAsteroid.getHeight();
        mYVel = mAsteroid.getWidth();
        notDestroyed = true;
    }

}
