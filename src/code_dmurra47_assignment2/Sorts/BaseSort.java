package code_dmurra47_assignment2.Sorts;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * The abstract base from which the supplied sorting algorithms extend. Implements common functionality.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see MergeSort
 * @see SelectionSort
 */
abstract class BaseSort<T extends Comparable<? super T>> implements SortingFunction<T> {

    protected ArrayList<T> array;  // Reference to array to be sorted.
    protected int begin;  // Beginning index of the array to be sorted.
    protected int end;  // Ending index of the array to be sorted.
    protected ReadWriteLock RWLock = null;  // Read-Write lock for the array.
    protected JPanel repainter = null;  // Panel to be repainted.

    /**
     * Function that is to be implemented by subclasses that implements the sorting functionality.
     */
    abstract protected void internalSort();

    /**
     * Runs the sorting algorithm asynchronously.
     * <i>Must not be called directly, use AsyncSort instead.</i>
     *
     * @throws UnsupportedOperationException
     */
    @Override
    public void run() {
        if (array != null && begin <= end && RWLock != null) {
            internalSort();
        } else {
            throw new UnsupportedOperationException("asyncSort was not called!");
        }
    }

    /**
     * Sort synchronously. Sets up for internalSort.
     *
     * @param array Array to be sorted.
     * @param begin Index of the array to begin sorting at.
     * @param end   Index of the array to stop sorting at.
     * @throws IllegalArgumentException
     */
    @Override
    public void sort(ArrayList<T> array, int begin, int end) {
        sort(array, begin, end, null);
    }

    /**
     * Sort synchronously with a target that needs to be repainted. Sets up for internalSort.
     *
     * @param array     Array to be sorted.
     * @param begin     Index of the array to begin sorting at.
     * @param end       Index of the array to stop sorting at.
     * @param repainter JPanel to be repainted after each change to the array.
     * @throws IllegalArgumentException
     */
    @Override
    public void sort(ArrayList<T> array, int begin, int end, JPanel repainter) {
        if (array != null && begin <= end) {
            // Set up internal variables
            this.array = array;
            this.begin = begin;
            this.end = end;
            this.repainter = repainter;

            // Start sort
            internalSort();
        } else
            throw new IllegalArgumentException("Invalid array or begin/end points!");
    }

    /**
     * Sort asynchronously with a target that needs to be repainted. Sets up for internalSort.
     *
     * @param array     Array to be sorted.
     * @param begin     Index of the array to begin sorting at.
     * @param end       Index of the array to stop sorting at.
     * @param RWLock    Read-write lock of the array to be used when sorting asynchronously.
     * @param repainter JPanel to be repainted after each change to the array.
     * @return The thread representing the sorting algorithm in progress.
     * @throws IllegalArgumentException
     */
    @Override
    public Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock, JPanel repainter) {
        if (array != null && RWLock != null && end >= begin) {
            // Set up internal variables
            this.array = array;
            this.begin = begin;
            this.end = end;
            this.RWLock = RWLock;
            this.repainter = repainter;
        } else {
            throw new IllegalArgumentException("Either array == null, no read/write lock was specified, or begin < end.");
        }

        // Start sort asynchronously.
        Thread runningThread = new Thread(this);
        runningThread.start();
        return runningThread;
    }

    /**
     * Sort asynchronously. Sets up for internalSort.
     *
     * @param array  Array to be sorted.
     * @param begin  Index of the array to begin sorting at.
     * @param end    Index of the array to stop sorting at.
     * @param RWLock Read-write lock of the array to be used when sorting asynchronously.
     * @return The thread representing the sorting algorithm in progress.
     * @throws IllegalArgumentException
     */
    @Override
    public Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock) {
        return asyncSort(array, begin, end, RWLock, null);
    }

    /**
     * Repaints the targeted Panel.
     *
     * @param message The message to be displayed if InterruptedException is thrown.
     */
    protected void repainting(String message) {
        if (repainter != null) {
            // If repainting is turned on
            if (RWLock != null) {
                // If asynchronously, repaint normally.
                repainter.repaint();
            } else {
                // If synchronously, repaint now.
                repainter.paintImmediately(repainter.getVisibleRect());
            }

            // Sleep a little bit so users can see the sort in action
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(message);
            }
        }
    }
}
