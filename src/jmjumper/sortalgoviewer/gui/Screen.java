package jmjumper.sortalgoviewer.gui;

import javax.swing.*;

public abstract class Screen extends JPanel {

    // Jedes Panel erweitert Screen, damit alle Panels als Screens in einer Liste für die GUI gespeichert werden kann.
    // Außerdem enthält somit jedes Panel eine startUp-Methode, die den Start des Panels sicherstellt.

    protected GUI mainGui;

    public Screen (GUI mainGui) {
        this.mainGui = mainGui;
    }

    public abstract void startUp ();
    public abstract void finish ();

}
