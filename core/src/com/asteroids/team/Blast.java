package com.asteroids.team;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Blast {
    Sprite mBlast;
    private Texture mSkin;
    int mXVel;
    int mYVel;

    Blast(){
        mSkin = new Texture("purple-ball.png");
        mBlast = new Sprite(mSkin);

        // Hides the sprite
        mBlast.setAlpha(1);


        mXVel = mSkin.getHeight() / 2;
        mYVel = mSkin.getWidth() / 2;
        mBlast.setPosition((Gdx.graphics.getHeight() / 2) - mXVel, (Gdx.graphics.getWidth() / 2) - mYVel + 75);
        mBlast.setOrigin(mXVel , mYVel - 75);
    }
}
