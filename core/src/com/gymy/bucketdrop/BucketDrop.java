package com.gymy.bucketdrop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


public class BucketDrop extends ApplicationAdapter {

	// loading assets
	private Texture bucketImage;
	private Texture dropletImage;
	private Sound waterDropSound;
	private Music rainMusic;
	
	@Override
	public void create () {
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		dropletImage = new Texture(Gdx.files.internal("drop.png"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain_sound.mp3"));
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

	}

	@Override
	public void render () {


	}
	
	@Override
	public void dispose () {
	}
}
