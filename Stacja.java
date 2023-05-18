import java.util.Arrays;
import java.util.Comparator;

public class Stacja {

    private final double locationX;
    private final double locationY;
    private final String miejscowosc;
    private static int ludnosc;
    private Stacja[] rozklad;
    private int maxOdjazd;
    private Stacja[] stacje;

    public Stacja(double locationX, double locationY, String miejscowosc, int ludnosc, int maxOdjazd){
        this.locationX = locationX;
        this.locationY = locationY;
        this.miejscowosc = miejscowosc;
        this.ludnosc = ludnosc;
        this.maxOdjazd = maxOdjazd;
    }
    public double getLocationX(){
        return locationX;
    }
    public double getLocationY(){
        return locationY;
    }
    public int getLudnosc(){
        return ludnosc;
    }
    public String getMiejscowosc(){
        return miejscowosc;
    }
    public Stacja[] getRozklad(){
        return rozklad;
    }
    public void setMaxOdjazd(){
        if(maxOdjazd<10) {
            maxOdjazd++;
        }
    }
    public void odjazdDo(){
        stacje = Main.getStacje();
        Stacja[] stacjeTu = new Stacja[stacje.length];
        Stacja[] odjazdDo = new Stacja[maxOdjazd];
        System.arraycopy(stacje, 0, stacjeTu, 0, stacje.length);
        Arrays.sort(stacjeTu, Comparator.comparingDouble(o -> new Trasa(this, o).przelicznikTrasy()));
        for(int i = 1; i<odjazdDo.length+1;i++){
            odjazdDo[i-1] = stacjeTu[i];
        }
        rozklad = odjazdDo;
    }
    public void setLudnosc(int ile){
        ludnosc += ile;
    }
    public String toString(){
        return miejscowosc;
    }
    public void dodajStacje(Stacja s){
        boolean jesttaka = false;
        odjazdDo();
        maxOdjazd++;
        for(int i = 0;i<rozklad.length;i++){
            if(s.getMiejscowosc().equals(rozklad[i].getMiejscowosc())){
                jesttaka = true;
            }
        }
        if(!jesttaka) {
            Stacja[] tmp = rozklad;
            rozklad = new Stacja[tmp.length + 1];
            for (int i = 0; i < tmp.length; i++) {
                rozklad[i] = tmp[i];
            }
            rozklad[rozklad.length - 1] = s;
        }
    }
    public void usunStacje(Stacja s){
        Stacja[] tmp = rozklad;
        rozklad = new Stacja[tmp.length-1];
        int k =0;
        for(int i = 0;i<tmp.length;i++){
            if(!s.getMiejscowosc().equals(tmp[i].getMiejscowosc())){
                rozklad[k] = tmp[i];
                k++;
            }
        }
    }
}
