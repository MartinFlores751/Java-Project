package com.asteroids.team;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//import com.asteroids.team.Asteroid;
//import com.asteroids.team.PlayerShip;

public class Asteroids extends ApplicationAdapter implements InputProcessor{
	private SpriteBatch batch;
	private PlayerShip player1;
	private Blast blaster;
	private Asteroid aster[] = new Asteroid[4];
    BitmapFont font;
    //private OrthographicCamera camera;
    int prevKey = -1;


	@Override
	public void create () {
        font = new BitmapFont();
		batch = new SpriteBatch();
        //camera = new OrthographicCamera();
        Gdx.gl.glClearColor(0, 0, 0, 1); //TEMP: Black Background

        //TODO: Spawn player ship here!
        spawnAsteroids();
        player1 = new PlayerShip();
        blaster = new Blast();

        aster[0] = new Asteroid();
        aster[1] = new Asteroid();
        aster[2] = new Asteroid();
        aster[3] = new Asteroid();
        for (int i = 0; i < 4; i++){
            aster[i].mAsteroid.setCenter(aster[i].mXVel, aster[i].mYVel);
        }
        aster[0].mAsteroid.setPosition(600, 600);
        aster[1].mAsteroid.setPosition(100, 100);
        aster[2].mAsteroid.setPosition(600, 100);
        aster[3].mAsteroid.setPosition(100, 600);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.input.setInputProcessor(this);
		//keyDown(Input.Keys.LEFT);
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            keyDown(Input.Keys.SPACE);
        }
        mouseMoved(Gdx.input.getX(), Gdx.input.getY());
		//if (Gdx.input.)
        //blaster.mBlast.rotate(15f);
       // player1.mShip.rotate(15f);
        for (int i = 0; i < 4; i++){
            aster[i].mAsteroid.rotate(.05f);
        }

        //TODO: Render asteroids and ship within the batch for optimized rendering!
		batch.begin();
		player1.mShip.draw(batch);
		blaster.mBlast.draw(batch);
		aster[0].mAsteroid.draw(batch);
        aster[1].mAsteroid.draw(batch);
        aster[2].mAsteroid.draw(batch);
        aster[3].mAsteroid.draw(batch);

        batch.end();

        handleCollisions();

        //TODO/TEMP: Game ends when player hits asteroid
	}

	@Override
	public void dispose () {
		batch.dispose();
        //TODO: Dispose of everything on exit
    }

	/*
	TODO: Function should spawn asteroids at LEAST ~20px away from center(player ship)
	- random velocity in +-5 range
	 */

    private void spawnAsteroids() {

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
        if (keycode == Input.Keys.SPACE && prevKey != keycode){
            //System.out.println("Pew Pew~");
            batch.begin();
            font.draw(batch, "hello world ", 380, 20);
            batch.end();
            prevKey = Input.Keys.SPACE;
        }
        else {
            batch.begin();
            font.draw(batch, "PEW! PEW!", 380, 20);
            batch.end();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        prevKey = -1;
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
