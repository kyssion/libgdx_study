package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.test.TestGame;

public class TestGameLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800,400); // 设置窗口大小
        config.setForegroundFPS(60); // 设置fps
        config.useVsync(true); // 设置垂直同步
        config.setTitle("Test Game");
        new Lwjgl3Application(new TestGame(), config);
    }
}
