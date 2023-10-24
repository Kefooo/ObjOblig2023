package com.example.objoblig2023.shape;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    private final double radius;

    public Circle(double startX, double startY, double radius,  Color strokeColor, Color fillColor) {
        super("Circle", startX, startY, strokeColor, fillColor); // Oppdater super() for Ã¥ inkludere "Circle"
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor()); // Sett strekfargen
        gc.setLineWidth(10);
        gc.fillOval(startX - radius, startY - radius,  radius, radius);
        gc.setLineWidth(10);
        gc.strokeOval(startX - radius, startY - radius, 2 * radius, 2 * radius); // Tegn kantlinjen
    }


    @Override
    public boolean contains(double x, double y) {
        double dx = x - getX();
        double dy = y - getY();
        return dx * dx + dy * dy <= radius * radius;
    }
}