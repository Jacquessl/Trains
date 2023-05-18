public class WagonNaMaterialyCiekle extends WagonTowarowyPodstawowy{
    private boolean elektryka = false;
    private double pojemnosc;
    private double gestoscCieczy; //policzyc z tym ile wazy zeby zobaczyc czy pojemnosc to zmiesci
    private double objetoscCieczy;
    public WagonNaMaterialyCiekle(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, TOWAR towar,
                                  double waga, double pojemnosc, double gestoscCieczy) throws ZaDuzoPrzesylekException {
        super(nadawca, zabezpieczenia, wNetto, towar, waga);
        this.pojemnosc = pojemnosc;
        this.gestoscCieczy = gestoscCieczy;
        objetoscCieczy = waga/gestoscCieczy;
        sprawdzCzySieZmiesci();
    }
    private void sprawdzCzySieZmiesci() throws ZaDuzoPrzesylekException {
        if(objetoscCieczy>pojemnosc){
            wylej();
        }
    }
    public void wylej() throws ZaDuzoPrzesylekException {
        objetoscCieczy -= Math.random()*(objetoscCieczy/10);
        if(objetoscCieczy>pojemnosc){
            throw new ZaDuzoPrzesylekException(objetoscCieczy,pojemnosc, id);
        }
        wBrutto = wNetto+objetoscCieczy*gestoscCieczy;
    }
    public String toString(){
        return "WagonCiekleMaterialy id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+
                wBrutto+" towar: "+towar+" waga towaru: "+wagaTowaru;
    }
}
