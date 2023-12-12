package com.example.sortvisualize;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SortingAlgorithm {

    public int iterration;
    GraphicsContext gc;
    private ArrayList<Integer> array;
    SortingAlgorithm(GraphicsContext gc, int length){
        this.array = generateArray(length);
        this.gc = gc;
    }
    private ArrayList<Integer> generateArray(int length) {
        ArrayList<Integer> initialList = new ArrayList<>();
        for (int i = 1; i <= length; i++)
            initialList.add(i);
        Collections.shuffle(initialList);
        return initialList;
    }
    public ArrayList<Integer> getArray(){
        return array;
    }
    public void setLengthArray(int length) {
        this.array = generateArray(length);
    }


    // Рисование на холсте
    public void drawArray(){
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(Color.BLACK);
        int arr_width = (int) (gc.getCanvas().getWidth() / array.size());
        int arr_height = (int) (gc.getCanvas().getHeight() / array.size());
        for (int i = 0; i < array.size(); i ++){
            gc.fillRect(arr_width * i, arr_height * (array.size()-array.get(i)), arr_width, arr_height * array.get(i));
        }
    }
    public void drawCurrentPos(int i, Color color){
        int arr_width = (int) (gc.getCanvas().getWidth() / array.size());
        int arr_height = (int) (gc.getCanvas().getHeight() / array.size());
        gc.setFill(color);
        gc.fillRect(arr_width * i, arr_height * (array.size()-array.get(i)), arr_width, arr_height * array.get(i));
    }


    // Сортировки
    public void InsertionSort(int delay) throws InterruptedException {
        for (int i = 1; i < array.size(); i++) {
            int key = array.get(i);
            int j = i - 1;

            while (j >= 0 && array.get(j) > key) {
                array.set(j + 1, array.get(j));
                j = j - 1;
                iterration++;
                drawArray();
                drawCurrentPos(j, Color.RED);
                Thread.sleep(delay);
            }

            array.set(j + 1, key);
            iterration++;
            drawArray();
            drawCurrentPos(j, Color.RED);
            Thread.sleep(delay);
        }
    }
    public void SelectionSort(int delay) throws InterruptedException {
        int n = array.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (array.get(j) < array.get(minIndex)) {
                    minIndex = j;
                }
                iterration++;
                drawArray();
                drawCurrentPos(j, Color.RED);
                Thread.sleep(delay);
            }
            swap(array, minIndex, i);
            iterration++;
            drawArray();
            drawCurrentPos(minIndex, Color.RED);
            Thread.sleep(delay);
        }
    }
    public void BubbleSort(int delay) throws InterruptedException {
        int n = array.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (array.get(j) > array.get(j + 1)) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
                iterration++;
                drawArray();
                drawCurrentPos(j, Color.RED);
                Thread.sleep(delay);
            }
            if (!swapped) {
                break;
            }
        }
    }
    public void QuickSort(int low, int high, int delay) throws InterruptedException {
        if (array.isEmpty())
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        int opora = array.get(middle);
        int i = low, j = high;
        while (i <= j) {
            while (array.get(i) < opora) {
                i++;
            }

            while (array.get(j) > opora) {
                j--;
            }

            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
                iterration++;
                drawArray();
                drawCurrentPos(i, Color.RED);
                drawCurrentPos(j, Color.GREEN);
                Thread.sleep(delay);
            }
        }

        if (low < j)
            QuickSort(low, j, delay);

        if (high > i)
            QuickSort(i, high, delay);
    }
    public void ShellSort(int delay) throws InterruptedException {
        int n = array.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = array.get(i);
                int j;
                for (j = i; j >= gap && array.get(j - gap) > temp; j -= gap) {
                    array.set(j, array.get(j - gap));
                    iterration++;
                    drawArray();
                    drawCurrentPos(j, Color.RED);
                    Thread.sleep(delay);
                }
                array.set(j, temp);
                iterration++;
                drawArray();
                drawCurrentPos(j, Color.RED);
                Thread.sleep(delay);
            }
        }
    }
    public void ShakerSort(int delay) throws InterruptedException {
        boolean swapped;
        do {
            swapped = false;

            for (int i = 0; i <= array.size() - 2; i++) {
                if (array.get(i) > array.get(i + 1)) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
                iterration++;
                drawArray();
                drawCurrentPos(i, Color.RED);
                Thread.sleep(delay);
            }

            if (!swapped) {
                break;
            }

            swapped = false;

            for (int i = array.size() - 2; i >= 0; i--) {
                if (array.get(i) > array.get(i + 1)) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
                iterration++;
                drawArray();
                drawCurrentPos(i, Color.RED);
                Thread.sleep(delay);
            }

        } while (swapped);
    }
    public void GnomeSort(int delay) throws InterruptedException {
        int n = array.size();
        int index = 0;

        while (index < n) {
            if (index == 0) {
                index++;
            }

            if (array.get(index) >= array.get(index - 1)) {
                index++;
            } else {
                swap(array, index, index - 1);
                index--;
            }
            iterration++;
            drawArray();
            drawCurrentPos(index, Color.RED);
            Thread.sleep(delay);
        }
    }
    public void HeapSort(int delay) throws InterruptedException {
        int n = array.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            Heapify(array, n, i,delay);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(array, 0, i);
            Heapify(array, i, 0, delay);
        }
    }
    private void Heapify(ArrayList<Integer> arr, int n, int i, int delay) throws InterruptedException {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr.get(left) > arr.get(largest)) {
            largest = left;
        }

        if (right < n && arr.get(right) > arr.get(largest)) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            Heapify(arr, n, largest, delay);
        }
        iterration++;
        drawArray();
        drawCurrentPos(i, Color.RED);
        Thread.sleep(delay);
    }
    public void MergeSort(int delay) throws InterruptedException {
        mergeSort(array, delay);
    }
    private void mergeSort(ArrayList<Integer> arr, int delay) throws InterruptedException {
        if (arr.size() > 1) {
            int mid = arr.size() / 2;
            ArrayList<Integer> left = new ArrayList<>(arr.subList(0, mid));
            ArrayList<Integer> right = new ArrayList<>(arr.subList(mid, arr.size()));

            mergeSort(left, delay);
            mergeSort(right, delay);

            merge(arr, left, right, delay);
        }
    }
    private void merge(ArrayList<Integer> arr, ArrayList<Integer> left, ArrayList<Integer> right, int delay) throws InterruptedException {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i) < right.get(j)) {
                arr.set(k++, left.get(i++));
            } else {
                arr.set(k++, right.get(j++));
            }
            iterration++;
            drawArray();
            drawCurrentPos(i, Color.RED);
            Thread.sleep(delay);
        }

        while (i < left.size()) {
            arr.set(k++, left.get(i++));
            iterration++;
            drawArray();
            drawCurrentPos(i, Color.RED);
            Thread.sleep(delay);
        }

        while (j < right.size()) {
            arr.set(k++, right.get(j++));
            iterration++;
            drawArray();
            drawCurrentPos(i, Color.RED);
            Thread.sleep(delay);
        }
    }
    public void CombSort(int delay) throws InterruptedException {
        int n = array.size();
        int gap = n;
        boolean swapped = true;

        while (gap != 1 || swapped) {
            gap = getNextGap(gap);
            swapped = false;

            for (int i = 0; i < n - gap; i++) {
                if (array.get(i) > array.get(i + gap)) {
                    swap(array, i, i + gap);
                    swapped = true;
                }
                iterration++;
                drawArray();
                drawCurrentPos(i, Color.RED);
                Thread.sleep(delay);
            }
        }
    }
    private static int getNextGap(int gap) {
        gap = (gap * 10) / 13;
        return Math.max(gap, 1);
    }
    public void CountingSort(int delay) throws InterruptedException {
        int max = findMaxValue(array);
        int[] count = new int[max + 1];

        for (int num : array) {
            count[num]++;
            iterration++;
        }

        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (count[i] > 0) {
                array.set(index++, i);
                count[i]--;
            }
            iterration++;
            drawArray();
            drawCurrentPos(i, Color.RED);
            Thread.sleep(delay);
        }
    }

    // Вспомогательные функции
    private static int findMinValue(List<Integer> arr) {
        int min = Integer.MAX_VALUE;
        for (int num : arr) {
            min = Math.min(min, num);
        }
        return min;
    }
    private static int findMaxValue(List<Integer> arr) {
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            max = Math.max(max, num);
        }
        return max;
    }
    private static void swap(List<Integer> arr, int i, int j){
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }
}
