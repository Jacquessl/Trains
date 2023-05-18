public class Osoba {
    private IMIONA imie;
    private boolean plec; //true dla mezczyzn | false dla kobiet
    private Stacja sDocelowa;
    private boolean glod;
    private int waga;

    public Osoba(Stacja sDocelowa){
        this.sDocelowa = sDocelowa;
        imie = setImie();
        plec = setPlec();
        waga = setWaga();
    }
    public void setGlod(Stacja nastepnaStacja){
        if(!nastepnaStacja.equals(sDocelowa)) {
            if (!plec) {
                glod = Math.random() < 0.2;
            } else {
                glod = Math.random() < 0.15;
            }
        }
        else{
            glod = false;
        }
    }
    public boolean getGlod(){
        return glod;
    }
    public boolean getPlec(){
        return plec;
    }
    private int setWaga(){
        if(!plec){
            return (int)(Math.random()*60)+35;
        }
        return (int)(Math.random()*80)+40;
    }
    public int getWaga(){
        return waga;
    }
    public Stacja getStacja(){
        return sDocelowa;
    }
    private IMIONA setImie(){
        return IMIONA.values()[(int)(Math.random()*IMIONA.values().length)];
    }
    public IMIONA getImie(){
        return imie;
    }
    public String stacjaDocelowa(){
        return sDocelowa.getMiejscowosc();
    }
    private boolean setPlec(){
        if(imie.getValue()==0){
            return false;
        }
        return true;
    }
}
