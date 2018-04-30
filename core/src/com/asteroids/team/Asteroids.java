package com.asteroids.team;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.audio.Mp3;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.css.Rect;

import java.util.Iterator;

//import com.asteroids.team.Asteroid;
//import com.asteroids.team.PlayerShip;

public class Asteroids extends ApplicationAdapter implements InputProcessor{
	private SpriteBatch batch;
	private Texture shipImage;
	private Texture asteroidImage;
	private Texture laser;
	private boolean fire = true;
	private Rectangle player;
	private boolean isAlive;
	private PlayerShip player1;
	private Blast blaster;
	private Music bgm;
    BitmapFont font;
    private OrthographicCamera camera;
    private Rectangle asteroid;
    private Array<Rectangle> aster;
    private Array<Rectangle> blasts;
    private Array<Fired> lasersShot;
    private Sound shooty;
    private double speed = 50;
    private int howMany;
    private int current;
    private int howManyLaser;
    private int currentLaser;
    private int howManyFired;
    private int currentFired;
    private int speedUp;
    int prevKey = -1;


	@Override
	public void create () {
        font = new BitmapFont();
        shipImage = new Texture(Gdx.files.internal("bgbattleship.png"));
        asteroidImage = new Texture(Gdx.files.internal("images.png"));
        laser = new Texture(Gdx.files.internal("purple-ball.png"));
		 batch = new SpriteBatch();
        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm.wav"));
        shooty = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
        bgm.setLooping(true);
        bgm.play();
        Gdx.gl.glClearColor(0, 0, 0, 1); //TEMP: Black Background
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        //there are only 4 asteroids at any one time; current keeps track of how many are on the field
        howMany = 4;
        current = howMany;
        speedUp = 0;
        //player is alive = keep going otherwise gameoever
        isAlive = true;
        //similar to to asteroids except we start with no blasts spawned
        howManyLaser = 9;
        currentLaser = 0;
        howManyFired = 9;
        currentFired = 0;
        aster = new Array<Rectangle>();
        blasts = new Array<Rectangle>();
        lasersShot = new Array<Fired>();
        for (int i = 0; i < howMany; i++){
            spawnAsteroids();
        }

        player1 = new PlayerShip();
        player = new Rectangle();
        player.x = 800 / 2 - player1.mSkin.getWidth() / 2;
        player.y = 800 / 2 - player1.mSkin.getHeight() / 2;
        player.width = player1.mXVel + 10;
        player.height = player1.mYVel + 10;
        blaster = new Blast();

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
        camera.update();
        batch.setProjectionMatrix(camera.combined);

		Gdx.input.setInputProcessor(this);
		if (isAlive == true){
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                keyDown(Input.Keys.SPACE);
                keyUp(Input.Keys.SPACE);
            }
            mouseMoved(Gdx.input.getX(), Gdx.input.getY());

            //TODO: Render asteroids and ship within the batch for optimized rendering!
            batch.begin();

            //blaster.mBlast.draw(batch);
            for (Rectangle i : aster){
                batch.draw(asteroidImage, i.x, i.y);
            }
            if (currentFired > 0){
                for (Fired i : lasersShot){
                    batch.draw(laser, i.f.x, i.f.y);
                }
            }
            player1.mShip.draw(batch);
            batch.end();
            if (current < howMany){
                current++;
                spawnAsteroids();
            }

            ////////////Asteroid movement//////////////
            Iterator<Rectangle> i = aster.iterator();
            while (i.hasNext()){
                //gotta use some cosine, sine, tangent math to get the meteors to move directly at the player
                Rectangle babyAsteroid = i.next();
                double center = 400 - asteroidImage.getHeight() / 2;

                double xPos = (double) babyAsteroid.x;
                double yPos = (double) babyAsteroid.y;
                double tempHeight;
                double tempWidth;
                double temp1;
                double temp2;
                double xSpeed;
                double ySpeed;
                float angle;
                if (speedUp >= 2){
                    speedUp = 0;
                    speed += 50;
                }
                if(babyAsteroid.x < center && babyAsteroid.y < center){
                    tempWidth = center - xPos;
                    tempHeight = center - yPos;
                    temp2 = tempWidth * -1 / tempHeight;
                    temp2 = temp2 / (-1);
                    temp1 = Math.atan(temp2);
                    angle = (float) temp1 * (-180f / (float) Math.PI);
                    xSpeed = speed * Math.cos(angle);
                    ySpeed = speed * Math.sin(angle);
                    babyAsteroid.y += ySpeed * Gdx.graphics.getDeltaTime();
                    babyAsteroid.x += xSpeed * Gdx.graphics.getDeltaTime();
                }
                else if(babyAsteroid.x > center && babyAsteroid.y > center){
                    tempWidth = xPos - center;
                    tempHeight = yPos - center;
                    temp2 = tempWidth * -1 / tempHeight;
                    temp2 = temp2 / (-1);
                    temp1 = Math.atan(temp2);
                    angle = (float) temp1 * (-180f / (float) Math.PI);
                    xSpeed = speed * Math.cos(angle);
                    ySpeed = speed * Math.sin(angle);
                    babyAsteroid.y -= (ySpeed) * Gdx.graphics.getDeltaTime();
                    babyAsteroid.x -= (xSpeed) * Gdx.graphics.getDeltaTime();
                }
                else if(babyAsteroid.x < center && babyAsteroid.y > center){
                    tempWidth = center - xPos;
                    tempHeight = center - yPos;
                    temp2 = tempWidth / tempHeight;
                    temp2 = temp2 / (-1);
                    temp1 = Math.atan(temp2);
                    angle = (float) temp1 * (-180f / (float) Math.PI);
                    xSpeed = speed * Math.cos(angle);
                    ySpeed = speed * Math.sin(angle);
                    babyAsteroid.y -= ySpeed * Gdx.graphics.getDeltaTime();
                    babyAsteroid.x += xSpeed * Gdx.graphics.getDeltaTime();
                }
                else if(babyAsteroid.x > center && babyAsteroid.y < center) {
                    tempWidth = xPos - center;
                    tempHeight = center - yPos;
                    temp2 = tempWidth / (-1 * tempHeight);
                    temp2 = temp2 / (-1);
                    temp1 = Math.atan(temp2);
                    angle = (float) temp1 * (-180f / (float) Math.PI);
                    xSpeed = speed * Math.cos(angle);
                    ySpeed = speed * Math.sin(angle);
                    babyAsteroid.y -= ySpeed * Gdx.graphics.getDeltaTime();
                    babyAsteroid.x += xSpeed * Gdx.graphics.getDeltaTime();
                }
                else if(babyAsteroid.x == 400){
                    if (babyAsteroid.y < 400){
                        babyAsteroid.y += speed * Gdx.graphics.getDeltaTime();
                    }
                    else{
                        babyAsteroid.y -= speed * Gdx.graphics.getDeltaTime();
                    }
                }
                else if (babyAsteroid.y == 400){
                    if (babyAsteroid.x < 400){
                        babyAsteroid.x += speed * Gdx.graphics.getDeltaTime();
                    }
                    else{
                        babyAsteroid.x -= speed * Gdx.graphics.getDeltaTime();
                    }
                }

                if (babyAsteroid.overlaps(player)){
                    isAlive = false;
                }
            }
            //////////blast movement if there is any on the field///////////
            if (currentLaser > 0){
                Iterator<Rectangle> z = blasts.iterator();
                Iterator<Fired> h = lasersShot.iterator();
                if (currentFired > 0){
                    while (h.hasNext()){
                        Fired babyFired = h.next();
                        /*
                        if (babyFired.f.x < 400 && babyFired.f.y < 400){
                            babyFired.f.y += babyFired.ySpeed * Gdx.graphics.getDeltaTime();
                            babyFired.f.x += babyFired.xSpeed * Gdx.graphics.getDeltaTime();
                        }
                        else if (babyFired.f.x > 400 && babyFired.f.y > 400){
                            babyFired.f.y -= babyFired.ySpeed * Gdx.graphics.getDeltaTime();
                        }
                        else if (babyFired.f.x < 400 && babyFired.f.y  > 400){
                            babyFired.f.y -= babyFired.ySpeed * Gdx.graphics.getDeltaTime();
                            babyFired.f.x += babyFired.xSpeed * Gdx.graphics.getDeltaTime();
                        }
                        else if (babyFired.f.x > 400 && babyFired.f.y < 400){
                            babyFired.f.y -= babyFired.ySpeed * Gdx.graphics.getDeltaTime();
                            babyFired.f.x += babyFired.xSpeed * Gdx.graphics.getDeltaTime();
                        }
                        */
                        babyFired.f.y += babyFired.ySpeed * Gdx.graphics.getDeltaTime();
                        babyFired.f.x += babyFired.xSpeed * Gdx.graphics.getDeltaTime();
                        //removes blast if goes out of scope
                        if(babyFired.f.x + laser.getHeight() < 0 || babyFired.f.y > 800 || babyFired.f.x + laser.getWidth() < 0 || babyFired.f.x > 800) {
                            h.remove();
                            currentLaser--;
                            currentFired--;
                        }
                        Iterator<Rectangle> y = aster.iterator();
                        while(y.hasNext()){
                            Rectangle babyAsteroid = y.next();
                            if(babyFired.f.overlaps(babyAsteroid)){
                                h.remove();
                                y.remove();
                                speedUp++;
                                currentLaser--;
                                current--;
                                //break;
                            }

                        }
                    }

                }
                while (z.hasNext()){
                    Rectangle babyBlast = z.next();
                    double center = 400;
                    double xPos = (double) Gdx.input.getX();
                    double yPos = (double) Gdx.input.getY();
                    double xSpeed;
                    double ySpeed;
                    double tempWidth;
                    double tempHeight;
                    double temp1;
                    double temp2;
                    float angle;
                    double blastSpeed = 900;
                    //babyBlast.y -= 300 * Gdx.graphics.getDeltaTime();
                    if(xPos < center && yPos < center){
                        //System.out.println("X: " + Gdx.input.getX() + " Y: " + Gdx.input.getY() );
                        //xPos = xPos + 800;
                        //yPos = yPos + 800;
                        tempWidth = center - xPos;
                        tempHeight = center - yPos;
                        temp2 = tempWidth * -1 / tempHeight;
                        temp2 = temp2 / (-1);
                        temp1 = Math.atan(temp2);
                        angle = (float) temp1 * (-180f / (float) Math.PI);
                        xSpeed = blastSpeed * Math.cos(angle);
                        ySpeed = blastSpeed * Math.sin(angle);
                        Fired babyFired = new Fired(xSpeed, ySpeed);
                        //System.out.println("X speed: " + xSpeed + " Y speed: " + ySpeed );
                        lasersShot.add(babyFired);
                        currentFired++;
                        //babyBlast.y += ySpeed * Gdx.graphics.getDeltaTime();
                       // babyBlast.x += xSpeed * Gdx.graphics.getDeltaTime();
                    }
                    else if(xPos > center && yPos > center){
                        //xPos = xPos - 800;
                        //yPos = yPos - 800;
                        tempWidth = xPos - center;
                        tempHeight = yPos - center;
                        temp2 = tempWidth * -1 / tempHeight;
                        temp2 = temp2 / (-1);
                        temp1 = Math.atan(temp2);
                        angle = (float) temp1 * (-180f / (float) Math.PI);
                        xSpeed = blastSpeed * Math.cos(angle);
                        ySpeed = blastSpeed * Math.sin(angle);
                        Fired babyFired = new Fired(xSpeed, ySpeed);
                        lasersShot.add(babyFired);
                        currentFired++;
                       // babyBlast.y -= (ySpeed) * Gdx.graphics.getDeltaTime();
                        //babyBlast.x -= (xSpeed) * Gdx.graphics.getDeltaTime();
                    }
                    else if(xPos < center && yPos > center){
                        //xPos = xPos + 800;
                       // yPos = yPos - 800;
                        tempWidth = center - xPos;
                        tempHeight = center - yPos;
                        temp2 = tempWidth / tempHeight;
                        temp2 = temp2 / (-1);
                        temp1 = Math.atan(temp2);
                        angle = (float) temp1 * (-180f / (float) Math.PI);
                        xSpeed = blastSpeed * Math.cos(angle);
                        ySpeed = blastSpeed * Math.sin(angle);
                        Fired babyFired = new Fired(xSpeed, ySpeed);
                        lasersShot.add(babyFired);
                        currentFired++;
                       // babyBlast.y -= ySpeed * Gdx.graphics.getDeltaTime();
                        //babyBlast.x += xSpeed * Gdx.graphics.getDeltaTime();
                    }
                    else if(xPos > center && yPos < center) {
                        //xPos = xPos - 800;
                        //yPos = yPos + 800;
                        tempWidth = xPos - center;
                        tempHeight = center - yPos;
                        temp2 = tempWidth / (-1 * tempHeight);
                        temp2 = temp2 / (-1);
                        temp1 = Math.atan(temp2);
                        angle = (float) temp1 * (-180f / (float) Math.PI);
                        xSpeed = blastSpeed * Math.cos(angle);
                        ySpeed = blastSpeed * Math.sin(angle);
                        Fired babyFired = new Fired(xSpeed, ySpeed);
                        lasersShot.add(babyFired);
                        currentFired++;
                        //babyBlast.y -= ySpeed * Gdx.graphics.getDeltaTime();
                        //babyBlast.x += xSpeed * Gdx.graphics.getDeltaTime();
                    }
                    else if(xPos == center){
                        if (yPos < 400){

                            Fired babyFired = new Fired(0, blastSpeed);
                            lasersShot.add(babyFired);
                            currentFired++;
                            yPos += blastSpeed * Gdx.graphics.getDeltaTime();
                        }
                        else{

                            Fired babyFired = new Fired(0, blastSpeed);
                            lasersShot.add(babyFired);
                            currentFired++;
                            //yPos -= blastSpeed * Gdx.graphics.getDeltaTime();
                        }
                    }
                    else if (yPos == center){
                        if (xPos < 400){

                            Fired babyFired = new Fired(blastSpeed, 0);
                            lasersShot.add(babyFired);
                            currentFired++;
                           // xPos += blastSpeed * Gdx.graphics.getDeltaTime();
                        }
                        else{
                            Fired babyFired = new Fired(blastSpeed, 0);
                            lasersShot.add(babyFired);
                            currentFired++;
                            //xPos -= blastSpeed * Gdx.graphics.getDeltaTime();
                        }
                    }
                    z.remove();




                }
            }
        }
        ///////GAME OVER render/////////
        else {
            Iterator<Rectangle> j = aster.iterator();
            while (j.hasNext()){
                Rectangle toRemove = j.next();
                j.remove();
                current--;
            }
            if (currentLaser > 0){
                Iterator<Rectangle> k = blasts.iterator();
                while(k.hasNext()){
                    Rectangle removeLaser = k.next();
                    k.remove();
                    currentLaser--;
                }   
            }
		    batch.begin();
            font.draw(batch, "GAME OVER (hit 'A' to exit or 'B' to RESTART)", 250, 400);
            batch.end();
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                dispose();
                Gdx.app.exit();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.B)){
                isAlive = true;
                speed = 100;
            }
        }


    }

	@Override
	public void dispose () {
	    asteroidImage.dispose();
	    shipImage.dispose();
	    laser.dispose();
	    bgm.dispose();
	    shooty.dispose();
	    player1.mSkin.dispose();
		//batch.dispose();
        //TODO: Dispose of everything on exit
    }

	/*
	TODO: Function should spawn asteroids at LEAST ~20px away from center(player ship)
	- random velocity in +-5 range
	 */

    private void spawnAsteroids() {
        int spawnWhere = MathUtils.random(0,3);
        Rectangle babyAster = new Rectangle();
        babyAster.width = asteroidImage.getWidth();
        babyAster.height = asteroidImage.getHeight();
        if (spawnWhere == 0){
            babyAster.x = MathUtils.random(0, 800-150);
            babyAster.y = 800 + 200;
            aster.add(babyAster);
        }
        else if (spawnWhere == 1){
            babyAster.x = MathUtils.random(0, 800-150);
            babyAster.y = 0-250;
            aster.add(babyAster);
        }
        else if (spawnWhere == 2){
            babyAster.x = 800 + 200;
            babyAster.y = MathUtils.random(0, 800-150);
            aster.add(babyAster);
        }
        else if (spawnWhere == 3){
            babyAster.x = -200;
            babyAster.y = MathUtils.random(0, 800-150);
            aster.add(babyAster);
        }
    }

    private void spawnBlasts(){
        float spawnLocX =  400;
        float spawnLocY =  400;
        Rectangle babyBlast = new Rectangle();
        babyBlast.height = 1;
        babyBlast.width = 1;
        babyBlast.x = spawnLocX;
        babyBlast.y = spawnLocY;
        blasts.add(babyBlast);
    }
    /*
    TODO: Check for collisions and handle them accordingly
    - bullets <---> asteroids
    - ship <---> asteroids
     */
    private void handleCollisions() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE && fire == true){
            fire = false;
            //only allows for things to be shot four times at a time
            if (currentLaser < howManyLaser - 1){
                shooty.play();
                spawnBlasts();
                currentLaser++;

            }

        }
        prevKey--;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE && prevKey != keycode){
            //System.out.println("Pew Pew~");
            batch.begin();
            font.draw(batch, "pew pew!", 380, 20);

            batch.end();
            prevKey = Input.Keys.SPACE;
        }
        else {
            fire = true;


        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y){
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        //blaster.mBlast.setCenter((Gdx.graphics.getHeight() / 2), (Gdx.graphics.getHeight() / 2) );
        if (x == width / 2){
            if(y > height / 2){
                player1.mShip.setRotation(0);
                blaster.mBlast.setRotation(0);
            }
            else if (y < height / 2){
                player1.mShip.setRotation(180);
                blaster.mBlast.setRotation(180);
            }

        }
        else if (y == height / 2){
            if(x > width / 2){
                player1.mShip.setRotation(90);
                blaster.mBlast.setRotation(90);
            }
            else if (x < width / 2){
                player1.mShip.setRotation(270);
                blaster.mBlast.setRotation(270);
            }
        }
        else{
            //math stuff here dude
            double middleH = (double) height / 2;
            double middleW = (double) width / 2;
            float angle;
            double temp1;
            double temp2;
            if( x > middleW && y < middleH){
                temp2 = ((double)x -middleW)/((double)y - middleH );
                temp2 = temp2 / (-1);
                temp1 = Math.atan(temp2);
                angle = (float) temp1 * (-180f / (float) Math.PI);
                angle = angle + 180f;
                //System.out.println("X: " + x + " Y: " + y);
                player1.mShip.setRotation(angle);
                blaster.mBlast.setRotation(angle);
            }
            else if( x < middleW && y < middleH){
                temp2 = ((double)x -middleW)/((double)y - middleH );
                temp2 = temp2 / (-1);
                temp1 = Math.atan(temp2);
                angle = (float) temp1 * (-180f / (float) Math.PI);
                angle = angle + 180f;
                //System.out.println("X: " + x + " Y: " + y);
                player1.mShip.setRotation(angle);
                blaster.mBlast.setRotation(angle);
            }
            else if( x > middleW && y > middleH){
                temp2 = ((double)x -middleW)/((double)y - middleH );
                temp2 = temp2 / (-1);
                temp1 = Math.atan(temp2);
                angle = (float) temp1 * (-180f / (float) Math.PI);
                //System.out.println("X: " + x + " Y: " + y);
                player1.mShip.setRotation(angle);
                blaster.mBlast.setRotation(angle);
            }
            else if( x < middleW && y > middleH){
                temp2 = ((double)x -middleW)/((double)y - middleH );
                temp2 = temp2 / (-1);
                temp1 = Math.atan(temp2);
                angle = (float) temp1 * (-180f / (float) Math.PI);
                //System.out.println("X: " + x + " Y: " + y);
                player1.mShip.setRotation(angle);
                blaster.mBlast.setRotation(angle);
            }



        }


        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
