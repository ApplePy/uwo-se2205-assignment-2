package GUI;

import Sorts.MergeSort;

import javax.swing.*;
import java.awt.*;

import static java.awt.FlowLayout.LEFT;

/**
 * Created by darryl on 2016-03-10.
 */

public class MainPanel extends JPanel {

    private GraphicsPanel graphicsPanel;
    private ButtonsPanel buttonsPanel;
    

    public MainPanel() {
        super();
        commonConstructor();
    }
    public MainPanel(LayoutManager layout) {
        super (layout);
        commonConstructor();
    }
    public MainPanel(boolean isDoubleBuffered) {
        super (isDoubleBuffered);
        commonConstructor();
    }
    public MainPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        commonConstructor();
    }

    private void commonConstructor() {
        setBackground(new Color(170,170,170));

        LayoutManager gridLayout = new GridLayout(0, 1, 0, 5);
        graphicsPanel = new GraphicsPanel(true);
        buttonsPanel = new ButtonsPanel(gridLayout, false);

        add(graphicsPanel);
        add(buttonsPanel);

        buttonsPanel.setTarget(graphicsPanel);
    }

    void setupComplete() {
        graphicsPanel.setup(new MergeSort<>());
    }
}
