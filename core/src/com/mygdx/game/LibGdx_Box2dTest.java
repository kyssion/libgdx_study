package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class LibGdx_Box2dTest implements ApplicationListener {

    protected OrthographicCamera camera;
    protected Box2DDebugRenderer renderer; // 测试用绘制器
    private World world;
    private float scale = 10f; // 屏幕的缩放比例，10像素/米
    private long start = 0;
    private Body body;
    private boolean isFinished = true;
    private int count = 0;
    private float totalTime = 0;

    @Override
    public void create() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        camera = new OrthographicCamera(200/scale, 200/scale);// 这里为了展示物理世界，把视野也转换成m
        camera.position.set(100/scale, 100/scale, 0);  // 摄像机的postion是画面的中心点
        renderer = new Box2DDebugRenderer();

        world = new World(new Vector2(0, -9.8f), true); // 一般标准重力场
        BodyDef bd = new BodyDef(); //声明物体定义
        bd.position.set(10, 20);
        bd.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bd); //通过world创建一个物体
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);   // 注意单位是边长的一半
        FixtureDef fd = new FixtureDef();
        fd.shape = box;
        fd.friction = 0;
        fd.restitution = 0;
        fd.density = 1;
        body.createFixture(fd); //将形状和密度赋给物体
        body.setLinearDamping(0f);  // 没有线性阻尼
        body.setAngularDamping(0f); // 没有旋转阻尼
        body.setAwake(false);
    }

    @Override
    public void render() {
        world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        if(start == 0) {
            count = 1;
            start = System.currentTimeMillis();
            System.out.println("start postion：" + body.getPosition().y);
            body.setAwake(true);    // 让渲染的第一帧再让物体运动，这样时间获得才准确，
            // 否则第一个deltaTime有初始化时间，会让整个计算不准
        } else {
            if(isFinished){
                System.out.println("动画渲染次数： " + count++);
                System.out.println("物体移动速度: " + body.getLinearVelocity().y);
                System.out.println("---------------------------------");
                totalTime += Gdx.app.getGraphics().getDeltaTime();
            }
            if(body.getPosition().y <= 0 && isFinished) {
                System.out.println("实际时间差：" + (System.currentTimeMillis() - start)/1000f);
                System.out.println("图像增量统计时间差：" + totalTime);
                System.out.println("end postion：" + body.getPosition().y);
                isFinished = false;
                world.destroyBody(body);
            }
        }
        GL20 gl = Gdx.app.getGraphics().getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();

        renderer = null;
        world = null;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {
    }
}

