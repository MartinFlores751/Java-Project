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
        f = new Rectangle();
        xSpeed = a;
        ySpeed = b;
        x = 400;
        y = 400;
        mSkin = new Texture("purple-ball.png");
        f.x = 400;
        f.y = 400;
    }
    Fired(double a, double b, double c, double d){
        xSpeed = a;
        ySpeed = b;
        x = 400;
        y = 400;
        f.x = 400;
        f.y = 400;
        f.width = (float) c;
        f.height = (float) d;
    }
}
