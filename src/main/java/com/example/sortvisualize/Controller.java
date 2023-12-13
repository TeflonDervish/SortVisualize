package com.example.sortvisualize;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

import static javafx.collections.FXCollections.observableArrayList;

public class Controller {

    @FXML
    private Button stopButton;

    @FXML
    private TabPane tabPane;

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


    private ArrayList<Tab> tabs;
    private ArrayList<Canvas> canvases;
    private ArrayList<GraphicsContext> graphicsContexts;
    private ArrayList<Boolean> isFinishedThread;
    private ArrayList<SortingAlgorithm> sortingAlgorithms;
    private ArrayList<Thread> threads;
    private int currentTab;


    @FXML
    void initialize(){
        // Для работоты с вкладками
        tabs = new ArrayList<>();
        canvases = new ArrayList<>();
        graphicsContexts = new ArrayList<>();
        isFinishedThread = new ArrayList<>();
        sortingAlgorithms = new ArrayList<>();
        threads = new ArrayList<>();

        // Первая вкладка
        Tab tab = new Tab("BubbleSort");
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(0);
        tabs.add(tab);

        // Помещаем содержимое на вкладку
        Canvas canvas = new Canvas(700, 500);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().setAll(canvas);
        tab.setContent(anchorPane);
        canvases.add(canvas);

        // Для работы с потоками
        isFinishedThread.add(true);
        threads.add(new Thread());

        // Для рисования
        graphicsContexts.add(canvases.get(currentTab).getGraphicsContext2D());

        // Для работы алгоритмов
        SortingAlgorithm sortingAlgorithm = new SortingAlgorithm(graphicsContexts.get(currentTab), 10);
        sortingAlgorithms.add(sortingAlgorithm);
        sortingAlgorithms.get(currentTab).drawArray();


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
                                                            "MergeSort", "CombSort", "CountingSort");
        chosenSort.setItems(items);
        chosenSort.setValue("BubbleSort");

        // Установка времени задержки
        textFormatter = new TextFormatter<>(new IntegerStringConverter(), 10, filter);
        timeDelay.setTextFormatter(textFormatter);

        // Для паузы
        stopButton.setOnAction(event -> {threads.get(currentTab).interrupt(); sortingAlgorithm.drawArray();});

        // Переключение индекса между вкладками
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null){
                currentTab = tabPane.getSelectionModel().getSelectedIndex();
            }
        });
    }

    @FXML // Slider
    void changeCount(MouseEvent event) {
        System.out.println(countSlider.getValue());
    }

    @FXML
    void closeWindow(ActionEvent event) {
        if (tabs.size() == 1 ){
            System.out.println("Закрыть вкладку невозможно");
        }else{

            if (currentTab == tabs.size() - 1){
                tabPane.getTabs().remove(currentTab);
                tabs.remove(currentTab);
                canvases.remove(currentTab);
                graphicsContexts.remove(currentTab);
                isFinishedThread.remove(currentTab);
                sortingAlgorithms.remove(currentTab);
                threads.remove(currentTab);
                currentTab = tabs.size() - 1;
                tabPane.getSelectionModel().select(currentTab);
            }else{
                tabPane.getTabs().remove(currentTab);
                tabs.remove(currentTab);
                canvases.remove(currentTab);
                graphicsContexts.remove(currentTab);
                isFinishedThread.remove(currentTab);
                sortingAlgorithms.remove(currentTab);
                threads.remove(currentTab);
                tabPane.getSelectionModel().select(currentTab);
            }
        }
    }

    @FXML
    void shuffle(ActionEvent event) {
        sortingAlgorithms.get(currentTab).setLengthArray((int) countSlider.getValue());
        sortingAlgorithms.get(currentTab).drawArray();
    }

    @FXML
    void startOnCurrentWindow(ActionEvent event) throws InterruptedException {
        sortingAlgorithms.get(currentTab).setLengthArray((int) countSlider.getValue());
        sortingAlgorithms.get(currentTab).drawArray();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int delay = Integer.parseInt(timeDelay.getText());
                sortingAlgorithms.get(currentTab).iterration = 0;
                switch (chosenSort.getValue()){
                    case "InsertionSort":   sortingAlgorithms.get(currentTab).InsertionSort(delay); break;
                    case "SelectionSort":   sortingAlgorithms.get(currentTab).SelectionSort(delay); break;
                    case "BubbleSort":  sortingAlgorithms.get(currentTab).BubbleSort(delay); break;
                    case "QuickSort":   sortingAlgorithms.get(currentTab).QuickSort(0, sortingAlgorithms.get(currentTab).getArray().size() - 1, delay); break;
                    case "ShellSort":   sortingAlgorithms.get(currentTab).ShellSort(delay); break;
                    case "ShakerSort":  sortingAlgorithms.get(currentTab).ShakerSort(delay); break;
                    case "GnomeSort":   sortingAlgorithms.get(currentTab).GnomeSort(delay); break;
                    case "HeapSort":    sortingAlgorithms.get(currentTab).HeapSort(delay); break;
                    case "MergeSort":   sortingAlgorithms.get(currentTab).MergeSort(delay); break;
                    case "CombSort":    sortingAlgorithms.get(currentTab).CombSort(delay); break;
                    case "CountingSort":    sortingAlgorithms.get(currentTab).CountingSort(delay); break;
                }

                iterationCount.setText("Кол-во итераций: " + sortingAlgorithms.get(currentTab).iterration);
                return null;
            }
        };
        threads.set(currentTab, new Thread(task));
        threads.get(currentTab).start();

    }

    @FXML
    void continueSort(ActionEvent event) throws InterruptedException{
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int delay = Integer.parseInt(timeDelay.getText());
                switch (chosenSort.getValue()){
                    case "InsertionSort":   sortingAlgorithms.get(currentTab).InsertionSort(delay);
                    case "SelectionSort":   sortingAlgorithms.get(currentTab).SelectionSort(delay);
                    case "BubbleSort":  sortingAlgorithms.get(currentTab).BubbleSort(delay);
                    case "QuickSort":   sortingAlgorithms.get(currentTab).QuickSort(0, sortingAlgorithms.get(currentTab).getArray().size(), delay);
                    case "ShellSort":   sortingAlgorithms.get(currentTab).ShellSort(delay);
                    case "ShakerSort":  sortingAlgorithms.get(currentTab).ShakerSort(delay);
                    case "GnomeSort":   sortingAlgorithms.get(currentTab).GnomeSort(delay);
                    case "HeapSort":    sortingAlgorithms.get(currentTab).HeapSort(delay);
                    case "MergeSort":   sortingAlgorithms.get(currentTab).MergeSort(delay);
                    case "CombSort":    sortingAlgorithms.get(currentTab).CombSort(delay);
                    case "CountingSort":    sortingAlgorithms.get(currentTab).CountingSort(delay);
                }
                iterationCount.setText("Кол-во итераций: " + sortingAlgorithms.get(currentTab).iterration);
                return null;
            }
        };
        threads.set(currentTab, new Thread(task));
        threads.get(currentTab).start();
    }

    @FXML
    void startOnNewWindow(ActionEvent event) {
        // Добавление вкладки
        Tab tab = new Tab(chosenSort.getValue());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(++currentTab);
        tabs.add(tab);

        // Помещаем содержимое на вкладку
        Canvas canvas = new Canvas(700, 500);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().setAll(canvas);
        tab.setContent(anchorPane);
        canvases.add(canvas);

        // Для работы с потоками
        isFinishedThread.add(true);
        threads.add(new Thread());

        // Для рисования
        graphicsContexts.add(canvases.get(currentTab).getGraphicsContext2D());

        // Для работы алгоритмов
        SortingAlgorithm sortingAlgorithm = new SortingAlgorithm(graphicsContexts.get(currentTab), (int) countSlider.getValue());
        sortingAlgorithms.add(sortingAlgorithm);
        sortingAlgorithms.get(currentTab).drawArray();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int delay = Integer.parseInt(timeDelay.getText());
                sortingAlgorithms.get(currentTab).iterration = 0;
                switch (chosenSort.getValue()){
                    case "InsertionSort":   sortingAlgorithms.get(currentTab).InsertionSort(delay); break;
                    case "SelectionSort":   sortingAlgorithms.get(currentTab).SelectionSort(delay); break;
                    case "BubbleSort":  sortingAlgorithms.get(currentTab).BubbleSort(delay); break;
                    case "QuickSort":   sortingAlgorithms.get(currentTab).QuickSort(0, sortingAlgorithms.get(currentTab).getArray().size() - 1, delay); break;
                    case "ShellSort":   sortingAlgorithms.get(currentTab).ShellSort(delay); break;
                    case "ShakerSort":  sortingAlgorithms.get(currentTab).ShakerSort(delay); break;
                    case "GnomeSort":   sortingAlgorithms.get(currentTab).GnomeSort(delay); break;
                    case "HeapSort":    sortingAlgorithms.get(currentTab).HeapSort(delay); break;
                    case "MergeSort":   sortingAlgorithms.get(currentTab).MergeSort(delay); break;
                    case "CombSort":    sortingAlgorithms.get(currentTab).CombSort(delay); break;
                    case "CountingSort":    sortingAlgorithms.get(currentTab).CountingSort(delay); break;
                }

                iterationCount.setText("Кол-во итераций: " + sortingAlgorithms.get(currentTab).iterration);
                return null;
            }
        };
        threads.set(currentTab, new Thread(task));
        threads.get(currentTab).start();
    }

}
