import java.io.BufferedReader;

public class Columns {
    public static void main(String[] args){
        int c = 0;
        try{
            Kdb.setRaw();
            System.out.print("\033[18t");
            Scanner sc = new Scanner(System.in);
            sc.skip("\033\\[8;\\d+;(\\d+)t");
            c = Integer.praseInt(sc.match().group(1));
        } finally{
            Kdb.unsetRaw();
        }
        System.out.println("Columns = " + c);
    }
}
