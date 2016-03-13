package Sorts;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by darryl on 2016-03-10.
 */
public interface SortingFunction<T extends Comparable<? super T>> extends Runnable {
    // Sort asynchronously with a target that needs to be repainted
    Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock, JPanel repainter);

    // Sort asynchronously
    Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock);

    // Sort synchronously with a target that needs to be repainted
    void sort(ArrayList<T> array, int begin, int end, JPanel repainter);

    // Sort synchronously
    void sort(ArrayList<T> array, int begin, int end);

    // Return enum of the algorithm used in this Sorting Function
    SortType getSortType();
}
