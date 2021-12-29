package jmjumper.sortalgoviewer.Algorithms.algos;

import jmjumper.sortalgoviewer.Algorithms.AbstractAlgo;
import jmjumper.sortalgoviewer.gui.SortArray;

public class InsertionSort implements AbstractAlgo {

    long delay = 20;

    void insertionSort(SortArray array)
    {
        int n = array.arraySize();
        for (int i = 1; i < n; ++i) {
            int key = array.getValue(i);
            int j = i - 1;
            while (j >= 0 && array.getValue(j) > key) {
                array.updateAtIndex(j + 1, array.getValue(j), delay, true);
                j = j - 1;
            }
            array.updateAtIndex(j+1, key, delay, true);
        }
    }


    @Override
    public String getName() {
        return "InsertionSort";
    }

    @Override
    public long getDelay() {
        return delay;
    }

    @Override
    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void runSort(SortArray array) {
        insertionSort(array);
    }

    @Override
    public String getBestCase() {
        return "O(n)";
    }

    @Override
    public String getAverageCase() {
        return "O(n^2)";
    }

    @Override
    public String getWorstCase() {
        return "O(n), O(1)";
    }
}
