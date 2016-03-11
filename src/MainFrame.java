import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.*;
import static java.awt.FlowLayout.*;

/**
 * Created by darryl on 2016-03-10.
 */
public class MainFrame extends JFrame {

    private int width = 1000;
    private int height = 500;

    public static void main(String[] args) {

        //==== Frame Initiation ====//
        new MainFrame("Sort Algorithm Visualizer");
    }

    public MainFrame() {
        super();
    }
    public MainFrame(String title) {
        super(title);
    }
}
