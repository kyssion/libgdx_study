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
public class TestBox2dV3 extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion img, dog;
    private OrthographicCamera camera;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body dogBody;
    // 在正常像素下物体重力现象不明显，需要对纹理进行缩小100++倍才有比较明显的物理效果
    private float reduce = 100;// 缩小100 倍易于观察到物理现象
    private boolean isJump;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / reduce, Gdx.graphics.getHeight() / reduce);

        // 图片一： 50*50 缩小100倍就是 0.5*0.5 在绘制时缩小的
        dog = new TextureRegion(new Texture("badlogic.jpg"), 24, 24);
        // 创建一个世界，里面的重力加速度为 10
        world = new World(new Vector2(0, -10), true);
        // 试调渲染，可以使用这个渲染观察到我们用Box2D绘制的物体图形
        debugRenderer = new Box2DDebugRenderer();

        // 创建一个地面，其实是一个静态物体，这里我们叫它地面，玩家可以走在上面
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = 0;// 位置
        groundBodyDef.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBox.setAsBox(Gdx.graphics.getWidth(), 0);// 物体的宽高
        groundBody.createFixture(groundBox, 0); // 静态物体的质量应该设为0

        // 再添加一个动态物体，可以把他看成玩家
        BodyDef dogBodyDef = new BodyDef();
        dogBodyDef.type = BodyDef.BodyType.DynamicBody;
        dogBodyDef.position.x = 0;
        dogBodyDef.position.y = 10;
        dogBody = world.createBody(dogBodyDef);
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(24 / 2 / reduce, 24 / 2 / reduce);

        // 给物体添加一些属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;// 形状
        fixtureDef.restitution = 0.2f; // 设置这个值后，物体掉落到地面就会弹起一点高度...
        dogBody.createFixture(fixtureDef).setUserData(this);//设置自定义数据可以从这个物体获取这个数据对象

        // 上面的图形要处理掉
        groundBox.dispose();
        dynamicBox.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 获取 物体的位置
        Vector2 position = dogBody.getPosition();
        // 将相机与批处理精灵绑定
        camera.position.set(0,2, 0);
        camera.update();

        // 将绘制与相机投影绑定 关键 关键
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(dog, position.x - 24 / 2 / reduce, position.y - 24 / 2 / reduce, // 设置位置 减少 50/2/reduce 是为了和物体的形状重合
                0, 0, 24, 24, // 绘制图片的一部分，这里就是全部了
                1 / reduce, 1 / reduce, // 缩小100倍
                0 // 不旋转
        );
        batch.end();

        // 给Box2D世界里的物体绘制轮廓，让我们看得更清楚，正式游戏需要注释掉这个渲染
        debugRenderer.render(world, camera.combined);

        // 更新世界里的关系 这个要放在绘制之后，最好放最后面
        world.step(1 / 60f, 6, 2);
    }
}
