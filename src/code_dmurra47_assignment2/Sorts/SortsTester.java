package code_dmurra47_assignment2.Sorts;

import code_dmurra47_assignment2.GUI.ImprovedRectangle;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Testing class to make sure that the sorting algorithms are behaving as expected.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see BaseSort
 * @see MergeSort
 * @see SelectionSort
 * @see SortingFunction
 */
class SortsTester {

    /**
     * Main function.
     *
     * @param args Starting arguments.
     */
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

    /**
     * Arranges random arrays and then times the progress, and checks for correctly.
     *
     * @param sorter       The sorting function to be tested.
     * @param length       The length of the testing arrays to be created.
     * @param asynchronous Test synchronous function or asynchronous function.
     */
    public static void testHelper(SortingFunction<ImprovedRectangle> sorter, int length, boolean asynchronous) {

        // Generate the array
        //ArrayList<Integer> array = SortsTester.arrayGenerator(length);
        ArrayList<ImprovedRectangle> array = new ArrayList<>();
        for (int i = 0; i < length; i++)
            array.add(new ImprovedRectangle());
        //printArray(array);

        // Sort the array
        if (!asynchronous)
            sorter.sort(array, 0, array.size());
        else {
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            Thread res = sorter.asyncSort(array, 0, array.size(), lock);

            // Time the array sorting
            Instant startTime = Instant.now();
            System.out.print("Waiting...");
            try {
                res.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted exception, test helper.");
            }

            System.out.println(" Took (milliseconds): " + (Duration.between(startTime, Instant.now()).toMillis()));
        }

        //printArray(array);

        // Validate array
        System.out.println(sortedValidator(array, true));
        System.out.println("");
    }

    /**
     * Validates if ImprovedRectangle sorted correctly.
     *
     * @param array The array to check.
     * @param dummy Ignored.
     * @return Returns if the array is sorted correctly.
     */
    public static boolean sortedValidator(ArrayList<ImprovedRectangle> array, boolean dummy) {
        boolean result = true;
        ListIterator<ImprovedRectangle> i = array.listIterator();
        ImprovedRectangle prevVal = i.next();

        // If a value is out of place, signal false and break
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

    /**
     * Validates if integers are sorted correctly.
     *
     * @param array The array to check.
     * @return Returns if the array is sorted correctly.
     */
    public static boolean sortedValidator(ArrayList<Integer> array) {
        boolean result = true;
        ListIterator<Integer> i = array.listIterator();
        Integer prevVal = i.next();

        // If a value is out of place, signal false and break
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

    /**
     * Generates an array of integers.
     *
     * @param length The length of the array to make.
     * @return Returns a random array of integers.
     */
    public static ArrayList<Integer> arrayGenerator(int length) {
        Random rnd = new Random();

        ArrayList<Integer> result = new ArrayList<>();

        // Generate new numbers
        for (int i = 0; i < length; i++)
            result.add(rnd.nextInt(1000));

        return result;
    }

    /**
     * Prints out the contents of an array of integers
     *
     * @param array The array to be printed.
     */
    public static void printArray(ArrayList<Integer> array) {
        System.out.print('[');
        ListIterator<Integer> i = array.listIterator();

        // Iterate through array, printing out value.
        while (i.hasNext()) {
            System.out.print(i.next());
            if (i.hasNext())
                System.out.print(",");
        }
        System.out.println(']');
    }
}
