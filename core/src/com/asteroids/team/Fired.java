package com.asteroids.team;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Fired {
    Rectangle f;
    Texture mSkin;
    double xSpeed;
    double ySpeed;
    double x;
    double y;
    Fired (double a, double b){
        mSkin = new Texture("purple-ball.png");
        double center = 400 - mSkin.getHeight() / 2;
        f = new Rectangle();
        xSpeed = a;
        ySpeed = b;
        x = 400;
        y = 400;
        f.x = (float) center;
        f.y = (float) center;
    }

}
