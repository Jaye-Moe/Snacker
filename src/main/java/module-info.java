module com.example.snacker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.snacker to javafx.fxml;
    exports com.snacker;
}