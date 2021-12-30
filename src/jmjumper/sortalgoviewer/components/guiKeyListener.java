package jmjumper.sortalgoviewer.components;

import jmjumper.sortalgoviewer.gui.GUI;
import jmjumper.sortalgoviewer.gui.SortArray;
import jmjumper.sortalgoviewer.gui.ViewerScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class guiKeyListener implements KeyListener {

    private GUI parent;

    public guiKeyListener(GUI parent) {
        this.parent = parent;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_Q) -> parent.deactivateAlgorithm();
        }
    }

    // Nicht benutzt
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }
}