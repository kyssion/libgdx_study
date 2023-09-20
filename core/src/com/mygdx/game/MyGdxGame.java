package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion img;
	OrthographicCamera camera;

	int reduce= 2;
	@Override
	public void create () {

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / reduce, Gdx.graphics.getHeight() / reduce);

		img = new TextureRegion(new Texture("badlogic.jpg"));
		System.out.println(img.getRegionWidth()+":"+img.getRegionHeight());
	}

	@Override
	public void render () {
		camera.position.set(0, 0, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(img, 0, 0,5,5); // 水平翻转和垂直翻转
		batch.draw(img, 0, 0,
				(float)img.getRegionWidth()/2,(float)img.getRegionHeight()/2,
				img.getRegionWidth(),img.getRegionHeight(),
				1,1,30); // 水平翻转和垂直翻转
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
