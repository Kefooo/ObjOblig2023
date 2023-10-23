package com.example.objoblig2023.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected double startX, startY;
    protected Color strokeColor;
    protected Color fillColor;
    protected String shape; // Legg til denne linjen

    protected long lastMoved; // Legg til denne linjen

    public Shape(String shape, double startX, double startY, Color strokeColor, Color fillColor) {
        this.shape = shape;
        this.startX = startX;
        this.startY = startY;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.lastMoved = System.currentTimeMillis();
    }

    public long getLastMoved() {
        return lastMoved;
    }

    public double getX() {
        return startX;
    }

    public double getY() {
        return startY;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }
    public Color getFillColor() {
        return strokeColor;
    }

    public abstract void draw(GraphicsContext gc);

    public abstract boolean contains(double x, double y);

    public void move(double dx, double dy) {
        startX += dx;
        startY += dy;
        lastMoved = System.currentTimeMillis(); // Oppdater nÃ¥r figuren sist ble flyttet
    }


    public String toString() {
        return "Shape: " + shape + " at (" + String.format("%.2f", startX) + ", " + String.format("%.2f", startY) + ") with color " + strokeColor.toString();
    }
}