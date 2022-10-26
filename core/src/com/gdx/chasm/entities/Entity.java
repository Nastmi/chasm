package com.gdx.chasm.entities;

import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.baseClasses.Vector2D;

public abstract class Entity {

    private double width;
    private double height;
    private Vector2D position;
    private Vector2D velocity;
    private Rectangle rectangle;


    public Entity(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight) {
        this.width = width;
        this.height = height;
        this.position = new Vector2D(x, y);
        this.rectangle = new Rectangle(collisionWidth, collisionHeight, colX, colY);
        this.velocity = new Vector2D(0, 0);
    }

    public Entity(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.rectangle = new Rectangle(collisionWidth, collisionHeight, collisionPosition);
    }

    public void move(float delta){
        Vector2D newVel = new Vector2D(this.velocity);
        newVel.scale(delta);
        this.position.add(newVel);
        this.getCollisionBox().getPosition().add(newVel);
    }

    abstract void setSpeed();

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Rectangle getCollisionBox() {
        return rectangle;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double x, double y){
        this.velocity.set(x, y);
    }

    public void setCollisionBox(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
