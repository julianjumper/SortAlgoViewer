package jmjumper.sortalgoviewer.gui;

import jmjumper.sortalgoviewer.components.guiKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GUI extends JFrame {

    private static final int WIN_WIDTH = 1200, WIN_HEIGHT = 800;
    public final ArrayList<Screen> screens;

    public GUI() {
        super("Sort-Algorithm-Viewer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLocationRelativeTo(null);
        addKeyListener(new guiKeyListener(this));
        // setResizable(false);
        screens = new ArrayList<>();

        setVisible(true);
    }

    public void pushScreen(Screen screen) {
        if (!screens.isEmpty()) remove(getCurrentScreen());
        screens.add(screen);
        setContentPane(screen);
        validate();
        screen.startUp();
    }

    public void popScreen() {
        if (!screens.isEmpty()) {
            Screen currentScreen = getCurrentScreen();
            screens.remove(currentScreen);
            remove(currentScreen);
            System.out.println(screens.size());
            if (!screens.isEmpty()) {
                Screen newCurrentScreen = getCurrentScreen();
                setContentPane(newCurrentScreen);
                validate();
                newCurrentScreen.startUp();
            } else {
                dispose();
            }
        }
    }

    public void deactivateAlgorithm () {
        getCurrentScreen().finish();
    }

    private Screen getCurrentScreen() {
        return screens.get(screens.size() - 1);
    }

    public void startingPoint() {
        // pushScreen (new ViewerScreen(this));
        pushScreen(new mainScreen(this));
        pack();
    }

}
