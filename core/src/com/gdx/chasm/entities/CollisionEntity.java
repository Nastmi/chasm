package com.gdx.chasm.entities;

public class CollisionEntity extends Entity{

    public CollisionEntity(double colX, double colY, double collisionWidth, double collisionHeight) {
        super(colX, colY, collisionWidth, collisionHeight);
    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e) {

    }

}
