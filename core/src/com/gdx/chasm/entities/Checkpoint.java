package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public class Checkpoint extends MovableEntity{
    int number;

    public Checkpoint(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture, int number) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture);
        this.number = number;
    }

    public Checkpoint(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture, int number) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
        this.number = number;
    }


    @Override
    void setSpeed(float delta, ArrayList<Entity> entities) {

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
