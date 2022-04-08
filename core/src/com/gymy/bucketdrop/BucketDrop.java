package com.gymy.bucketdrop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class BucketDrop extends ApplicationAdapter {

	// loading assets
	private Texture bucketImage;
	private Texture dropletImage;
	private Sound waterDropSound;
	private Music rainMusic;

	private Rectangle bucket;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Color backgroundColor = new Color(0, 0, 0.2f, 1);

	static int HEIGHT = 480;
	static int WIDTH = 800;

	@Override
	public void create() {
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		dropletImage = new Texture(Gdx.files.internal("drop.png"));

		// sounds reside in memory for faster access, thus for larger files use music
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain_sound.mp3"));
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

		// playing the sound in a loop
		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();

		// This will make sure the camera always shows us an area of our game world that
		// is 800x480 units wide.
		camera.setToOrtho(false, WIDTH, HEIGHT);

		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.height = 64;
		bucket.width = 64;
		bucket.x = (WIDTH / 2) - (64 / 2);
		bucket.y = 20;

	}

	@Override
	public void render() {
		ScreenUtils.clear(backgroundColor);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			// To transform these coordinates to our camera’s coordinate system
			camera.unproject(touchPos);

			bucket.x = touchPos.x - 64 / 2; // how to move
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// for out of boundary cases
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - 64)
			bucket.x = 800 - 64;

	}

	@Override
	public void dispose() {
	}
}
