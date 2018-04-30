package com.asteroids.team;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Blast {
    Sprite mBlast;
    Texture mSkin;
    int mXVel;
    int mYVel;
    private double x;
    private double y;
    private int r;

    private double dx;
    private double dy;
    private double rad;
    private double speed;

    private Color color1;


    Blast(double angle, int x, int y) {
        mSkin = new Texture("purple-ball.png");
        mBlast = new Sprite(mSkin);
        mXVel = mSkin.getHeight() / 2;
        mYVel = mSkin.getWidth() / 2;
        mBlast.setPosition((Gdx.graphics.getHeight() / 2) - mXVel, (Gdx.graphics.getWidth() / 2) - mYVel + 75);
        mBlast.setOrigin(mXVel, mYVel - 75);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
        this.x = x;
        this.y = y;
        r = 2;

        rad = Math.toRadians(angle);
        dx = Math.cos(rad);
        dy = Math.sin(rad);
        speed = 15;

        color1 = Color.BLUE;
    }

    public boolean update() {
        x += dx;
        y += dy;

        if (x < -r || x > Asteroids.WIDTH + r || y < -r || y > Asteroids.HEIGHT + r) {
            return true;
        }

        return false;
    }
    //public void draw(Gdx){}

}
  */
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    Blast(){
        mSkin = new Texture("purple-ball.png");
        mBlast =new Sprite(mSkin);
        mXVel = mSkin.getHeight() / 2;
        mYVel = mSkin.getWidth() / 2;
        mBlast.setPosition((Gdx.graphics.getHeight() / 2) - mXVel, (Gdx.graphics.getWidth() / 2) - mYVel + 75);
        mBlast.setOrigin(mXVel , mYVel - 75);
    }



    Blast(){
        mSkin = new Texture("purple-ball.png");
        mBlast =new Sprite(mSkin);
        mXVel = mSkin.getHeight() / 2;
        mYVel = mSkin.getWidth() / 2;
        mBlast.setPosition((Gdx.graphics.getHeight() / 2) - mXVel, (Gdx.graphics.getWidth() / 2) - mYVel + 75);
        mBlast.setOrigin(mXVel , mYVel - 75);
    }

}
