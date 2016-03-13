package GUI;

import java.awt.*;
import java.util.Random;

/**
 * Created by darryl on 2016-03-10.
 */
public class ImprovedRectangle extends Rectangle implements Comparable<ImprovedRectangle> {

    public static int maxRandomHeight = 300;
    private Color rectangleColor;

    public ImprovedRectangle() {
        super(randomHeight());
        rectangleColor = randomColour();
    }

    public ImprovedRectangle(ImprovedRectangle r) {
        super(r);
        rectangleColor = randomColour();
    }

    public ImprovedRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
        rectangleColor = randomColour();
    }

    public ImprovedRectangle(int width, int height) {
        super(width, height);
        rectangleColor = randomColour();
    }

    public ImprovedRectangle(Point p, Dimension d) {
        super(p, d);
        rectangleColor = randomColour();
    }

    public ImprovedRectangle(Point p) {
        super(p);
        rectangleColor = randomColour();
    }

    public ImprovedRectangle(Dimension d) {
        super(d);
        rectangleColor = randomColour();
    }

    private static Color randomColour() {
        Random rand = new Random();
        return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    private static Dimension randomHeight() {
        Random rnd = new Random();
        return new Dimension(1, rnd.nextInt(maxRandomHeight));
    }

    public Color getColor() {
        return new Color(rectangleColor.getRGB());
    }

    public int compareTo(ImprovedRectangle other) {
        return height - other.height;
    }
}
