package com.asteroids.team;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/*
TODO: Fill in all of the details for the asteroid class
- Texture
- AI ?
 */

class Asteroid {
    Sprite mAsteroid;
    Texture mSkin;
    int mXVel;
    int mYVel;
    boolean notDestroyed;

    Asteroid() {
        mSkin = new Texture("images.png");
        mAsteroid = new Sprite(mSkin);
        mXVel = mSkin.getHeight() / 2;
        mYVel = mSkin.getWidth() / 2;
        notDestroyed = true;
    }

}
