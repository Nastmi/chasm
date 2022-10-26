package com.gdx.chasm.handlers;

import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.baseClasses.Vector2D;
import org.w3c.dom.css.Rect;

public class CollisionHandler {

    public static double rectangleRectangleCollision(Rectangle r1, Rectangle r2, int axis){
        double intersections = 0;
        Vector2D pos1 = r1.getPosition();
        Vector2D pos2 = r2.getPosition();
        if(axis == 0){
            if((pos1.y > pos2.y && pos1.y < pos2.y + r2.getHeight()) || (pos1.y + r1.getHeight() > pos2.y && pos1.y + r1.getHeight() < pos2.y + r2.getHeight())){
                if(pos1.x > pos2.x && pos1.x < pos2.x + r2.getWidth()){
                    System.out.println("xtrue");
                    intersections = pos2.x + r2.getWidth() - pos1.x;
                }
                else if(pos1.x + r1.getWidth() > pos2.x && pos1.x + r1.getWidth() < pos2.x + r2.getWidth()){
                    System.out.println("xtrue");
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
