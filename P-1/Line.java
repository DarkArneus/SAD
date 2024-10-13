public class Line {
    private StringBuilder line;  // linia de text
    private int cursorPosition;  // posicio del cursor

    public Line() {
        line = new StringBuilder();
        cursorPosition = 0;
    }

    // Inserir un caracter a la posicio del cursor
    public void insertChar(char ch) {
        line.insert(cursorPosition, ch);
        cursorPosition++;
    }

    // Esborra caracter esquerra del cursor
    public void deleteCharBefore() {
        if (cursorPosition > 0) {
            line.deleteCharAt(cursorPosition - 1);
            moveCursorLeft();
        }
    }

    // Moure cursor esquerra
    public void moveCursorLeft() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }

    // Moure cursor dreta
    public void moveCursorRight() {
        if (cursorPosition < line.length()) {
            cursorPosition++;
        }
    }

    // Moure cursor principi de la linia
    public void moveCursorHome() {
        cursorPosition = 0;
    }

    // Moure cursor final de la linia
    public void moveCursorEnd() {
        cursorPosition = line.length();
    }

    //get texte de line
    public String getText() {
        return line.toString();
    }

    // get posicio del cursor
    public int getCursorPosition() {
        return cursorPosition;
    }

    public void displayLine(){
        // Esborrar la consola i mostrar la lÃ­nia amb el cursor
        System.out.print("\033[H\033[2J");  // Clear console
        System.out.flush();
        
        String text = this.getText();
        int cursorPos = this.getCursorPosition();
        
        // Mostrem texte
        System.out.print(text);
        // Posiciona el cursor
        System.out.print("\033[" + (cursorPos + 1) + "G");
    }
}