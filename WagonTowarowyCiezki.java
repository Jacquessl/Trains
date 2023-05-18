public class WagonTowarowyCiezki extends Wagon{
    protected NADAWCA nadawca;
    protected ZABEZPIECZENIA zabezpieczenia;
    protected double wNetto;
    protected TOWAR towar;
    protected double wagaTowaru;
    protected double wBrutto;
    protected boolean ubezpieczony;
    protected double maxWaga;
    protected int id;
    protected static int idCounter = 0;
    protected boolean elektryka = false;
    protected boolean otwarteDrzwi;
    public WagonTowarowyCiezki(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, TOWAR towar,double waga,
                               boolean ubezpieczony, double maxWaga) throws ZaDuzoPrzesylekException {
        id = idCounter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wNetto = wNetto;
        this.wagaTowaru = waga;
        this.towar = towar;
        this.ubezpieczony = ubezpieczony;
        this.maxWaga = maxWaga;
        otwarteDrzwi = false;
        try{
            sprawdzCzyWagaPrzekroczona();
        }
        catch(ZaDuzoPrzesylekException e){
            czyWyrzucicTowar();
        }
        wBrutto = wNetto+wagaTowaru;
    }
    private void sprawdzCzyWagaPrzekroczona() throws ZaDuzoPrzesylekException {
        if(wagaTowaru>maxWaga){
            throw new ZaDuzoPrzesylekException(maxWaga, wagaTowaru, id);
        }
    }
    private void czyWyrzucicTowar() throws ZaDuzoPrzesylekException {
        if(Math.random()>0.9){
            wagaTowaru=maxWaga;
        }
        else{
            throw new ZaDuzoPrzesylekException(maxWaga, wagaTowaru, id);
        }
    }
    private void setwBrutto(){
        wBrutto = wNetto+wagaTowaru;
    }
    public void dodajTowar(int ileTowaru){
        if(wagaTowaru+ileTowaru<=maxWaga) {
            wagaTowaru += ileTowaru;
        }
        else{
            System.out.println("Probowano dodac za duzo przesylek, liczba ta zostala automatycznie zmniejszona");
            wagaTowaru = maxWaga;
        }
        setwBrutto();
    }
    public void usunTowar(int ileTowaru){
        if(wagaTowaru+ileTowaru>0) {
            wagaTowaru -= ileTowaru;
        }
        else{
            System.out.println("Probowano usunac za duzo przesylek, liczba ta zostala automatycznie zmniejszona");
            wagaTowaru = 0;
        }
        setwBrutto();
    }
    public TOWAR getTowar(){
        return towar;
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
        return "WagonTowarowyCiezki id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+wBrutto+
                " towar: "+towar+ " waga towaru: "+ wagaTowaru;
    }

    @Override
    public int compareTo(Wagon o) {
        return (int)(this.getWBrutto()-o.getWBrutto());
    }
}
