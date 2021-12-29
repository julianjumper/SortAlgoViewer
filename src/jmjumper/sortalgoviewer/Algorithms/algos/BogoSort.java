package jmjumper.sortalgoviewer.Algorithms.algos;

// der Beste aller Algorithmen

import jmjumper.sortalgoviewer.Algorithms.AbstractAlgo;
import jmjumper.sortalgoviewer.gui.SortArray;

import java.util.Random;

public class BogoSort implements AbstractAlgo {
    long delay = 20;

    private void bogoSort (SortArray array) {
        Random r = new Random(); // Du weißt, dass ein Sortier-Algorithmus gut ist, wenn er mit Random() beginnt


        while (!isSorted(array)) {
            int a = r.nextInt(array.arraySize());
            int b = r.nextInt(array.arraySize());
            int temp = array.getValue(a);
            array.updateAtIndex(a, array.getValue(b), delay, true);
            array.updateAtIndex(b, temp, delay, true);
        }
    }

    private boolean isSorted (SortArray array) {
        for ( int i = 0; i < array.arraySize(); i++ ) {
            if ( array.getValue(i) > array.getValue(i + 1) ) return false;
        }
        return true;
    }


    @Override
    public String getName() {
        return "BogoSort";
    }

    @Override
    public long getDelay() {
        return this.delay;
    }

    @Override
    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void runSort(SortArray array) {
        bogoSort(array);
    }

    @Override
    public String getBestCase() {
        return "O(n)";
    }

    @Override
    public String getAverageCase() {
        return "not available";
    }

    @Override
    public String getWorstCase() {
        return "O(n*n!)";
    }
}
