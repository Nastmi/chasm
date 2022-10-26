package com.gdx.chasm.baseClasses;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v2){
        this.x = v2.x;
        this.y = v2.y;
    }

    public void add(Vector2D v2){
        this.x += v2.x;
        this.y += v2.y;
    }

    public void add(double x, double y){
        this.x += x;
        this.y += y;
    }

    public void set(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void scale(double s){
        this.x *= s;
        this.y *= s;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void scaleX(double s){
        this.x *= s;
    }

    public void scaleY(double s){
        this.y *= s;
    }

    public void addX(double s){
        this.x += s;
    }

    public void addY(double s){
        this.y += s;
    }

    public String toString(){
        return "x:"+x+"y:"+y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
