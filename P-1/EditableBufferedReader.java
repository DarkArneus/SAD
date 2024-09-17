import java.io.*;


class EditableBufferedReader extends BufferedReader{
  public EditableBufferedReader(Reader in) {
    super(in);
    //TODO Auto-generated constructor stub
  }

  public static void setRaw(){

  }

  public static void unsetRaw(){

  }
  @Override
  public int read(){
    return 0;

  }
  @Override
  public String readLine(){
    return null;
  }
}