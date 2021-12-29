package jmjumper.sortalgoviewer.gui;

import jmjumper.sortalgoviewer.Algorithms.AbstractAlgo;
import jmjumper.sortalgoviewer.Sound.SinSynth;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class SortArray extends JPanel {

    private static final int WIN_WIDTH = 1200, WIN_HEIGHT = 800;
    private static final int BAR_WIDTH = 10;
    private static final double BAR_HEIGHT_PERCENT = 512.0 / 720.0;

    private static final int NUM_BARS = WIN_WIDTH / BAR_WIDTH;

    private final int array[];
    private final int arrayColors[];

    private int numberChanges;
    private AbstractAlgo algorithm;
    private final SinSynth playSound;
    private boolean isUnmuted;

    public SortArray() {
        setBackground(Color.BLACK);
        numberChanges = 0;
        array = new int[NUM_BARS];
        arrayColors = new int[NUM_BARS];
        playSound = new SinSynth();
        isUnmuted = true;
        for (int i = 0; i < NUM_BARS; i++) {
            arrayColors[i] = 0;
            array[i] = i;
        }
    }

    public void shuffle() {
        numberChanges = 0;
        Random ran = new Random();
        for (int i = 0; i < arraySize(); i++) {
            int indexToSwap = ran.nextInt(arraySize() - 1);
            swap(i, indexToSwap, 5, false);
            if (isUnmuted)
                playSound.playSound(array[i]);
        }
    }

    private void swap(int indexFrom, int indexTo, long delay, boolean step) {
        int tempValue = array[indexFrom];
        array[indexFrom] = array[indexTo];
        array[indexTo] = tempValue;
        doUpdate(123, delay, step);
    }

    public void updateAtIndex(int index, int value, long delay, boolean step) {
        array[index] = value;
        arrayColors[index] = 100;
        if (isUnmuted)
            playSound.playSound(value);
        doUpdate(value, delay, step);
        repaint();
    }

    private void doUpdate(int value, long delay, boolean step) {
        repaint();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
        if (step) numberChanges++;
    }

    public void highlightArray() {
        for (int i = 0; i < arraySize(); i++) {
            updateAtIndex(i, getValue(i), 5, false);
        }
    }

    public void resetAllColors() {
        for (int i = 0; i < NUM_BARS; i++) {
            arrayColors[i] = 0;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D panelGraphics = (Graphics2D) g.create();

        try {
            panelGraphics.setColor(Color.GREEN);
            panelGraphics.setFont(new Font("Arial", Font.BOLD, 20));
            int leftAlign = 400;
            int rightAlign = 600;
            panelGraphics.drawString(getAlgoName(), leftAlign, 30);
            panelGraphics.drawString("Changes: " + numberChanges, leftAlign, 55);
            panelGraphics.drawString("Size: " + arraySize(), leftAlign, 80);
            panelGraphics.drawString("Best Case: " + getBestCase(), rightAlign, 30);
            panelGraphics.drawString("Average Case: " + getAverageCase(), rightAlign, 55);
            panelGraphics.drawString("Worst Case: " + getWorstCase(), rightAlign, 80);

            drawBars(panelGraphics);
        } finally {
            panelGraphics.dispose();
        }
    }

    /*
     * bei Farbvalue von 100 kommt es zur grünen Farbe.
     * Dieser Farbcode (gespeichert im colorArray) wird durch stetiger Dekrementierung wieder mit jedem Mal verkleindert.
     * Ist die Farbe jedoch kleiner als Code 100, wird sie rot. Dieses rot nimmt jedes Mal weiter ab
     */
    private void drawBars(Graphics2D panelGraphics) {
        int barWidth = getWidth() / NUM_BARS; // dynamische Größenanpassung
        int bufferedImageWidth = barWidth * NUM_BARS;
        int bufferedImageHeight = getHeight();

        if (bufferedImageHeight > 0 && bufferedImageWidth > 0) {
            if (bufferedImageWidth < 256)
                bufferedImageWidth = 256;

            float maxValue = getMaxValue();

            BufferedImage bufferedImage = new BufferedImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D bufferedGraphics = null;
            try {
                bufferedGraphics = bufferedImage.createGraphics();

                for (int i = 0; i < NUM_BARS; i++) {
                    double currentValue = getValue(i);
                    double relativeHeight = currentValue / maxValue;      // Höhe relativ der Values (da ein Array mit höchstem Wert von 1000 nicht größere Balken haben kann, als eines mit 10 als höchstem Wert)
                    double relativeHeightPanel = relativeHeight * BAR_HEIGHT_PERCENT;
                    int height = (int) (relativeHeightPanel * getHeight());  // Tatsächliche Höhe der Balken
                    int x_coord = i + (barWidth - 1) * i;
                    int y_coord = getHeight() - height;

                    int color_val = arrayColors[i];
                    if (color_val > 94) {
                        bufferedGraphics.setColor(new Color(0, 255, 0));
                    } else if (color_val > 0) {
                        bufferedGraphics.setColor(new Color(0, 183 + color_val / 2, 150 + color_val)); // jedes Mal wengier rot, da color_val um 5 dekrementiert wird und somit mehr grün/blau reinkommt
                    } else bufferedGraphics.setColor(new Color(255, 255, 255));

                    bufferedGraphics.fillRect(x_coord, y_coord, barWidth, height);
                    if (arrayColors[i] > 0) {
                        arrayColors[i] -= 5;
                    }
                }
            } finally {
                if (bufferedGraphics != null) {
                    bufferedGraphics.dispose();
                }
            }

            panelGraphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
    }

    public String getAlgoName() {
        if (algorithm != null)
            return algorithm.getName();
        return "wait..";
    }

    public void setAlgo(AbstractAlgo algo) {
        algorithm = algo;
    }

    public int arraySize() {
        return array.length;
    }

    public int getValue(int index) {
        return array[index];
    }

    public int getMaxValue() {
        return Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
    }

    private String getBestCase() {
        if (algorithm != null) return algorithm.getBestCase();
        return "wait..";
    }

    private String getAverageCase() {
        if (algorithm != null) return algorithm.getAverageCase();
        return "wait..";
    }

    private String getWorstCase() {
        if (algorithm != null) return algorithm.getWorstCase();
        return "wait..";
    }

    public void setUnmuted(boolean muted) {
        isUnmuted = muted;
    }

}
