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

    public int width = 1000;
    public int height = 500;

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
        LayoutManager layout = new FlowLayout(LEFT);
        JPanel topPanel = new JPanel(layout, false);
        GraphicsPanel graphicsPanel = new GraphicsPanel(true);
        ButtonsPanel buttonsPanel = new ButtonsPanel(false);


        //==== Elements Setup ====//
        graphicsPanel.setPreferredSize(new Dimension(width * 4 / 5, height));
        buttonsPanel.setPreferredSize(new Dimension(width / 5, height));
        topPanel.add(graphicsPanel);
        topPanel.add(buttonsPanel);
        graphicsPanel.add(new JLabel("test"));
        topPanel.setBackground(new Color(170,170,170));
        //mainFrame.add(topPanel);



        //==== Frame Boilerplate ====//
        //mainFrame.setJMenuBar();
        setLayout(layout);
        setContentPane(topPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setMinimumSize(new Dimension(MainFrame.width, MainFrame.height));
        pack();
        setResizable(false);
        setVisible(true);
        graphicsPanel.sortRectangles(new MergeSort<ImprovedRectangle>());
    }
}
