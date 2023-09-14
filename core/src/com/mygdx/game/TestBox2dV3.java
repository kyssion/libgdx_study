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
        // 创建一个相机， 这里缩小64倍，因为要观察的物体需要缩小100倍
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / reduce, Gdx.graphics.getHeight() / reduce);

        // 图片一： 50*50 缩小100倍就是 0.5*0.5 在绘制时缩小的
        dog = new TextureRegion(new Texture("badlogic.jpg"), 24, 24);

        // 创建一个世界，里面的重力加速度为 10
        world = new World(new Vector2(0, -10), false);
        // 试调渲染，可以使用这个渲染观察到我们用Box2D绘制的物体图形
        debugRenderer = new Box2DDebugRenderer();

        // 创建一个地面，其实是一个静态物体，这里我们叫它地面，玩家可以走在上面
        BodyDef 地面定义 = new BodyDef();
        地面定义.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        地面定义.position.x = 0;// 位置
        地面定义.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(地面定义);
        PolygonShape 地面轮廓 = new PolygonShape();// 物体的形状，这样创建是矩形的
        地面轮廓.setAsBox(Gdx.graphics.getWidth(), 1);// 物体的宽高
        groundBody.createFixture(地面轮廓, 0); // 静态物体的质量应该设为0

        dogBody = CreateNewItem(0);
        CreateNewItem(1);
        CreateNewItem(2);

    }

    public Body CreateNewItem(int v) {
        // 再添加一个动态物体，可以把他看成玩家
        BodyDef 用户物体定义 = new BodyDef();
        用户物体定义.type = BodyDef.BodyType.DynamicBody;
        用户物体定义.position.x = v/reduce*5;
        用户物体定义.position.y = 8+v;
        Body bodyItem = world.createBody(用户物体定义);
        PolygonShape 用户物体轮廓 = new PolygonShape();
        用户物体轮廓.setAsBox((float)24 / 2 / reduce, (float) 24 / 2 / reduce);

        // 给物体添加一些属性
        FixtureDef 用户物体工具 = new FixtureDef();
        用户物体工具.shape = 用户物体轮廓;// 形状
        用户物体工具.restitution = 0.5f; // 设置这个值后，物体掉落到地面就会弹起一点高度...
        用户物体工具.density = 0.3f;
        bodyItem.createFixture(用户物体工具).setUserData(this);//设置自定义数据可以从这个物体获取这个数据对象
        用户物体轮廓.dispose();
        return bodyItem;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        System.out.println(dogBody.getTransform().getRotation());
        // 获取 物体的位置
        Vector2 position = dogBody.getPosition();

        // 将相机与批处理精灵绑定
        camera.position.set(0, 5, 0);
        camera.update();
        // 将绘制与相机投影绑定 关键 关键
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(dog, position.x - 24 / 2 / reduce, position.y - 24 / 2 / reduce, // 设置位置 减少 50/2/reduce 是为了和物体的形状重合
                0, 0, 24, 24, // 绘制图片的一部分，这里就是全部了
                1 / reduce, 1 / reduce, // 缩小100倍
                dogBody.getTransform().getRotation() // 不旋转
        );
        batch.end();

        // 获取五星的线速度
        Vector2 linearVelocity = dogBody.getLinearVelocity();
        if (Gdx.input.isKeyPressed(Input.Keys.D) && linearVelocity.x <= 2) { // 现在最大速度为 2，不然会放飞自我
            // 施加冲动 让物体运行起来，可以看成我们推一下物体就往一边移动了
            dogBody.applyLinearImpulse(new Vector2(0.1f, 0), dogBody.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && linearVelocity.x >= -2) {
            dogBody.applyLinearImpulse(new Vector2(-0.1f, 0), dogBody.getWorldCenter(), true);
        }

        // 跳起来的逻辑，比较简单。但是时候这个演示
        if (!isJump && Gdx.input.isKeyPressed(Input.Keys.W) && linearVelocity.y <= 4) {
            dogBody.applyLinearImpulse(new Vector2(0, 4), dogBody.getWorldCenter(), true);
            isJump = true;
        }
        if (linearVelocity.y == 0) {
            isJump = false;
        }

        // 给Box2D世界里的物体绘制轮廓，让我们看得更清楚，正式游戏需要注释掉这个渲染
        debugRenderer.render(world, camera.combined);

        // 更新世界里的关系 这个要放在绘制之后，最好放最后面
        world.step(1 / 60f, 6, 2);
    }
}