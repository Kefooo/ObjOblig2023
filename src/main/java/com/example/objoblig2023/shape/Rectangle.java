package com.example.objoblig2023.shape;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double startX, double startY, double width, double height, Color strokeColor, Color fillColor) {
        super("Rectangle", startX, startY, strokeColor, fillColor); // Oppdater super() for Ã¥ inkludere "Rectangle"
        this.width = width;
        this.height = height;
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor());
        gc.setLineWidth(20);
        gc.fillRect(startX, startY, width, height);
    }
    @Override
    public boolean contains(double x, double y) {
        return x >= startX && x <= startX + width && y >= startY && y <= startY + height;
    }
}