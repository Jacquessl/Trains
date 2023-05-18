public class WagonNaMaterialyGazowe extends WagonTowarowyPodstawowy{
    private boolean elektryka = false;
    private double stezenie;
    private double maxStezenie;
    public WagonNaMaterialyGazowe(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, TOWAR towar, double waga,
                                  double stezenie, double maxStezenie) throws ZagrazaPasazeromException {
        super(nadawca, zabezpieczenia, wNetto, towar, waga);
        this.stezenie = stezenie;
        this.maxStezenie = maxStezenie;
        try {
            sprawdzStezenie();
        }catch(ZagrazaPasazeromException e){
            ulotnijGaz();
        }
    }
    private void sprawdzStezenie() throws ZagrazaPasazeromException {
        if(stezenie>maxStezenie){
            throw new ZagrazaPasazeromException(towar, stezenie, maxStezenie);
        }
    }
    private void ulotnijGaz() throws ZagrazaPasazeromException {
        if(czyNaPewnoWypuszczamy()){
            stezenie=maxStezenie;
        }
        else{
            throw new ZagrazaPasazeromException(towar, stezenie, maxStezenie);
        }
    }
    private boolean czyNaPewnoWypuszczamy(){
        return Math.random()>0.25;
    }
    public String toString(){
        return "WagonMaterialyGazowe id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+
                " waga brutto: "+wBrutto+" towar: "+towar+" waga towaru: "+wagaTowaru;
    }
}
