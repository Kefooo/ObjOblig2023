package com.example.objoblig2023.controller;

import com.example.objoblig2023.shape.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.control.Button;

public class CanvasController {
    private boolean moveToFrontMode = false;
    private boolean moveToBackMode = false;
    private double startX, startY;
    private final Canvas canvas;
    private final Button moveToFrontButton;
    private final Button moveToBackButton;
    private final Label detailsLabel;
    private final ColorPicker fillColorPicker;
    private final ColorPicker strokeColorPicker;
    private final GraphicsContext gc;
    private final ChoiceBox<String> choiceBox;
    private Shape selectedShape = null;
    private LinkedList<Shape> shapes = new LinkedList<>();

    public CanvasController(Canvas canvas,
                            Button moveToFrontButton,
                            Button moveToBackButton,
                            Label detailsLabel,
                            ColorPicker strokeColor,
                            ColorPicker fillColor,
                            GraphicsContext gc,
                            ChoiceBox<String> choiceBox
                          ){
        this.canvas = canvas;
        this.moveToFrontButton = moveToFrontButton;
        this.moveToBackButton = moveToBackButton;
        this.detailsLabel = detailsLabel;
        this.strokeColorPicker = strokeColor;
        this.fillColorPicker = fillColor;
        this.gc = gc;
        this.choiceBox = choiceBox;

    }

    public EventHandler<MouseEvent> mouseClick(){
     EventHandler<MouseEvent> mouseClick = e -> {
         startX = e.getX();
         startY = e.getY();

         for (Shape shape : shapes) {
             if (shape.contains(startX, startY)) {
                 selectedShape = shape;
                 detailsLabel.setText(selectedShape.toString()); // Oppdaterer detaljene om den valgte figuren
                 break;
             }
         }
     };
     return mouseClick;
    }

    public EventHandler<MouseEvent> mouseDragged(){

        EventHandler<MouseEvent> mouseDragged = e -> {
        double endX = e.getX();
        double endY = e.getY();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(strokeColorPicker.getValue());
        gc.setFill(fillColorPicker.getValue());

        if (selectedShape != null) {
            selectedShape.move(endX - startX, endY - startY);
            startX = endX;
            startY = endY;
            detailsLabel.setText(selectedShape.toString());
        }

        for (Shape shape : shapes) {
            shape.draw(gc);
        }
      };
        return mouseDragged;
    }


    public EventHandler<MouseEvent> mouseReleased() {

        EventHandler<MouseEvent> mouseReleased = e -> {
            if (selectedShape == null) {
                double endX = e.getX();
                double endY = e.getY();

                switch (choiceBox.getValue()) {
                    case "Rett linje":
                        shapes.add(new Line(startX, startY, endX, endY, strokeColorPicker.getValue(), fillColorPicker.getValue()));
                        break;
                    case "Rektangel":
                        shapes.add(new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY), strokeColorPicker.getValue(), fillColorPicker.getValue()));
                        break;
                    case "Sirkel":
                        double radius = Math.sqrt(Math.pow(endX - startX, 1) + Math.pow(endY - startY, 1));
                        shapes.add(new Circle(startX - radius, startY - radius, radius * 2, strokeColorPicker.getValue(), fillColorPicker.getValue()));
                        break;
                    case "Tekst":
                        // Legg til en ny tekstfigur
                        shapes.add(new Text(startX, startY, strokeColorPicker.getValue(), fillColorPicker.getValue(), "Din tekst her"));
                        break;
                }
            }

            selectedShape = null; // Nullstill den valgte figuren etter at musen er sluppet

        };
        return mouseReleased;
    }
//    public Button moveToFront(){
//        moveToFrontButton.setOnMousePressed(e -> {
//            if (selectedShape != null) {
//                Shape getLastShape = shapes.getLast();
//                shapes.remove(getLastShape);
//                shapes.addFirst(selectedShape); // Flytt figuren til slutten av listen
//                dragtoFrontShape();
//            }
//        });
//        return moveToFrontButton;
//    }
//
//    public Button moveToBack(){
//        moveToBackButton.setOnMousePressed(e -> {
//            if (selectedShape != null) {
//                Shape firstShape = shapes.getFirst();
//                shapes.remove(firstShape);
//                shapes.addLast(selectedShape); // Flytt figuren til begynnelsen av listen
//                dragtoBackShape();
//            }
//        });
//        return moveToBackButton;
//    }
public Button moveToFront(){
    moveToFrontButton.setOnMousePressed(e -> {
        moveToFrontMode = true;
        moveToBackMode = false;
    });
    return moveToFrontButton;
}

    public Button moveToBack(){
        moveToBackButton.setOnMousePressed(e -> {
            moveToBackMode = true;
            moveToFrontMode = false;
        });
        return moveToBackButton;
    }

    public EventHandler<MouseEvent> dragShape() {
        AtomicBoolean moveOne = new AtomicBoolean(false);
        EventHandler<MouseEvent> mouseReleased = e -> {
            if(moveToBackMode) {
//                if (e.isAltDown() && e.getButton() == MouseButton.PRIMARY) {
//                    moveSelectedShapeOneStepBackward();
//                    moveOne.set(true);
//
//                } else if (e.isAltDown() && e.getButton() == MouseButton.SECONDARY && moveToFrontMode &&!moveToBackMode) {
//                    moveSelectedShapeOneStepForward();
//                    moveOne.set(true);
//
//                }

                    shapes.sort(Comparator.comparing(Shape::getLastMoved).reversed());

                } else{
                    shapes.sort(Comparator.comparing(Shape::getLastMoved));


            }

            for (Shape shape : shapes) {
                shape.draw(gc);
            }
            System.out.println(moveOne.get());
        };
            return mouseReleased;

    }
    private void moveSelectedShapeOneStepBackward() {
        if (selectedShape != null) {
            int index = shapes.indexOf(selectedShape);
            if (index > 0) {
                // Swap the selectedShape with the shape before it
                Shape shapeBefore = shapes.get(index - 1);
                shapes.set(index - 1, selectedShape);
                shapes.set(index, shapeBefore);
                selectedShape.setLastMoved(System.currentTimeMillis()); // Update lastMoved time
            }
        }

    }
    private void moveSelectedShapeOneStepForward() {
        if (selectedShape != null) {
            int index = shapes.indexOf(selectedShape);
            if (index < shapes.size() - 1) {
                // Swap the selectedShape with the shape after it
                Shape shapeAfter = shapes.get(index + 1);
                shapes.set(index + 1, selectedShape);
                shapes.set(index, shapeAfter);
                selectedShape.setLastMoved(System.currentTimeMillis()); // Update lastMoved time
            }
        }

    }
}
