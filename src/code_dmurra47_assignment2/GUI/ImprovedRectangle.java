package code_dmurra47_assignment2.GUI;

import java.awt.*;
import java.util.Random;

/**
 * Extends the Rectangle class to contain color information and the ability to easily compare rectangles by height.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see Rectangle
 */
public class ImprovedRectangle extends Rectangle implements Comparable<ImprovedRectangle> {

    /**
     * Specifies the maximum height that randomly-generated ImprovedRectangles can be.
     */
    private static int maxRandomHeight = 300;

    private Color rectangleColor;

    /**
     * Default - creates an ImprovedRectangle with a random colour and random height < maxRandomHeight, at (0,0).
     * <i>See maxRandomHeight for controlling the random height.</i>
     */
    public ImprovedRectangle() {
        super(randomHeight());
        rectangleColor = randomColour();
    }

    /**
     * Creates a copy of another ImprovedRectangle with a new colour, at (0,0).
     *
     * @param rectangle The rectangle whose dimensions are to be copied
     */
    public ImprovedRectangle(ImprovedRectangle rectangle) {
        super(rectangle);
        rectangleColor = randomColour();
    }

    /**
     * Creates a new ImprovedRectangle with a specified location, dimension and a random colour.
     *
     * @param x      The top-left corner x-coordinate of the new rectangle.
     * @param y      The top-left corner y-coordinate of the new rectangle.
     * @param width  The width of the new rectangle.
     * @param height The height of the new rectangle.
     */
    public ImprovedRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
        rectangleColor = randomColour();
    }

    /**
     * Creates a new rectangle with the specified dimension with a random colour, at (0,0).
     *
     * @param width  The width of the new rectangle.
     * @param height The height of the new rectangle.
     */
    public ImprovedRectangle(int width, int height) {
        super(width, height);
        rectangleColor = randomColour();
    }

    /**
     * Creates a new ImprovedRectangle with a specified location, dimension and a random colour.
     *
     * @param p The coordinates for the top-left corner of the new rectangle.
     * @param d The dimensions of the new rectangle.
     */
    public ImprovedRectangle(Point p, Dimension d) {
        super(p, d);
        rectangleColor = randomColour();
    }

    /**
     * Creates a new rectangle with a random height at a specified location.
     *
     * @param p The coordinates for the top-left corner of the new rectangle.
     */
    public ImprovedRectangle(Point p) {
        super(p, randomHeight());
        rectangleColor = randomColour();
    }

    /**
     * Creates a new rectangle with the specified dimension, at (0,0).
     *
     * @param d The dimensions of the new rectangle.
     */
    public ImprovedRectangle(Dimension d) {
        super(d);
        rectangleColor = randomColour();
    }

    /**
     * A static helper function to generate a new random colour.
     *
     * @return A random colour.
     */
    private static Color randomColour() {
        Random rand = new Random();
        return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    /**
     * A static helper function to generate random dimensions for new rectangles.
     *
     * @return Dimensions with a width of 1 and a random height.
     */
    private static Dimension randomHeight() {
        Random rnd = new Random();
        return new Dimension(1, rnd.nextInt(maxRandomHeight));
    }

    /**
     * Static function; returns the maximum height that randomly-generated ImprovedRectangles will be.
     *
     * @return Returns the max height of random ImprovedRectangles
     */
    public static int getMaxRandomHeight() {
        return maxRandomHeight;
    }

    /**
     * Static function; sets the maximum random height. Must be above or equal to 1.
     *
     * @param newMax The new max height for randomly generated Rectangles.
     * @throws IllegalArgumentException
     */
    public static void setMaxRandomHeight(int newMax) {
        // Sanity-check to protect maxRandomHeight from being set badly.
        if (newMax >= 1)
            maxRandomHeight = newMax;
        else
            throw new IllegalArgumentException("New max height must be >= 1!");
    }

    /**
     * Gets the colour of the ImprovedRectangle.
     *
     * @return The colour of the ImprovedRectangle.
     */
    public Color getRectColour() {
        return new Color(rectangleColor.getRGB());  // Used to create a new copy
    }

    /**
     * Allows the colour of the ImprovedRectangle to be changed.
     *
     * @param newCol The new colour for the ImprovedRectangle.
     */
    public void setRectColour(Color newCol) {
        rectangleColor = new Color(newCol.getRGB());  // Used to create a new copy
    }

    /**
     * Allows ImprovedRectangles to be compared by their height.
     *
     * @param other The ImprovedRectangle to compare this rectangle to.
     * @return The difference between this rectangle's height and the other rectangle's height.
     */
    public int compareTo(ImprovedRectangle other) {
        return height - other.height;
    }
}
