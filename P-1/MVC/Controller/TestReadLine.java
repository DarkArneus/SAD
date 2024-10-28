package MVC.Controller;
import MVC.Model.*;

import java.io.*;
public class TestReadLine {
    public static void main(String[] args) throws IOException {
        Line line = new Line();  // Model
        Console console = new Console();  // View
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in), line, console);  // Controller
        
        console.displayLine(line);  // Mostrem la linia inicial
        reader.readLine();  // Processar l'entrada de l'usuari
    }
}
