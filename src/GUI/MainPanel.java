package GUI;

import javax.swing.*;
import java.awt.*;

import static java.awt.FlowLayout.LEFT;

/**
 * Created by darryl on 2016-03-10.
 */

public class MainPanel extends JFrame {

    public MainPanel(String title) {
        super (title);
        //commonConstructor();
    }
    public MainPanel() {
        super();
        //commonConstructor();
    }

    /*private void commonConstructor() {
        LayoutManager layout = new FlowLayout(LEFT);
        JPanel topPanel = new MainPanel(layout, false);
        GraphicsPanel graphicsPanel = new GraphicsPanel(true);
        ButtonsPanel buttonsPanel = new ButtonsPanel(false);


        //==== Elements Setup ====//
        graphicsPanel.setPreferredSize(new Dimension(MainFrame.width * 4 / 5, MainFrame.height));
        buttonsPanel.setPreferredSize(new Dimension(MainFrame.width / 5, MainFrame.height));
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
    }*/
}
