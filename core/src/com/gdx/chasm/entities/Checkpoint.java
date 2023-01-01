package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public class Checkpoint extends MovableEntity{
    int number;
    boolean collected;

    public Checkpoint(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture, int number, TextureAtlas entityAtlas) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture, entityAtlas);
        this.number = number;
        super.setAnimation("red", 0.25, 12, 12, false);
        this.collected = false;
    }

    public Checkpoint(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture, int number) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
        this.number = number;
    }


    @Override
    void createAnimations(TextureAtlas entityAtlas) {
        super.animations.put("red", entityAtlas.findRegion("checkpoint_red"));
        super.animations.put("green", entityAtlas.findRegion("checkpoint_green"));
        super.animations.put("transition", entityAtlas.findRegion("checkpoint_transition"));
    }

    @Override
    void setSpeed(float delta, ArrayList<Entity> entities) {

    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e){
        if(e instanceof Player && !collected){
            this.collected = true;
            super.setAnimation("transition", 0.05, 12, 12, false);
        }
    }

    @Override
    public void update(float delta, ArrayList<Entity> entities){
        setCurTexture(curAnimation.getKeyFrame((float)getAnimationTime(), false));
        setAnimationTime(getAnimationTime() + delta);
        setSpeed(delta, entities);
    }

    public int getNumber() {
        return number;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
