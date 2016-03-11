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
            /*ArrayList<T> leftArray = new ArrayList<>();
            ArrayList<T> rightArray = new ArrayList<>();

            for (int i = 0; i < subArray.size() / 2; i++) {
                leftArray.add(subArray.get(i));
            }

            for (int i = subArray.size() / 2; i < subArray.size(); i++) {
                rightArray.add(subArray.get(i));
            }*/

            int partition = (boundary.end + boundary.begin) / 2;

            Bounds left = internalSortHelper(new Bounds(boundary.begin, partition));
            Bounds right = internalSortHelper(new Bounds(partition, boundary.end));

            return merge(left, right);
        }
    }

    private Bounds merge(Bounds bLeft, Bounds bRight) {
        ArrayList<T> leftArray = new ArrayList<>();
        ArrayList<T> rightArray = new ArrayList<>();

        for (int i = bLeft.begin; i < bLeft.end; i++)
            leftArray.add(array.get(i));
        for (int i = bRight.begin; i < bRight.end; i++)
            rightArray.add(array.get(i));


        ListIterator<T> leftPointer = leftArray.listIterator();
        ListIterator<T> rightPointer = rightArray.listIterator();
        ListIterator<T> result = array.listIterator(bLeft.begin);
        result.next(); // Just to get it ready

        while (leftPointer.hasNext() && rightPointer.hasNext()) {
            T leftComparator = leftPointer.next();
            T rightComparator = rightPointer.next();

            if (leftComparator.compareTo(rightComparator) < 0) {
                result.set(leftComparator);
                result.next();
                rightPointer.previous();
            } else {
                result.set(rightComparator);
                result.next();
                leftPointer.previous();
            }
        }

        while (leftPointer.hasNext()) {
            result.set(leftPointer.next());
        }
        while (rightPointer.hasNext()) {
                result.set(rightPointer.next());
        }

        return new Bounds(bLeft.begin, bRight.end);
    }
}
