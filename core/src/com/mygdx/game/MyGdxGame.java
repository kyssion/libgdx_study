package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Item.Dog;
import com.mygdx.game.Item.ItemDog;
import com.mygdx.game.adapter.MyInputInfo;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion dog;
	OrthographicCamera camera;

	float reduce= 2;

	List<Dog> dogList;
	List<ItemDog> itemDogList;
	private MyInputInfo InputInfo;

	@Override
	public void create () {

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / reduce, Gdx.graphics.getHeight() / reduce);

		dog = new TextureRegion(new Texture("badlogic.jpg"));
		System.out.println(dog.getRegionWidth()+":"+dog.getRegionHeight());
		this.InputInfo = new MyInputInfo(camera);
		Gdx.input.setInputProcessor(this.InputInfo);
		dogList = new ArrayList<>();
		itemDogList = new ArrayList<>();
	}

	@Override
	public void render () {
		camera.position.set(0, 0, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			itemDogList.add(new ItemDog((int) (100/reduce), (int) (100/reduce), this.InputInfo.tp.x,  this.InputInfo.tp.y,dog));
		}
		batch.begin();
		batch.draw(dog, 0- (float) 20 /reduce, 0- (float) 20 /reduce, (float) 20 /reduce, (float) 20 /reduce); // 水平翻转和垂直翻转
		batch.draw(dog, 0, 0,
				100/reduce/2,100/reduce/2,
				100/reduce,100/reduce,
				1,1,90); // 水平翻转和垂直翻转
		for (ItemDog dog : itemDogList){
			dog.Draw(batch,reduce);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
