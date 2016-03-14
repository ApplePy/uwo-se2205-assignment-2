package GUI;

import Sorts.MergeSort;

import javax.swing.*;
import java.awt.*;

/**
 * Main panel of the display window - manages the content and the sub-panels
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see GraphicsPanel
 * @see ButtonsPanel
 * @see Sorts.SortingFunction
 */

public class MainPanel extends JPanel {

    private GraphicsPanel graphicsPanel;
    private ButtonsPanel buttonsPanel;


    /**
     * Default - Creates a panel with Flow Layout, and not double-buffered.
     */
    public MainPanel() {
        super();
        commonConstructor();
    }

    /**
     * Creates a panel with a custom layout manager, but not double-buffered.
     *
     * @param layout The layout manager used to control the panel display.
     */
    public MainPanel(LayoutManager layout) {
        super(layout);
        commonConstructor();
    }

    /**
     * Creates a panel with double-buffering on or off, flow layout manager used.
     *
     * @param isDoubleBuffered Specifies if double-buffering is on or off.
     */
    public MainPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        commonConstructor();
    }

    /**
     * Creates a panel with a custom layout and double-buffering state.
     *
     * @param layout           The layout manager used to control the panel display.
     * @param isDoubleBuffered Specifies if double-buffering is on or off.
     */
    public MainPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        commonConstructor();
    }

    /**
     * The common behaviour between the different types of constructors.
     */
    private void commonConstructor() {
        setBackground(new Color(170, 170, 170));  // Sets background colour of panel

        // Initialize child panels
        LayoutManager gridLayout = new GridLayout(0, 1, 0, 5);
        graphicsPanel = new GraphicsPanel(true);
        buttonsPanel = new ButtonsPanel(gridLayout, false);

        // Add child panels to this panel
        add(graphicsPanel);
        add(buttonsPanel);

        buttonsPanel.setControlTarget(graphicsPanel);
    }

    /**
     * Signals graphics panel that the window is on-screen and it is now safe to draw the opening triangle.
     */
    void setupComplete() {
        graphicsPanel.setup(new MergeSort<>());
    }
}
