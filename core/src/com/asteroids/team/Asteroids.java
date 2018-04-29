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
	private PlayerShip player1;
	private Blast blaster;
	private Music bgm;
    BitmapFont font;
    private OrthographicCamera camera;
    private Rectangle asteroid;
    private Array<Rectangle> aster;
    private Sound shooty;
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


        //TODO: Spawn player ship here!
        aster = new Array<Rectangle>();
        for (int i = 0; i < 6; i++){
            spawnAsteroids();
        }

        player1 = new PlayerShip();
        player = new Rectangle();
        player.x = 800 / 2 - player1.mSkin.getWidth() / 2;
        player.y = 800 / 2 - player1.mSkin.getHeight() / 2;
        player.width = player1.mXVel - 10;
        player.height = player1.mYVel - 10;
        blaster = new Blast();
        

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

		Gdx.input.setInputProcessor(this);
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            keyDown(Input.Keys.SPACE);
            keyUp(Input.Keys.SPACE);
        }
        mouseMoved(Gdx.input.getX(), Gdx.input.getY());

        //TODO: Render asteroids and ship within the batch for optimized rendering!
		batch.begin();
		player1.mShip.draw(batch);;
		blaster.mBlast.draw(batch);
        for (Rectangle i : aster){
            batch.draw(asteroidImage, i.x, i.y);
        }

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
        int spawnWhere = MathUtils.random(0,3);
        Rectangle babyAster = new Rectangle();
        babyAster.width = asteroidImage.getWidth();
        babyAster.height = asteroidImage.getHeight();
        if (spawnWhere == 0){
            babyAster.x = MathUtils.random(0, 800-64);
            babyAster.y = 800 - 100;
            aster.add(babyAster);
        }
        else if (spawnWhere == 1){
            babyAster.x = MathUtils.random(0, 800-64);
            babyAster.y = -100;
            aster.add(babyAster);
        }
        else if (spawnWhere == 2){
            babyAster.x = 800 - 100;
            babyAster.y = MathUtils.random(0, 800-64);
            aster.add(babyAster);
        }
        else if (spawnWhere == 3){
            babyAster.x = -100;
            babyAster.y = MathUtils.random(0, 800-64);
            aster.add(babyAster);
        }
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
            shooty.play();
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
