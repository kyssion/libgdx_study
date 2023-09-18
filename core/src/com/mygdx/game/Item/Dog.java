package com.mygdx.game.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Text;

public class Dog {
    private int heigth;
    private int width;

    private Body body;
    private World world;
    private TextureRegion img;

    private float scaleX;
    private float scaleY;

    public Dog(int height , int width, World world, TextureRegion img) {
        super();
        this.heigth = height;
        this.width = width;
        this.world = world;
        this.img = img;
        scaleX = (float)this.width/(float)this.img.getRegionWidth();
        scaleY = (float)this.heigth/(float)this.img.getRegionHeight();

    }

    public Dog InitBody(float reduce){
        // 再添加一个动态物体，可以把他看成玩家
        BodyDef dogBodyDef = new BodyDef();
        dogBodyDef.type = BodyDef.BodyType.DynamicBody;
        dogBodyDef.position.x = 0;
        dogBodyDef.position.y = 15;
        Body dogBody = world.createBody(dogBodyDef);
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(this.width / 2.0f / reduce, this.heigth / 2.0f / reduce);

        // 给物体添加一些属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;// 形状
        fixtureDef.restitution = 0.2f; // 设置这个值后，物体掉落到地面就会弹起一点高度...
        fixtureDef.density = 1f;
        dogBody.createFixture(fixtureDef).setUserData(this);//设置自定义数据可以从这个物体获取这个数据对象
        this.body = dogBody;
        dynamicBox.dispose();
        return this;
    }

    public void Draw( SpriteBatch batch, float reduce){
        // 获取 物体的位置
        Vector2 position = this.body.getPosition();
        batch.draw(this.img, position.x - this.width/ 2.0f / reduce, position.y - this.heigth / 2.0f / reduce, // 设置位置 减少 50/2/reduce 是为了和物体的形状重合
                0, 0, this.width, this.heigth, // 绘制图片的一部分，这里就是全部了
                1.0f / reduce, 1.0f / reduce, // 缩小100倍0 // 不旋转
                (float) (this.body.getTransform().getRotation()*Math.PI/2.0*reduce)
        );
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}