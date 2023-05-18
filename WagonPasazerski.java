import java.util.ArrayList;

public class WagonPasazerski extends Wagon{
    private NADAWCA nadawca;
    private ZABEZPIECZENIA zabezpieczenia;
    private double wNetto;
    private double wBrutto;
    private int siedzenia;
    private int id;
    protected static int idCounter = 0;
    private boolean elektryka = true;
    private boolean przedialowy;
    private int miejscaStojace;
    private ArrayList<Osoba> pasazerowie = new ArrayList<>();
    private Stacja[] trasa;
    private int wagaPasazerow = 0;
    private boolean otwarteDrzwi;

    public WagonPasazerski(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, boolean przedzialowy,
                           int siedzenia,int miejscaStojace){
        id = idCounter++;
        this.nadawca = nadawca;
        if(zabezpieczenia.getValue()<=3) {
            this.zabezpieczenia = zabezpieczenia;
        }else{
            this.zabezpieczenia = ZABEZPIECZENIA.values()[2];
        }
        this.wNetto = wNetto;
        this.siedzenia = siedzenia;
        if(przedzialowy){
            this.siedzenia -= siedzenia%6;
        }
        this.miejscaStojace = miejscaStojace;
        this.przedialowy = przedzialowy;
        this.otwarteDrzwi = false;
        setWBrutto();
    }
    public String toString(){
        return "WagonPasazerski id: "+id+" nazwa: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+
                " waga brutto: "+wBrutto+" liczba pasazerow: "+pasazerowie.size()+" miejsca siedzace "+siedzenia+" miejsca stojace: "+miejscaStojace;
    }
    public void setPasazerowie(Stacja s, Stacja nastepnaStacja, Stacja[] trasa){
        int ileWysiada = 0;
        int j = 0;
        for (int i = 0; i<pasazerowie.size();i++) {
            Osoba o = pasazerowie.get(j);
            if (o.getStacja().equals(s)) {
                wagaPasazerow -= o.getWaga();
                pasazerowie.remove(o);
                ileWysiada++;
            }else {
                j++;
            }
        }

        s.setLudnosc(ileWysiada);
        int rand = (int)(Math.random()*(s.getLudnosc()/1000));
        int ileWsiada = rand+getIloscPasazerow()>(miejscaStojace+siedzenia)?(miejscaStojace+siedzenia-getIloscPasazerow()): rand;
        for(int i = 0; i<ileWsiada;i++) {
            pasazerowie.add(new Osoba(dokadJade(trasa)));
            pasazerowie.get(pasazerowie.size()-1).setGlod(nastepnaStacja);
            wagaPasazerow += pasazerowie.get(pasazerowie.size()-1).getWaga();
        }
        s.setLudnosc(-ileWsiada);
        setWBrutto();
    }
    public void dodajPasazerow(Stacja s, Stacja nastepnaStacja, Stacja[] trasa, int iluDodac){
        if((getIloscPasazerow()+iluDodac)>(miejscaStojace+siedzenia)){
            System.out.println("Probowano dodac za duzo pasazerow, liczba ta zostala automatycznie zmniejszona");
            iluDodac = miejscaStojace+siedzenia-getIloscPasazerow();
        }
        for(int i = 0; i<iluDodac;i++) {
            pasazerowie.add(new Osoba(dokadJade(trasa)));
            pasazerowie.get(i).setGlod(nastepnaStacja);
            wagaPasazerow += pasazerowie.get(i).getWaga();
        }
        s.setLudnosc(-iluDodac);
        setWBrutto();
    }
    public void usunPasazerow(Stacja s, int iluUsunac) {
        if (getIloscPasazerow() < iluUsunac) {
            System.out.println("Probowano usunac za duzo pasazerow, liczba ta zostala automatycznie zmniejszona");
            iluUsunac = getIloscPasazerow();
        }
        for (int i = 0; i < iluUsunac; i++) {
            Osoba o = pasazerowie.get(0);

            wagaPasazerow -= o.getWaga();
            pasazerowie.remove(o);
        }
        s.setLudnosc(iluUsunac);
    }
    public void pozostaleStacje(Stacja[] trasa){
        this.trasa = trasa;
    }
    private Stacja dokadJade(Stacja[] trasa){
        int number = 0;
        for(Stacja s : trasa){
            try {
                number += (s.getLudnosc() / 1000);
            }catch(NullPointerException e){

            }
        }
        int rand = (int)(Math.random()*number);
        number = 0;
        for(int i = 0;i<trasa.length;i++){
            Stacja s = trasa[i];
            number+=(s.getLudnosc()/1000);
            if(number>=rand){
                return s;
            }
        }
        return trasa[trasa.length-1];
    }
    private void setWBrutto(){
        wBrutto = wNetto+wagaPasazerow;
    }
    public int getIloscPasazerow(){
        return pasazerowie.size();
    }
    @Override
    public double getWBrutto() {
        return wBrutto;
    }
    public ArrayList<Osoba> getPasazerowie(){
        return pasazerowie;
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

    @Override
    public int compareTo(Wagon o) {
        return (int)(this.getWBrutto()-o.getWBrutto());
    }
}
