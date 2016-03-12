package Sorts;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;

/**
 * Created by darryl on 2016-03-11.
 *
 * I have to sort this in-place. -.-
 */
public class MergeSort<T extends Comparable<? super T>> extends BaseSort<T> {

    private Lock read = null;
    private Lock write = null;

    @Override
    protected void internalSort() {
        if (RWLock != null) {
            read = RWLock.readLock();
            write = RWLock.writeLock();
        }

        internalSortHelper(new Bounds(begin, end));
    }

    private Bounds internalSortHelper(Bounds boundary) {
        if(boundary.size() < 2){
            return boundary;
        }
        else {

            int partition = (boundary.end + boundary.begin) / 2;

            Bounds left = internalSortHelper(new Bounds(boundary.begin, partition));
            Bounds right = internalSortHelper(new Bounds(partition, boundary.end));

            return merge(left, right);
        }
    }

    private Bounds merge(Bounds bLeft, Bounds bRight) {
        ArrayList<T> leftArray = new ArrayList<>();
        ArrayList<T> rightArray = new ArrayList<>();

        if (RWLock != null)
            read.lock();

        for (int i = bLeft.begin; i < bLeft.end; i++)
            leftArray.add(array.get(i));
        for (int i = bRight.begin; i < bRight.end; i++)
            rightArray.add(array.get(i));

        if (RWLock != null)
            read.unlock();


        ListIterator<T> leftPointer = leftArray.listIterator();
        ListIterator<T> rightPointer = rightArray.listIterator();
        ListIterator<T> result = array.listIterator(bLeft.begin);
        result.next(); // Just to get it ready

        while (leftPointer.hasNext() && rightPointer.hasNext()) {
            T leftComparator = leftPointer.next();
            T rightComparator = rightPointer.next();
            T valueToWrite = null;



            if (leftComparator.compareTo(rightComparator) < 0) {
                valueToWrite = leftComparator;
                rightPointer.previous();
            } else {
                valueToWrite = rightComparator;
                leftPointer.previous();
            }

            if (RWLock != null)
                write.lock();

            result.set(valueToWrite);
            result.next();

            if (RWLock != null)
                write.unlock();

            repainting("Interrupted Exception, Merge Sort.");

        }

        if (RWLock != null)
            write.lock();

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

        repainting("Interrupted Exception, Merge Sort.");


        return new Bounds(bLeft.begin, bRight.end);
    }
}
