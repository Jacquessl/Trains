import java.util.*;

public class Lokomotywa implements Comparable<Lokomotywa> {

    private final int wagonyMax;
    private final int uciagMax;
    private final int eWagonyMax;
    private final String nazwa;
    private Stacja sMacierzysta;
    private Stacja sZrodlowa;
    private Stacja sDocelowa;
    private Stacja aktualnaStacja;
    private static int IDcounter = 0;
    private int ID;
    private double predkosc = 90;
    private Stacja[] trasa;
    private int licznikStacji = 1;
    private final ArrayList<Wagon> sklad = new ArrayList<>();
    private Stacja nastepnaStacja;
    private boolean railroadHazard;
    private boolean jestesmyNaKoncu;
    private double pokonanyDystans;
    private double calyPokonanyDystans;
    private Queue<Watki> nextThreadWaiting = new LinkedList<>();
    private Watki tenWatek;

    public Lokomotywa(int wagonyMax, int uciagMax, int eWagonyMax, Stacja s1, Stacja s2, Stacja s3) throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ZaSlabeZabezpieczeniaException, ZagrazaPasazeromException, ProblemZeSklademException {
        this.wagonyMax = wagonyMax;
        this.uciagMax = uciagMax;
        this.eWagonyMax = eWagonyMax;
        nazwa = generateName();
        sMacierzysta = s1;
        sZrodlowa = s2;
        sDocelowa = s3;
        aktualnaStacja = sZrodlowa;
        ID = IDcounter++;
        jestesmyNaKoncu = false;
        licznikStacji = 1;
        setSklad();
        trasa = prawidlowaStacja();
        nastepnaStacja = aktualnaStacja;
        zadaniaNaStacji();
    }
    public Lokomotywa(){
        wagonyMax = 0;
        uciagMax = 0;
        eWagonyMax = 0;
        nazwa ="";
    }
    private void setSklad() throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ZaSlabeZabezpieczeniaException, ZagrazaPasazeromException, ProblemZeSklademException {
        SkladPociagu sp = new SkladPociagu(this);
        sklad.addAll(sp.generate(wagonyMax, uciagMax, eWagonyMax));
        zagrozenieDlaPasazerow();
    }
    public void setWatek(Watki w){
        tenWatek = w;
    }

    public Watki getWatek(){
        return tenWatek;
    }
    public Stacja[] getTrasa(){
        return trasa;
    }
    private Stacja[] prawidlowaStacja(){
        Stacja[] prawidlowaStacja = trasaIni();
        int index = 0;
        for(int i = 0; i<prawidlowaStacja.length;i++){
            if(prawidlowaStacja[i]==null){
                index = i;
                break;
            }
        }
        Stacja[] tmp = new Stacja[index];
        for(int i = 0; i<tmp.length;i++){
            tmp[i] = prawidlowaStacja[i];
        }
        return tmp;
    }
    public Stacja[] gdzieJestem(){
        Stacja[] aktualnaTrasa = new Stacja[2];
        aktualnaTrasa[0] = aktualnaStacja;
        aktualnaTrasa[1] = nastepnaStacja;
        return aktualnaTrasa;
    }
    public void dotarlismyNaStacje(Watki t) throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ProblemZeSklademException, InterruptedException {
        if(jestesmyNaKoncu){
            t.dojechalismyNaStacjeKoncowa();
        }else {
            t.dojechalismyNaStacje();
        }
    }
    public void getNextThread(Watki t){
        nextThreadWaiting.add(t);
    }
    public void dotarlismyNaStacjeKoncowa() throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ProblemZeSklademException {
        Stacja tmp = sDocelowa;
        sDocelowa = sZrodlowa;
        sZrodlowa = tmp;
        trasa = prawidlowaStacja();
        aktualnaStacja = sZrodlowa;
        nastepnaStacja = aktualnaStacja;
        jestesmyNaKoncu = false;
        licznikStacji=1;
        zadaniaNaStacji();
        calyPokonanyDystans = 0;
    }
    public void zadaniaNaStacji() throws ZaDuzoPrzesylekException, ProblemZeSklademException, PrzeterminowanePozwolenieException {
        aktualnaStacja = nastepnaStacja;
        try {
            nastepnaStacja = trasa[licznikStacji];
        }catch (ArrayIndexOutOfBoundsException e){

        }
        licznikStacji++;
        if(nastepnaStacja.getMiejscowosc().equals(sDocelowa.getMiejscowosc())) {
            jestesmyNaKoncu = true;
        }
        otworzDrzwi();
        setPasazerowie();
        coZBagazem();
        checkUciag();
        ludzieGlodni();
        kontrolaPozwolenia();
        zamknijDrzwi();
        pokonanyDystans = 0;
        if(nextThreadWaiting.size()>0){
            synchronized (nextThreadWaiting.peek()) {
                nextThreadWaiting.peek().notify();
            }
            Watki w = nextThreadWaiting.peek();
            nextThreadWaiting.remove();
            for(int i = 0; i<nextThreadWaiting.size();i++){
                w.getLokomotywa().getNextThread(nextThreadWaiting.peek());
                nextThreadWaiting.remove();
            }
        }
    }
    public void thredoweZadania() throws ZagrazaPasazeromException, RailroadHazard {
        for(int i = 0; i<sklad.size();i++){
            Wagon w = sklad.get(i);
            if(w instanceof WagonChlodniczy){
                ((WagonChlodniczy) w).setTemperatura();
            }
            else if(w instanceof WagonNaCiekleMaterialyToksyczne){
                try {
                    ((WagonNaCiekleMaterialyToksyczne) w).setTemperatura();
                }catch(ZagrazaPasazeromException e){
                    sklad.remove(w);
                }
            }
        }
    }
    private void kontrolaPozwolenia() throws PrzeterminowanePozwolenieException {
        for(Wagon w : sklad){
            if(w instanceof WagonNaMaterialyWybuchowe){
                ((WagonNaMaterialyWybuchowe) w).kontrola();
            }
        }
    }
    public void dodajWagon(Wagon w){
        double wagaWagonow = 0;
        if(sklad.size()==wagonyMax){

        }
        else {
            int counterElektrycznych=0;
            for (int i = 0; i < sklad.size(); i++) {
                if(sklad.get(i).getElektyczny()){
                    counterElektrycznych++;
                }
                wagaWagonow += sklad.get(i).getWBrutto();
            }
            wagaWagonow+=w.getWBrutto();
            if(w.getElektyczny()){
                counterElektrycznych++;
            }
            if(wagaWagonow<=uciagMax&&counterElektrycznych<=eWagonyMax){
                sklad.add(w);
            }
            else{
                System.out.println("Nie udalo sie dodac wagonu, przekroczone limity");
            }
        }
    }
    public void usunStacje(Stacja s) throws NieUdanaProbaUsunieciaStacjiException {
        for(int i = 0; i<trasa.length;i++){
            if(s.getMiejscowosc().equals(trasa[i].getMiejscowosc())){
                if(trasa.length<=2){
                    throw new NieUdanaProbaUsunieciaStacjiException("Nie udana proba usuniecia stacji "+s.getMiejscowosc());
                }
                if(nastepnaStacja.getMiejscowosc().equals(s.getMiejscowosc())){
                    throw new NieUdanaProbaUsunieciaStacjiException("Nie udana proba usuniecia stacji "+s.getMiejscowosc()+", lokomotywa "+ID+", aktualnie zmierza " +
                            "w kierunku tej stacji, odczekaj chwile, aby usunac");
                }
                if(i==trasa.length-1 && licznikStacji>=trasa.length-2){
                    throw new NieUdanaProbaUsunieciaStacjiException("Nie udana proba usuniecia stacji "+s.getMiejscowosc()+", odczekaj chwile, aby usunac");
                }
                if(i<licznikStacji){
                    licznikStacji--;
                }
                if(i==0){
                    sZrodlowa = trasa[1];
                    calyPokonanyDystans -= new Trasa(trasa[0],trasa[1]).przelicznikTrasy();
                }
                if(i==trasa.length-1){
                    sDocelowa = trasa[trasa.length-2];
                }
                trasa[i] = null;
                int k = 0;
                Stacja[] tmp = trasa;
                trasa = new Stacja[tmp.length-1];
                for(int j = 0;j<tmp.length;j++){
                    if(tmp[j]!=null){
                        trasa[k] = tmp[j];
                        k++;
                    }
                }
                System.out.println("Lokomotywa "+ID+": usunieto stacje: "+s.getMiejscowosc()+", zmieniono trase");
            }
        }

    }
    public void usunWagon(int index){
        sklad.remove(index);
    }
    public ArrayList<Wagon> getSklad(){
        return sklad;
    }
    private void otworzDrzwi(){
        for(Wagon w: sklad){
            w.otworzDrzwi();
        }
    }
    private void zamknijDrzwi(){
        for(Wagon w: sklad){
            w.zamknijDrzwi();
        }
    }
    private void zagrozenieDlaPasazerow() throws ZagrazaPasazeromException, ProblemZeSklademException {
        int counterPasazerskich = 0;
        boolean czyWyrzucacException = false;
        int counterRestauracyjnych = 0;
        TOWAR towar = TOWAR.WODA;
        for(Wagon w : sklad){
            if(w instanceof WagonPasazerski){
                counterPasazerskich++;
            }
            if(w instanceof WagonNaCiekleMaterialyToksyczne){
                czyWyrzucacException = ((WagonNaCiekleMaterialyToksyczne) w).czyZagrazaPasazerom();
                towar = ((WagonNaCiekleMaterialyToksyczne) w).getTowar();
            }
            if(w instanceof WagonNaMaterialyWybuchowe){
                czyWyrzucacException = ((WagonNaMaterialyWybuchowe) w).czyZagrazaPasazerom();
                towar = ((WagonNaMaterialyWybuchowe) w).getTowar();
            }
            if(counterPasazerskich>0 && czyWyrzucacException){
                throw new ZagrazaPasazeromException(towar);
            }
            if(w instanceof WagonRestauracyjny){
                counterRestauracyjnych++;
            }
            if(counterPasazerskich==0&&counterRestauracyjnych>0){
                throw new ProblemZeSklademException("Nie potrzebny wagon restauracyjny jak nie ma pasazerskiego");
            }
        }
    }
    private void coZBagazem() throws ZaDuzoPrzesylekException {
        int counterPasazerow = 0;
        int counterBagazowych = 0;
        for(Wagon w : sklad){
            if(w instanceof WagonPasazerski){
                counterPasazerow += ((WagonPasazerski) w).getIloscPasazerow();
            }
            if(w instanceof WagonBagazowoPocztowy){
                counterBagazowych++;
            }
        }
        for(Wagon w : sklad){
            if(w instanceof WagonBagazowoPocztowy){
                ((WagonBagazowoPocztowy) w).setBagaz(counterPasazerow/counterBagazowych);
            }
        }
    }
    private void ludzieGlodni(){
        Queue<Osoba> que = new LinkedList<>();
        int counterPasazerskich = 0;
        int[] indexPasazerskich = new int[10];
        int counterRestauracyjnych = 0;
        int indexRestauracyjnych = 0;
        for(Wagon w : sklad){
            if(w instanceof WagonPasazerski){
                indexPasazerskich[counterPasazerskich] = counterPasazerskich;
                counterPasazerskich++;
            }
            if(w instanceof WagonRestauracyjny){
                indexRestauracyjnych = counterRestauracyjnych;
                counterRestauracyjnych++;
            }
        }
        if(counterRestauracyjnych>0&&counterPasazerskich>0){
            for(int j = 0; j<counterPasazerskich;j++){
                if(sklad.get(indexPasazerskich[j]) instanceof WagonPasazerski) {
                    for (Osoba o : ((WagonPasazerski) sklad.get(indexPasazerskich[j])).getPasazerowie()) {
                        if (o.getGlod()) {
                            que.add(o);
                        }
                    }
                }
            }
            if(sklad.get(indexRestauracyjnych) instanceof WagonRestauracyjny){
                ((WagonRestauracyjny) sklad.get(indexRestauracyjnych)).setKlienci(que, nastepnaStacja);
            }
        }
    }
    public Stacja getSZrodlowa(){
        return sZrodlowa;
    }
    public double getDlugoscAktualnegoOdcinka(){
        Trasa t = new Trasa(aktualnaStacja, nastepnaStacja);
        return t.przelicznikTrasy();
    }
    public void predkosc() throws RailroadHazard {
        int rand = (int)(Math.random()*2);
        if(rand==1) {
            predkosc += predkosc * 0.03;
        }
        else {
            predkosc -= predkosc * 0.03;
        }
        if(predkosc > 200 && !railroadHazard){
            railroadHazard = true;
            throw new RailroadHazard(this.toString());
        }
        else if(predkosc < 200 && railroadHazard){
            railroadHazard = false;
        }
    }
    public int getID(){
        return ID;
    }
    private void setPasazerowie() throws ZaDuzoPrzesylekException, ProblemZeSklademException {
        for(int i = 0; i<sklad.size();i++){
            Wagon w = sklad.get(i);
            if (w instanceof WagonPasazerski){
                Stacja[] arr = new Stacja[trasa.length-licznikStacji+1];
                for(int j = 0; j<arr.length;j++){
                    arr[j] = trasa[licznikStacji+j-1];
                }
                ((WagonPasazerski) w).pozostaleStacje(arr);
                ((WagonPasazerski) w).setPasazerowie(aktualnaStacja, nastepnaStacja, arr);
            }
        }
    }
    public void dodajPasazerow(int ileDodac) throws ZaDuzoPrzesylekException, ProblemZeSklademException {
        boolean czyMaPasazerski = false;
        for(int i = 0; i<sklad.size();i++){
            Wagon w = sklad.get(i);
            if (w instanceof WagonPasazerski){
                czyMaPasazerski = true;
                Stacja[] arr = new Stacja[trasa.length-licznikStacji+1];
                for(int j = 0; j<arr.length;j++){
                    arr[j] = trasa[licznikStacji+j-1];
                }
                ((WagonPasazerski) w).pozostaleStacje(arr);
                ((WagonPasazerski) w).dodajPasazerow(aktualnaStacja, nastepnaStacja, arr, ileDodac);
            }
        }
        if(!czyMaPasazerski){
            System.out.println("Podana lokomotywa nie posiada wagonu pasazerskiego, operacja nie powiodla sie");
        }else{
            coZBagazem();
        }
    }
    public void usunPasazerow(int ileUsunac) throws ZaDuzoPrzesylekException, ProblemZeSklademException {
        boolean czyMaPasazerski = false;
        for(int i = 0; i<sklad.size();i++){
            Wagon w = sklad.get(i);
            if (w instanceof WagonPasazerski){
                czyMaPasazerski = true;
                Stacja[] arr = new Stacja[trasa.length-licznikStacji+1];
                for(int j = 0; j<arr.length;j++){
                    arr[j] = trasa[licznikStacji+j-1];
                }
                ((WagonPasazerski) w).pozostaleStacje(arr);
                ((WagonPasazerski) w).usunPasazerow(aktualnaStacja, ileUsunac);
            }
        }
        if(!czyMaPasazerski){
            System.out.println("Podana lokomotywa nie posiada wagonu pasazerskiego, operacja nie powiodla sie");
        }else{
            coZBagazem();
        }
    }
    public double getPredkosc(){
        return predkosc;
    }
    private String generateName(){
        int rand = (int)(Math.random()*6)+4;
        StringBuilder name = new StringBuilder();
        int c1 = (int)(Math.random()*('Z'-'A')+'A');
        char cc1 = (char)c1;
        name.append(cc1);
        for(int i = 0; i<rand; i++){
            int c = (int)((Math.random()*('z'-'a'))+'a');
            char cc = (char)c;
            name.append(cc);
        }
        return name.toString();
    }

    public String toString(){
        StringBuilder strSklad = new StringBuilder();
        Collections.sort(sklad);
        for(Wagon w : sklad) {
            strSklad.append(w).append(", ");
        }
        return "Lokomotywa "+nazwa + " ID: " + ID +" aktualnie na trasie: "+aktualnaStacja+" - "+nastepnaStacja+", pokonano: "+(getPokonanyDystans()/getDlugoscAktualnegoOdcinka())*100+"%, wagony: "+strSklad;
    }
    public String getRaport(){
        StringBuilder strSklad = new StringBuilder();
        Collections.sort(sklad);
        for(Wagon w : sklad) {
            strSklad.append(w).append(", \n");
        }
        return "Lokomotywa "+nazwa + " ID: " + ID +" max wagonow: "+ wagonyMax+", max wagonow elektrycznych: "+eWagonyMax+", maksymalny uciag: "+uciagMax
                +",\nna trasie: "+sZrodlowa+" - "+sDocelowa+", pokonano: "+(calyPokonanyDystans/dlugoscCalejTrasy())*100+"% calej trasy"
                +", \nwagony:\n"+strSklad
                +"aktualnie na trasie: "+aktualnaStacja+" - "+nastepnaStacja+", pokonano: "+(getPokonanyDystans()/getDlugoscAktualnegoOdcinka())*100+ "% trasy";
    }
    public Stacja[] trasaIni(){
        Trasa t = new Trasa(sZrodlowa, sDocelowa);

        return t.najDroga();
    }
    private void checkUciag() throws ProblemZeSklademException {
        int wspolnaMasa = 0;
        for(int i = 0;i<sklad.size();i++){
            Wagon w = sklad.get(i);
            wspolnaMasa+=w.getWBrutto();
        }
        if(wspolnaMasa>uciagMax){
            throw new ProblemZeSklademException("Maksymalne obciazenie: "+uciagMax+", probowano uciagnac "+wspolnaMasa);
        }
    }
    public void setPokonanyDystans(){
        pokonanyDystans += predkosc/36000;
        calyPokonanyDystans += predkosc/36000;
    }
    public double getPokonanyDystans(){
        return pokonanyDystans;
    }
    public double dlugoscCalejTrasy(){
        double calaTrasa = 0;
        for(int i =1; i<trasa.length;i++){
            Trasa czesc = new Trasa(trasa[i-1],trasa[i]);
            calaTrasa += czesc.przelicznikTrasy();
        }
        return calaTrasa;
    }
    @Override
    public int compareTo(Lokomotywa o) {
        return (int)((o.getPokonanyDystans()/o.getDlugoscAktualnegoOdcinka()-getPokonanyDystans()/getDlugoscAktualnegoOdcinka())*100);
    }
}
