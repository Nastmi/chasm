package com.gdx.chasm.entities;

import com.gdx.chasm.baseClasses.CollidableObject;
import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.baseClasses.Vector2D;

public abstract class Entity {
    private CollidableObject collisionBox;

    public Entity(double colX, double colY, double collisionWidth, double collisionHeight) {
        this.collisionBox = new Rectangle(collisionWidth, collisionHeight, colX, colY);
    }

    public Entity(Vector2D collisionPosition, double collisionWidth, double collisionHeight) {
        this.collisionBox = new Rectangle(collisionWidth, collisionHeight, collisionPosition);
    }

    public abstract void handleCollision(double intersectX, double intersectY, Entity e);

    public CollidableObject getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle rectangle) {
        this.collisionBox = rectangle;
    }
}
