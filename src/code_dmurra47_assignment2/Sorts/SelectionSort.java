package code_dmurra47_assignment2.Sorts;

import java.util.concurrent.locks.Lock;

/**
 * Merge sort implementation extended from BaseSort.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see BaseSort
 * @see SortingFunction
 */
public class SelectionSort<T extends Comparable<? super T>> extends BaseSort<T> {

    private Lock read = null;
    private Lock write = null;

    /**
     * Returns algorithm name.
     *
     * @return Returns algorithm name.
     */
    @Override
    public SortType getSortType() {
        return SortType.SELECTIONSORT;
    }

    /**
     * Sorting function for this sorting algorithm.
     */
    protected void internalSort() {
        if (RWLock != null) {
            read = RWLock.readLock();
            write = RWLock.writeLock();
        }
        // While not done sorting the array
        for (int i = begin; i < end && i < array.size(); i++) {
            if (RWLock != null)
                read.lock();

            // Find the smallest element left
            int smallest = findSmallest(i, end);

            if (RWLock != null) {
                read.unlock();
                write.lock();
            }

            // Swap it with the first element of the unsorted section.
            swap(i, smallest);

            if (RWLock != null)
                write.unlock();

            repainting("Interrupted Exception, Selection Sort.");
        }
    }

    /**
     * Finds the smallest element between two bounds.
     *
     * @param start Starting index to search.
     * @param end   Ending index to search
     * @return Returns the index of the smallest element.
     */
    private int findSmallest(int start, int end) {
        int smallest = start; // Holds the smallest element in the array

        // Compares each element of the array with the smallest one found to see if it's smaller
        for (int iter = start; iter < end && iter < array.size(); iter++) {
            if (array.get(iter).compareTo(array.get(smallest)) < 0)
                smallest = iter;
        }

        return smallest;
    }

    /**
     * Swap two element locations.
     *
     * @param pos1 First position to swap.
     * @param pos2 Second position to swap.
     */
    private void swap(int pos1, int pos2) {
        T temp = array.get(pos1);
        array.set(pos1, array.get(pos2));
        array.set(pos2, temp);
    }
}
