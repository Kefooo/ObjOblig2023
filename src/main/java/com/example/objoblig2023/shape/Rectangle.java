package com.example.objoblig2023.shape;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double startX, double startY, double width, double height, Color fillColor, Color strokeColor) {
        super("Rectangle", startX, startY, fillColor, strokeColor);
        this.width = width;
        this.height = height;
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor());
        gc.setLineWidth(1);
        gc.fillRect(startX, startY, width, height);
        gc.strokeRect(startX, startY, width, height);
    }
    @Override
    public boolean contains(double x, double y) {
        return x >= startX && x <= startX + width && y >= startY && y <= startY + height;
    }
}