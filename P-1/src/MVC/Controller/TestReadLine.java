package src.MVC.Controller;
import java.io.*;

import src.MVC.Model.Line;
import src.MVC.View.Console;

public class TestReadLine {
    public static void main(String[] args) throws IOException {
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));  // Controlador

        reader.readLine();  // Procesa la entrada del usuario
    }
}
