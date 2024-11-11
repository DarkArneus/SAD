package src.MVC.View;

import java.util.Observer;
import src.MVC.Model.Line;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Console implements Observer {
    private Observable observable;

    public Console(Line line) {
        setObservable(line);  // Registrar Console como observador de Line
    }
    
    private void setObservable(Observable observable) {
        this.observable = observable;
        this.observable.addObserver(this);  
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
            // Convertir el StringBuilder a String para mostrar
            System.out.print("o");
            displayLine((Line) o);
        }
    }
}
