package com.example.sortvisualize;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;



public class SortingVisualization extends Application {

    private static final int ARRAY_SIZE = 10;
    private static final int ARRAY_MAX_VALUE = 100;

    private int[] array;
    private Canvas canvas;
    private GraphicsContext gc;
    private int currentIndex = 0;

    private int widthCanvas;

    @Override
    public void start(Stage primaryStage) {
        array = generateRandomArray(ARRAY_SIZE, ARRAY_MAX_VALUE);

        canvas = new Canvas(400, 200);
        widthCanvas = 400;
        gc = canvas.getGraphicsContext2D();
        updateCanvas();

        Button sortButton = new Button("Sort");
        sortButton.setOnAction(event -> {
            sortArray();
        });

        FlowPane root = new FlowPane();
        root.getChildren().addAll(canvas, sortButton);

        Scene scene = new Scene(root, 400, 250);

        primaryStage.setTitle("Sorting Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int[] generateRandomArray(int size, int maxValue) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(maxValue) + 1;
        }
        return arr;
    }

    private void updateCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double barWidth = canvas.getWidth() / array.length;
        double maxBarHeight = canvas.getHeight() - 10;

        for (int i = 0; i < array.length; i++) {
            double barHeight = (array[i] / (double) ARRAY_MAX_VALUE) * maxBarHeight;
            double x = i * barWidth;
            double y = canvas.getHeight() - barHeight;
            gc.fillRect(x, y, barWidth, barHeight);
        }
    }

    private void sortArray() {
        Timeline timeline = new Timeline();
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1, timeline);
                }
            }
        }

        timeline.setOnFinished(event -> updateCanvas());
        timeline.play();
    }

    private void swap(int index1, int index2, Timeline timeline) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;

        double x1 = index1 * (canvas.getWidth() / array.length);
        double x2 = index2 * (canvas.getWidth() / array.length);

        KeyValue keyValue1 = new KeyValue(canvas.widthProperty(), x1);
        KeyValue keyValue2 = new KeyValue(canvas.widthProperty(), x2);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue1, keyValue2);

        timeline.getKeyFrames().add(keyFrame);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
