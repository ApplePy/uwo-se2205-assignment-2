import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.util.Random;
import static java.lang.Math.abs;

/**
 * Created by darryl on 2016-03-10.
 */
public class ImprovedRectangle extends Rectangle implements Comparable<ImprovedRectangle> {

    private Color rectangleColor = Color.RED;

    public ImprovedRectangle() {
        super();
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

    public Color getColor() {
        return new Color (rectangleColor.getRGB());
    }

    private static Color randomColour() {
        Random rand = new Random();
        return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    public int compareTo(ImprovedRectangle other) {
        return abs(height - other.height);
    }
}
