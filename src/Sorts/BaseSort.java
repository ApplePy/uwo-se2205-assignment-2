package Sorts;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by darryl on 2016-03-11.
 */
abstract class BaseSort<T extends Comparable<? super T>> implements SortingFunction<T> {

    protected ArrayList<T> array;
    protected int begin;
    protected int end;
    protected ReadWriteLock RWLock = null;
    protected JPanel repainter = null;

    abstract protected void internalSort();

    @Override
    public void run() {
        if (array != null && begin <= end && RWLock != null) {
            internalSort();
        } else {
            throw new UninitializedException("asyncSort was not called first!");
        }
    }

    @Override
    public void sort(ArrayList<T> array, int begin, int end) {
        sort(array, begin, end, null);
    }

    @Override
    public void sort(ArrayList<T> array, int begin, int end, JPanel repainter) {
        if (array != null && begin <= end) {
            this.array = array;
            this.begin = begin;
            this.end = end;
            this.repainter = repainter;
            internalSort();
        } else
            throw new IllegalArgumentException("Invalid array or begin/end points!");
    }

    @Override
    public Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock, JPanel repainter) {
        if (array != null && RWLock != null && end >= begin) {
            this.array = array;
            this.begin = begin;
            this.end = end;
            this.RWLock = RWLock;
            this.repainter = repainter;
        } else {
            throw new IllegalArgumentException("Either array == null, no read/write lock was specified, or begin < end.");
        }
        Thread runningThread = new Thread(this);
        runningThread.start();
        return runningThread;
    }

    @Override
    public Thread asyncSort(ArrayList<T> array, int begin, int end, ReadWriteLock RWLock) {
        return asyncSort(array, begin, end, RWLock, null);
    }

    protected void repainting(String message) {
        if (repainter != null) {
            if (RWLock != null)
                repainter.repaint();
            else
                repainter.paintImmediately(repainter.getVisibleRect());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(message);
            }
        }
    }
}
