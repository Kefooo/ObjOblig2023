package com.example.objoblig2023.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Text extends Shape {
    private final String text;

    public Text(double startX, double startY, Color strokeColor, Color fillColor, String text) {
        super("Text", startX, startY, strokeColor,fillColor); // Oppdater super() for 책 inkludere "Text"
        this.text = text;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(getStrokeColor());
        gc.strokeText(text, getX(), getY());
    }

    @Override
    public boolean contains(double x, double y) {
        // Sjekk om punktet (x, y) er innenfor en viss avstand fra startpunktet til teksten
        double dx = x - getX();
        double dy = y - getY();
        return dx * dx + dy * dy <= 1000; // Endre dette tallet for 책 justere "valgomr책det" for teksten
    }


    @Override
    public String toString() {
        return "Tekst: '" + text + "' p책 (" + getX() + ", " + getY() + ") med farge " + getStrokeColor();
    }
}