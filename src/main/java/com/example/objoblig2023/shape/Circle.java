package com.example.objoblig2023.shape;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    private final double radius;

    public Circle(double startX, double startY, double radius,  Color strokeColor, Color fillColor) {
        super("Circle", startX, startY, fillColor, strokeColor); // Oppdater super() for Ã¥ inkludere "Circle"
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor());
        gc.setLineWidth(1);
        gc.fillOval(startX, startY, radius, radius);
        gc.strokeOval(startX, startY, radius, radius);

    }


    @Override
    public boolean contains(double x, double y) {
        double dx = x - getX();
        double dy = y - getY();
        return dx * dx + dy * dy <= radius * radius;
    }
}