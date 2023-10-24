package com.example.objoblig2023;

import com.example.objoblig2023.controller.CanvasController;
import com.example.objoblig2023.shape.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


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

        ColorPicker fillColorPicker = new ColorPicker(Color.BLACK);
        ColorPicker strokeColorPicker = new ColorPicker(Color.TRANSPARENT);

        Label detailsLabel = new Label();
        TextArea textArea = new TextArea();
        Button moveToFrontButton = new Button("Flytt til forgrunnen");
        Button moveToBackButton = new Button("Flytt til bakgrunnen");
        Button stop = new Button("Ikke flytt");

        CanvasController controller = new CanvasController(
                canvas,
                moveToFrontButton,
                moveToBackButton,
                stop,
                detailsLabel,
                strokeColorPicker,
                fillColorPicker,
                gc,
                choiceBox,
                textArea
        );

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, controller.mouseClick());
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller.mouseDragged());
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, controller.mouseReleased());
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, controller::handleShapeClickAndMove);
        //canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, controller.clickShape());
        VBox controls = new VBox(10);
        moveToBackButton = controller.moveToBack();
        moveToFrontButton = controller.moveToFront();
        stop = controller.stop();
        controls.getChildren().addAll(choiceBox, fillColorPicker, strokeColorPicker, detailsLabel, moveToFrontButton, moveToBackButton, stop, textArea);
        BorderPane layout = new BorderPane();
        layout.setRight(controls); // Flytter kontrollene til høyre side
        layout.setCenter(canvas); // Setter tegneflaten i midten

        Scene scene = new Scene(layout, 1200, 600); // Juster scenestørrelsen etter behov
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}