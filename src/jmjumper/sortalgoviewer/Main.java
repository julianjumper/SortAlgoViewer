package jmjumper.sortalgoviewer;

import jmjumper.sortalgoviewer.gui.GUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().startingPoint());
    }

}
