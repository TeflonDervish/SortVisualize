module com.example.sortvisualize {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sortvisualize to javafx.fxml;
    exports com.example.sortvisualize;
}