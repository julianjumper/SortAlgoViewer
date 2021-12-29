package jmjumper.sortalgoviewer.Algorithms.algos;

import jmjumper.sortalgoviewer.Algorithms.AbstractAlgo;
import jmjumper.sortalgoviewer.gui.SortArray;

public class QuickSort implements AbstractAlgo {


    private long stepDelay = 20;
    private SortArray array;
    private int intArr[];

    public void quickSort(int l, int r) {
        int q;
        if (l < r) {
            q = partition(l, r);
            quickSort(l, q);
            quickSort(q + 1, r);
        }
    }

    int partition(int l, int r) {

        int i, j, x = intArr[(l + r) / 2];
        i = l - 1;
        j = r + 1;
        while (true) {
            do {
                i++;
            } while (intArr[i] < x);

            do {
                j--;
            } while (intArr[j] > x);

            if (i < j) {
                int k = intArr[i];
                intArr[i] = intArr[j];
                array.updateAtIndex(i, intArr[j], stepDelay, true);
                intArr[j] = k;
                array.updateAtIndex(j, k, stepDelay, true);
            } else {
                return j;
            }
        }
    }



    @Override
    public String getName() {
        return "QuickSort";
    }

    @Override
    public long getDelay() {
        return stepDelay;
    }

    @Override
    public void setDelay(long delay) {
        stepDelay = delay;
    }

    @Override
    public void runSort(SortArray array) {
        this.array = array;
        intArr = new int[array.arraySize()];
        for (int i = 0; i < array.arraySize(); i++) {
            intArr[i] = array.getValue(i);
        }
        quickSort(0, array.arraySize() - 1);
    }

    @Override
    public String getBestCase() {
        return "O(n * log(n)";
    }

    @Override
    public String getAverageCase() {
        return "~O(n * log(n)";
    }

    @Override
    public String getWorstCase() {
        return "O(n^2)";
    }
}
