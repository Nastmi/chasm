package com.gdx.chasm.handlers;

import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.baseClasses.Vector2D;
import com.gdx.chasm.entities.Entity;
import com.gdx.chasm.entities.ExplodingEnemy;
import com.gdx.chasm.entities.MovableEntity;
import com.gdx.chasm.entities.Player;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public class CollisionHandler {

    public static void handleCollisions(float delta, ArrayList<Entity> collisions, int axis){
        for(Entity e1:collisions){
            for(Entity e2:collisions){
                if(e1 != e2){
                    double intersect = 0;
                    double intersectX = 0;
                    double intersectY = 0;
                    if(e1.getCollisionBox() instanceof Rectangle && e2.getCollisionBox() instanceof Rectangle){
                        intersect = rectangleRectangleCollision((Rectangle)e1.getCollisionBox(), (Rectangle)e2.getCollisionBox(), axis);
                        if(intersect == 0)
                            intersect = rectangleRectangleCollision((Rectangle)e2.getCollisionBox(), (Rectangle)e1.getCollisionBox(), axis);
                    }
                    if(Math.abs(intersect) > 0){
                        if(axis == 0)
                            intersectX = intersect;
                        else if(axis == 1)
                            intersectY = intersect;
                        e1.handleCollision(intersectX, intersectY, e2);
                    }
                }
            }
        }
    }
    public static double rectangleRectangleCollision(Rectangle r1, Rectangle r2, int axis){
        double intersections = 0;
        Vector2D pos1 = r1.getPosition();
        Vector2D pos2 = r2.getPosition();
        if(axis == 0){
            if((pos1.y > pos2.y && pos1.y < pos2.y + r2.getHeight()) || (pos1.y + r1.getHeight() > pos2.y && pos1.y + r1.getHeight() < pos2.y + r2.getHeight())){
                if(pos1.x > pos2.x && pos1.x < pos2.x + r2.getWidth()){
                    intersections = pos2.x + r2.getWidth() - pos1.x;
                }
                else if(pos1.x + r1.getWidth() > pos2.x && pos1.x + r1.getWidth() < pos2.x + r2.getWidth()){
                    intersections = pos2.x - (pos1.x + r1.getWidth());
                }
            }
        }
        else if(axis == 1){
            if((pos1.x > pos2.x && pos1.x < pos2.x + r2.getWidth()) || (pos1.x + r1.getWidth() > pos2.x && pos1.x + r1.getWidth() < pos2.x + r2.getWidth())){
                if(pos1.y > pos2.y && pos1.y < pos2.y + r2.getHeight()){
                    intersections = pos2.y + r2.getHeight() - pos1.y;
                }
                else if(pos1.y + r1.getHeight() > pos2.y && pos1.y + r1.getHeight() < pos2.y + r2.getHeight()){
                    intersections = pos2.y - (pos1.y + r1.getHeight());
                }
            }
        }
        return intersections;
    }

}

/*Vector2D newVel = new Vector2D(super.getVelocity());
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
 */