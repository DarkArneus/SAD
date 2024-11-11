package src.MVC.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import src.MVC.Model.*;
import src.MVC.View.*;

class EditableBufferedReader extends BufferedReader {
  static final int ENTER = 13;

  static final int ESC = 27; // escape sequence, todas empiezan por esta ^[
  static final int CORCHETE = 91; // Las escape sequence son del estilo ^[[X

  static final int LEFT = 68; // escape sequence de la flecha izq ^[[D D en ascii es 68
  static final int RIGHT = 67;
  static final int HOME = 72; // ^[[H H es 72 en ascii
  static final int END = 70; // ^[F
  static final int SUPR = 51; // ^[[3~ --> puede afectar a la escalabilidad ya que ^[[3J tiene funcionalidades
                              // como borrar toda la consola y habria que hacer mas distinciones y parsear
                              // mejor si queremos en un futuro aÃ±adir mas funcionalidades como por ejemplo tiene VIM.
  static final int INS = 50; // ^[[2~
  static final int BKSP_1 = 8; // depende del ordenador es un valor u otro (8,127)
  static final int BKSP_2 = 127;

  static final int LEFT_RET = -1; // como ascii no tiene valores negativos vamos a usarlos para nuestros
                                  // caracteres especiales
  static final int RIGHT_RET = -2;
  static final int HOME_RET = -3;
  static final int END_RET = -4;
  static final int SUPR_RET = -5;
  static final int INS_RET = -6;
  static final int BKSP_RET = -7;

  EditableBufferedReader(InputStreamReader in) {
    super(in);  
  }

  public static void setRaw() throws IOException {
    try {
      ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty raw -echo </dev/tty");
      pb.start();
    } catch (IOException e) {
      System.err.println("Error while setting terminal to raw mode: " + e.getMessage());
      throw e;
    }
  }

  public static void unsetRaw() throws IOException {
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
              super.read();
              return INS_RET;
            case SUPR:
              super.read();
              return SUPR_RET;
          }
        }
      } else if (cha == BKSP_1 || cha == BKSP_2) {
        return BKSP_RET;
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
    Line line = new Line();
    Console console = new Console(line);

    EditableBufferedReader.setRaw(); // entramos en modo raw que es el modo en el que operaremos en la terminal
    lectura = this.read();
    line.insertChar((char) lectura);

    while (lectura != ENTER) {
      lectura = this.read();
      switch (lectura) {
        case LEFT_RET:
          line.moveCursorLeft();
          break;
        case RIGHT_RET:
          line.moveCursorRight();
          break;
        case HOME_RET:
          line.moveCursorHome();
          break;
        case END_RET:
          line.moveCursorEnd();
          break;
        case INS_RET:
          line.setInsert();
          break;
        case SUPR_RET:
          line.moveCursorRight();
          line.deleteCharBefore();
          break;
        case BKSP_RET:
          line.deleteCharBefore();
          break;
        default:
          if (line.getInsert())
               if (line.getInsert() && line.getCursorPosition() != line.getText().length()) {
                    line.moveCursorRight();
                    line.deleteCharBefore();
                }
          line.insertChar((char) lectura);
          //console.displayLine(line);
      }
    }
    EditableBufferedReader.unsetRaw(); // quitamos modo raw para volver al estado normal de la terminal
    return line.getText();
  }
}
