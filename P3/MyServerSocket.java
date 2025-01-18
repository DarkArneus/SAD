package P3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {
    ServerSocket serverSocket;
/*No fem servir herencia ja que si volem encapsular les excepcions no ho podríem fer al constructor
 * ja que no podem ficar super() dintre de un try-catch i al només voler les funcions més simples de 
 ServerSocket hem optat per aquesta opció per encapsular totes les excepcions. Encara que amb herencia
  seria mes facil i flexible*/
    public MyServerSocket(int port) throws IOException {
        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IOException("There has been an error creating the ServerSocket", e);
        }
    }

    public MySocket accept() throws IOException {
        try{
            Socket clientSocket = serverSocket.accept();
            return new MySocket(clientSocket);
        } catch (IOException e) {
            throw new IOException("There has been an error establishing a connection with the server", e);
        }
    }

    public void close() throws IOException {
        try{
            serverSocket.close();
        } catch (IOException e) {
            throw new IOException("There has been a problem closing the ServerSocket", e);
        }
    }
}
