package com.example.sortvisualize;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class SortingVisualization extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("window.fxml"));
        stage.setTitle("Визуализация сортировок");
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
