package GUI;

import Sorts.SortingFunction;
import Sorts.MergeSort;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.*;

/**
 * Created by darryl on 2016-03-10.
 */
public class GraphicsPanel extends JPanel {

    private ArrayList<ImprovedRectangle> rectanglesOriginal;
    private ArrayList<ImprovedRectangle> rectangles;
    private ReentrantReadWriteLock rectLock = new ReentrantReadWriteLock();

    public GraphicsPanel () {
        super();
        commonConstructor();
    }
    public GraphicsPanel ( LayoutManager layout) {
        super (layout);
        commonConstructor();
    }
    public GraphicsPanel ( boolean isDoubleBuffered) {
        super (isDoubleBuffered);
        commonConstructor();
    }
    public GraphicsPanel ( LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        commonConstructor();
    }

    private void commonConstructor() {
        rectangles = new ArrayList<>();
        rectanglesOriginal = new ArrayList<>();
        setPreferredSize(new Dimension(640 * 4 / 5, 500)); // TODO: FIX HARDCODING!
        add(new JLabel("Visualization Area"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ReadLock rl = rectLock.readLock();

        rl.lock();
        // TODO: Possible divide-by-zero error
        int width = getWidth() / rectangles.size();   // Not allowed to resize the rectangles themselves...
        int startPos = (getWidth() - (rectangles.size() * width)) / 2;
        int counter = 0;

        for (ImprovedRectangle rect : rectangles) {
            g.setColor(rect.getColor());
            g.fillRect(counter++ * width + startPos, getHeight() * 4 / 5 - rect.height, rect.width, rect.height);
        }
        rl.unlock();
    }

    public void scramble() {
        rectanglesOriginal.clear();
        rectangles.clear();

        ImprovedRectangle.maxRandomHeight = getHeight() * 3 / 5;

        for (int i = 0; i < 256; i++) {
            rectanglesOriginal.add(new ImprovedRectangle()); // No write lock needed, rectanglesOriginal never leaves class
        }
        reset();
    }

    public Thread setup(SortingFunction<ImprovedRectangle> input) {
        scramble();
        return sortRectangles(input, false);
    }

    public void reset() {
        if (rectanglesOriginal.size() > 0) {
            WriteLock wLock = rectLock.writeLock();
            wLock.lock();
            rectangles = (ArrayList) rectanglesOriginal.clone();
            wLock.unlock();
            repaint();
        } else
            scramble();
    }

    public Thread sortRectangles(SortingFunction<ImprovedRectangle> input) {
        return sortRectangles(input, true);
    }

    private Thread sortRectangles (SortingFunction<ImprovedRectangle> input, boolean repaint) {
        if (input != null) {
            JPanel panel = null;

            if (rectangles.size() == 0)
                reset();

            if (repaint)
                panel = this;

            return input.asyncSort(rectangles, 0, rectangles.size(), rectLock, panel);
        }
        else {
            throw new IllegalArgumentException("A sorting function must be supplied!");
        }
    }
}
