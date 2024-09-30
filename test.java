import java.io.*;

class TestRead {
    public static void main (String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int character = 0;

        try {
            System.out.print("Introduce un carácter: ");
            character = in.read();  // Leer un solo carácter
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nSe ha leído el carácter: " + (char) character + "test ascii: " + (char) 500);
    }
}
