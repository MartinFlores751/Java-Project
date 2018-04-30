package com.asteroids.team;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/*
TODO: Fill in all of the necessary player class info
 - Texture
 - Control Handling
 */


class PlayerShip {
    Sprite mShip;
    Texture mSkin;
    Rectangle mHitBox;
    private int mXCord;
    private int mYCord;
    private int mXVel;
    private int mYVel;
    boolean mIsAlive;
    boolean mFire;

    PlayerShip() {
        mSkin= new Texture("bgbattleship.png");
        mShip = new Sprite(mSkin);
        mHitBox = new Rectangle();
        mHitBox.width = mSkin.getWidth();
        mHitBox.height = mSkin.getHeight();
        mXVel = mYVel = 0;
        mXCord = (Gdx.graphics.getHeight() / 2) - mSkin.getHeight() / 2;
        mYCord = (Gdx.graphics.getHeight() / 2) - mSkin.getWidth() / 2;
        mIsAlive = true;
        mFire = false;
        mShip.setPosition(mXCord, mYCord);
        mHitBox.setPosition(mXCord, mYCord);
        mShip.setRotation(0);
    }

    void updateVel(int press) {

        float rotation = mShip.getRotation();
        rotation = (rotation + 90.0f) >= 360.0f ? (rotation + 90.0f) - 360.0f : rotation + 90.0f;

        System.out.println(rotation);

        if (press == 0) {

            if (rotation == 0f)
                mYVel -= 2;
            else if (rotation == 90f)
                mXVel -= 2;
            else if (rotation == 180f)
                mYVel += 2;
            else if (rotation == 270f)
                mXVel += 2;
            else if (0f < rotation && rotation < 90f) {
                mXVel += 1;
                mYVel += 1;
            } else if (90f < rotation && rotation < 180f) {
                mXVel -= 1;
                mYVel += 1;
            } else if (180f < rotation && rotation < 270f) {
                mXVel -= 1;
                mYVel -= 1;
            } else if (270f < rotation && rotation < 360f) {
                mXVel += 1;
                mYVel -= 1;
            }

            //TODO: Fix stop
        } else if (press == 1) {

            if (rotation == 0f)
                mYVel += 2;
            else if (rotation == 90f)
                mXVel += 2;
            else if (rotation == 180f)
                mYVel -= 2;
            else if (rotation == 270f)
                mXVel -= 2;
            else if (0f < rotation && rotation < 90f) {
                mXVel -= 1;
                mYVel += 1;
            } else if (90f < rotation && rotation < 180f) {
                mXVel += 1;
                mYVel += 1;
            } else if (180f < rotation && rotation < 270f) {
                mXVel += 1;
                mYVel -= 1;
            } else if (270f < rotation && rotation < 360f) {
                mXVel -= 1;
                mYVel -= 1;
            }

        }
    }

    void updateCords(OrthographicCamera cam) {
        mXCord += mXVel;
        mYCord += mYVel;
        mShip.setPosition(mXCord, mYCord);
        mHitBox.setPosition(mXCord, mYCord);

        cam.translate(mXVel, mYVel);
        cam.update();
    }

}
