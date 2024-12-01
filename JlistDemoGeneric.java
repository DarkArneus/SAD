// BoxLayout es el bueno

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class JlistDemoGeneric extends JPanel {

    // Define a JList and a DefaultListModel with type safety
    private DefaultListModel<String> listModel;
    private JList<String> itemList;
    private JButton add;
    private JButton remove;
    private JTextField inp;

    public JlistDemoGeneric() {
        super(new BorderLayout());

        // Create initial listModel and populate it with example items
        listModel = new DefaultListModel<>();
        listModel.addElement("Usuari 1");
        listModel.addElement("Usuari 2");
        listModel.addElement("Usuari 3");
        listModel.addElement("Usuari 4");
        listModel.addElement("Usuari 5");
        listModel.addElement("Usuari 6");
        listModel.addElement("Usuari 7");

        // Create the JList with the model
        itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Put the JList into a scroll pane
        JScrollPane scrollPane = new JScrollPane(itemList);

        // Add the scroll pane to the panel
        add(scrollPane, BorderLayout.CENTER);

        // Create bottom panel with a text field and button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JTextField textField = new JTextField();
        add = new JButton("add");
        remove = new JButton("remove");

        
        bottomPanel.add(add);
        bottomPanel.add(remove);
        bottomPanel.add(textField);
    
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Method to add a new element to the list
     */
    public void actionPerformed(ActionEvent event){
        JComponent source = (JComponent) event.getSource();
        if(source == remove){
            
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Set up the JFrame
        JFrame frame = new JFrame("JList Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        JlistDemoGeneric newContentPane = new JlistDemoGeneric();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JlistDemoGeneric::createAndShowGUI);
    }
}
