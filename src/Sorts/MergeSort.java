package Sorts;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;

/**
 * In-place merge sort implementation extended from BaseSort.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see BaseSort
 * @see SortingFunction
 */
public class MergeSort<T extends Comparable<? super T>> extends BaseSort<T> {

    private Lock read = null;
    private Lock write = null;

    /**
     * Returns algorithm name.
     *
     * @return Returns algorithm name.
     */
    @Override
    public SortType getSortType() {
        return SortType.MERGESORT;
    }

    /**
     * Sorting function for this sorting algorithm.
     */
    @Override
    protected void internalSort() {
        if (RWLock != null) {
            read = RWLock.readLock();
            write = RWLock.writeLock();
        }

        internalSortHelper(new Bounds(begin, end));
    }

    /**
     * Helper function for this sorting algorithm.
     *
     * @param boundary Represents the beginning and end bounds of the array that this function is allowed to touch.
     * @return The bounds that hold the sorted elements.
     */
    private Bounds internalSortHelper(Bounds boundary) {
        // Base case, return.
        if (boundary.range() < 2) {
            return boundary;
        } else {

            // Split the array in half an sort each part.
            int partition = (boundary.end + boundary.begin) / 2;

            Bounds left = internalSortHelper(new Bounds(boundary.begin, partition));
            Bounds right = internalSortHelper(new Bounds(partition, boundary.end));

            // Return the merged result of the sorted left and right arrays.
            return merge(left, right);
        }
    }

    /**
     * Merges two arrays in a sorted fashion.
     *
     * @param bLeft  Left array
     * @param bRight Right array
     * @return Returns bounds of sorted array elements.
     */
    private Bounds merge(Bounds bLeft, Bounds bRight) {
        ArrayList<T> leftArray = new ArrayList<>();
        ArrayList<T> rightArray = new ArrayList<>();

        if (RWLock != null)
            read.lock();

        // copy elements to be sorted into temporary arrays
        for (int i = bLeft.begin; i < bLeft.end; i++)
            leftArray.add(array.get(i));
        for (int i = bRight.begin; i < bRight.end; i++)
            rightArray.add(array.get(i));

        if (RWLock != null)
            read.unlock();


        // Position tracking iterators
        ListIterator<T> leftPointer = leftArray.listIterator();
        ListIterator<T> rightPointer = rightArray.listIterator();
        ListIterator<T> result = array.listIterator(bLeft.begin);
        result.next(); // Just to get it ready

        // Find the smallest of each array repeatedly and copy it into the result array
        while (leftPointer.hasNext() && rightPointer.hasNext()) {
            T leftComparator = leftPointer.next();
            T rightComparator = rightPointer.next();
            T valueToWrite;


            if (leftComparator.compareTo(rightComparator) < 0) {
                valueToWrite = leftComparator;
                rightPointer.previous();
            } else {
                valueToWrite = rightComparator;
                leftPointer.previous();
            }

            if (RWLock != null)
                write.lock();

            // Write value to result array
            result.set(valueToWrite);
            result.next();

            if (RWLock != null)
                write.unlock();

            // Paint the new state of the result array
            repainting("Interrupted Exception, Merge Sort.");

        }

        if (RWLock != null)
            write.lock();

        // Write any remaining elements into the result array.
        while (leftPointer.hasNext()) {
            result.set(leftPointer.next());

            if (result.hasNext())
                result.next();
        }
        while (rightPointer.hasNext()) {
            result.set(rightPointer.next());

            if (result.hasNext())
                result.next();
        }
        if (RWLock != null)
            write.unlock();

        // Paint the new state of the result array
        repainting("Interrupted Exception, Merge Sort.");

        // Return the sorted array bounds.
        return new Bounds(bLeft.begin, bRight.end);
    }
}
