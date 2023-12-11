package com.example.sortvisualize;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;

public class SortingAlgorithm {

    private int[] array;
    SortingAlgorithm(int length){
        this.array = generateArray(length);
    }
    private int[] generateArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = i + 1;
        }
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            swap(arr, i, index);
        }
        return arr;
    }
    public int[] getArray(){
        return array;
    }
    public void setLengthArray(int length) {
        this.array = generateArray(length);
    }
    private void swap(int[] arr, int index1, int index2){
        if (index1 >= 0 && index1 < arr.length && index2 >= 0 && index2 < arr.length) {
            int temp = arr[index1];
            arr[index1] = arr[index2];
            arr[index2] = temp;
        } else {
            System.out.println("Invalid indices for swap");
        }
    }
    public void bubbleSort() {
        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (array[i - 1] > array[i]) {
                    swap(array, i - 1, i);
                    swapped = true;
                }
            }
        } while (swapped);
    }

}
