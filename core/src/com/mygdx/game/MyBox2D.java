package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * @author lingkang
 */
public class MyBox2D extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion img, dog;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body dogBody;
    private boolean isJump;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        dog = new TextureRegion(new Texture("badlogic.jpg"), 50, 50);
        world = new World(new Vector2(0, -999999999), true);
        debugRenderer = new Box2DDebugRenderer();

        BodyDef dogBodyDef = new BodyDef();
        dogBodyDef.type = BodyDef.BodyType.DynamicBody;
        dogBodyDef.position.x = 0;
        dogBodyDef.position.y = 1000;
        dogBody = world.createBody(dogBodyDef);
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox((float) 25 , (float) 25 );
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.restitution = 0.2f;
        dogBody.createFixture(fixtureDef).setUserData(this);
        dynamicBox.dispose();


        // 创建一个地面，其实是一个静态物体，这里我们叫它地面，玩家可以走在上面
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = 0;// 位置
        groundBodyDef.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBox.setAsBox(Gdx.graphics.getWidth(), 10);// 物体的宽高
        groundBody.createFixture(groundBox, 0); // 静态物体的质量应该设为0


        BodyDef groundBodyDefLeft = new BodyDef();
        groundBodyDefLeft.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDefLeft.position.x = -600;// 位置
        groundBodyDefLeft.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBodyLeft = world.createBody(groundBodyDefLeft);
        PolygonShape groundBoxLeft = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBoxLeft.setAsBox(10, Gdx.graphics.getHeight());// 物体的宽高
        groundBodyLeft.createFixture(groundBoxLeft, 0); // 静态物体的质量应该设为0

        BodyDef groundBodyDefRight = new BodyDef();
        groundBodyDefRight.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDefRight.position.x = 600;// 位置
        groundBodyDefRight.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBodyRight = world.createBody(groundBodyDefRight);
        PolygonShape groundBoxRight = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBoxRight.setAsBox(10, Gdx.graphics.getHeight());// 物体的宽高
        groundBodyRight.createFixture(groundBoxRight, 0); // 静态物体的质量应该设为0
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Vector2 position = dogBody.getPosition();
        camera.position.set(0, 300, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(dog, position.x-25 , position.y-25 );
        batch.end();
        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);
    }
}
