package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LibGdx_Box2dTest extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion dog;
    private OrthographicCamera camera;
    private World world;

    private float REDUCE = 100;

    public LibGdx_Box2dTest() {
        super();
        // 1. 初始化精灵
        batch = new SpriteBatch();
        // 2. 初始化正交相机
        camera = new OrthographicCamera();
        // 3. 设置相机初始化位置
        camera.setToOrtho(false, Gdx.graphics.getWidth() / REDUCE, Gdx.graphics.getHeight() / REDUCE);

        // 4. 设置一个需要绘制的纹理
        dog = new TextureRegion(new Texture("badlogic.jpg"));
        // 5. 创建一个世界，里面的重力加速度为 10
        world = new World(new Vector2(0, -10), true);
        // 6. 创建一个底面
        createBow();
        //
    }

    public void createBow(){
        // 创建一个地面，其实是一个静态物体，这里我们叫它地面，玩家可以走在上面
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = 0;// 位置
        groundBodyDef.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBox.setAsBox(Gdx.graphics.getWidth()*1000, 0);// 物体的宽高
        groundBody.createFixture(groundBox, 0); // 静态物体的质量应该设为0
        // 因为调用的外部的c++代码需要手动释放内存
        groundBox.dispose();
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
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
    }
}
