package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(2000,1000);
//		config.setForegroundFPS(60);
		config.setTitle("My GDX Game");
//		new Lwjgl3Application(new findBug(), config);
//		new Lwjgl3Application(new TestBox2dV3(),config);
//		new Lwjgl3Application(new libgdx文字(), config);
//		new Lwjgl3Application(new OrthographicCameraExample(),config);
//		new Lwjgl3Application(new libgdx_触摸鼠标(),config);
		new Lwjgl3Application(new TestBox2dV3(),config);
	}
}
