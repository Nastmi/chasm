package com.gdx.chasm.baseClasses;

public abstract class CollidableObject {

    private Vector2D position;

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public CollidableObject(double x, double y) {
        this.position = new Vector2D(x, y);
    }

    public CollidableObject(Vector2D v) {
        this.position = v;
    }

    public CollidableObject(CollidableObject r2){
        this.position = r2.position;
    }

    public Vector2D getPosition() {
        return position;
    }

}
