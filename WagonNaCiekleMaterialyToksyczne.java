public class WagonNaCiekleMaterialyToksyczne extends WagonTowarowyCiezki{
    private boolean elektryka = false;
    private double pojemnosc;
    private double objetoscCieczy; //policzyc po tym ile wazy zeby zobaczyc czy pojemnosc to zmiesci
    private int minimalneZabezpieczenia; //zrobic typem generycznmy nazwy zabezpieczen i porownac numerki
    private boolean latwoPalne;
    private boolean zagrazaPasazerom;
    private double gestoscCieczy;
    private double temperatura;
    private double maxTemperatura;

    public WagonNaCiekleMaterialyToksyczne(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, TOWAR towar,
                                           boolean ubezpieczony, double maxWaga, double waga, double pojemnosc, double gestoscCieczy,
                                           int minimalneZabezpieczenia, boolean latwoPalne, boolean zagrazaPasazerom) throws ZaDuzoPrzesylekException, ZaSlabeZabezpieczeniaException, ZagrazaPasazeromException {
        super(nadawca, zabezpieczenia, wNetto, towar, waga, ubezpieczony, maxWaga);
        this.pojemnosc = pojemnosc;
        this.minimalneZabezpieczenia = minimalneZabezpieczenia;
        this.latwoPalne = latwoPalne;
        this.zagrazaPasazerom = zagrazaPasazerom;
        this.gestoscCieczy = gestoscCieczy;
        if(waga/gestoscCieczy>pojemnosc){
            throw new ZaDuzoPrzesylekException(pojemnosc, waga/gestoscCieczy, id);
        }
        if(zabezpieczenia.getValue()<minimalneZabezpieczenia){
            throw new ZaSlabeZabezpieczeniaException(minimalneZabezpieczenia, zabezpieczenia);
        }
        if(zagrazaPasazerom){
            wzmocnijZabezpieczenia();
        }
        temperatura = Math.random()*20;
        maxTemperatura = Math.random()*120;
        setTemperatura();
    }
    public boolean czyZagrazaPasazerom(){
        return zagrazaPasazerom;
    }
    public TOWAR getTowar(){
        return towar;
    }
    private void wzmocnijZabezpieczenia(){
        if(!zabezpieczenia.equals(ZABEZPIECZENIA.values()[ZABEZPIECZENIA.values().length-1])) {
            zabezpieczenia = ZABEZPIECZENIA.values()[zabezpieczenia.getValue()];
            if(zabezpieczenia.equals(ZABEZPIECZENIA.values()[ZABEZPIECZENIA.values().length-1])){
                zagrazaPasazerom = false;
            }
        }
    }
    public void setTemperatura() throws ZagrazaPasazeromException {
        temperatura += Math.random()>0.5?Math.random():Math.random()*-1;
        sprawdzTemperature();
    }
    private void sprawdzTemperature() throws ZagrazaPasazeromException {
        if(temperatura>maxTemperatura){
            throw new ZagrazaPasazeromException(towar, temperatura);
        }
    }
    public String toString(){
        return "WagonCiekleMaterialyToksyczne id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+
                wBrutto+" towar: "+towar+" waga towaru: "+wagaTowaru;
    }
}
