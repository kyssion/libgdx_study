package com.mygdx.game.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Text;

public class ItemDog {
    private int heigth;
    private int width;
    private TextureRegion img;
    private float positionX;
    private float positionY;

    public ItemDog(int height , int width, float positionX , float positionY,  TextureRegion img) {
        super();
        this.heigth = height;
        this.width = width;
        this.img = img;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void Draw( SpriteBatch batch, float reduce){
        batch.draw(this.img, this.positionX, this.positionY,
                100/reduce/2,100/reduce/2,
                100/reduce,100/reduce,
                1/reduce,1/reduce,90); // 水平翻转和垂直翻转
        // 获取 物体的位置
        batch.draw(this.img, this.positionX , this.positionY, // 设置位置 减少 50/2/reduce 是为了和物体的形状重合
                this.width/reduce/2, this.heigth/reduce/2,
                this.width/reduce, this.heigth/reduce, // 绘制图片的一部分，这里就是全部了
                1.0f, 1.0f, // 缩小100倍0 // 不旋转
                0);
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

}
