public class WagonPocztowy extends Wagon{
    private NADAWCA nadawca;
    private ZABEZPIECZENIA zabezpieczenia;
    private double wNetto;
    private double wBrutto;
    private int id;
    protected static int idCounter = 0;
    private int miejsce;
    private int ilePrzesylek;
    private boolean elektryka = true;
    private boolean otwarteDrzwi = false;
    public WagonPocztowy(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, int miejsce,
                         int ilePrzesylek) throws ZaDuzoPrzesylekException {
        id = idCounter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wNetto = wNetto;

        this.miejsce = miejsce;
        this.ilePrzesylek = ilePrzesylek;
        try {
            sprawdzIlePrzesylek();
        }catch(ZaDuzoPrzesylekException e){
            wypakuj();
        }
        wBrutto = wNetto+ilePrzesylek;
        otwarteDrzwi = false;
    }
    public String toString(){
        return "WagonPocztowy id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+
                wBrutto+" miejsce: "+miejsce+" liczba przesylek: "+ilePrzesylek;
    }
    private void sprawdzIlePrzesylek() throws ZaDuzoPrzesylekException {
        if(ilePrzesylek>miejsce){ // nie wiadomo czy throw ma sens
            ilePrzesylek = miejsce;
            throw new ZaDuzoPrzesylekException(miejsce, ilePrzesylek, id);
        }
    }
    private void wypakuj() throws ZaDuzoPrzesylekException {
        if(Math.random()>0.9){
            ilePrzesylek = miejsce;
        }
        else{
            throw new ZaDuzoPrzesylekException(miejsce, ilePrzesylek, id);
        }
    }
    public void dodajTowar(int ileTowaru){
        if(ilePrzesylek+ileTowaru<=miejsce) {
            ilePrzesylek += ileTowaru;
        }
        else{
            System.out.println("Probowano dodac za duzo przesylek, liczba ta zostala automatycznie zmniejszona");
            ilePrzesylek = miejsce;
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
        wBrutto = wNetto+ilePrzesylek;
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

    @Override
    public int compareTo(Wagon o) {
        return (int)(this.getWBrutto()-o.getWBrutto());
    }
}
