package com.example.objoblig2023.controller;

import com.example.objoblig2023.shape.*;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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
    private final TextArea textArea;
    private final Button stop;
    private Shape selectedShape = null;
    private LinkedList<Shape> shapes = new LinkedList<>();
    private boolean isMoveToFrontMode;
    private boolean isMoveToBackMode;
    String savedText = "";
    public CanvasController(Canvas canvas,
                            Button moveToFrontButton,
                            Button moveToBackButton,
                            Button stop,
                            Label detailsLabel,
                            ColorPicker strokeColor,
                            ColorPicker fillColor,
                            GraphicsContext gc,
                            ChoiceBox<String> choiceBox,
                            TextArea textArea
                          ){
        this.canvas = canvas;
        this.moveToFrontButton = moveToFrontButton;
        this.moveToBackButton = moveToBackButton;
        this.detailsLabel = detailsLabel;
        this.strokeColorPicker = strokeColor;
        this.fillColorPicker = fillColor;
        this.gc = gc;
        this.choiceBox = choiceBox;
        this.textArea = textArea;
        this.stop = stop;
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
                        shapes.add(new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY), fillColorPicker.getValue(),strokeColorPicker.getValue()));
                        break;
                    case "Sirkel":
                        double radius = Math.sqrt(Math.pow(endX - startX, 1) + Math.pow(endY - startY, 1));
                        shapes.add(new Circle(startX - radius, startY - radius, radius * 2, strokeColorPicker.getValue(), fillColorPicker.getValue()));
                        break;
                    case "Tekst":
                        // Legg til en ny tekstfigur

                        String text = createText(startX, startY , endX, endY );
                        shapes.add(new Text(startX, startY, strokeColorPicker.getValue(), fillColorPicker.getValue(), text));
                        break;
                }
            }

            selectedShape = null; // Nullstill den valgte figuren etter at musen er sluppet

        };

        return mouseReleased;
    }

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

    public Button stop(){
        stop.setOnMouseClicked(e ->{
            moveToBackMode = false;
            moveToFrontMode = false;
        });
        return stop;
    }

    public EventHandler<MouseEvent> clickShape() {

            EventHandler<MouseEvent> changeColor = e -> {
                if(!moveToBackMode && !moveToFrontMode){
                    shapes.stream()
                            .filter(shape -> shape.contains(e.getX(), e.getY()))
                            .forEach(shape -> {
                                shape.setStrokeColor(strokeColorPicker.getValue());
                                shape.setFillColor(fillColorPicker.getValue());
                            });
                }else if(moveToBackMode && !moveToFrontMode && e.isAltDown()){
                    List<Shape> shapesCopy = new ArrayList<>(shapes);
                    shapesCopy.stream()
                            .filter(s -> s.contains(e.getX(), e.getY()))
                            .forEach(s -> {
                                // Move the clicked shape to the back
                                shapes.remove(s);
                                shapes.add(0, s);
                            });
                }
            };

            return changeColor;

    }

//    public EventHandler<MouseEvent> dragShape() {
//
//        EventHandler<MouseEvent> mouseReleased = e -> {
//            if(moveToBackMode) {
//                shapes.sort(Comparator.comparing(Shape::getLastMoved).reversed());
//            }else{
//                shapes.sort(Comparator.comparing(Shape::getLastMoved));
//            }
//            for (Shape shape : shapes) {
//                shape.draw(gc);
//            }
//        };
//
//        return mouseReleased;
//    }
public EventHandler<MouseEvent> dragShape() {
    EventHandler<MouseEvent> mouseReleased = e -> {
        if (moveToBackMode) {
            // Sort the shapes list based on the lastMoved property in descending order
            shapes.sort(Comparator.comparing(Shape::getLastMoved).reversed());
        } else {
            // Sort the shapes list based on the lastMoved property in ascending order
            shapes.sort(Comparator.comparing(Shape::getLastMoved));
        }

        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    };
    return mouseReleased;
}
    public void handleShapeClickAndMove(MouseEvent event) {
        // Check if a shape is clicked and move it one step behind
        shapes.stream()
                .filter(shape -> shape.contains(event.getX(), event.getY()))
                .findFirst() // Find the first matching shape
                .ifPresent(shape -> {
                    if (moveToBackMode) {
                        moveShapeOneStepBack(shape);
                    } else if (moveToFrontMode) {
                        moveShapeOneStepForward(shape);
                    } else {
                        shape.setStrokeColor(strokeColorPicker.getValue());
                        shape.setFillColor(fillColorPicker.getValue());
                    }
                });
        // Call the dragShape method to reorganize the shapes as needed
        dragShape().handle(event);
    }
    public void moveShapeOneStepBack(Shape shape) {
        if (shape != null) {
            shape.setLastMoved(System.currentTimeMillis()); // Update the lastMoved timestamp
            // Optionally, you can change other properties, such as the order in the list
            shapes.remove(shape);
            shapes.add(0, shape); // Add the shape to the front of the list
        }
    }
    public void moveShapeOneStepForward(Shape shape) {
        if (shape != null) {
            shape.setLastMoved(System.currentTimeMillis()); // Update the lastMoved timestamp
            int currentIndex = shapes.indexOf(shape);
            if (currentIndex < shapes.size() - 1) {
                // Swap the shape with the one ahead of it
                Shape nextShape = shapes.get(currentIndex + 1);
                shapes.set(currentIndex, nextShape);
                shapes.set(currentIndex + 1, shape);
            }
        }
    }

    public String createText(double startX, double startY, double endX, double endY){

            javafx.scene.shape.Rectangle dashedBorder = new javafx.scene.shape.Rectangle(startX,startY, endX,  endY);
            dashedBorder.setStroke(Color.BLACK);
            dashedBorder.setStrokeDashOffset(2.0);
            dashedBorder.setStrokeWidth(2.0);
            dashedBorder.setFill(null);
            dashedBorder.setVisible(true);
            // textArea.setText();
            textArea.setLayoutX(20);
            textArea.setLayoutY(20);

            textArea.setPrefWidth(endX - startX);
            textArea.setPrefWidth(endY - endY);

            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            savedText = textArea.getText();
        });
        return savedText;
    }

}
