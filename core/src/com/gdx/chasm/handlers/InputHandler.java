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
        single = new HashMap<>();
        bindings = new HashMap<>();
        bindings.put("left", Keys.LEFT);
        bindings.put("right", Keys.RIGHT);
        bindings.put("up", Keys.UP);
        bindings.put("down", Keys.DOWN);
        bindings.put("jump", Keys.X);
        bindings.put("dash", Keys.C);
        for(String s:bindings.keySet()){
            single.put(s, false);
            held.put(s, false);
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        for(String s:single.keySet()){
            if(keycode == bindings.get(s)){
                single.put(s, true);
                held.put(s, true);
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for(String s:single.keySet()){
            if(keycode == bindings.get(s)){
                held.put(s, false);
            }
        }
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
