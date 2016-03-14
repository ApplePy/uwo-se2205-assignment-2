package code_dmurra47_assignment2.Sorts;

/**
 * A simple class to represent the beginning and end of a range. Used for MergeSort.
 *
 * @see MergeSort
 */
class Bounds {

    public int begin;
    public int end;

    /**
     * Constructor
     *
     * @param beginning Beginning of range.
     * @param ending    End of range.
     */
    Bounds(int beginning, int ending) {
        begin = beginning;
        end = ending;
    }

    /**
     * Return range of bounds.
     *
     * @return Returns range of the bounds.
     */
    int range() {
        return end - begin;
    }
}
