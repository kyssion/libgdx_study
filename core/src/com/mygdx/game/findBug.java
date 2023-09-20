package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Item.Dog;
import com.mygdx.game.Item.ItemDog;
import com.mygdx.game.adapter.MyInputInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lingkang
 */
public class findBug extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion  dog;
    private OrthographicCamera camera;

//    private World world;
//    private Box2DDebugRenderer debugRenderer;
    // 在正常像素下物体重力现象不明显，需要对纹理进行缩小100++倍才有比较明显的物理效果
    private float reduce = 2;//
    List<Dog> dogList;
    List<ItemDog> itemDogList;
    private int boxNum ;

    private MyInputInfo InputInfo;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / reduce, Gdx.graphics.getHeight() / reduce);

        // 图片一： 50*50 缩小100倍就是 0.5*0.5 在绘制时缩小的
        dog = new TextureRegion(new Texture("badlogic.jpg"));
        // 创建一个世界，里面的重力加速度为 10
//        world = new World(new Vector2(0, 0), true);
        // 试调渲染，可以使用这个渲染观察到我们用Box2D绘制的物体图形
//        debugRenderer = new Box2DDebugRenderer();
        dogList = new ArrayList<>();
        itemDogList = new ArrayList<>();

        this.boxNum = 0;
        this.InputInfo = new MyInputInfo(camera);
        Gdx.input.setInputProcessor(this.InputInfo);
    }
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int fps =  Gdx.graphics.getFramesPerSecond();
        if (fps>=30 &&  Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            itemDogList.add(new ItemDog((int) (100/reduce), (int) (100/reduce), this.InputInfo.tp.x,  this.InputInfo.tp.y,dog));
            boxNum++;
        }

        // 将相机与批处理精灵绑定
        camera.position.set(0, (float) ((float) Gdx.graphics.getHeight()*0.4/reduce), 0);
        camera.update();

        // 将绘制与相机投影绑定 关键 关键
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (ItemDog dog : itemDogList){
            dog.Draw(batch,reduce);
        }

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        this.batch.dispose();
    }

}
