package com.gymy.bucketdrop;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class BucketDrop extends ApplicationAdapter {

	// loading assets
	private Texture bucketImage;
	private Texture dropletImage;
	private Sound rainDropSound;
	private Music rainMusic;

	private Rectangle bucket;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Color backgroundColor = new Color(0, 0, 0.2f, 1);

	static int SCREEN_HEIGHT = 480;
	static int SCREEN_WIDTH = 800;
	static int SPRITE_SIZE = 64;

	private Array<Rectangle> raindrops;
	private long lastDropTime;

	@Override
	public void create() {
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		dropletImage = new Texture(Gdx.files.internal("drop.png"));

		// sounds reside in memory for faster access, thus for larger files use music
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain_sound.mp3"));
		rainDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

		// playing the sound in a loop
		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();

		// This will make sure the camera always shows us an area of our game world that
		// is 800x480 units wide.
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.height = SPRITE_SIZE;
		bucket.width = SPRITE_SIZE;
		bucket.x = (SCREEN_WIDTH / 2) - (SPRITE_SIZE / 2);
		bucket.y = 20;

		raindrops = new Array<>();
		spawnDrop();
	}

	@Override
	public void render() {
		ScreenUtils.clear(backgroundColor);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle drop : raindrops) {
			batch.draw(dropletImage, drop.x, drop.y);
		}

		batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			// To transform these coordinates to our camera’s coordinate system
			camera.unproject(touchPos);

			bucket.x = touchPos.x - SPRITE_SIZE / 2; // how to move
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// for out of boundary cases
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - SPRITE_SIZE)
			bucket.x = 800 - SPRITE_SIZE;

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnDrop();

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0)
				iter.remove();
			if (raindrop.overlaps(bucket)) {
				rainDropSound.play();
				iter.remove();
			}
		}

	}

	@Override
	public void dispose() {
		batch.dispose();
		dropletImage.dispose();
		bucketImage.dispose();
		rainMusic.dispose();
		rainDropSound.dispose();
	}

	public void spawnDrop() {
		Rectangle drop = new Rectangle();
		drop.x = MathUtils.random(0, 800 - SPRITE_SIZE);
		drop.y = SCREEN_HEIGHT;
		drop.height = SPRITE_SIZE;
		drop.width = SPRITE_SIZE;
		raindrops.add(drop);
		lastDropTime = TimeUtils.nanoTime();
	}
}
