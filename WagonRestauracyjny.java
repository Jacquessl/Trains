import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WagonRestauracyjny extends Wagon{
    private NADAWCA nadawca;
    private ZABEZPIECZENIA zabezpieczenia;
    private double wNetto;
    private double wBrutto;
    private int siedzenia;
    private int id;
    protected static int idCounter = 0;
    private int stoliki;
    private Queue<Osoba> osobyOczekujace = new LinkedList<>();
    private ArrayList<Osoba> klienci = new ArrayList<>();
    private boolean elektryka = true;
    private int wagaPasazerow;
    private boolean otwarteDrzwi;

    public WagonRestauracyjny(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto,
                              int stoliki) {
        id = idCounter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wNetto = wNetto;
        setWBrutto();
        siedzenia = stoliki*4;
        this.stoliki = stoliki;
        otwarteDrzwi = false;
    }

    @Override
    public double getWBrutto() {
        return wBrutto;
    }
    private void setWBrutto(){
        wBrutto = wNetto+wagaPasazerow;
    }
    public void setKlienci(Queue<Osoba> lso, Stacja nastepnaStacja){
        for(int i = 0; i<klienci.size();i++) {
            Osoba o = klienci.get(i);
            o.setGlod(nastepnaStacja);
            if(!o.getGlod()) {
                wagaPasazerow -= o.getWaga();
                klienci.remove(o);
            }
        }
        for(int i = 0; i<klienci.size();i++) {
            Osoba o = klienci.get(i);
            o.setGlod(nastepnaStacja);
            if(!o.getGlod()){
                wagaPasazerow -= o.getWaga();

            }
        }
        osobyOczekujace.addAll(lso);
        while(klienci.size()<siedzenia&&osobyOczekujace.size()>0){
            klienci.add(osobyOczekujace.peek());
            osobyOczekujace.remove();
        }
        setWagaPasazerow();
    }
    public void setWagaPasazerow(){
        for(Osoba o: klienci){
            wagaPasazerow+=o.getWaga();
        }
        setWBrutto();
    }
    @Override
    public boolean getElektyczny() {
        return elektryka;
    }

    @Override
    public void otworzDrzwi() {
        otwarteDrzwi = true;
    }

    @Override
    public void zamknijDrzwi() {
        otwarteDrzwi = false;
    }

    public String toString(){
        return "WagonRestauracyjny id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+
                wBrutto+" liczba stolikow: "+stoliki+" liczba miejsc siedzacych: "+siedzenia+" ilosc klientow: "+klienci.size()+" ilosc osob w kolejce: "+osobyOczekujace.size();
    }

    @Override
    public int compareTo(Wagon o) {
        return (int)(this.getWBrutto()-o.getWBrutto());
    }
}
