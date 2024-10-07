import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class EditableBufferedReader extends BufferedReader {
  static final int ENTER = 13;

  static final int ESC = 27; // escape sequence, todas empiezan por esta ^[
  static final int CORCHETE = 91; // Las escape sequence son del estilo ^[[X

  static final int LEFT = 68; // escape sequence de la flecha izq ^[[D D en ascii es 68
  static final int RIGHT = 67;
  static final int HOME = 72; // ^[[H H es 72 en ascii
  static final int END = 70;
  static final int SUPR = 51; // ^[[3~ --> puede afectar a la escalabilidad ya que ^[[3J tiene funcionalidades como borrar toda la consola y habria que hacer mas distinciones y parsear mejor
                              // si queremos en un futuro añadir mas funcionalidades como por ejemplo tiene VIM.
  static final int INS = 50;   // ^[[2~
  static final int BKSP = 127;  // 

  static final int LEFT_RET = -1; // como ascii no tiene valores negativos vamos a usarlos para nuestros caracteres especiales 
  static final int RIGHT_RET = -2;
  static final int HOME_RET = -3;
  static final int END_RET = -4;
  static final int SUPR_RET = -5;
  static final int INS_RET = -6;
  static final int BKSP_RET = -7;

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
            case HOME:
              return HOME_RET;
            case END:
              return END_RET;
            case INS:
              return INS_RET;
            case SUPR:
              return SUPR_RET;
            case BKSP:
              return BKSP_RET;

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
      lectura = this.read();
      switch (lectura) {
        case LEFT_RET:
          System.out.print("\033[D"); // Usamos la escape sequence para movernos a la izquierda \033 es ESC
        break;
        case RIGHT_RET:
          System.out.print("\033[C"); // Usamos la escape sequence para movernos a la derecha
        break;
        case HOME_RET:
          System.out.print("\033[H");
        break;
        case END_RET:
          System.out.print("\033[F");
        break;
        case INS_RET:
          System.out.print("");
        break;
        case SUPR_RET:
          System.out.print("\033[C");
          System.out.print("\033[P");
        break;
        case BKSP_RET:
          System.out.print("");
        break;
        default:
          System.out.print((char) lectura); // Si no es ningun caracter especial printeamos la tecla directamente con un cast a char
      }
    }

    EditableBufferedReader.unsetRaw(); // quitamos modo raw para volver al estado normal de la terminal
    System.out.println("finish");

    return null;
  }
}
