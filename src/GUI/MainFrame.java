package GUI;


import javax.swing.*;
import java.awt.*;


/**
 * The top window class, used to initialize the top panel and display the contents in the proper size.
 *
 * @author Darryl Murray, dmurra47@uwo.ca
 * @version 1.0
 * @see MainPanel
 * @see JFrame
 */
public class MainFrame extends JFrame {

    // TODO: Why is this here?
    public int width = 640;
    public int height = 500;

    private MainPanel topPanel;

    /**
     * Default - creates a frame without a title
     */
    public MainFrame() {
        super();
        commonConstructor();
    }

    /**
     * Creates a frame with the specified title
     *
     * @param title The title to be given to the new frame.
     */
    public MainFrame(String title) {
        super(title);
        commonConstructor();
    }

    /**
     * Main function.
     *
     * @param args Starting arguments.
     */
    public static void main(String[] args) {

        //==== Frame Initiation ====//
        new MainFrame("Sort Algorithm Visualizer");
    }

    /**
     * The common behaviour between the different types of constructors.
     */
    private void commonConstructor() {
        topPanel = new MainPanel(false);  // Make new top panel

        //==== Frame Boilerplate ====//
        setContentPane(topPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack(); // Resize the window to properly capture all the content
        setMinimumSize(new Dimension(getWidth(), getHeight())); // Set the found size to the minimum size
        setResizable(false); // Disable resizing
        setVisible(true);
        topPanel.setupComplete(); // Signal to child pane that setup is complete
    }
}
