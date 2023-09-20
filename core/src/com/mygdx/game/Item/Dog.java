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
    private float positionX;
    private float positionY;

    public Dog(int height , int width, float positionX , float positionY, World world, TextureRegion img) {
        super();
        this.heigth = height;
        this.width = width;
        this.world = world;
        this.img = img;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Dog InitBody(float reduce){
        // 再添加一个动态物体，可以把他看成玩家
        BodyDef dogBodyDef = new BodyDef();
        dogBodyDef.type = BodyDef.BodyType.DynamicBody;
        dogBodyDef.position.x = this.positionX;
        dogBodyDef.position.y = this.positionY;
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
        batch.draw(this.img, position.x - this.width / reduce/2, position.y - this.heigth  / reduce/2, // 设置位置 减少 50/2/reduce 是为了和物体的形状重合
                this.width/reduce/2, this.heigth/reduce/2, this.width/reduce, this.heigth/reduce, // 绘制图片的一部分，这里就是全部了
                1.0f , 1.0f , // 缩小100倍0 // 不旋转
                (float) (this.body.getTransform().getRotation()*180/Math.PI));
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
