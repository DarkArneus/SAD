package P3;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private MyServerSocket serverSocket;
    private Map<String, MySocket> clientMap;

    public Server(int port) {
        try {
            serverSocket = new MyServerSocket(port);
            clientMap = Collections.synchronizedMap(new HashMap<>());
        } catch (IOException e) {
            System.err.println("Error initializing server: " + e.getMessage());
        }
    }

    public void start() {
        System.out.println("Server is running...");
        while (true) {
            try {
                MySocket clientSocket = serverSocket.accept();
                System.out.println("New client connected!");
                new ClientHandler(clientSocket, clientMap).start();
            } catch (IOException e) {
                System.err.println("Error accepting client: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java P2.Server <port>");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            new Server(port).start();
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number: " + e.getMessage());
        }
    }
}