package com.example.sortvisualize;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.util.function.UnaryOperator;

import static javafx.collections.FXCollections.observableArrayList;

public class Controller {

    @FXML
    private Canvas canvasBubbleSort;

    @FXML
    private Tab bubleSort;

    @FXML
    private ComboBox<String> chosenSort;

    @FXML
    private TextField count;

    @FXML
    private Slider countSlider;

    @FXML
    private TextField timeDelay;

    private SortingAlgorithm sortingAlgorithm;
    GraphicsContext gc;

    @FXML
    void initialize(){
        // Для рисования
        gc = canvasBubbleSort.getGraphicsContext2D();

        // Выбор кол-ва элементов
        count.textProperty().bindBidirectional(countSlider.valueProperty(), NumberFormat.getNumberInstance());
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), 10, filter);
        count.setTextFormatter(textFormatter);

        // Выбор типа сортировки
        ObservableList<String> items = observableArrayList("Сортировка пузырьком", "Быстрая сортировка");
        chosenSort.setItems(items);
        chosenSort.setValue("Сортировка пузырьком");

        // Установка времени задержки
        textFormatter = new TextFormatter<>(new IntegerStringConverter(), 100, filter);
        timeDelay.setTextFormatter(textFormatter);

        // Массив для сортировки
        sortingAlgorithm = new SortingAlgorithm(10);
        drawArray();
    }

    @FXML // Slider
    void changeCount(MouseEvent event) {
        System.out.println(countSlider.getValue());
    }

    @FXML
    void closeWindow(ActionEvent event) {

    }

    @FXML
    void runSort(ActionEvent event) {

    }

    @FXML
    void setTimeDelay(ActionEvent event) {

    }

    @FXML
    void shuffle(ActionEvent event) {
        sortingAlgorithm.setLengthArray((int) countSlider.getValue());
        drawArray();
    }

    @FXML
    void startOnCurrentWindow(ActionEvent event) {
        sortingAlgorithm.bubbleSort();
        drawArray();
    }

    @FXML
    void startOnNewWindow(ActionEvent event) {

    }

    @FXML
    void stopSort(ActionEvent event) {

    }

    private void drawArray(){
        gc.clearRect(0, 0, canvasBubbleSort.getWidth(), canvasBubbleSort.getHeight());
        int arr[] = sortingAlgorithm.getArray();
        int arr_width = (int) (canvasBubbleSort.getWidth() / arr.length);
        int arr_height = (int) (canvasBubbleSort.getHeight() / arr.length);
        for (int i = 0; i < arr.length; i ++){
            gc.fillRect(arr_width * i, arr_height * (arr.length-arr[i]), arr_width, arr_height * arr[i]);
        }
    }
}
