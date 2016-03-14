package Sorts;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Interface to implement new sorting functions with for use in Graphics Panel.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see GUI.GraphicsPanel
 * @see SortType
 */
public interface SortingFunction<T extends Comparable<? super T>> extends Runnable {
    /**
     * Sort asynchronously with a target that needs to be repainted
     *
     * @param array     Array to be sorted.
     * @param begin     Index of the array to begin sorting at.
     * @param end       Index of the array to stop sorting at.
     * @param RWLock    Read-write lock of the array to be used when sorting asynchronously.
     * @param repainter JPanel to be repainted after each change to the array.
     * @return The thread representing the sorting algorithm in progress.
     */
    Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock, JPanel repainter);

    /**
     * Sort asynchronously
     *
     * @param array  Array to be sorted.
     * @param begin  Index of the array to begin sorting at.
     * @param end    Index of the array to stop sorting at.
     * @param RWLock Read-write lock of the array to be used when sorting asynchronously.
     * @return The thread representing the sorting algorithm in progress.
     */
    Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock);

    /**
     * Sort synchronously with a target that needs to be repainted
     *
     * @param array     Array to be sorted.
     * @param begin     Index of the array to begin sorting at.
     * @param end       Index of the array to stop sorting at.
     * @param repainter JPanel to be repainted after each change to the array.
     */
    void sort(ArrayList<T> array, int begin, int end, JPanel repainter);

    /**
     * Sort synchronously
     *
     * @param array Array to be sorted.
     * @param begin Index of the array to begin sorting at.
     * @param end   Index of the array to stop sorting at.
     */
    void sort(ArrayList<T> array, int begin, int end);

    /**
     * Return enum of the algorithm used in this Sorting Function
     *
     * @return The sorting algorithm used in this function.
     */
    SortType getSortType();
}
