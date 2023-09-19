package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class libgdx_触摸鼠标 extends ApplicationAdapter implements InputProcessor {
    // we will use 32px/unit in world
    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f/SCALE;
    // this is our "target" resolution, note that the window can be any size, it is not bound to this one
    public final static float VP_WIDTH = 1280 * INV_SCALE;
    public final static float VP_HEIGHT = 720 * INV_SCALE;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private SpriteBatch batch;

    private TextureRegion dog;
    @Override public void create () {
        camera = new OrthographicCamera();
        // pick a viewport that suits your thing, ExtendViewport is a good start
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
        // ShapeRenderer so we can see our touch point
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        dog = new TextureRegion(new Texture("badlogic.jpg"));

    }

    @Override public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(dog,tp.x, tp.y, 1f, 1f);
        batch.end();
    }

    Vector3 tp = new Vector3();
    boolean dragging;
    @Override public boolean mouseMoved (int screenX, int screenY) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        System.out.println("33333");
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        System.out.println("1111");

        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = true;
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer) {
        System.out.println("22222");

        if (!dragging) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        System.out.println("fsdafsfsad");
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = false;
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public void resize (int width, int height) {
        // viewport must be updated for it to work properly
        viewport.update(width, height, true);
    }

    @Override public void dispose () {
        // disposable stuff must be disposed
        batch.dispose();
    }

    @Override public boolean keyDown (int keycode) {
        return false;
    }

    @Override public boolean keyUp (int keycode) {
        return false;
    }

    @Override public boolean keyTyped (char character) {
        return false;
    }

}
