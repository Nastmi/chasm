package com.gdx.chasm.entities;

import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.baseClasses.Vector2D;
import com.gdx.chasm.handlers.CollisionHandler;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Entity{

    private States state;

    public Player(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight);
        this.state = States.IDLE;
    }

    public Player(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight);
        this.state = States.IDLE;
    }


    public void setStates(HashMap<String, Boolean> held, HashMap<String, Boolean> single){
        if(held.get("left"))
            this.state = States.MOVE_LEFT;
        if(held.get("right"))
            this.state = States.MOVE_RIGHT;
        if(!held.get("left") && !held.get("right"))
            this.state = States.IDLE;
        if(single.get("jump")){
            this.state = States.JUMP_START;
            single.put("jump", false);
        }
    }

    @Override
    public void setSpeed(){
        if(this.state == States.IDLE)
            this.getVelocity().setX(0);
        else if(this.state == States.MOVE_LEFT){
            this.getVelocity().setX(-3);
        }
        else if(this.state == States.MOVE_RIGHT){
            this.getVelocity().setX(3);
        }
        if(this.state == States.JUMP_START){
            this.getVelocity().setY(10);
            this.state = States.IN_JUMP;
        }
        if(this.getVelocity().getY() > -7){
            this.getVelocity().addY(-0.5);
        }
    }

    public void update(float delta, ArrayList<Rectangle> collisions){
        move(delta, collisions);
    }

    public void move(float delta, ArrayList<Rectangle> collisions){
        moveX(delta, collisions);
        moveY(delta, collisions);
    }

    public void moveX(float delta, ArrayList<Rectangle> collisions){
        Vector2D newVel = new Vector2D(super.getVelocity());
        newVel.scale(delta);
        double newX = this.getCollisionBox().getPosition().getX() + newVel.getX();
        double intersect = 0;
        for(Rectangle r2:collisions){
            Rectangle newPosition = new Rectangle(this.getCollisionBox().getWidth(), this.getCollisionBox().getHeight(), new Vector2D(this.getCollisionBox().getPosition()));
            newPosition.getPosition().setX(newX);
            double tempIntersect = CollisionHandler.rectangleRectangleCollision(newPosition, r2, 0);
            if(Math.abs(tempIntersect) > Math.abs(intersect))
                intersect = tempIntersect;
        }
        this.getPosition().setX(this.getPosition().getX() + newVel.getX() + intersect);
        this.getCollisionBox().getPosition().setX(this.getCollisionBox().getPosition().getX() + newVel.getX() + intersect);
    }

    public void moveY(float delta, ArrayList<Rectangle> collisions){
        Vector2D newVel = new Vector2D(super.getVelocity());
        newVel.scale(delta);
        double newY = this.getCollisionBox().getPosition().getY() + newVel.getY();
        double intersect = 0;
        for(Rectangle r2:collisions){
            Rectangle newPosition = new Rectangle(this.getCollisionBox().getWidth(), this.getCollisionBox().getHeight(), new Vector2D(this.getCollisionBox().getPosition()));
            newPosition.getPosition().setY(newY);
            double tempIntersect = CollisionHandler.rectangleRectangleCollision(newPosition, r2, 1);
            if(Math.abs(tempIntersect) > Math.abs(intersect))
                intersect = tempIntersect;
        }
        if(Math.abs(intersect) > 0)
            this.getVelocity().setY(0);
        this.getPosition().setY(this.getPosition().getY() + newVel.getY() + intersect);
        this.getCollisionBox().getPosition().setY(this.getCollisionBox().getPosition().getY() + newVel.getY() + intersect);
    }


    public States getState(){
        return this.state;
    }

    enum States {
        IDLE,
        MOVE_LEFT,
        MOVE_RIGHT,
        IN_JUMP,
        JUMP_START,
        JUMP_HELD
    }

}
