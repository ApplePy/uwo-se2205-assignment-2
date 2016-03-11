package Sorts;

/**
 * Created by darryl on 2016-03-11.
 */
class Bounds {
    public int begin;
    public int end;

    Bounds(int beginning, int ending) {
        begin = beginning;
        end = ending;
    }

    int size() {
        return end - begin;
    }
}
