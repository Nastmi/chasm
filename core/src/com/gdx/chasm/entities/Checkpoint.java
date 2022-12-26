package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;

public class Checkpoint extends CollisionEntity{

    Texture texture;
    int number;
    public Checkpoint(double colX, double colY, double collisionWidth, double collisionHeight, Texture texture, int number) {
        super(colX, colY, collisionWidth, collisionHeight);
        this.texture = texture;
        this.number = number;
    }


    public Texture getTexture() {
        return texture;
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
