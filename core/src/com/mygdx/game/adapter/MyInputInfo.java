package com.mygdx.game.adapter;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MyInputInfo implements InputProcessor {
    public Vector3 tp = new Vector3();
    boolean dragging;
    Camera camera;

    public MyInputInfo(Camera camera){
        super();
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown:"+keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp:"+keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped:"+character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 item = this.camera.unproject(this.tp.set(screenX,screenY,0));
        System.out.println("touchDown: screenX "+screenX +" screenY "+screenY+" pointer "+pointer+" button "+button +"v3x:"+item.x+"v3y:"+item.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp: screenX "+screenX +" screenY "+screenY+" pointer "+pointer+" button "+button);
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchCancelled: screenX "+screenX +" screenY "+screenY+" pointer "+pointer+" button "+button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged: screenX "+screenX +" screenY "+screenY+" pointer "+pointer);
        Vector3 item = this.camera.unproject(this.tp.set(screenX,screenY,0));

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
//        System.out.println("mouse moved : "+screenX+" | "+screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
