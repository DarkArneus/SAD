/*
 * HelloWorldSwing.java requires no other files. 
 */
import java.awt.*;
import javax.swing.*;        

public class HelloWorldSwing {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        //Create and set up the window. Exit on close
        JFrame frame = new JFrame("Hello World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,750);
        

        //Add the ubiquitous "Hello World" label to frame.
        JLabel label = new JLabel("Tenemos black label");
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.add(label);
        //Display the window. set size and center
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}