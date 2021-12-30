package jmjumper.sortalgoviewer.components;

import jmjumper.sortalgoviewer.gui.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class guiKeyListener implements KeyListener {

    private final GUI parent;

    public guiKeyListener(GUI parent) {
        this.parent = parent;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_Q || keyCode == KeyEvent.VK_ESCAPE)
            parent.deactivateAlgorithm();
    }

    // Nicht benutzt
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }
}