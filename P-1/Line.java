public class Line {
    private StringBuilder line;  // Conté la línia de text
    private int cursorPosition;  // Posició del cursor a la línia

    public Line() {
        line = new StringBuilder();
        cursorPosition = 0;
    }

    // Inserir un caràcter a la posició del cursor
    public void insertChar(char ch) {
        line.insert(cursorPosition, ch);
        cursorPosition++;
    }

    // Esborra el caràcter a l'esquerra del cursor
    public void deleteCharBefore() {
        if (cursorPosition > 0) {
            line.deleteCharAt(cursorPosition - 1);
            moveCursorLeft();
        }
    }

    // Moure el cursor a l'esquerra
    public void moveCursorLeft() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }

    // Moure el cursor a la dreta
    public void moveCursorRight() {
        if (cursorPosition < line.length()) {
            cursorPosition++;
        }
    }

    // Moure el cursor al principi de la línia
    public void moveCursorHome() {
        cursorPosition = 0;
    }

    // Moure el cursor al final de la línia
    public void moveCursorEnd() {
        cursorPosition = line.length();
    }

    // Retorna la posició actual del cursor
    public int getCursorPosition() {
        return cursorPosition;
    }

    public void displayLine(Line line){
        // Esborrar la consola i mostrar la línia amb el cursor
        System.out.print("\033[H\033[2J");  // Clear the console
        System.out.flush();
        
        String text = line.toString();
        int cursorPos = line.getCursorPosition();
        
        // Mostrem la línia amb el cursor en la seva posició
        System.out.print(text);
        // Posiciona el cursor
        System.out.print("\033[" + (cursorPos + 1) + "G");  // Col·locar el cursor a la posició correcta
    }
}