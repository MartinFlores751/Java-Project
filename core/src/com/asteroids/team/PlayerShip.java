package com.asteroids.team;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
TODO: Fill in all of the necessary player class info
 - Texture
 - Control Handling
 */


class PlayerShip {
    Sprite mShip;
    Texture mSkin;
    int mXVel;
    int mYVel;
    boolean mIsAlive;

    PlayerShip() {
        mSkin= new Texture("bgbattleship.png");
        mShip = new Sprite(mSkin);
        mXVel = mSkin.getHeight() / 2;
        mYVel = mSkin.getWidth() / 2;
        mIsAlive = true;
        mShip.setPosition((Gdx.graphics.getHeight() / 2) - mXVel, (Gdx.graphics.getHeight() / 2) - mYVel);
        mShip.setRotation(0);

    }

}
