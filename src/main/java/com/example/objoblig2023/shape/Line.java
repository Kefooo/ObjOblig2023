package com.example.objoblig2023.shape;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
    private double endX;
    private double endY;

    public Line(double startX, double startY, double endX, double endY, Color strokeColor, Color fillColor) {
        super("Line", startX, startY, strokeColor, fillColor); // Oppdater super() for å inkludere "Line"
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.beginPath();
        gc.setStroke(getStrokeColor());
        gc.setFill(getFillColor());
        gc.strokeLine(getX(), getY(), endX, endY);
        gc.closePath();
    }
    @Override
    public boolean contains(double x, double y) {
        // Beregn avstanden fra punktet (x, y) til linjen
        double lineLength = Math.sqrt(Math.pow(endX - getX(), 2) + Math.pow(endY - getY(), 2));
        double distanceToStart = Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
        double distanceToEnd = Math.sqrt(Math.pow(x - endX, 2) + Math.pow(y - endY, 2));

        // Hvis summen av avstandene er lik lengden på linjen (med en liten feiltoleranse), er punktet på linjen
        return Math.abs(distanceToStart + distanceToEnd - lineLength) < 0.5;
    }

    @Override
    public void move(double dx, double dy) {
        super.move(dx, dy);
        endX += dx;
        endY += dy;
    }
}