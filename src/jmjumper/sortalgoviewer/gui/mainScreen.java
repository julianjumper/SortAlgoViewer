package jmjumper.sortalgoviewer.gui;

import jmjumper.sortalgoviewer.Algorithms.AbstractAlgo;
import jmjumper.sortalgoviewer.components.Button;
import jmjumper.sortalgoviewer.components.DefaultFont;
import jmjumper.sortalgoviewer.components.guiKeyListener;

import javax.swing.*;
import java.awt.*;

public class mainScreen extends Screen {

    private final GridBagConstraints c;
    private final GridBagConstraints cMain;
    private AbstractAlgo algorithm;
    private JLabel selectedAlgoLabel;
    private int delay = 20;

    public mainScreen(GUI mainGui) {
        super(mainGui);
        setLayout(new GridBagLayout());
        cMain = new GridBagConstraints();
        c = new GridBagConstraints();
        createGui();
    }

    private void createGui() {
        JPanel jP = new JPanel();
        jP.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.VERTICAL;
        cMain.fill = GridBagConstraints.NONE;
        cMain.anchor = GridBagConstraints.NORTH;
        cMain.weighty = 1f;

        cMain.insets = new Insets(0, 0, 0, 0);
        JLabel title = new JLabel("Sort-Algorithm Viewer");
        title.setFont( new DefaultFont (Font.BOLD, 60) );
        add(title, cMain);



        // Beginn des Buttonpanels
        cMain.gridy = 1;

        c.gridy = 1;
        c.insets = new Insets(0, 0, 5, 0);
        Button b1 = new Button("Start", () ->
        {
            if (algorithm != null)
                mainGui.pushScreen(new ViewerScreen(mainGui, algorithm, delay));
            else JOptionPane.showMessageDialog(mainGui, "Error: Please select an algorithm first!\n" +
                    "You can find a list of various algorithms under 'Select algorithm...'");
        } , 200);
        b1.setFont(new DefaultFont(Font.BOLD, 20));
        b1.setBackground(Color.GRAY);
        jP.add(b1, c);

        c.gridy = 2;
        Button b2 = new Button("Select algorithm...", this::createInternalWindow, 200);
        b2.setFont(new DefaultFont(Font.PLAIN, 18));
        b2.setBackground(Color.GRAY);
        jP.add(b2, c);

        c.gridy = 3;
        selectedAlgoLabel = new JLabel("Selected algorithm: please select one");
        selectedAlgoLabel.setFont(new DefaultFont(Font.BOLD, 15));
        jP.add(selectedAlgoLabel, c);

        add(jP, cMain);
    }

    private void createInternalWindow () {
        new InternalWindow(this).restart();
    }

    public void setAlgorithm(AbstractAlgo algo) {
        algorithm = algo;
        selectedAlgoLabel.setText("Selected algorithm: " + algorithm.getName());
    }

    public void setDelay (int delay) {
        this.delay = delay;
    }

    @Override
    public void startUp() {
    }

    @Override
    public void finish() {
    }
}
