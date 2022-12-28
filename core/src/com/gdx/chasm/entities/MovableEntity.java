package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public abstract class MovableEntity extends Entity {

    private double width;
    private double height;
    private Vector2D position;
    private Vector2D velocity;
    public boolean isDead;

    Texture texture;

    public MovableEntity(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture) {
        super(colX, colY, collisionWidth, collisionHeight);
        this.width = width;
        this.height = height;
        this.position = new Vector2D(x, y);
        this.velocity = new Vector2D(0, 0);
        this.isDead = false;
        this.texture = texture;
    }

    public MovableEntity(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(collisionPosition, collisionWidth, collisionHeight);
        this.width = width;
        this.height = height;
        this.position = position;
        this.texture = texture;
    }

    public void moveX(float delta){
        Vector2D newVel = new Vector2D(getVelocity());
        newVel.scale(delta);
        this.getPosition().setX(this.getPosition().getX() + newVel.getX());
        this.getCollisionBox().getPosition().setX(this.getCollisionBox().getPosition().getX() + newVel.getX());
    }

    public void moveY(float delta){
        Vector2D newVel = new Vector2D(getVelocity());
        newVel.scale(delta);
        this.getPosition().setY(this.getPosition().getY() + newVel.getY());
        this.getCollisionBox().getPosition().setY(this.getCollisionBox().getPosition().getY() + newVel.getY());
    }

    public void update(float delta, ArrayList<Entity> entities){
        setSpeed(delta, entities);
    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e){
        if(e instanceof CollisionEntity){
            this.setPosition(new Vector2D(this.getPosition().getX()+intersectX, this.getPosition().getY()+intersectY));
            this.getCollisionBox().setPosition(new Vector2D(this.getCollisionBox().getPosition().getX()+intersectX, this.getCollisionBox().getPosition().getY()+intersectY));
        }
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
    abstract void setSpeed(float delta, ArrayList<Entity> entities);

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

    public boolean isDead() {
        return isDead;
    }

    public Texture getTexture() {
        return texture;
    }

}

