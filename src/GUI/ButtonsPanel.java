package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by darryl on 2016-03-10.
 */
public class ButtonsPanel extends JPanel {
    public ButtonsPanel() {
        super();
    }

    public ButtonsPanel(LayoutManager layout) {
        super (layout);
    }

    public ButtonsPanel(boolean isDoubleBuffered) {
        super (isDoubleBuffered);
    }

    public ButtonsPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }
}
