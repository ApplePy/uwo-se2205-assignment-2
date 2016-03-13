package GUI;

import Sorts.MergeSort;
import Sorts.SelectionSort;

import javax.swing.*;
import java.awt.*;

import static java.awt.FlowLayout.*;

/**
 * Created by darryl on 2016-03-10.
 */
public class MainFrame extends JFrame {

    public int width = 640;
    public int height = 500;

    private MainPanel topPanel;

    public static void main(String[] args) {

        //==== Frame Initiation ====//
        new MainFrame("Sort Algorithm Visualizer");
    }

    public MainFrame() {
        super();
        commonConstructor();
    }
    public MainFrame(String title) {
        super(title);
        commonConstructor();
    }

    private void commonConstructor() {
        topPanel = new MainPanel(false);

        //==== Frame Boilerplate ====//
        //mainFrame.setJMenuBar();
        //setLayout(layout);
        setContentPane(topPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(getWidth(),getHeight()));
        setResizable(true);
        setVisible(true);
        topPanel.setupComplete();
        //graphicsPanel.setup(new MergeSort<ImprovedRectangle>());
        //try {Thread.sleep(500);} catch (InterruptedException e) {System.out.println("test");}
        //graphicsPanel.scramble();
        //graphicsPanel.repaint();
        //try {Thread.sleep(500);} catch (InterruptedException e) {System.out.println("test");}
        //graphicsPanel.sortRectangles(new SelectionSort<>());
        //try {Thread.sleep(500);} catch (InterruptedException e) {System.out.println("test");}
    }
}
