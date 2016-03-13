package GUI;


import javax.swing.*;
import java.awt.*;


/**
 * Created by darryl on 2016-03-10.
 */
public class MainFrame extends JFrame {

    public int width = 640;
    public int height = 500;

    private MainPanel topPanel;

    public MainFrame() {
        super();
        commonConstructor();
    }

    public MainFrame(String title) {
        super(title);
        commonConstructor();
    }

    public static void main(String[] args) {

        //==== Frame Initiation ====//
        new MainFrame("Sort Algorithm Visualizer");
    }

    private void commonConstructor() {
        topPanel = new MainPanel(false);

        //==== Frame Boilerplate ====//
        setContentPane(topPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(getWidth(), getHeight()));
        setResizable(true);
        setVisible(true);
        topPanel.setupComplete();
    }
}
