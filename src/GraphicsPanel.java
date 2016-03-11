import Sorts.SortingFunction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.*;

/**
 * Created by darryl on 2016-03-10.
 */
public class GraphicsPanel extends JPanel {

    private ArrayList<ImprovedRectangle> rectangles;
    private ReentrantReadWriteLock rectLock = new ReentrantReadWriteLock();

    public GraphicsPanel() {
        rectangles = new ArrayList<>();
        reset();
    }

    public void reset() {
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
        try {
            if (rl.tryLock(5, TimeUnit.MILLISECONDS)) {
                for (ImprovedRectangle rect : rectangles) {
                    g.setColor(rect.getColor());
                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }
            }
            rl.unlock();
        } catch (InterruptedException e) {
            //IDK what to put in here
            //rl.unlock(); // TODO: fix this - not sure how to use locks in this scenario
        }
    }

    public void sortRectangles (SortingFunction<ImprovedRectangle> input) {

        /*
        TODO:
             what to accomplish - sort should run in its own thread, sorting slowly, yielding after every switch (optional).
             Meanwhile, the paint subsystem should be regularly called to display the results as a snapshot in time.
             This should serve to allow any generic sort to function properly with the results displayable.
         */
        if (input != null) {

            Thread newThread = input.asyncSort(rectangles, 0, rectangles.size(), rectLock, this);
        }
    }
}
