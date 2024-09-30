import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class EditableBufferedReader extends BufferedReader {
  static final int ENTER = 13;

  static final int ESC = 27; // escape sequence, todas empiezan por esta ^[
  static final int CORCHETE = 91; // Las escape sequence son del estilo ^[[X

  static final int LEFT = 68; // escape sequence de la flecha izq ^[[D D en ascii es 68
  static final int RIGHT = 67;

  static final int LEFT_RET = -1; // como ascii no tiene valores negativos vamos a usarlos para nuestros
                                  // caracteres especiales
  static final int RIGHT_RET = -2;

  EditableBufferedReader(InputStreamReader in) {
    super(in);
    // TODO Auto-generated constructor stub
  }

  public static void setRaw() throws IOException {
    /*
     * try{
     * String[] cmd = {"/bin/sh", "-c", "stty -echo raw -echo </dev/tty"};
     * Runtime.getRuntime().exec(cmd);
     * }catch(Exception e){
     * System.out.println("Error setRaw");
     * }
     */

    try {
      ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty raw -echo </dev/tty");
      pb.start();
    } catch (IOException e) {
      System.err.println("Error while setting terminal to raw mode: " + e.getMessage());
      throw e;
    }
  }

  public static void unsetRaw() throws IOException {
    /*
     * try{
     * String[] cmd = {"/bin/sh", "-c", "stty cooked echo </dev/tty"};
     * Runtime.getRuntime().exec(cmd);
     * }catch(Exception e){
     * System.out.print("Error unsetRaw");
     * }
     */
    // hem decidit canviar Runtime.getRuntime().exec() per ProcessBuilder ja que te
    // mes funcionalitats i tracta el I/O de forma mes segura
    try {
      ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty cooked echo </dev/tty");
      pb.start();
    } catch (IOException e) {
      System.err.println("Error while setting terminal to cooked mode: " + e.getMessage());
      throw e;
    }
  }

  @Override
  public int read() {
    int cha = 0;
    try {
      cha = super.read();// leemos caracter
      if (cha == ESC) {
        cha = super.read();
        if (cha == CORCHETE) {
          cha = super.read();
          switch (cha) {
            case LEFT:
              return LEFT_RET;
            case RIGHT:
              return RIGHT_RET;
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return cha;
  }

  @Override
  public String readLine() throws IOException {
    // Leemos el input
    int lectura = 0;

    EditableBufferedReader.setRaw(); // entramos en modo raw que es el modo en el que operaremos en la terminal

    lectura = this.read();
    while (lectura != ENTER) {
      System.out.print((char) lectura);
      lectura = this.read();
    }

    EditableBufferedReader.unsetRaw(); // quitamos modo raw para volver al estado normal de la terminal
    System.out.println("finish");

    return null;
  }
}
