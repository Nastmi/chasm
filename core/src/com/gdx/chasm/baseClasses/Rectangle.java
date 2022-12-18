package com.gdx.chasm.baseClasses;

public class Rectangle extends CollidableObject{

    private double width;
    private double height;

    public Rectangle(double width, double height, double x, double y) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public Rectangle(double width, double height, Vector2D v) {
        super(v);
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle r2){
        super(r2.getPosition());
        this.width = r2.width;
        this.height = r2.height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }



}
