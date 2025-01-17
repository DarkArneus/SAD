package P2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client extends Thread {
    private MySocket socket;
    private InputThread in;
    private OutputThread out;

    public Client(String host, int port, String nick) {
        this.socket = new MySocket(host, port);
        this.socket.print(nick);
        this.in = new InputThread(socket);
        this.out = new OutputThread(socket);
    }

    @Override
    public void run() {
        in.start();
        out.start();

        try {
            in.join();
            out.join();
        } catch (InterruptedException e) {
            System.err.println("Transmission error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java P2.Client <host> <port> <nickname>");
            return;
        }

        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String nickname = args[2];

            Client client = new Client(host, port, nickname);
            client.start();
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number: " + e.getMessage());
        }
    }
}

class InputThread extends Thread {
    MySocket socket;
    BufferedReader in;
    String line;

    public InputThread(MySocket socket) {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        try {
            while ((line = in.readLine()) != null) {
                socket.print(line);
            }
            socket.close();
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}

class OutputThread extends Thread {
    MySocket socket;
    String line;

    public OutputThread(MySocket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            while ((line = socket.read()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error reading from socket: " + e.getMessage());
        }
    }
}
