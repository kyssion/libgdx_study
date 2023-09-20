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

    private World world;
    private Box2DDebugRenderer debugRenderer;
    // 在正常像素下物体重力现象不明显，需要对纹理进行缩小100++倍才有比较明显的物理效果
    private float reduce = 2;//
    List<Dog> dogList;
    private BitmapFont font;

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
        world = new World(new Vector2(0, -10), true);
        // 试调渲染，可以使用这个渲染观察到我们用Box2D绘制的物体图形
        debugRenderer = new Box2DDebugRenderer();

        // 创建一个地面，其实是一个静态物体，这里我们叫它地面，玩家可以走在上面
        createBow();
        createLeft((int) (Gdx.graphics.getWidth()*0.4));
        createRight((int) (Gdx.graphics.getWidth()*0.4));
        dogList = new ArrayList<>();
        //读取 .fnt 文件
        font = new BitmapFont(Gdx.files.internal("ziti.fnt"));
        //设置黑色
        font.setColor(Color.WHITE);
        //设置三倍大小
        font.getData().setScale(1/reduce);
        font.setUseIntegerPositions(false);
        this.boxNum = 0;
        this.InputInfo = new MyInputInfo(camera);
        Gdx.input.setInputProcessor(this.InputInfo);
    }
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int fps =  Gdx.graphics.getFramesPerSecond();
        if (fps>=30 &&  Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            dogList.add(new Dog((int) (100/reduce), (int) (100/reduce), this.InputInfo.tp.x,  this.InputInfo.tp.y,world,dog).InitBody(reduce));
            boxNum++;
        }

        // 将相机与批处理精灵绑定
        camera.position.set(0, (float) ((float) Gdx.graphics.getHeight()*0.4/reduce), 0);
        camera.update();

        // 将绘制与相机投影绑定 关键 关键
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "boxNum : "+boxNum +"\n fps : "+ Gdx.graphics.getFramesPerSecond(), 0, 0/reduce);
        for (Dog dog : dogList){
            dog.Draw(batch,reduce);
        }

        batch.end();

        // 给Box2D世界里的物体绘制轮廓，让我们看得更清楚，正式游戏需要注释掉这个渲染
        debugRenderer.render(world, camera.combined);

        // 更新世界里的关系 这个要放在绘制之后，最好放最后面
        world.step(1 / 120f, 6, 2);
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
        this.world.dispose();
        this.batch.dispose();
        this.debugRenderer.dispose();
        this.font.dispose();
    }

    public void createRight(int path){
        // 创建一个地面，其实是一个静态物体，这里我们叫它地面，玩家可以走在上面
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = path/reduce;// 位置
        groundBodyDef.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBox.setAsBox(0, Gdx.graphics.getHeight());// 物体的宽高
        groundBody.createFixture(groundBox, 0); // 静态物体的质量应该设为0
        // 上面的图形要处理掉
        groundBox.dispose();
    }
    public void createLeft(int path){
        // 创建一个地面，其实是一个静态物体，这里我们叫它地面，玩家可以走在上面
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = -path/reduce;// 位置
        groundBodyDef.position.y = 0;
        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBox.setAsBox(0, Gdx.graphics.getHeight());// 物体的宽高
        groundBody.createFixture(groundBox, 0); // 静态物体的质量应该设为0
        // 上面的图形要处理掉
        groundBox.dispose();
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
        groundBox.setAsBox(Gdx.graphics.getWidth(), 0);// 物体的宽高
        groundBody.createFixture(groundBox, 0); // 静态物体的质量应该设为0
        // 上面的图形要处理掉
        groundBox.dispose();
    }

}
