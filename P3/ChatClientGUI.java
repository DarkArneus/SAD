package P3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ChatClientGUI extends JPanel {
    private MySocket socket;
    private DefaultListModel<String> userModel; 
    private JList<String> userList; 
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatClientGUI(String host, int port, String nickname) {
        super(new BorderLayout());

        // Connect to the server
        socket = new MySocket(host, port);
        socket.print(nickname);

        userModel = new DefaultListModel<>();
        userList = new JList<>(userModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setBorder(BorderFactory.createTitledBorder("Users"));

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setBorder(BorderFactory.createTitledBorder("Messages"));

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add the message area and user list
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messageScrollPane, userScrollPane);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.75);

        add(splitPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Add action listener to the send button and enter
        sendButton.addActionListener(this::sendMessage);
        inputField.addActionListener(this::sendMessage);

        // receive msg thread
        new Thread(this::receiveMessages).start();
    }

    
    private void sendMessage(ActionEvent e) {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            socket.print(message);
            inputField.setText("");
        }
    }

    // Send messages to users and when it detects a new user joined or a user leaving it deletes them from
    //the user list
    private void receiveMessages() {
    try {
        String line;
        while ((line = socket.read()) != null) {
            final String message = line;
            // gets the nickname from the "has joined the chat" message
            SwingUtilities.invokeLater(() -> {
                if (message.endsWith("has joined the chat.")) {
                    String user = message.split(" ")[0];
                    addUserToPanel(user);
                    messageArea.append(message + "\n");
                } else if (message.endsWith("has left the chat.")) {
                    String user = message.split(" ")[0];
                    removeUserFromPanel(user);
                    messageArea.append(message + "\n");
                } else {
                    messageArea.append(message + "\n");
                }
            });
        }
    } catch (IOException e) {
        System.err.println("Error with the application" + e);
    }
}
    

    private void addUserToPanel(String user) {
    if (!userModel.contains(user)) {
        userModel.addElement(user);
    }
}

private void removeUserFromPanel(String user) {
    userModel.removeElement(user);
}
    

    private static void createAndShowGUI(String host, int port, String nickname) {
        JFrame frame = new JFrame("Chat - " + nickname);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChatClientGUI contentPane = new ChatClientGUI(host, port, nickname);
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java P3.ChatClientGUI <host> <port> <nickname>");
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String nickname = args[2];

        SwingUtilities.invokeLater(() -> createAndShowGUI(host, port, nickname));
    }
}