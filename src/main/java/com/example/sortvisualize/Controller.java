package com.example.sortvisualize;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
    private Button resumeButton, stopButton;

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

    @FXML
    private TextArea description;

    @FXML
    private TextField iterationCount;
    private volatile boolean paused = false;
    private SortingAlgorithm sortingAlgorithm;
    GraphicsContext gc;
    Thread mainTread;

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
        ObservableList<String> items = observableArrayList("InsertionSort", "SelectionSort", "BubbleSort",
                                                            "QuickSort", "ShellSort", "ShakerSort",
                                                            "GnomeSort", "HeapSort",
                                                            "MergeSort", "CombSort", "CountingSort",
                                                            "TournamentSort");
        chosenSort.setItems(items);
        chosenSort.setValue("BubbleSort");

        // Установка времени задержки
        textFormatter = new TextFormatter<>(new IntegerStringConverter(), 10, filter);
        timeDelay.setTextFormatter(textFormatter);

        // Для паузы
        stopButton.setOnAction(event -> {mainTread.interrupt(); sortingAlgorithm.drawArray();});

        // Массив для сортировки
        sortingAlgorithm = new SortingAlgorithm(gc, 10);
        sortingAlgorithm.drawArray();
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
    void shuffle(ActionEvent event) {
        sortingAlgorithm.setLengthArray((int) countSlider.getValue());
        sortingAlgorithm.drawArray();
    }

    @FXML
    void startOnCurrentWindow(ActionEvent event) throws InterruptedException {
        sortingAlgorithm.setLengthArray((int) countSlider.getValue());
        sortingAlgorithm.drawArray();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int delay = Integer.parseInt(timeDelay.getText());
                sortingAlgorithm.iterration = 0;
                switch (chosenSort.getValue()){
                    case "InsertionSort":   sortingAlgorithm.InsertionSort(delay); break;
                    case "SelectionSort":   sortingAlgorithm.SelectionSort(delay); break;
                    case "BubbleSort":  sortingAlgorithm.BubbleSort(delay); break;
                    case "QuickSort":   sortingAlgorithm.QuickSort(0, sortingAlgorithm.getArray().size() - 1, delay); break;
                    case "ShellSort":   sortingAlgorithm.ShellSort(delay); break;
                    case "ShakerSort":  sortingAlgorithm.ShakerSort(delay); break;
                    case "GnomeSort":   sortingAlgorithm.GnomeSort(delay); break;
                    case "HeapSort":    sortingAlgorithm.HeapSort(delay); break;
                    case "MergeSort":   sortingAlgorithm.MergeSort(delay); break;
                    case "CombSort":    sortingAlgorithm.CombSort(delay); break;
                    case "CountingSort":    sortingAlgorithm.CountingSort(delay); break;
                    case "TournamentSort":  sortingAlgorithm.TournamentSort(delay); break;
                }

                iterationCount.setText(String.valueOf(sortingAlgorithm.iterration));
                return null;
            }
        };
        mainTread = new Thread(task);
        mainTread.start();

    }

    @FXML
    void continueSort(ActionEvent event) throws InterruptedException{
        System.out.println(1);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int delay = Integer.parseInt(timeDelay.getText());
                System.out.println(chosenSort.getValue());
                switch (chosenSort.getValue()){
                    case "InsertionSort":   sortingAlgorithm.InsertionSort(delay);
                    case "SelectionSort":   sortingAlgorithm.SelectionSort(delay);
                    case "BubbleSort":  sortingAlgorithm.BubbleSort(delay);
                    case "QuickSort":   sortingAlgorithm.QuickSort(0, sortingAlgorithm.getArray().size(), delay);
                    case "ShellSort":   sortingAlgorithm.ShellSort(delay);
                    case "ShakerSort":  sortingAlgorithm.ShakerSort(delay);
                    case "GnomeSort":   sortingAlgorithm.GnomeSort(delay);
                    case "HeapSort":    sortingAlgorithm.HeapSort(delay);
                    case "MergeSort":   sortingAlgorithm.MergeSort(delay);
                    case "CombSort":    sortingAlgorithm.CombSort(delay);
                    case "CountingSort":    sortingAlgorithm.CountingSort(delay);
                    case "TournamentSort":  sortingAlgorithm.TournamentSort(delay);
                }
                return null;
            }
        };
        mainTread = new Thread(task);
        mainTread.start();
    }

    @FXML
    void startOnNewWindow(ActionEvent event) {

    }

}
