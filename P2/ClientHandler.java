package P2;

import java.io.IOException;
import java.util.Map;

public class ClientHandler extends Thread {
    private MySocket clientSocket;
    private Map<String, MySocket> clientMap;
    private String nick;

    public ClientHandler(MySocket clientSocket, Map<String, MySocket> clientMap) {
        this.clientSocket = clientSocket;
        this.clientMap = clientMap;
    }

    @Override
    public void run() {
        try {
            nick = clientSocket.read();
            synchronized (clientMap) {
                clientMap.put(nick, clientSocket);
                broadcast(nick + " has joined the chat.");
            }

            String message;
            while ((message = clientSocket.read()) != null) {
                System.out.println("Received message from " + nick + ": " + message); //log
                broadcast(nick + ": " + message);
            }
            // EOF detected
            System.out.println("EOF detected for " + nick + ". Closing connection...");
            broadcast(nick + " has left the chat.");
            synchronized (clientMap) {
                clientMap.remove(nick);
            }
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private void broadcast(String message) {
        synchronized (clientMap) {
            for (Map.Entry<String, MySocket> entry : clientMap.entrySet()) {
                try {
                    entry.getValue().print(message + "\n");
                } catch (Exception e) {
                    System.err.println("Error broadcasting to: " + e.getMessage());
                }
            }
        }
    }
}