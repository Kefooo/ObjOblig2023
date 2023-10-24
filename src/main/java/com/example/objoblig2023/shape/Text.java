package com.example.objoblig2023.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Text extends Shape {
    private final String text;

    public Text(double startX, double startY, Color fillColor, Color strokeColor, String text) {
        super("Text", startX, startY, fillColor, strokeColor);
        this.text = text;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(getStrokeColor());
        gc.setFill(getFillColor());
        gc.setLineWidth(5);
        gc.strokeText(text, getX(), getY());
        gc.fillText(text, getX(), getY());
    }

    @Override
    public boolean contains(double x, double y) {
        // Sjekk om punktet (x, y) er innenfor en viss avstand fra startpunktet til teksten
        double dx = x - getX();
        double dy = y - getY();
        return dx * dx + dy * dy <= 1000;
    }


    @Override
    public String toString() {
        return "Tekst: '" + text + getX() + ", " + getY() + ") med farge " + getStrokeColor();
    }
}