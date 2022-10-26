package com.gdx.chasm.baseClasses;

public class Rectangle {

    private double width;
    private double height;
    private Vector2D position;

    public Rectangle(double width, double height, double x, double y) {
        this.width = width;
        this.height = height;
        this.position = new Vector2D(x, y);
    }

    public Rectangle(double width, double height, Vector2D v) {
        this.width = width;
        this.height = height;
        this.position = v;
    }

    public Rectangle(Rectangle r2){
        this.width = r2.width;
        this.height = r2.height;
        this.position = r2.position;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Vector2D getPosition() {
        return position;
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


}
