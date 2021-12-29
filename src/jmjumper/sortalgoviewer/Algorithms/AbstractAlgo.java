package jmjumper.sortalgoviewer.Algorithms;

import jmjumper.sortalgoviewer.gui.SortArray;

public interface AbstractAlgo {

    String getName();

    long getDelay();

    /*
    Setzt das Delay zwischen den Schritten.
     */
    void setDelay(long delay);

    void runSort(SortArray array);

    String getBestCase ();

    String getAverageCase ();

    String getWorstCase ();

}
