package MVC.View;
import MVC.Model.*;
import java.util.Observer;
import java.util.Observable;

public class Console implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        displayLine(o);
    }

    public void displayLine(Line line) {
        // Esborrar la consola i mostrar la linia amb el cursor
        System.out.print("\033[H\033[2J"); // Clear console
        System.out.flush(); // obliguem a imprimir per consola que no tinguem retards

        String text = line.getText();
        int cursorPos = line.getCursorPosition();

        // Mostrem texte
        System.out.print(text);
        // Posiciona el cursor
        System.out.print("\033[" + (cursorPos + 1) + "G");
    }
}
