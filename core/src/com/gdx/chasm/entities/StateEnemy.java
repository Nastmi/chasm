package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public abstract class StateEnemy extends MovableEntity{
    public StateEnemy(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture);
    }

    public StateEnemy(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
    }

    abstract void handleStates();
    @Override
    void setSpeed(float delta, ArrayList<Entity> entities) {

    }

    @Override
    public void update(float delta, ArrayList<Entity> entities){
        handleStates();
        setSpeed(delta, entities);
    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e){
        if(!(e instanceof Player)){
            this.setPosition(new Vector2D(this.getPosition().getX()+intersectX, this.getPosition().getY()+intersectY));
            this.getCollisionBox().setPosition(new Vector2D(this.getCollisionBox().getPosition().getX()+intersectX, this.getCollisionBox().getPosition().getY()+intersectY));
        }
    }
}
