package com.example.objoblig2023.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text extends Shape {
    private final String text;
    private final int fontSize;
    public Text(double startX, double startY, Color fillColor, Color strokeColor, String text, int fontSize) {
        super("Text", startX, startY, fillColor, strokeColor);
        this.text = text;
        this.fontSize = fontSize;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(getStrokeColor());
        gc.setFill(getFillColor());
        gc.setLineWidth(5);
        gc.setFont(new Font("Arial", fontSize));
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