package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public class Spikes extends EnemyEntity{

    boolean small;
    public Spikes(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture, TextureAtlas entityAtlas, boolean small) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture, entityAtlas);
        this.small = small;
        if(small)
            super.setAnimation("small", 1, 12, 12, false);
        else
            super.setAnimation("big", 1, 12, 12, false);
    }

    public Spikes(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture, boolean small) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
        this.small = small;
    }

    @Override
    void createAnimations(TextureAtlas entityAtlas) {
        super.animations.put("small", entityAtlas.findRegion("spikes_small"));
        super.animations.put("big", entityAtlas.findRegion("spikes_big"));
    }

    @Override
    void setSpeed(float delta, ArrayList<Entity> entities) {

    }
}
