package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public class DashRestore extends MovableEntity{
    public DashRestore(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture, TextureAtlas entityAtlas) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture, entityAtlas);
        super.setAnimation("dash", 0.33, 12, 12, false);
    }

    public DashRestore(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
    }

    @Override
    void createAnimations(TextureAtlas entityAtlas) {
        super.animations.put("dash", entityAtlas.findRegion("dash_restore"));
    }


    @Override
    void setSpeed(float delta, ArrayList<Entity> entities) {

    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e){
        if(e instanceof Player){
            this.isDead = true;
        }
    }
}
