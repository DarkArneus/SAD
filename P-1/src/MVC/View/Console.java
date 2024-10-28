package src.MVC.View;
import java.util.Observer;

import src.MVC.Model.Line;

import java.util.Observable;

public class Console implements Observer {

    public Console(Line line) {
        // Registrar Console como observador de Line
        line.addObserver(this);
    }

    public void displayLine(Line line) {
        // Limpiar la consola y mostrar la línea con el cursor
        System.out.print("\033[H\033[2J");
        System.out.flush();

        String text = line.getText();
        int cursorPos = line.getCursorPosition();

        // Mostrar texto
        System.out.print(text);
        // Posicionar el cursor
        System.out.print("\033[" + (cursorPos + 1) + "G");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Line) {
            displayLine((Line) o);
        }
    }
}
