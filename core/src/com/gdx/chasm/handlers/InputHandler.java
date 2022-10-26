package com.gdx.chasm.handlers;

import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;

public class InputHandler implements InputProcessor {

    public  static HashMap<String, Boolean> held;
    public  static HashMap<String, Boolean> single;

    public static HashMap<String, Integer> bindings;

    public InputHandler(){
        held = new HashMap<>();
        held.put("left", false);
        held.put("right", false);
        held.put("jump", false);
        single = new HashMap<>();
        single.put("left", false);
        single.put("right", false);
        single.put("jump", false);
        bindings = new HashMap<>();
        bindings.put("left", Keys.LEFT);
        bindings.put("right", Keys.RIGHT);
        bindings.put("jump", Keys.X);
    }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == bindings.get("left")){
            single.put("left", true);
            held.put("left", true);
        }
        else if(keycode == bindings.get("right")){
            single.put("right", true);
            held.put("right", true);
        }
        else if(keycode == bindings.get("jump")){
            single.put("jump", true);
            held.put("jump", true);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == bindings.get("left"))
            held.put("left", false);
        else if(keycode == bindings.get("right"))
            held.put("right", false);
        else if(keycode == bindings.get("jump"))
            held.put("jump", false);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
