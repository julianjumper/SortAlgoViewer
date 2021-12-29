package jmjumper.sortalgoviewer.components;

import jmjumper.sortalgoviewer.gui.GUI;
import jmjumper.sortalgoviewer.gui.SortArray;
import jmjumper.sortalgoviewer.gui.ViewerScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class guiKeyListener implements KeyListener {

    private GUI parent;
    private ViewerScreen viewerScreen;

    public guiKeyListener(GUI parent, ViewerScreen viewerScreen) {
        this.parent = parent;
        this.viewerScreen = viewerScreen;
    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_Q) -> viewerScreen.closeByButton();
        }
    }

    // Nicht benutzt
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }
}