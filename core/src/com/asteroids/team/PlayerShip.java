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
    int mXCord;
    int mYCord;
    int mXVel;
    int mYVel;
    boolean mIsAlive;

    PlayerShip() {
        mSkin= new Texture("bgbattleship.png");
        mShip = new Sprite(mSkin);
        mXCord = mSkin.getHeight() / 2;
        mYCord = mSkin.getWidth() / 2;
        mIsAlive = true;
        mShip.setPosition((Gdx.graphics.getHeight() / 2) - mXCord, (Gdx.graphics.getHeight() / 2) - mYCord);
        mShip.setRotation(0);
    }

    void updateVel(int press) {
        if (press == 0) {
            float rotation = mShip.getRotation();
            System.out.println(rotation);
        } else if (press == 1) {

        } else if (press == 2) {

        } else if (press == 3) {

        }
    }

}
