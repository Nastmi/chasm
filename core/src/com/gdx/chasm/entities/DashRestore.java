package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public class DashRestore extends MovableEntity{
    public DashRestore(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture);
    }

    public DashRestore(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
    }

    @Override
    void setSpeed(float delta, ArrayList<Entity> entities) {

    }
}
