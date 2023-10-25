package com.example.objoblig2023.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected double startX, startY;
    protected Color fillColor;
    protected Color strokeColor;
    protected String shape; // Legg til denne linjen

    protected long lastMoved; // Legg til denne linjen

    public Shape(String shape, double startX, double startY, Color fillColor, Color strokeColor) {
        this.shape = shape;
        this.startX = startX;
        this.startY = startY;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.lastMoved = System.currentTimeMillis();
    }

    public void setFillColor(Color color){
        this.fillColor = color;
    }

    public void setStrokeColor(Color color){
        this.strokeColor = color;
    }

    public long getLastMoved() {
        return lastMoved;
    }

    public void setLastMoved(Long millis){
        this.lastMoved = millis;
    }

    public double getX() {
        return startX;
    }

    public double getY() {
        return startY;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }
    public abstract void draw(GraphicsContext gc);

    public abstract boolean contains(double x, double y);

    public void move(double dx, double dy) {
        startX += dx;
        startY += dy;
        lastMoved = System.currentTimeMillis(); // Oppdater når figuren sist ble flyttet
    }

    public String getShape(){
        return shape;
    }
    public String toString() {
        return "Shape: " + shape + " at (" + String.format("%.2f", startX) + ", " + String.format("%.2f", startY) + ") with color " + strokeColor.toString();
    }
}