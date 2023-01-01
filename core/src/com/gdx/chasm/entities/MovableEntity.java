package com.gdx.chasm.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.chasm.baseClasses.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MovableEntity extends Entity {

    private double width;
    private double height;
    private Vector2D position;
    private Vector2D velocity;
    public boolean isDead;

    private double animationTime;
    Texture texture;
    Animation<TextureRegion> curAnimation;
    HashMap<String, TextureRegion> animations;
    TextureRegion curTexture;


    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Animation getCurAnimation() {
        return curAnimation;
    }

    public HashMap<String, TextureRegion> getAnimations() {
        return animations;
    }

    public void setAnimations(HashMap<String, TextureRegion> animations) {
        this.animations = animations;
    }

    public MovableEntity(double width, double height, double x, double y, double colX, double colY, double collisionWidth, double collisionHeight, Texture texture, TextureAtlas entityAtlas) {
        super(colX, colY, collisionWidth, collisionHeight);
        this.width = width;
        this.height = height;
        this.position = new Vector2D(x, y);
        this.velocity = new Vector2D(0, 0);
        this.isDead = false;
        this.texture = texture;
        this.animationTime = 0;
        this.animations = new HashMap<>();
        createAnimations(entityAtlas);
    }

    public MovableEntity(double width, double height, Vector2D position, Vector2D collisionPosition, double collisionWidth, double collisionHeight, Texture texture) {
        super(collisionPosition, collisionWidth, collisionHeight);
        this.width = width;
        this.height = height;
        this.position = position;
        this.texture = texture;
    }

    abstract void createAnimations(TextureAtlas entityAtlas);

    public void setAnimation(String name, double duration, int w, int h, boolean flip){
        TextureRegion orig = animations.get(name);
        int c = orig.getRegionWidth()/w;
        int r = orig.getRegionHeight()/h;
        TextureRegion[][] splt = orig.split(w, h);
        TextureRegion[] anim = new TextureRegion[c*r];
        int idx = 0;
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                anim[idx] = splt[i][j];
                if(flip)
                    anim[idx].flip(true, false);
                idx++;
            }
        }
        curAnimation = new Animation<TextureRegion>((float) duration, anim);
        setAnimationTime(0);
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
        setCurTexture(curAnimation.getKeyFrame((float)getAnimationTime(), true));
        setAnimationTime(getAnimationTime() + delta);
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

    public double getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(double animationTime) {
        this.animationTime = animationTime;
    }

    public void setCurAnimation(Animation<TextureRegion> curAnimation) {
        this.curAnimation = curAnimation;
    }

    public TextureRegion getCurTexture() {
        return curTexture;
    }

    public void setCurTexture(TextureRegion curTexture) {
        this.curTexture = curTexture;
    }
}

