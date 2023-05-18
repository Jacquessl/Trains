public class WagonChlodniczy extends WagonTowarowyPodstawowy{

    private boolean elektryka = true;
    private int pojemoscChlodni;
    private int iloscTowaru;
    private double temperatura;
    private double maxTemperatura;
    private TOWAR nieZaplanowanyTowar;
    private int counterPrzegrzania = 0;
    public WagonChlodniczy(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, TOWAR towar, double waga, int pojemoscChlodni) throws ZaDuzoPrzesylekException {
        super(nadawca, zabezpieczenia, wNetto, towar, waga);
        this.pojemoscChlodni = pojemoscChlodni;
        iloscTowaru = (int)(pojemoscChlodni/waga);
        if(iloscTowaru>pojemoscChlodni){
            throw new ZaDuzoPrzesylekException(pojemoscChlodni, iloscTowaru, id);
        }
        temperatura = 0;
        setTemperatura();
        maxTemperatura = Math.random()*12;
    }
    public void setTemperatura(){ //to nie na kazdej stacji tylko w jakim tredzie
        temperatura += Math.random()>0.5?Math.random():Math.random()*-1;
        sprawdzTemperature();
    }
    private void sprawdzTemperature(){
        if(temperatura>maxTemperatura){
            rozpuszczanie();
            counterPrzegrzania++;
        }
        else if(temperatura<0 && !towar.equals(TOWAR.LOD)){
            nieZaplanowanyTowar = TOWAR.LOD;
        }
        else if(temperatura<0){
            nieZaplanowanyTowar = null;
        }
    }
    private void rozpuszczanie(){
        nieZaplanowanyTowar = TOWAR.WODA;
    }
    public String toString(){
        return "WagonChlodniczy id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+
                wBrutto+" towar: "+towar+" waga towaru: "+wagaTowaru+" pojemnosc chlodni: "+pojemoscChlodni+" ilosc towaru: "+iloscTowaru;
    }
}
