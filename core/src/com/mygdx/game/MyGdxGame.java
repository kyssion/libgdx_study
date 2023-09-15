package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new TextureRegion(new Texture("badlogic.jpg"));
		System.out.println(img.getRegionWidth()+":"+img.getRegionHeight());
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(img, 0, 0,(float)img.getRegionWidth(),(float)img.getRegionHeight(),
				img.getRegionWidth(),img.getRegionHeight(),
				1,1,-45); // 水平翻转和垂直翻转

//		batch.draw(img, 256, 0,0,0,
//				img.getWidth(),img.getHeight(),
//				1,1,0,
//				0,0,img.getWidth(),img.getWidth(),
//				false,false);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
