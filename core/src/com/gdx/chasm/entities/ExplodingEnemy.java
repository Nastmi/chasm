package com.gdx.chasm.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.baseClasses.Vector2D;
import com.gdx.chasm.states.ExplodingEnemyStates;

import java.util.ArrayList;

public class ExplodingEnemy extends StateEnemy{

    private ExplodingEnemyStates state;
    private double explodeTimer = 3;
    private double lifeTime = 1;

    public ExplodingEnemy(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture);
        this.state = ExplodingEnemyStates.LOOKOUT;
    }

    public ExplodingEnemy(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
        this.state = ExplodingEnemyStates.LOOKOUT;
    }

    @Override
    void handleStates() {
        System.out.println(this.state);
        if(this.state == ExplodingEnemyStates.LOOKOUT){
            this.setCollisionBox(new Rectangle(this.getWidth()+10, this.getHeight()+10, this.getPosition().getX()-5, this.getPosition().getY()-5));
        }
        if(this.state == ExplodingEnemyStates.IN_CHASE){
            this.setCollisionBox(new Rectangle(this.getWidth(), this.getHeight(), this.getPosition().getX(), this.getPosition().getY()));
        }
        if(this.state == ExplodingEnemyStates.EXPLODING){

        }
        System.out.println(lifeTime);
        if(this.lifeTime <= 0)
            this.isDead = true;
    }

    @Override
    void setSpeed(float delta, ArrayList<Entity> entities){
        if(this.state == ExplodingEnemyStates.IN_CHASE){
            double playerX = this.getPosition().getX();
            double playerY = this.getPosition().getY();
            for(Entity e:entities){
                if(e instanceof Player){
                    playerX = ((Player) e).getPosition().getX();
                    playerY = ((Player) e).getPosition().getY();
                }
            }
            Vector2D dir = new Vector2D(playerX - this.getPosition().getX(), playerY - this.getPosition().getY());
            dir = dir.norm();
            dir.scale(3);
            this.setVelocity(dir);
            this.explodeTimer -= delta;
        }
        if(explodeTimer <= 0 && this.state == ExplodingEnemyStates.IN_CHASE){
            this.state = ExplodingEnemyStates.EXPLODING;
            this.setCollisionBox(new Rectangle(this.getWidth()+2, this.getHeight()+2, this.getPosition().getX()-1, this.getPosition().getY()-1));
            this.texture = new Texture(Gdx.files.internal("boom.png"));
            this.setWidth(this.getWidth()+2);
            this.setHeight(this.getHeight()+2);
            this.setPosition(new Vector2D(this.getPosition().getX()-1, this.getPosition().getY()-1));
        }
        if(this.state == ExplodingEnemyStates.EXPLODING){
            this.setVelocity(new Vector2D(0,0));
            this.lifeTime -= delta;
        }
    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e){
        if(e instanceof Player && this.state == ExplodingEnemyStates.LOOKOUT){
            this.state = ExplodingEnemyStates.IN_CHASE;
        }
        else if(this.state == ExplodingEnemyStates.IN_CHASE){
            this.explodeTimer = 0;
        }
    }


    public ExplodingEnemyStates getState() {
        return state;
    }
}
