package GUI;

import Sorts.SortingFunction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * Graphics Panel controls the drawing of the visualization of an algorithm at work.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see ImprovedRectangle
 * @see SortingFunction
 */
public class GraphicsPanel extends JPanel {

    /**
     * Holds the original random order of the generated rectangles, for algorithm comparison.
     */
    private ArrayList<ImprovedRectangle> rectanglesOriginal = new ArrayList<>();
    /**
     * <i>Multithreaded access</i>. Holds the order of the rectangles being sorted for sorting algorithm and painting code access.
     */
    private ArrayList<ImprovedRectangle> rectangles = new ArrayList<>();
    /**
     * The master lock for synchronizing thread access to "rectangles" member.
     */
    private ReentrantReadWriteLock rectLock = new ReentrantReadWriteLock();

    /**
     * Default - creates a panel with a flow layout, and not double buffered.
     */
    public GraphicsPanel() {
        super();
        commonConstructor();
    }

    /**
     * Creates a panel with a custom layout manager, but not double-buffered.
     *
     * @param layout The layout manager used to control the panel display.
     */
    public GraphicsPanel(LayoutManager layout) {
        super(layout);
        commonConstructor();
    }

    /**
     * Creates a panel with double-buffering on or off, flow layout manager used.
     *
     * @param isDoubleBuffered Specifies if double-buffering is on or off.
     */
    public GraphicsPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        commonConstructor();
    }

    /**
     * Creates a panel with a custom layout and double-buffering state.
     *
     * @param layout           The layout manager used to control the panel display.
     * @param isDoubleBuffered Specifies if double-buffering is on or off.
     */
    public GraphicsPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        commonConstructor();
    }

    /**
     * The common behaviour between the different types of constructors.
     */
    private void commonConstructor() {
        // Sets the preferred size of this panel
        setPreferredSize(new Dimension(640 * 4 / 5, 500)); // TODO: FIX HARDCODING!

        // Set title text and size
        JLabel title = new JLabel("Visualization Area");
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 40));

        // Add title to panel
        add(title);

    }

    /**
     * Panel panting code.
     *
     * @param g Passed in from Swing calls, specifies the painting Graphics to use.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ReadLock rl = rectLock.readLock();  // Acquire the read lock object for the rectangles object.

        rl.lock();  // Lock the rectangles array for reading

        // Set up the width of the space between rectangles, the start position inside the panel for the rectangles,
        // and a counter to properly distribute the triangles along the x-axis.
        int numberOfRects = (rectangles.size() <= 0) ? 256 : rectangles.size(); // Eliminating a possible divide-by-zero
        int width = (getWidth() / numberOfRects > 0) ? getWidth() / numberOfRects : 1;   // Prevent widths of zero
        int startPos = (getWidth() - (numberOfRects * width)) / 2;
        int counter = 0;

        // Display all the rectangles equally spaced across the panel screen, with a common bottom y-coordinate.
        // Also resizes the height of rectangles displayed if the screen's dimensions have been changed mid-sort.
        for (ImprovedRectangle rect : rectangles) {
            g.setColor(rect.getRectColour());
            g.fillRect(
                    counter++ * width + startPos,
                    getHeight() * 4 / 5 - rect.height * getHeight() * 3 / 5 / ImprovedRectangle.getMaxRandomHeight(),
                    rect.width,
                    rect.height * getHeight() * 3 / 5 / ImprovedRectangle.getMaxRandomHeight()
            );
            // TODO: Clean up fillRect - it's messy.
        }

        rl.unlock(); // Reading and painting complete, unlock rectangles array
    }

    /**
     * Create a new array of scrambled rectangles, clearing out the old rectangles.
     * Also resets the max height of ImprovedRectangles to the max that this window can support.
     * Set to synchronized so that simultaneous calls to scramble() doesn't damage the array.
     */
    public synchronized void scramble() {
        // Clear out old rectangles from everywhere
        rectanglesOriginal.clear();

        // Set new max height for generated rectangles.
        ImprovedRectangle.setMaxRandomHeight(getHeight() * 3 / 5);

        for (int i = 0; i < 256; i++) {
            rectanglesOriginal.add(new ImprovedRectangle()); // No write lock needed, rectanglesOriginal never leaves class
        }
        reset();  // Populate rectangles array with new rectangles
    }

    /**
     * Initial Setup - creates an array of rectangles, and then sorts them non-visually into the opening triangle.
     *
     * @param input The sorting method to use for the opening sort.
     * @return Returns the thread that is currently doing the opening sort.
     */
    public Thread setup(SortingFunction<ImprovedRectangle> input) {
        scramble();
        return sortRectangles(input, false);
    }

    /**
     * Create copies array of scrambled rectangles, clearing out the old rectangles.
     */
    public void reset() {
        //Note: Set to synchronized unnecessary; lock will handle dealing with simultaneous calls.


        // If scramble was called to initialize rectanglesOriginal
        if (rectanglesOriginal.size() > 0) {
            // Lock the rectangles array, empty it of the sorted ordering, and repopulate with unsorted ordering.
            rectLock.writeLock().lock();

            rectangles.clear();
            for (ImprovedRectangle rect : rectanglesOriginal)
                rectangles.add(rect);

            rectLock.writeLock().unlock();  // Writing complete, unlock

            repaint();  // Trigger repaint of new ordering.
        } else
            scramble();  // Call scramble to initialize rectanglesOriginal
    }

    /**
     * Sorts rectangles without needing to attach a repaint destination (and associated delays).
     *
     * @param input The sorting function to be used. (Must not be null)
     * @return Returns the thread representing the sorting algorithm in progress.
     */
    public Thread sortRectangles(SortingFunction<ImprovedRectangle> input) {
        return sortRectangles(input, true);
    }

    /**
     * Sorts rectangles and triggers repaints after each move.
     *
     * @param input   The sorting algorithm to be used.
     * @param repaint The panel to be repainted during the action of the sorting algorithm.
     * @return The thread representing the sorting in progress.
     * @throws IllegalArgumentException
     */
    private Thread sortRectangles(SortingFunction<ImprovedRectangle> input, boolean repaint) {
        // Check that input is not null.
        if (input != null) {
            JPanel panel = null;

            // If repaint was requested, set the panel to be repainted to this panel
            if (repaint)
                panel = this;

            // If rectangles is empty, the array needs to be reset.
            rectLock.readLock().lock();
            int rectanglesSize = rectangles.size();
            rectLock.readLock().unlock();

            if (rectanglesSize == 0)
                reset();

            // Start sort
            return input.asyncSort(rectangles, 0, rectanglesSize, rectLock, panel);
        } else {
            throw new IllegalArgumentException("A sorting function must be supplied!");
        }
    }
}
