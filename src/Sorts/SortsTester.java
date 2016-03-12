package Sorts;

import GUI.ImprovedRectangle;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by darryl on 2016-03-11.
 */
public class SortsTester {

    public static void main(String[] args) {

        int length = 3000;

        System.out.println("Testing Selection Sort - Synchronous");
        testHelper(new SelectionSort<>(), length, false);

        System.out.println("Testing Merge Sort - Synchronous");
        testHelper(new MergeSort<>(), length, false);

        System.out.println("Testing Selection Sort - Asynchronous");
        testHelper(new SelectionSort<>(), length, true);

        System.out.println("Testing Merge Sort - Asynchronous");
        testHelper(new MergeSort<>(), length, true);

    }

    public static void testHelper(SortingFunction<ImprovedRectangle> sorter, int length, boolean asynchronous) {
        //ArrayList<Integer> array = SortsTester.arrayGenerator(length);
        ArrayList<ImprovedRectangle> array = new ArrayList<>();
        for (int i = 0; i < length; i++)
            array.add(new ImprovedRectangle());
        //printArray(array);

        if (!asynchronous)
            sorter.sort(array, 0, array.size());
        else {
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            Thread res = sorter.asyncSort(array, 0, array.size(), lock);

            Instant startTime = Instant.now();
            System.out.print("Waiting...");
            while (res.isAlive()) {
                //System.out.print (".");
                try {Thread.sleep(1);} catch (InterruptedException e) {System.out.println ("Exception");}
            }
            System.out.println(" Took (milliseconds): " + (Duration.between(startTime, Instant.now()).toMillis()));
        }

        //printArray(array);

        System.out.println(sortedValidator(array, true));
        System.out.println("");
    }

    public static boolean sortedValidator(ArrayList<ImprovedRectangle> array, boolean dummy) {
        boolean result = true;
        ListIterator<ImprovedRectangle> i = array.listIterator();
        ImprovedRectangle prevVal = i.next();

        while (i.hasNext()) {
            ImprovedRectangle nextVal = i.next();
            if (prevVal.compareTo(nextVal) > 0) {
                result = false;
                break;
            }
            prevVal = nextVal;
        }

        return result;
    }

    public static boolean sortedValidator(ArrayList<Integer> array) {
        boolean result = true;
        ListIterator<Integer> i = array.listIterator();
        Integer prevVal = i.next();

        while (i.hasNext()) {
            Integer nextVal = i.next();
            if (prevVal > nextVal) {
                result = false;
                break;
            }
            prevVal = nextVal;
        }

        return result;
    }

    public static ArrayList<Integer> arrayGenerator(int length) {
        Random rnd = new Random();

        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < length; i++)
            result.add(rnd.nextInt(1000));

        return result;
    }

    public static void printArray(ArrayList<Integer> array) {
        System.out.print('[');
        ListIterator<Integer> i = array.listIterator();
        while (i.hasNext()) {
                System.out.print(i.next());
            if (i.hasNext())
                System.out.print(",");
        }
        System.out.println(']');
    }
}
