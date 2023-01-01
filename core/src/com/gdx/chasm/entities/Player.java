package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.chasm.baseClasses.Vector2D;
import com.gdx.chasm.states.ExplodingEnemyStates;
import com.gdx.chasm.states.PlayerStates;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends MovableEntity {

    private PlayerStates state;
    private double dashTime;
    private final double defaultDashTime = 0.1;
    private int[] lastDir;
    private boolean jumpAllowed;
    private boolean dashAllowed;
    int checkpointNumber;

    public Player(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture, TextureAtlas entityAtlas) {
        super(width, height, x, y, colX, colY, collisionWidth, collisionHeight, texture, entityAtlas);
        this.state = PlayerStates.IDLE;
        this.dashTime = defaultDashTime;
        this.lastDir = new int[]{0, 0};
        this.jumpAllowed = true;
        this.dashAllowed = true;
        this.checkpointNumber = 0;
        setAnimation("idle", 1, 12, 12, false);
    }

    public Player(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(width, height, position, collisionPosition, collisionWidth, collisionHeight, texture);
        this.state = PlayerStates.IDLE;
    }

    @Override
    void createAnimations(TextureAtlas entityAtlas) {
        super.animations.put("idle", entityAtlas.findRegion("player_idle"));
        super.animations.put("run", entityAtlas.findRegion("player_run"));
        super.animations.put("jump", entityAtlas.findRegion("player_jump"));
        super.animations.put("fall", entityAtlas.findRegion("player_fall"));
    }

    public void handleStates(HashMap<String, Boolean> held, HashMap<String, Boolean> single){
        switch(this.state){
            case IDLE:
                if(single.get("jump") && this.jumpAllowed){
                    this.state = PlayerStates.JUMP_START;
                    single.put("jump", false);
                }
                else if(held.get("left") && !held.get("right")){
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, true);
                    else
                        setAnimation("run", 0.075, 12, 12, true);
                    this.state = PlayerStates.MOVE_LEFT;
                }
                else if(held.get("right") && !held.get("left")){
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, false);
                    else
                        setAnimation("run", 0.075, 12, 12, false);
                    this.state = PlayerStates.MOVE_RIGHT;
                }
                if(single.get("dash") && this.dashAllowed){
                    this.state = PlayerStates.DASH_START;
                    this.dashAllowed = false;
                    single.put("dash", false);
                }
                break;
            case JUMP_START:
                this.setAnimation("jump", 1, 12, 12, lastDir[0] < 0);
                this.jumpAllowed = false;
                this.state = PlayerStates.IN_JUMP;
                break;
            case MOVE_LEFT:
                if(!held.get("left")){
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, lastDir[0] < 0);
                    else
                        setAnimation("idle", 1, 12, 12, lastDir[0] < 0);
                    this.state = PlayerStates.IDLE;
                }
                if(single.get("jump") && this.jumpAllowed){
                    this.state = PlayerStates.JUMP_START;
                    single.put("jump", false);
                }
                if(single.get("dash") && this.dashAllowed){
                    this.state = PlayerStates.DASH_START;
                    this.dashAllowed = false;
                    single.put("dash", false);
                }
                break;
            case MOVE_RIGHT:
                if(!held.get("right")){
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, lastDir[0] < 0);
                    else
                        setAnimation("idle", 1, 12, 12, lastDir[0] < 0);
                    this.state = PlayerStates.IDLE;
                }
                if(single.get("jump") && this.jumpAllowed){
                    this.state = PlayerStates.JUMP_START;
                    single.put("jump", false);
                }
                if(single.get("dash") && this.dashAllowed){
                    this.state = PlayerStates.DASH_START;
                    this.dashAllowed = false;
                    single.put("dash", false);
                }
                break;
            case JUMP_LEFT:
                if(!held.get("left") && this.getVelocity().getY() == 0){
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, lastDir[0] < 0);
                    else
                        setAnimation("idle", 1, 12, 12, lastDir[0] < 0);
                    this.state = PlayerStates.IDLE;
                }
                else if(!held.get("left") && this.getVelocity().getY() > 0)
                    this.state = PlayerStates.IN_JUMP;
            case JUMP_RIGHT:
                if(!held.get("right") && this.getVelocity().getY() == 0){
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, lastDir[0] < 0);
                    else
                        setAnimation("idle", 1, 12, 12, lastDir[0] < 0);
                    this.state = PlayerStates.IDLE;
                }
                else if(!held.get("right") && this.getVelocity().getY() > 0)
                    this.state = PlayerStates.IN_JUMP;
            case IN_JUMP:
                if(this.getVelocity().getY() == 0){
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, lastDir[0] < 0);
                    else
                        setAnimation("idle", 1, 12, 12, lastDir[0] < 0);
                    this.state = PlayerStates.IDLE;
                }
                else if(held.get("left")){
                    this.state = PlayerStates.JUMP_LEFT;
                    this.setAnimation("jump", 1, 12, 12, true);
                }
                else if(held.get("right")){
                    this.state = PlayerStates.JUMP_RIGHT;
                    this.setAnimation("jump", 1, 12, 12, false);
                }
                if(single.get("dash") && this.dashAllowed){
                    this.state = PlayerStates.DASH_START;
                    this.dashAllowed = false;
                    single.put("dash", false);
                }
                break;
            case DASH_START:
                this.state = PlayerStates.IN_DASH;
                break;
            case IN_DASH:
                if(dashTime <= 0){
                    dashTime = defaultDashTime;
                    this.state = PlayerStates.IDLE;
                    if(this.getVelocity().getY() < 0)
                        setAnimation("fall", 1, 12, 12, lastDir[0] < 0);
                    else
                        setAnimation("idle", 1, 12, 12, lastDir[0] < 0);
                }
                break;
        }
        if(held.get("left") || held.get("right") || held.get("down") || held.get("up")){
            this.lastDir = new int[]{0, 0};
            if(held.get("left"))
                this.lastDir[0] = -1;
            else if(held.get("right"))
                this.lastDir[0] = 1;
            if(held.get("up"))
                this.lastDir[1] = 1;
            else if(held.get("down"))
                this.lastDir[1] = -1;
        }
        if(!this.jumpAllowed)
            single.put("jump", false);
        if(!this.dashAllowed)
            single.put("dash", false);
    }


    @Override
    public void setSpeed(float delta, ArrayList<Entity> entities){
        if(this.state == PlayerStates.IDLE || this.state == PlayerStates.IN_JUMP)
            this.getVelocity().setX(0);
        else if(this.state == PlayerStates.MOVE_LEFT || this.state == PlayerStates.JUMP_LEFT){
            this.getVelocity().setX(-6);
        }
        else if(this.state == PlayerStates.MOVE_RIGHT || this.state == PlayerStates.JUMP_RIGHT){
            this.getVelocity().setX(6);
        }
        if(this.state == PlayerStates.JUMP_START){
            this.getVelocity().setY(10);
        }
        if(this.getVelocity().getY() >= -7){
            this.getVelocity().addY(-0.5);
        }
        if(this.state == PlayerStates.DASH_START){
            this.setVelocity(new Vector2D(20*this.lastDir[0], 20*this.lastDir[1]));
        }
        if(this.state == PlayerStates.IN_DASH){
            dashTime -= delta;
            if(dashTime <= 0)
                this.getVelocity().set(0, 0);
        }
    }

    public void update(float delta, HashMap<String, Boolean> held, HashMap<String, Boolean> single, ArrayList<Entity> entities){
        super.setCurTexture(curAnimation.getKeyFrame((float)super.getAnimationTime(), true));
        super.setAnimationTime(super.getAnimationTime() + delta);
        handleStates(held, single);
        setSpeed(delta, entities);
    }

    public void moveX(float delta){
        Vector2D newVel = new Vector2D(super.getVelocity());
        newVel.scale(delta);
        this.getPosition().setX(this.getPosition().getX() + newVel.getX());
        this.getCollisionBox().getPosition().setX(this.getCollisionBox().getPosition().getX() + newVel.getX());
    }

    public void moveY(float delta){
        Vector2D newVel = new Vector2D(super.getVelocity());
        newVel.scale(delta);
        this.getPosition().setY(this.getPosition().getY() + newVel.getY());
        this.getCollisionBox().getPosition().setY(this.getCollisionBox().getPosition().getY() + newVel.getY());
    }

    @Override
    public void handleCollision(double intersectX, double intersectY, Entity e){
        if(e instanceof ExplodingEnemy){
            if(Math.abs(intersectX) > 0){
                ExplodingEnemy ee = (ExplodingEnemy) e;
                if(ee.getState() == ExplodingEnemyStates.IN_CHASE || ee.getState() == ExplodingEnemyStates.EXPLODING){
                    this.isDead = true;
                }
            }
        }
        else if(e instanceof Checkpoint){
            Checkpoint c = (Checkpoint) e;
            this.checkpointNumber = c.getNumber();
        }
        else if(e instanceof  DashRestore){
            dashAllowed = true;
        }
        else if(e instanceof MovableEntity){
            this.isDead = true;
        }
        else{
            this.setPosition(new Vector2D(this.getPosition().getX()+intersectX, this.getPosition().getY()+intersectY));
            this.getCollisionBox().setPosition(new Vector2D(this.getCollisionBox().getPosition().getX()+intersectX, this.getCollisionBox().getPosition().getY()+intersectY));
            if(intersectY > 0){
                this.dashAllowed = true;
                this.jumpAllowed = true;
                this.getVelocity().setY(0);
            }
        }
    }

    public PlayerStates getState(){
        return this.state;
    }

    public double getDashTime() {
        return dashTime;
    }

    public double getDefaultDashTime() {
        return defaultDashTime;
    }

    public int[] getLastDir() {
        return lastDir;
    }

    public boolean isJumpAllowed() {
        return jumpAllowed;
    }

    public boolean isDashAllowed() {
        return dashAllowed;
    }

    public int getCheckpointNumber() {
        return checkpointNumber;
    }

}
