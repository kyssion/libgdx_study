package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ChangeDemo implements ApplicationListener {
    private static final float PXTM = 30;//每30个像素就是1米
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private World world;
    private Body body;

    @Override
    public void create() {
        //取得窗口宽高
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //camera宽高
        float cameraWidth = w / PXTM;
        float cameraHeight = h / PXTM;
        camera = new OrthographicCamera(cameraWidth, cameraHeight);
        renderer = new Box2DDebugRenderer();//渲染器
        world = new World(new Vector2(0f, 0f), true);//实例化一个世界
        BodyDef def = new BodyDef();
        def.type = BodyType.StaticBody;
        def.position.set(0f, 0f);//设置刚体的位置
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f);//凡是与物理世界相关的都是以米为单位
        FixtureDef def2 = new FixtureDef();
        def2.shape = shape;
        body.createFixture(def2);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        renderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
