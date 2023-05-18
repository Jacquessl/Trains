public class WagonBagazowoPocztowy extends Wagon{
    private Enum<NADAWCA> nadawca;
    private Enum<ZABEZPIECZENIA> zabezpieczenia;
    private double wNetto;
    private double wBrutto;
    private int miejsce;
    private int ileBagazu;
    private int maxPrzesylek;
    private int ilePrzesylek;
    private int id;
    protected static int idCounter = 0;
    private boolean elektryka = false;
    private boolean otwarteDrzwi;
    public WagonBagazowoPocztowy(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, int miejsce,
                                 int ilePrzesylek){
        id = idCounter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wNetto = wNetto;
        this.miejsce = miejsce;
        this.ilePrzesylek = ilePrzesylek;
        otwarteDrzwi = false;
    }
    public void setBagaz(int ilePasazerow) throws ZaDuzoPrzesylekException {
        int bagaz = 0;
        for(int i = 0; i < ilePasazerow; i++){
            bagaz++;
        }
        ileBagazu = bagaz;
        wBrutto = wNetto+(ileBagazu*20)+ilePrzesylek;
        maxPrzesylek = miejsce-ileBagazu;
        if(ilePrzesylek>maxPrzesylek){
            try {
                wypakujPrzesylki();
            }
            catch (ZaDuzoPrzesylekException e){

            }
        }

    }
    private void wypakujPrzesylki() throws ZaDuzoPrzesylekException {
        ilePrzesylek -= (int)(Math.random()*ilePrzesylek);
        zapakujPrzesylki();
    }
    private void zapakujPrzesylki() throws ZaDuzoPrzesylekException {
        ilePrzesylek += (int)(Math.random()*maxPrzesylek);
        if(ilePrzesylek>maxPrzesylek){
            throw new ZaDuzoPrzesylekException(maxPrzesylek, ilePrzesylek, id);
        }
    }
    public void dodajTowar(int ileTowaru){
        if(ilePrzesylek+ileTowaru<=maxPrzesylek) {
            ilePrzesylek += ileTowaru;
        }
        else{
            System.out.println("Probowano dodac za duzo przesylek, liczba ta zostala automatycznie zmniejszona");
            ilePrzesylek = maxPrzesylek;
        }
        setwBrutto();
    }
    public void usunTowar(int ileTowaru){
        if(ilePrzesylek+ileTowaru>0) {
            ilePrzesylek -= ileTowaru;
        }
        else{
            System.out.println("Probowano usunac za duzo przesylek, liczba ta zostala automatycznie zmniejszona");
            ilePrzesylek = 0;
        }
        setwBrutto();
    }
    private void setwBrutto(){
        wBrutto = wNetto+(ileBagazu*20)+ilePrzesylek;
    }
    @Override
    public double getWBrutto() {
        return wBrutto;
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
        return "WagonBagazowoPocztowy id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+
                wBrutto+" miejsce na bagaz: "+miejsce+" ilosc bagazu: "+ileBagazu+" maksymalna ilosc przesylek: "+maxPrzesylek+" ilosc przesylek: "+ilePrzesylek;
    }

    @Override
    public int compareTo(Wagon o) {
        return (int)(this.getWBrutto()-o.getWBrutto());
    }
}
