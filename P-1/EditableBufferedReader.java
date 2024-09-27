import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class EditableBufferedReader extends BufferedReader{
  static final int ESC = 27;

  EditableBufferedReader(InputStreamReader in) {
    super(in);
    //TODO Auto-generated constructor stub
  }

  public static void setRaw() throws IOException{
    try{
      String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
      Runtime.getRuntime().exec(cmd);
    }catch(Exception e){
      System.out.println("Error setRaw")
    }
  }

  public static void unsetRaw() throws IOException{
    try{
      String[] cmd = {"/bin/sh", "-c", "stty -echo cooked </dev/tty"};
      Runtime.getRuntime().exec(cmd);
    }catch(Exception e){
      System.out.println("Error unsetRaw");
    }
  }
  @Override
  public int read(){
    int cha = 0;
    try {
      cha = super.read();// leemos caracter
    } catch (IOException e) {
      e.printStackTrace();
    } 

    while(cha != ESC){
      return cha;
    }
    return -1;
  }
  
  @SuppressWarnings("deprecation")
  @Override
  public String readLine() {
    return null;
}
}
