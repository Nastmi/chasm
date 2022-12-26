package com.gdx.chasm.baseClasses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdx.chasm.entities.Entity;
import com.gdx.chasm.entities.MovableEntity;

import java.util.ArrayList;

public class DebugRenderer {

    public static void render(ShapeRenderer shapeRenderer, ArrayList<Entity> entities, OrthographicCamera camera, ArrayList<Rectangle> rest){
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for(Entity e:entities){
            if(e.getCollisionBox() instanceof Rectangle){
                Rectangle r = (Rectangle)e.getCollisionBox();
                shapeRenderer.rect((float) r.getPosition().getX(), (float)r.getPosition().getY(), (float)r.getWidth(), (float)r.getHeight());
            }
        }
        for(Rectangle r:rest){
            shapeRenderer.rect((float) r.getPosition().getX(), (float)r.getPosition().getY(), (float)r.getWidth(), (float)r.getHeight());
        }
        shapeRenderer.end();
    }

}
