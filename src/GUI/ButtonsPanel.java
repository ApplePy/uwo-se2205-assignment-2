package GUI;

import Sorts.MergeSort;
import Sorts.SelectionSort;
import Sorts.SortType;
import Sorts.SortingFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by darryl on 2016-03-10.
 */
public class ButtonsPanel extends JPanel {

    private JButton scramble = new JButton("Scramble Lines");
    private JButton selection = new JButton("Selection Sort");
    private JButton merge = new JButton("Merge Sort");
    private JButton reset = new JButton("Reset");

    private boolean selectionDone = false;
    private boolean mergeDone = false;

    public ButtonsPanel() {
        super();
        commonConstructor();
    }

    public ButtonsPanel(LayoutManager layout) {
        super(layout);
        commonConstructor();
    }

    public ButtonsPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        commonConstructor();
    }

    public ButtonsPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        commonConstructor();
    }

    private void commonConstructor() {
        setPreferredSize(new Dimension(640 / 5, 500)); // TODO: FIX HARDCODING!

        add(scramble);
        add(selection);
        add(merge);
        add(reset);
        selection.setEnabled(false);
        merge.setEnabled(false);
        reset.setEnabled(false);
    }

    public void setControlTarget(GraphicsPanel target) {
        removeOldActionListeners(scramble);
        removeOldActionListeners(selection);
        removeOldActionListeners(merge);
        removeOldActionListeners(reset);

        scramble.addActionListener(new scrambleAction(target));
        selection.addActionListener(new sortAction(target, new SelectionSort<>(), SortType.SELECTIONSORT));
        merge.addActionListener(new sortAction(target, new MergeSort<>(), SortType.MERGESORT));
        reset.addActionListener(new resetAction(target));
    }

    private void removeOldActionListeners(JButton target) {
        ActionListener[] listeners = target.getActionListeners();
        for (ActionListener al : listeners) {
            target.removeActionListener(al);
        }
    }


    private class scrambleAction implements ActionListener {
        private GraphicsPanel target;

        public scrambleAction(GraphicsPanel target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target.scramble();
            JButton source = (JButton) e.getSource();
            source.setEnabled(false);

            selection.setEnabled(true);
            merge.setEnabled(true);
            selectionDone = false;
            mergeDone = false;
        }
    }

    private class resetAction implements ActionListener {
        private GraphicsPanel target;

        public resetAction(GraphicsPanel target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target.reset();
            selection.setEnabled(true);
            merge.setEnabled(true);
            reset.setEnabled(false);
        }
    }

    private class sortAction implements ActionListener, Runnable {
        private GraphicsPanel target;
        private SortingFunction<ImprovedRectangle> sorter;

        public sortAction(GraphicsPanel target, SortingFunction<ImprovedRectangle> sorter, SortType sort) {
            this.target = target;
            this.sorter = sorter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            scramble.setEnabled(false);
            selection.setEnabled(false);
            merge.setEnabled(false);
            reset.setEnabled(false);

            Thread newThread = new Thread(this);
            newThread.start(); // This functionality has to be put inside a runnable object,
            // as it will otherwise clog up the event dispatch and painting thread
        }

        @Override
        public void run() {

            Thread thread = target.sortRectangles(sorter);
            try {
                while (thread.isAlive()) {
                    Thread.sleep(5);
                }

                if (sorter.getSortType() == SortType.SELECTIONSORT)
                    selectionDone = true;
                else if (sorter.getSortType() == SortType.MERGESORT)
                    mergeDone = true;

                if (mergeDone && selectionDone) {
                    scramble.setEnabled(true);
                }

                reset.setEnabled(true);
            } catch (InterruptedException x) {

                // Contingency in case something goes wrong - reset the program button states to opening to recovery
                scramble.setEnabled(true);
                selection.setEnabled(false);
                merge.setEnabled(false);
                reset.setEnabled(false);
                System.out.println("Interrupted exception, sortAction.");
            }
        }
    }
}
