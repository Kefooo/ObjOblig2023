module com.example.objoblig2023 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.objoblig2023 to javafx.fxml;
    exports com.example.objoblig2023;
}