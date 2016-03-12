package GUI;

import Sorts.SortingFunction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.*;

/**
 * Created by darryl on 2016-03-10.
 */
public class GraphicsPanel extends JPanel {

    private ArrayList<ImprovedRectangle> rectangles;
    private ReentrantReadWriteLock rectLock = new ReentrantReadWriteLock();

    public GraphicsPanel() {
        super();
        rectangles = new ArrayList<>();
    }

    public GraphicsPanel(LayoutManager layout) {
        super (layout);
        rectangles = new ArrayList<>();
    }

    public GraphicsPanel(boolean isDoubleBuffered) {
        super (isDoubleBuffered);
        rectangles = new ArrayList<>();
    }

    public GraphicsPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        rectangles = new ArrayList<>();
    }

    public void reset() {

        ImprovedRectangle.maxRandomHeight = getHeight() * 3 / 5;

        WriteLock wLock = rectLock.writeLock();
        if (wLock.tryLock()) {
            for (int i = 0; i < 256; i++) {
                rectangles.add(new ImprovedRectangle());
            }
            wLock.unlock();
        }
        else
            throw new RuntimeException("This should not happen...");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ReadLock rl = rectLock.readLock();

        rl.lock();
        int width = getWidth() / rectangles.size();   // Not allowed to resize the rectangles themselves...
        int startPos = (getWidth() - (rectangles.size() * width)) / 2;
        int counter = 0;

        for (ImprovedRectangle rect : rectangles) {
            g.setColor(rect.getColor());
            g.fillRect(counter++ * width + startPos, getHeight() * 4 / 5 - rect.height, rect.width, rect.height);
        }
        rl.unlock();
    }

    public Thread sortRectangles (SortingFunction<ImprovedRectangle> input) {
        if (input != null) {
            if (rectangles.size() == 0)
                reset();

            return input.asyncSort(rectangles, 0, rectangles.size(), rectLock, this);
        }
        else {
            throw new IllegalArgumentException("A sorting function must be supplied!");
        }
    }
}
