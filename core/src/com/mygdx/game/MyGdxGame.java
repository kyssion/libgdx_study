package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		System.out.println(img.getHeight()+":"+img.getWidth());
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(img, 0, 0,(float)img.getWidth()/2,(float)img.getHeight()/2,
				img.getWidth(),img.getHeight(),
				1,1,9, // 拉伸和现状
				0,img.getHeight()-100,100,100, // 这个坐标系是做上角 0,0 向右向下
				false,false); // 水平翻转和垂直翻转

		batch.draw(img, 256, 0,0,0,
				img.getWidth(),img.getHeight(),
				1,1,0,
				0,0,img.getWidth(),img.getWidth(),
				false,false);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
