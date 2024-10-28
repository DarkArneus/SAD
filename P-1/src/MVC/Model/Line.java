package src.MVC.Model;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;

public class Line extends Observable{
    private StringBuilder line; // linia de text
    private int cursorPosition; // posicio del cursor
    private boolean insert;

    public Line() {
        line = new StringBuilder();
        cursorPosition = 0;
    }

    // Inserir un caracter a la posicio del cursor
    public void insertChar(char ch) {
        line.insert(cursorPosition, ch);
        cursorPosition++;
        this.setChanged(); // Definimos el cambio
        this.notifyObservers(line); // Notificamos el cambio
    }

    public boolean getInsert() {
        return insert;
    }

    public void setInsert() {
        insert = !insert;
        if (insert)
            System.out.print("\033[4 q"); //ficar cursor underline
        else
            System.out.print("\033[0 q"); //ficar cursor normal
        this.setChanged(); // Definimos el cambio
        this.notifyObservers(line); // Notificamos el cambio
    }

    // Esborra caracter esquerra del cursor
    public void deleteCharBefore() {
        if (cursorPosition > 0) {
            line.deleteCharAt(cursorPosition - 1);
            moveCursorLeft();
        } else {
            System.out.print((char) 7); // valor ASCII bell sound
        }
        
        this.setChanged(); // Definimos el cambio
        this.notifyObservers(line); // Notificamos el cambio
    }

    // Moure cursor esquerra
    public void moveCursorLeft() {
        if (cursorPosition > 0) {
            cursorPosition--;
        } else {
            System.out.print((char) 7); // valor ASCII bell sound
        }
        this.setChanged(); // Definimos el cambio
        this.notifyObservers(line); // Notificamos el cambio
    }

    // Moure cursor dreta
    public void moveCursorRight() {
        if (cursorPosition < line.length()) {
            cursorPosition++;
        } else {
            System.out.print((char) 7); // valor ASCII bell sound
        }
        this.setChanged(); // Definimos el cambio
        this.notifyObservers(line); // Notificamos el cambio
    }

    // Moure cursor principi de la linia
    public void moveCursorHome() {
        cursorPosition = 0;
        this.setChanged(); // Definimos el cambio
        this.notifyObservers(line); // Notificamos el cambio
    }

    // Moure cursor final de la linia
    public void moveCursorEnd() {
        cursorPosition = line.length();
        this.setChanged(); // Definimos el cambio
        this.notifyObservers(line); // Notificamos el cambio
    }

    // get texte de line
    public String getText() {
        return line.toString();
    }

    // get posicio del cursor
    public int getCursorPosition() {
        return cursorPosition;
    }
}