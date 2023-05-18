import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class WypisywanieDoPliku extends Thread{
    private Lokomotywa[] lokomotywy;
    public WypisywanieDoPliku(Lokomotywa[] lokomotywy){
        this.lokomotywy = lokomotywy;
    }
    public void usunLokomotywe(int index){
        Lokomotywa[] tmp = lokomotywy;
        lokomotywy = new Lokomotywa[tmp.length-1];
        int k = 0;
        for(int i = 0;i<tmp.length;i++){
            if(i!=index){
                lokomotywy[k] = tmp[i];
                k++;
            }
        }
    }
    public void dodajLokomotywe(Lokomotywa l){
        Lokomotywa[] tmp = lokomotywy;
        lokomotywy = new Lokomotywa[tmp.length+1];
        for(int i = 0;i<tmp.length;i++){
            lokomotywy[i] = tmp[i];
        }
        lokomotywy[lokomotywy.length-1] = l;
    }
    @Override
    public void run() {
        File file = new File("AppState.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, false);
        while (!interrupted()) {
            try {
                Arrays.sort(lokomotywy);
            }catch (IllegalArgumentException e){

            }
            for (Lokomotywa l : lokomotywy) {
                char[] charr = l.toString().toCharArray();
                for (char c : charr) {
                    fw.write(c);
                }
                fw.write('\n');
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {

            }
        }
        } catch (IOException e) {

        }
    }
}
