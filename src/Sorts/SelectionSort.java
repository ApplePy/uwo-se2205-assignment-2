package Sorts;

import java.util.concurrent.locks.Lock;

/**
 * Created by darryl on 2016-03-11.
 */
public class SelectionSort<T extends Comparable<? super T>> extends BaseSort<T> {

    private Lock read = null;
    private Lock write = null;

    @Override
    public SortType getSortType() {
        return SortType.SELECTIONSORT;
    }

    protected void internalSort() {
        if (RWLock != null) {
            read = RWLock.readLock();
            write = RWLock.writeLock();
        }
        for (int i = begin; i < end && i < array.size(); i++) {
            if (RWLock != null)
                read.lock();

            int smallest = findSmallest(i, end);

            if (RWLock != null) {
                read.unlock();
                write.lock();
            }

            swap(i, smallest);

            if (RWLock != null)
                write.unlock();

            repainting("Interrupted Exception, Selection Sort.");
        }
    }

    private int findSmallest(int start, int end) {
        int smallest = start;

        for (int iter = start; iter < end && iter < array.size(); iter++) {
            if (array.get(iter).compareTo(array.get(smallest)) < 0)
                smallest = iter;
        }

        return smallest;
    }

    private void swap(int pos1, int pos2) {
        T temp = array.get(pos1);
        array.set(pos1, array.get(pos2));
        array.set(pos2, temp);
    }
}
