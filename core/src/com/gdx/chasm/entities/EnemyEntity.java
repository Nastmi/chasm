package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.chasm.baseClasses.Vector2D;

public abstract class EnemyEntity extends MovableEntity{

    public EnemyEntity(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture);
    }

    public EnemyEntity(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
    }
}
