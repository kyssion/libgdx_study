package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class libgdx文字 extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //读取 .fnt 文件
        font = new BitmapFont(Gdx.files.internal("ziti.fnt"));
        //设置黑色
        font.setColor(Color.WHITE);
        //设置三倍大小
        font.getData().setScale(0.4f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        batch.begin();
        //\n是换行
        font.draw(batch, "hello world!\n你好世界!", 0, 40);
        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}

