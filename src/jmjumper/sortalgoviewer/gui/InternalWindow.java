package jmjumper.sortalgoviewer.gui;

import jmjumper.sortalgoviewer.Algorithms.AbstractAlgo;
import jmjumper.sortalgoviewer.Algorithms.algos.BogoSort;
import jmjumper.sortalgoviewer.Algorithms.algos.InsertionSort;
import jmjumper.sortalgoviewer.Algorithms.algos.MergeSort;
import jmjumper.sortalgoviewer.Algorithms.algos.QuickSort;
import jmjumper.sortalgoviewer.components.Button;
import jmjumper.sortalgoviewer.components.DefaultFont;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InternalWindow extends JFrame {

    private JList<String> list;
    private JPanel listPanel;
    private GridBagConstraints c;
    private mainScreen parent;
    private List<AbstractAlgo> algoList;
    private String listData[];
    private JSpinner spinnerDelay;
    private JLabel labelDelay;

    public InternalWindow(mainScreen parent) {
        super("Select algorithm...");
        this.parent = parent;

        createAlgoList();

        listPanel = new JPanel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));
        setSize(400, 400);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        c = new GridBagConstraints();

        initialiseList();
        addSpinnerInput();
        addButtons();

        setVisible(true);
    }

    private void createAlgoList() {
        algoList = new ArrayList<>();
        algoList.add(new MergeSort());
        algoList.add(new BogoSort());
        algoList.add(new QuickSort());
        algoList.add(new InsertionSort());

        listData = new String[algoList.size()];

        for (int i = 0; i < algoList.size(); i++) {
            listData[i] = algoList.get(i).getName();
        }
    }

    private void initialiseList() {
        list = new JList<>(listData);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(300, 300));
        listPanel.add(listScroller);

        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 300;
        add(listPanel, c);
    }

    private void addSpinnerInput() {
        c.gridwidth = 100;
        labelDelay = new JLabel("Delay in ms:");

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(20, 0, 100, 1);
        spinnerDelay = new JSpinner(spinnerModel);

        c.gridy = 1;
        c.gridx = 0;
        add(labelDelay, c);
        c.insets = new Insets(0, 100, 0, 100);
        c.gridx = 1;
        add(spinnerDelay, c);
    }

    private void addButtons() {
        Button confirmButton = new Button("Confirm", this::setAndDispose, 100);
        confirmButton.setBackground(new Color(0x4DB10F));
        confirmButton.setFont(new DefaultFont(Font.BOLD, 12));

        c.gridwidth = 300;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,200,0,0);
        add(confirmButton, c);
    }

    private void setAndDispose() {
        String algoName = list.getSelectedValue();
        AbstractAlgo selectedAlgo = null;

        for (AbstractAlgo algo : algoList) {
            if (algo.getName().equals(algoName))
                selectedAlgo = algo;
        }
        parent.setAlgorithm(selectedAlgo);
        parent.setDelay((Integer) spinnerDelay.getValue());
        dispose();
    }

}
