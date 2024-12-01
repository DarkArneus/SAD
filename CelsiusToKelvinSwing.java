/**
 * CelsiusConverter.java is a 1.4 application that 
 * demonstrates the use of JButton, JTextField and
 * JLabel.  It requires no other files.
 */

 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;
 
 public class CelsiusToKelvinSwing implements ActionListener {
     JFrame converterFrame;
     JPanel converterPanel;
     JTextField tempCelsius;
     JLabel celsiusLabel, fahrenheitLabel;
     JButton convertTemp;
 
     public CelsiusToKelvinSwing() {
         //Create and set up the window.
         
        JFrame convertFrame = new JFrame();
        convertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         //Create and set up the panel.
         converterPanel = new JPanel(new GridLayout(2,2));
 
         //Add the widgets.
 
         //Set the default button.
 
         //Add the panel to the window.
         
 
         //Display the window.
         
     }
 
     /**
      * Create and add the widgets.
      */
     private void addWidgets() {
         //Create widgets.
         
 
         //Listen to events from the Convert button.
         
 
         //Add the widgets to the container.
         
     }
 
     public void actionPerformed(ActionEvent event) {
         //Parse degrees Celsius as a double and convert to Fahrenheit.
     }
 
     /**
      * Create the GUI and show it.  For thread safety,
      * this method should be invoked from the
      * event-dispatching thread.
      */
     private static void createAndShowGUI() {
         //Set the look and feel.
         try {
                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (Exception e) {}
 
         //Make sure we have nice window decorations.
         JFrame.setDefaultLookAndFeelDecorated(true);
 
         CelsiusConverter converter = new CelsiusConverter();
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