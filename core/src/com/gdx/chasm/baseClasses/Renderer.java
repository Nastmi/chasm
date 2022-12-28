package com.gdx.chasm.baseClasses;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.chasm.entities.Checkpoint;
import com.gdx.chasm.entities.Entity;
import com.gdx.chasm.entities.MovableEntity;

import java.util.ArrayList;

public class Renderer {
    public static void render(SpriteBatch batch, ArrayList<Entity> entities, OrthographicCamera camera){
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for(Entity e:entities){
            if(e instanceof MovableEntity){
                MovableEntity me = (MovableEntity) e;
                batch.draw(me.getTexture(), (float) me.getPosition().getX(),(float) me.getPosition().getY(), (float)me.getWidth(), (float)me.getHeight());
            }
        }
        batch.end();
    }
}
