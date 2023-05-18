import java.util.ArrayList;

public class Presentation {
    public static void main(String[] args) throws ZaDuzoPrzesylekException, ZagrazaPasazeromException, PrzeterminowanePozwolenieException, ZaSlabeZabezpieczeniaException, ProblemZeSklademException {
        Main.stacjeIni();
        Stacja[] stacje = Main.getStacje();
        int ludnoscNaStacji1 = 0;
        Lokomotywa l = null;
        for(int i = 0; i<1;i++) {
            int rand1 = (int)(Math.random()*100);
            int rand2 = (int)(Math.random()*100);
            if (rand1 != rand2) {
                try {
                    ludnoscNaStacji1 = stacje[rand1].getLudnosc();
                    l = new Lokomotywa(10, 1000000, 10, stacje[(int) (Math.random() * 100)], stacje[rand1], stacje[rand2]);
                } catch (ZaSlabeZabezpieczeniaException | ZaDuzoPrzesylekException |
                         PrzeterminowanePozwolenieException | ProblemZeSklademException | ZagrazaPasazeromException e) {
                    i--;
                }
            }else{
                i--;
            }
        }
        System.out.println("==========================================");
        System.out.println("Tutaj zaczyna sie prezentacja: ");
        if(l!=null){
            Stacja[] trasa = l.getTrasa();
            Trasa t = new Trasa(trasa[0], trasa[trasa.length-1]);
            System.out.println("Cala trasa: "+trasa[0]+" - "+trasa[trasa.length-1]+" odleglosc w linii prostej: "+t.przelicznikTrasy());
            System.out.println("Odcinki na trasie:");
            for(int i = 0; i< trasa.length-1;i++){
                System.out.println(trasa[i] + " - " + trasa[i+1]+" odlegosc: "+new Trasa(trasa[i],trasa[i+1]).przelicznikTrasy());
            }
            ArrayList<Wagon> skladPociagu = l.getSklad();
            for(Wagon w : skladPociagu){
                System.out.println(w);
                if(w instanceof WagonPasazerski){
                    System.out.println("Pasazerowie:");
                    ArrayList<Osoba> pasazerowie = ((WagonPasazerski) w).getPasazerowie();
                    for(Osoba o : pasazerowie){
                        System.out.print(o.getImie()+" jade do: "+o.stacjaDocelowa());
                        if(o.getGlod()){
                            if(o.getPlec()){
                                System.out.print(" i jestem glodny");
                            }else{
                                System.out.print(" i jestem glodna");
                            }
                        }else{
                            if(o.getPlec()){
                                System.out.print(" i nie jestem glodny");
                            }else{
                                System.out.print(" i nie jestem glodna");
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("Ludnosc na stacji przed utworzeniem pociagu: "+ludnoscNaStacji1);
                    ludnoscNaStacji1 = l.getSZrodlowa().getLudnosc();
                    System.out.println("Ludnosc na stacji po utworzeniu i zaladowaniu pasazerow: "+ludnoscNaStacji1);
                    System.out.println("Ilosc pasazerow przed dodaniem: "+((WagonPasazerski) w).getIloscPasazerow());
                    ((WagonPasazerski) w).dodajPasazerow(trasa[0],trasa[1],trasa, 30);
                    System.out.println("Ilosc pasazerow po dodaniu: "+((WagonPasazerski) w).getIloscPasazerow());
                    ludnoscNaStacji1 = l.getSZrodlowa().getLudnosc();
                    System.out.println("Ludnosc na stacji po zaladowaniu dodatkowych pasazerow: "+ludnoscNaStacji1);
                }
                else if(w instanceof WagonTowarowyPodstawowy){
                    System.out.println("Wagon towarowy podstawowy lub jego typ, towar: "+((WagonTowarowyPodstawowy) w).getTowar());
                }
                else if(w instanceof WagonTowarowyCiezki){
                    System.out.println("Wagon towarowy ciezki lub jego typ, towar: "+((WagonTowarowyCiezki) w).getTowar());
                }
            }
        }
    }
}
