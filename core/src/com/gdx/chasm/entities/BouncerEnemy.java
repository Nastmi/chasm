package com.gdx.chasm.entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;

public class BouncerEnemy extends EnemyEntity{

    private Vector2D defaultSpeed = new Vector2D(3, 0);
    private double stopTime = 0;
    private double maxTime;

    public BouncerEnemy(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, double maxTime, Texture texture, TextureAtlas entityAtlas) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture, entityAtlas);
        this.maxTime = maxTime;
        super.setAnimation("move", 0.15, 18, 18, false);
    }

    public BouncerEnemy(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, double maxTime, Texture texture) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
        this.maxTime = maxTime;
    }

    @Override
    public void setSpeed(float delta, ArrayList<Entity> entities) {
        if(stopTime <= 0){
            this.setVelocity(new Vector2D(defaultSpeed));
        }
        else{
            this.setVelocity(0, 0);
            stopTime -= delta;
        }
    }

    @Override
    void createAnimations(TextureAtlas entityAtlas) {
        super.animations.put("move", entityAtlas.findRegion("enemy_star"));
    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e){
        if(!(e instanceof MovableEntity)){
            this.setPosition(new Vector2D(this.getPosition().getX()+intersectX, this.getPosition().getY()+intersectY));
            this.getCollisionBox().setPosition(new Vector2D(this.getCollisionBox().getPosition().getX()+intersectX, this.getCollisionBox().getPosition().getY()+intersectY));
            this.stopTime = this.maxTime;
            defaultSpeed.negate();
        }
    }

}
