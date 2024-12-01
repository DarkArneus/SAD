import java.awt.*;
import javax.swing.*;

public class Xat {
    private static void createAndShowGUI() {
        // Set the look and feel to the system's default.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and set up the window.
        JFrame frame = new JFrame("Xat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an output JPanel with a JTextArea inside a JScrollPane.
        JPanel outputPanel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(20, 30);
        textArea.setEditable(false); // Make the text area non-editable for output.
        JScrollPane scrollPane = new JScrollPane(textArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Create an input JPanel with a JTextField and a JButton.
        JPanel inputPanel = new JPanel();
        JTextField textField = new JTextField(25);
        JButton sendButton = new JButton("Send");
        inputPanel.add(textField);
        inputPanel.add(sendButton);

        // Add action listener to the button (simple example).
        sendButton.addActionListener(e -> {
            String inputText = textField.getText();
            if (!inputText.trim().isEmpty()) {
                textArea.append(inputText + "\n");
                textField.setText(""); // Clear the text field after sending.
            }
        });

        // Add panels to the main frame.
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(outputPanel, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // Display the window centered.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
