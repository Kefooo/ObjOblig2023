package com.example.objoblig2023;

import com.example.objoblig2023.controller.CanvasController;
import com.example.objoblig2023.shape.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Comparator;
import java.util.LinkedList;


public class Tegneprogram extends Application {
    private Shape selectedShape = null;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("JavaFX Tegneflate");

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Rett linje", "Rektangel", "Sirkel", "Tekst");
        choiceBox.setValue("Rett linje");

        ColorPicker strokeColorPicker = new ColorPicker(Color.BLACK);
        ColorPicker fillColorPicker = new ColorPicker(Color.TRANSPARENT);

        Label detailsLabel = new Label();

        Button moveToFrontButton = new Button("Flytt til forgrunnen");
        Button moveToBackButton = new Button("Flytt til bakgrunnen");

        CanvasController controller = new CanvasController(
                canvas,
                moveToFrontButton,
                moveToBackButton,
                detailsLabel,
                strokeColorPicker,
                fillColorPicker,
                gc,
                choiceBox

        );

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, controller.mouseClick());
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller.mouseDragged());
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, controller.mouseReleased());
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, controller.dragShape());
        VBox controls = new VBox(10);
        moveToBackButton = controller.moveToBack();
        moveToFrontButton = controller.moveToFront();
        controls.getChildren().addAll(choiceBox, strokeColorPicker, fillColorPicker, detailsLabel, moveToFrontButton, moveToBackButton);
        BorderPane layout = new BorderPane();
        layout.setRight(controls); // Flytter kontrollene til høyre side
        layout.setCenter(canvas); // Setter tegneflaten i midten

        Scene scene = new Scene(layout, 1200, 600); // Juster scenestørrelsen etter behov
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}