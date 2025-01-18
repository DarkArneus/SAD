package P3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket {
/*Un alte cop si volem encapsular totes les excepcions no ho podem fer amb herencia i
 * per aixo hem tornat a fer servir composicio encara que de l'altre manera després 
  tinguem mes flexibilitat amb els metodes de socket*/
    
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    public MySocket(String host, int port){
        try{
            this.socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("There has been an error creating the Socket"+ e);
        }
    }

    public MySocket(Socket socket){
        try{
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("There has been an error converting the Socket"+e);
        }
    }

    public void close(){
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing socket"+ e);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public String read() throws IOException {
        try{
            return reader.readLine();
        } catch(IOException e) {
            throw new IOException("Error reading from socket" + e);
        }

    }

    public void print(String s) {
        writer.println(s);
        writer.flush();
    }
}
