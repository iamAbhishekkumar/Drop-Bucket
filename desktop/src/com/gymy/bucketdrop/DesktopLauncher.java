package com.gymy.bucketdrop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		int HEIGHT = 480;
		int WIDTH = 800;
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Bucket Drop");
		config.useVsync(true);
		config.setWindowedMode(WIDTH,HEIGHT);
		new Lwjgl3Application(new BucketDrop(), config);
	}
}
