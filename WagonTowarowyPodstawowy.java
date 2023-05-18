public class WagonTowarowyPodstawowy extends Wagon{
    protected NADAWCA nadawca;
    protected ZABEZPIECZENIA zabezpieczenia;
    protected double wNetto;
    protected TOWAR towar;
    protected double wagaTowaru;
    protected double wBrutto;
    protected int id;
    protected static int idCounter = 0;
    protected boolean elektryka = false;
    protected boolean otwarteDrzwi;

    public WagonTowarowyPodstawowy(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, TOWAR towar,double waga){
        id = idCounter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wNetto = wNetto;
        this.wagaTowaru = waga;
        setWBrutto();
        this.towar = towar;
        otwarteDrzwi = false;
    }

    public void setWBrutto(){
        wBrutto = wNetto+wagaTowaru;
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
    public void dodajTowar(int ileTowaru){
        wagaTowaru += ileTowaru;
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
    private void setwBrutto(){
        wBrutto = wNetto+wagaTowaru;
    }

    public String toString(){
        return "WagonTowarowyPodstawowy id: "+id+" nadawdca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto "+wBrutto+" towar: "+towar +
                " waga towaru: "+wagaTowaru;
    }

    @Override
    public int compareTo(Wagon o) {
        return (int)(this.getWBrutto()-o.getWBrutto());
    }
}
