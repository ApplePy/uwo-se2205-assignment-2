package Sorts;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;

/**
 * Created by darryl on 2016-03-11.
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

        internalSortHelper(array);
    }

    private ArrayList<T> internalSortHelper(ArrayList<T> subArray) {
        if(subArray.size() < 2){
            return subArray;
        }
        else {
            ArrayList<T> leftArray = new ArrayList<>();
            ArrayList<T> rightArray = new ArrayList<>();

            for (int i = 0; i < subArray.size() / 2; i++) {
                leftArray.add(subArray.get(i));
            }

            for (int i = subArray.size() / 2; i < subArray.size(); i++) {
                rightArray.add(subArray.get(i));
            }

            leftArray = internalSortHelper(leftArray);
            rightArray = internalSortHelper(rightArray);

            ArrayList<T> result = merge(leftArray, rightArray);
            return result;
        }
    }

    private ArrayList<T> merge(ArrayList<T> leftArray, ArrayList<T> rightArray) {
        ListIterator<T> leftPointer = leftArray.listIterator();
        ListIterator<T> rightPointer = rightArray.listIterator();
        ArrayList<T> result = new ArrayList<>();

        while (leftPointer.hasNext() && rightPointer.hasNext()) {
            T leftComparator = leftPointer.next();
            T rightComparator = rightPointer.next();

            if (leftComparator.compareTo(rightComparator) < 0) {
                result.add(leftComparator);
                rightPointer.previous();
            } else {
                result.add(rightComparator);
                leftPointer.previous();
            }
        }

        while (leftPointer.hasNext()) {
            result.add(leftPointer.next());
        }
        while (rightPointer.hasNext()) {
                result.add(rightPointer.next());
        }

        return result;
    }
}
