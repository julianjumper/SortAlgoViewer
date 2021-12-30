package jmjumper.sortalgoviewer.gui;

import jmjumper.sortalgoviewer.Algorithms.AbstractAlgo;

import javax.swing.*;
import java.awt.*;

public class ViewerScreen extends Screen {

    private final GUI gui;
    private final SortArray arrayPanel;
    private final AbstractAlgo algorithm;

    public ViewerScreen(GUI gui, AbstractAlgo algorithm, int delay) {
        super(gui);
        this.gui = gui;
        setLayout(new BorderLayout());

        arrayPanel = new SortArray();
        add(arrayPanel, BorderLayout.CENTER);

        this.algorithm = algorithm;
        algorithm.setDelay(delay);
    }

    private void sleepyMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void shuffleAndWait() {
        arrayPanel.shuffle();
        arrayPanel.resetAllColors();
        sleepyMethod();
    }

    @Override
    public void startUp() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() { // reminder an mich selbst: swingworker ist sowas wie Threading für GUIs
            @Override
            protected Void doInBackground() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                arrayPanel.setUnmuted(true);
                arrayPanel.setAlgo(algorithm);
                algorithm.setActivated(true);
                shuffleAndWait();
                algorithm.runSort(arrayPanel);
                arrayPanel.resetAllColors();
                arrayPanel.highlightArray();
                arrayPanel.resetAllColors();
                sleepyMethod();
                return null;
            }

            @Override
            public void done() {
                gui.popScreen();
            }
        };

        swingWorker.execute();
    }

    @Override
    public void finish() {
        algorithm.setActivated(false);
    }


}
