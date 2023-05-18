public class WagonNaMaterialyToksyczne extends WagonTowarowyCiezki{
    private boolean elektryka = false;
    private int poziomToksycznosci;
    private int maxPoziomToksycznosci;
    public WagonNaMaterialyToksyczne(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto,
                                     TOWAR towar, double waga, boolean ubezpieczony, double maxWaga,
                                     int poziomToksycznosci, int maxPoziomToksycznosci) throws ZaDuzoPrzesylekException, ZagrazaPasazeromException {
        super(nadawca, zabezpieczenia, wNetto, towar, waga, ubezpieczony, maxWaga);
        this.poziomToksycznosci = poziomToksycznosci;
        this.maxPoziomToksycznosci = maxPoziomToksycznosci;
        try {
            sprawdzWage();
        }
        catch(ZaDuzoPrzesylekException e){
            usunTowar();
        }
        sprawdzToksycznosc();
    }
    private void sprawdzWage() throws ZaDuzoPrzesylekException {
        if(wagaTowaru>maxWaga){
            throw new ZaDuzoPrzesylekException(wagaTowaru, maxWaga, id);
        }
    }
    private void sprawdzToksycznosc() throws ZagrazaPasazeromException {
        if(poziomToksycznosci>maxPoziomToksycznosci){
            throw new ZagrazaPasazeromException(towar, poziomToksycznosci, maxPoziomToksycznosci);
        }
    }
    private void usunTowar() throws ZaDuzoPrzesylekException {
        if(poziomToksycznosci>Math.random()*5){
            wagaTowaru=maxWaga;
            wBrutto = wNetto+wagaTowaru;
        }
        else{
            sprawdzWage();
        }
    }
    public String toString(){
        return "WagonMaterialyToksyczne id: "+id+ " nadawca: " +nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+
                wNetto+" waga brutto: "+wBrutto+" towar: "+towar+" waga towaru: "+wagaTowaru;
    }
}
