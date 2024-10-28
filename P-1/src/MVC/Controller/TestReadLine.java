package src.MVC.Controller;
import java.io.*;

import src.MVC.Model.Line;
import src.MVC.View.Console;

public class TestReadLine {
    public static void main(String[] args) throws IOException {
        Line line = new Line();  // Modelo
        Console console = new Console(line);  // Vista que observa el modelo
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in), line, console);  // Controlador

        console.displayLine(line);  // Mostrar la línea inicial
        reader.readLine();  // Procesa la entrada del usuario
    }
}
