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
 * Buttons panel controls the display and sorting algorithms for Graphics Panel.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see GraphicsPanel
 * @see SortingFunction
 */
public class ButtonsPanel extends JPanel {

    // The buttons of this panel
    private JButton scramble = new JButton("Scramble Lines");
    private JButton selection = new JButton("Selection Sort");
    private JButton merge = new JButton("Merge Sort");
    private JButton reset = new JButton("Reset");

    // Signals if a sorting method was done.
    private boolean selectionDone = false;
    private boolean mergeDone = false;

    /**
     * Default - Creates a panel with Flow Layout, and not double-buffered.
     */
    public ButtonsPanel() {
        super();
        commonConstructor();
    }

    /**
     * Creates a panel with a custom layout manager, but not double-buffered.
     *
     * @param layout The layout manager used to control the panel display.
     */
    public ButtonsPanel(LayoutManager layout) {
        super(layout);
        commonConstructor();
    }

    /**
     * Creates a panel with double-buffering on or off, flow layout manager used.
     *
     * @param isDoubleBuffered Specifies if double-buffering is on or off.
     */
    public ButtonsPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        commonConstructor();
    }

    /**
     * Creates a panel with a custom layout and double-buffering state.
     *
     * @param layout           The layout manager used to control the panel display.
     * @param isDoubleBuffered Specifies if double-buffering is on or off.
     */
    public ButtonsPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        commonConstructor();
    }

    /**
     * Static helper function to remove all the action listeners from a target.
     *
     * @param target The object that is to have its listeners removed.
     */
    private static synchronized void removeOldActionListeners(JButton target) {
        // Synchronized is set to prevent corruption of the listeners attached.

        // Get all listeners and remove them
        ActionListener[] listeners = target.getActionListeners();
        for (ActionListener al : listeners) {
            target.removeActionListener(al);
        }
    }

    /**
     * The common behaviour between the different types of constructors.
     */
    private void commonConstructor() {
        // Sets the preferred size of the panel
        setPreferredSize(new Dimension(640 / 5, 500)); // TODO: FIX HARDCODING!

        // Adds the buttons to this panel
        add(scramble);
        add(selection);
        add(merge);
        add(reset);

        // Sets the state of the buttons as appropriate
        selection.setEnabled(false);
        merge.setEnabled(false);
        reset.setEnabled(false);
    }

    /**
     * Instructs this panel which Graphics Panel it needs to control.
     *
     * @param target The Graphics Panel to be controlled.
     */
    public synchronized void setControlTarget(GraphicsPanel target) {
        // Synchronized is set to prevent corruption of the listeners attached.

        // Remove all action listeners from this object
        removeOldActionListeners(scramble);
        removeOldActionListeners(selection);
        removeOldActionListeners(merge);
        removeOldActionListeners(reset);

        // Add the new listeners
        scramble.addActionListener(new scrambleAction(target));
        selection.addActionListener(new sortAction(target, new SelectionSort<>()));
        merge.addActionListener(new sortAction(target, new MergeSort<>()));
        reset.addActionListener(new resetAction(target));
    }

    /**
     * Action listener class to trigger Graphics Panel's scramble() function.
     */
    private class scrambleAction implements ActionListener {
        private GraphicsPanel target;

        /**
         * Constructor - sets the Graphics Panel to be targeted by this action listener.
         *
         * @param target The graphics panel to be targeted.
         */
        public scrambleAction(GraphicsPanel target) {
            this.target = target;
        }

        /**
         * Set the buttons active state as appropriate and scramble graphics state.
         *
         * @param e The action event - ignored.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            target.scramble();

            // Set button states
            scramble.setEnabled(false);
            reset.setEnabled(false);
            selection.setEnabled(true);
            merge.setEnabled(true);

            selectionDone = false;
            mergeDone = false;
        }
    }

    /**
     * Action listener class to trigger Graphics Panel's reset() function.
     */
    private class resetAction implements ActionListener {
        private GraphicsPanel target;

        /**
         * Constructor - sets the Graphics Panel to be targeted by this action listener.
         *
         * @param target The graphics panel to be targeted.
         */
        public resetAction(GraphicsPanel target) {
            this.target = target;
        }

        /**
         * Set the button active state as appropriate and reset graphics state.
         *
         * @param e The action event - ignored.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            target.reset();

            // Set button states
            selection.setEnabled(true);
            merge.setEnabled(true);
            reset.setEnabled(false);
        }
    }

    /**
     * Action listener class to trigger Graphics Panel's sortRectangles() function.
     */
    private class sortAction implements ActionListener, Runnable {
        private GraphicsPanel target;
        private SortingFunction<ImprovedRectangle> sorter;

        /**
         * Constructor - sets the Graphics Panel to be targeted by this action listener, and the sorting function to use.
         *
         * @param target The graphics panel to be targeted.
         * @param sorter The sorting algorithm to use.
         */
        public sortAction(GraphicsPanel target, SortingFunction<ImprovedRectangle> sorter) {
            this.target = target;
            this.sorter = sorter;
        }

        /**
         * Set the button active state as appropriate and initiate rectangle sorting.
         *
         * @param e The action event - ignored.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Set button state
            scramble.setEnabled(false);
            selection.setEnabled(false);
            merge.setEnabled(false);
            reset.setEnabled(false);

            // Start sort
            Thread newThread = new Thread(this);
            newThread.start(); // This functionality has to be put inside a runnable object,
            // as it will otherwise clog up the event dispatch and painting thread
        }

        /**
         * Start the sorting and then change button states in a separate thread.
         */
        @Override
        public void run() {

            Thread thread = target.sortRectangles(sorter); // Start sorting
            try {
                // Wait for sorting to finish
                while (thread.isAlive()) {
                    Thread.sleep(5);
                }

                // Mark off the sorting type completed
                if (sorter.getSortType() == SortType.SELECTIONSORT)
                    selectionDone = true;
                else if (sorter.getSortType() == SortType.MERGESORT)
                    mergeDone = true;

                // Enable the scramble button again if both have been tried
                if (mergeDone && selectionDone) {
                    scramble.setEnabled(true);
                }

                // Renable the reset button
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
