import java.util.*;

public class Menu extends Thread{
    private Scanner scanner;
    private Lokomotywa[] lokomotywy;
    private Stacja[] stacje;
    private WypisywanieDoPliku wdp;
    public Menu(Lokomotywa[] lokomotywy, Lokomotywa[] lDoWDP, Stacja[] stacje){
        this.lokomotywy = lokomotywy;
        this.stacje = stacje;
        wdp = new WypisywanieDoPliku(lDoWDP);
    }
    public void setScaner(){
        scanner = new Scanner(System.in);
    }
    @Override
    public void run() {
        wdp.start();
        while(!interrupted()) {
            setScaner();
            System.out.println("1 - dodaj obiekt | 2 - usun obiekt | 3 - edycja | 4 - wyswietl raport");
            System.out.print("Wpisz numer odpowiadajacy operacji, ktora chcesz zrobic: ");
            int wybor = 0;
            try {
                wybor = scanner.nextInt();
            }catch(InputMismatchException e){
                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
            }
            System.out.println();
            switch (wybor) {
                case 1: {
                    try {
                        dodawanie();
                    } catch (ZaDuzoPrzesylekException | ProblemZeSklademException | ZaSlabeZabezpieczeniaException |
                             PrzeterminowanePozwolenieException | ZagrazaPasazeromException e) {
                        System.out.println("Nie udalo sie dodac ");
                    }
                    break;
                }
                case 2: {
                    try {
                        usuwanie();
                    } catch (ZaDuzoPrzesylekException | PrzeterminowanePozwolenieException | ProblemZeSklademException e) {

                    }
                    break;
                }
                case 3: {
                    try {
                        edycja();
                    } catch (ZaDuzoPrzesylekException | ProblemZeSklademException e) {
                        System.out.println("Operacja nie powiodla sie w 100%");
                    }
                    break;
                }
                case 4: {
                    raport();
                    break;
                }
                default: {
                    System.out.println("Wprowadzono niepoprawna liczbe");
                }
            }
        }
    }
    private void dodawanie() throws ZaDuzoPrzesylekException, ZagrazaPasazeromException, PrzeterminowanePozwolenieException, ZaSlabeZabezpieczeniaException, ProblemZeSklademException {
        System.out.println("1 - lokomotywe | 2 - wagon | 3 - stacje | 4 - polaczenie");
        System.out.print("Co chcesz dodac?:");
        int wybor = 0;
        try {
            wybor = scanner.nextInt();
        }catch(InputMismatchException e){
            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
        }
        System.out.println();
        System.out.println("Wprowadz wartosci, \" -1 \"-  jezeli chcesz aby wartosc byla losowa");
        switch (wybor){
            case 1:{
                System.out.print("Wprowadz maksymalna liczbe wagonow: ");
                int wagonyMax = -1;
                try {
                    wagonyMax = scanner.nextInt();
                }catch(InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(wagonyMax==-1){
                    wagonyMax = (int)(Math.random()*6)+5;
                }
                System.out.println();
                System.out.print("Wprowadz maksymalna liczbe wagonow podlaczonych do sieci elektrycznej: ");
                int eWagonyMax = -1;
                try {
                    eWagonyMax = scanner.nextInt();
                }catch(InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(eWagonyMax==-1){
                    eWagonyMax = (int)(Math.random()*6);
                }
                System.out.println();
                System.out.print("Wprowadz maksymalny uciag lokomotywy: ");
                int uciagMax = -1;
                try {
                    uciagMax = scanner.nextInt();
                }catch(InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(uciagMax==-1){
                    uciagMax = (int)(Math.random()*500000)+250000;
                }
                System.out.println();
                Stacja sMacierzysta;
                Stacja sZrodlowa;
                Stacja sDocelowa;
                System.out.println("Wprowadz numer odpowiadajacy stacji, od "+0+" do "+(stacje.length-1) +", aby dodac ja jako stacje macierzysta, aby wyswietlic liste stacji wpisz 1010: ");
                int s1 = -1;
                try {
                    s1 = scanner.nextInt();
                }catch(InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(s1==1010){
                    pobieranieStacji();
                    System.out.println("Wprowadz numer odpowiadajacy stacji, aby dodac ja jako stacje macierzysta: ");
                    try {
                        s1 = scanner.nextInt();
                    }catch(InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                sMacierzysta = ustawianieStacji(s1, stacje);
                System.out.println();
                System.out.println("Wprowadz numer odpowiadajacy stacji, od "+0+" do "+(stacje.length-1) +", aby dodac ja jako stacje zrodlowa, aby wyswietlic liste stacji wpisz 1010: ");
                int s2 = -1;
                try {
                    s2 = scanner.nextInt();
                }catch(InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
  
                }
                if(s2==1010){
                    pobieranieStacji();
                    System.out.println("Wprowadz numer odpowiadajacy stacji, aby dodac ja jako stacje macierzysta: ");
                    try {
                        s2 = scanner.nextInt();
                    }catch(InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                sZrodlowa = ustawianieStacji(s2, stacje);
                System.out.println();
                System.out.println("Wprowadz numer odpowiadajacy stacji, od "+0+" do "+(stacje.length-1) +", aby dodac ja jako stacje docelowa, aby wyswietlic liste stacji wpisz 1010: ");
                int s3 = -1;
                try {
                    s3 = scanner.nextInt();
                }catch(InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(s3==1010){
                    pobieranieStacji();
                    System.out.println("Wprowadz numer odpowiadajacy stacji, aby dodac ja jako stacje macierzysta: ");
                    try {
                        s3 = scanner.nextInt();
                    }catch(InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                if(s2 == s3 && s2 != -1) {
                    System.out.println("Wprowadzono taka sama stacje zrodlowa i docelowa");
                    break;
                }
                sDocelowa = ustawianieStacji(s3, stacje);
                System.out.println();
                try {
                    Lokomotywa l = new Lokomotywa(wagonyMax, uciagMax, eWagonyMax, sMacierzysta, sZrodlowa, sDocelowa);
                    Watki w = new Watki(l, lokomotywy);
                    w.start();
                    Lokomotywa[] tmp = lokomotywy;
                    lokomotywy = new Lokomotywa[tmp.length + 1];
                    for (int i = 0; i < tmp.length; i++) {
                        lokomotywy[i] = tmp[i];
                    }
                    lokomotywy[lokomotywy.length - 1] = l;
                    wdp.dodajLokomotywe(l);
                } catch(ZaSlabeZabezpieczeniaException | ZaDuzoPrzesylekException | PrzeterminowanePozwolenieException | ProblemZeSklademException | ZagrazaPasazeromException e){

                }
                break;
            }
            case 2:{
                SkladPociagu sp = new SkladPociagu(new Lokomotywa());
                System.out.println("1 - Wagon Bagazowo Pocztowy | 2 - Wagon Chlodniczy | 3 - Wagon na Ciekle Materialy Toksyczne | 4 - Wagon Na Materialy Ciekle |\n" +
                        "5 - Wagon Na Materialy Gazowe | 6 - Wagon Na Materialy Toksyczne | 7 - Wagon Na Materialy Wybuchowe | 8 - Wagon Pasazerski |\n" +
                        "9 - Wagon Pocztowy | 10 - Wagon Restauracyjny | 11 - Wagon Towarowy Ciezkie | 12 - Wagon Towarowy Podstawowy");
                int wybor2 = -1;
                try {
                    wybor2 = scanner.nextInt();
                }catch(InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(wybor2==-1){
                    wybor2 = (int)(Math.random()*12)+1;
                }
                Wagon w = null;
                switch (wybor2){
                    case 1:
                        w = new WagonBagazowoPocztowy(sp.setNadawca(), sp.setZabezpieczenia(), (Math.random()*13.5)+32.5, sp.setMiejsce(),sp.setMiejsce());
                        sp.setCounter();
                        break;
                    case 2:
                        w = new WagonChlodniczy(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5,sp.setTowar(2), Math.random()*2, sp.setMiejsce());
                        sp.setCounter();
                        break;
                    case 3:
                        w = new WagonNaCiekleMaterialyToksyczne(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.setTowar(3), Math.random()>0.5,
                                (Math.random()*50000)+10000, sp.setWaga(),(Math.random()*55)+20, (Math.random()*500)+1000,
                                (int)(Math.random()*4), Math.random()>0.5, Math.random()>0.5);
                        break;
                    case 4:
                        w = new WagonNaMaterialyCiekle(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.setTowar(4), sp.setWaga(),
                                (Math.random()*300)+400, (Math.random()*500)+1000);
                        break;
                    case 5:
                        w = new WagonNaMaterialyGazowe(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.setTowar(5),
                                sp.setWaga(), Math.random()*0.16, 0.15);
                        break;
                    case 6:
                        w = new WagonNaMaterialyToksyczne(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.setTowar(6), sp.setWaga(),
                                Math.random()>0.5, (Math.random()*50000)+10000, (int)(Math.random()*11),(int)(Math.random()*6)+5);
                        break;
                    case 7:
                        w = new WagonNaMaterialyWybuchowe(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.setTowar(7), sp.setWaga(),
                                Math.random()>0.5, (Math.random()*50000)+10000, Math.random()>0.5, new Date(sp.rok(), sp.miesiac(), sp.dzien()));
                        sp.setMiesiac();
                        sp.setRok();
                        break;
                    case 8:
                        w = new WagonPasazerski(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.przedzialy(), sp.miejsca(), (int)(Math.random()*300)+100);
                        break;
                    case 9:
                        w = new WagonPocztowy(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.setMiejsce(), sp.setMiejsce());
                        sp.setCounter();
                        break;
                    case 10:
                        w = new WagonRestauracyjny(sp.setNadawca(), sp.setZabezpieczenia(), (Math.random() * 13.5) + 32.5, (int) (Math.random() * 10) + 5);
                        break;
                    case 11:
                        w = new WagonTowarowyCiezki(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5,
                                sp.setTowar(1), sp.setWaga(), Math.random() > 0.5, (Math.random()*50000)+10000);
                        break;
                    case 12:
                        w = new WagonTowarowyPodstawowy(sp.setNadawca(),sp.setZabezpieczenia(),(Math.random()*13.5)+32.5, sp.setTowar(0), (Math.random()*15000)+1000);
                        break;
                }
                if(w != null) {
                    edycja(w);
                }
                break;
            }
            case 3:{
                System.out.print("Wprowadz dlugosc geograficzna: ");
                double x = -1;
                try {
                    x = scanner.nextDouble();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                if(x==-1){
                    x = Math.random()*90;
                }
                System.out.print("Wprowadz szerokosc geograficzna: ");
                double y = -1;
                try {
                    y = scanner.nextDouble();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                if(y==-1){
                    y = Math.random()*90;
                }

                System.out.print("Wprowadz nazwe miejscowosci(spacje zastap znakiem \'-\'): ");
                String miejscowosc = scanner.next();
                scanner.nextLine();
                System.out.println();
                if(miejscowosc.equals("-1")){
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
                    miejscowosc = name.toString();
                }

                System.out.print("Wprowadz liczbe ludnosci: ");
                int ludnosc = -1;
                try {
                    ludnosc = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                if(ludnosc==-1){
                    ludnosc = (int)(Math.random()*10000000);
                }

                System.out.print("Wprowadz ilosc stacji, do ktorych odjezdzaja pociagi z tej stacji: ");
                int maxOdjazd = -1;
                try {
                    maxOdjazd = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                if(maxOdjazd==-1){
                    maxOdjazd = (int)(Math.random()*6)+5;
                }

                Stacja s = new Stacja(x,y,miejscowosc,ludnosc,maxOdjazd);
                s.odjazdDo();
                Stacja[] tmp = stacje;
                stacje = new Stacja[tmp.length+1];
                for(int i = 0; i<tmp.length;i++){
                    stacje[i] = tmp[i];
                }
                stacje[stacje.length-1] = s;
                Main.setStacje(s);
                break;
            }
            case 4:{
                System.out.println("Wprowadz numer odpowiadajacy stacji, od "+0+" do "+(stacje.length-1) +", aby dodac pierwsza stacje, aby wyswietlic liste stacji wpisz 1010: ");
                int s = -1;
                try {
                    s = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                if(s==1010){
                    pobieranieStacji();
                    System.out.println("Wprowadz numer odpowiadajacy stacji, aby dodac ja jako pierwsza stacje: ");
                    try {
                        s = scanner.nextInt();
                    }catch (InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                Stacja s1 = ustawianieStacji(s,stacje);

                System.out.println();
                System.out.println("Wprowadz numer odpowiadajacy stacji, od "+0+" do "+(stacje.length-1) +", aby dodac druga stacje, aby wyswietlic liste stacji wpisz 1010: ");
                try {
                    s = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                if(s==1010){
                    pobieranieStacji();
                    System.out.println("Wprowadz numer odpowiadajacy stacji, aby dodac ja jako druga stacje: ");
                    try {
                        s = scanner.nextInt();
                    }catch (InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                Stacja s2 = ustawianieStacji(s,stacje);
                s1.dodajStacje(s2);
                System.out.println("Wprowadz 1 jezeli chcesz, aby rowniez z drugiej stacji utworzono polaczenie do pierwszej stacji");
                int wybor3 = -1;
                try {
                    wybor3 = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(wybor3==1) {
                    s2.dodajStacje(s1);
                }
                break;
            }
            default:{
                System.out.println("Wprowadzono niepoprawna liczbe");
            }
        }
    }
    private void pobieranieStacji(){
        stacje = Main.getStacje();
        for(int i = 0; i<stacje.length;i++){
            if((i+1)%6==0){
                System.out.println();
            }
            System.out.print(stacje[i].getMiejscowosc()+" - numer: "+i+", ");
        }
        System.out.println();
    }
    private Stacja ustawianieStacji(int s, Stacja[] stacje){
        switch (s){
            case -1:{
                return stacje[(int)(Math.random()*stacje.length)];
            }
            default:{
                try{
                    return stacje[s];
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Wprowadzono niepoprawna liczbe");
                }
            }
        }
        return stacje[0];
    }
    private void usuwanie() throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ProblemZeSklademException {
        System.out.println("1 - lokomotywe | 2 - wagon | 3 - stacje | 4 - polaczenie");
        System.out.print("Co chcesz usunac?:");
        int wybor = -1;
        try {
            wybor = scanner.nextInt();
        }catch (InputMismatchException e){
            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
        }
        System.out.println();
        System.out.println("Wprowadz wartosci, \" -1 \"-  jezeli chcesz aby wartosc byla losowa");
        switch (wybor){
            case 1:{
                System.out.print("Podaj numer id lokomotywy, ktora chcesz usunac, wpisz 1010, aby wyswietlic liste lokomotyw: ");
                int wybor2 = -1;
                try {
                    wybor2 = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(wybor2==1010){
                    pobieranieLokomotyw();
                    System.out.print("Podaj numer id lokomotywy, ktora chcesz usunac: ");
                    try {
                        wybor2 = scanner.nextInt();
                    }catch (InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                System.out.println();
                switch (wybor2){
                    case -1: {
                        int rand = (int)(Math.random()*lokomotywy.length);
                        lokomotywy[rand].getWatek().stop();
                        lokomotywy[rand] = null;
                        Lokomotywa[] tmp = lokomotywy;
                        lokomotywy = new Lokomotywa[tmp.length-1];
                        int k = 0;
                        for(int i = 0; i<tmp.length;i++){
                            if(i!=rand) {
                                lokomotywy[k] = tmp[i];
                                k++;
                            }
                        }
                        wdp.usunLokomotywe(rand);
                        break;
                    }
                    default:{
                        int index = -10;
                        Lokomotywa l = null;
                        for(int i = 0; i< lokomotywy.length; i++) {
                            if (wybor2 == lokomotywy[i].getID()) {
                                l = lokomotywy[i];
                                index = i;
                                break;
                            }
                        }
                        if(l!=null){
                            l.getWatek().stop();
                            lokomotywy[index] = null;
                            Lokomotywa[] tmp = lokomotywy;
                            lokomotywy = new Lokomotywa[tmp.length-1];
                            int k = 0;
                            for(int i = 0; i<tmp.length;i++){
                                if(i!=index) {
                                    lokomotywy[k] = tmp[i];
                                    k++;
                                }
                            }
                            wdp.usunLokomotywe(index);
                        }else{
                            System.out.println("Wprowadzono niepoprawna liczbe");
                        }

                    }
                }
                break;
            }
            case 2:{
                System.out.print("Podaj numer id lokomotywy, z ktorej chcesz usunac wagon, wpisz 1010, aby wyswietlic liste lokomotyw: ");
                int wybor2 = -1;
                try {
                    wybor2 = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if(wybor2==1010){
                    pobieranieLokomotyw();
                    System.out.print("Podaj numer id lokomotywy, z ktorej chcesz usunac wagon: ");
                    try {
                        wybor2 = scanner.nextInt();
                    }catch (InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                System.out.println();
                switch (wybor2){
                    case -1:{
                        Lokomotywa l = lokomotywy[(int)(Math.random()* lokomotywy.length)];
                        System.out.print("Podaj numer wagonu, ktory chcesz usunac , wpisz 1010, aby wyswietlic liste wagonow: ");
                        int wybor3 = -1;
                        try {
                            wybor3 = scanner.nextInt();
                        }catch (InputMismatchException e){
                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                        }
                        if(wybor3==1010){
                            ArrayList<Wagon> wagony = l.getSklad();
                            for(int i = 0;i<wagony.size();i++){
                                System.out.println(wagony.get(i)+" - numer: "+i);
                            }
                            System.out.println();
                            System.out.print("Podaj numer wagonu, ktory chcesz usunac: ");
                            try {
                                wybor3 = scanner.nextInt();
                            }catch (InputMismatchException e){
                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                            }
                        }
                        switch (wybor3){
                            case -1:{
                                l.usunWagon((int)(Math.random()*l.getSklad().size()));
                                break;
                            }
                            default:{
                                if(wybor3>=0&&wybor3<l.getSklad().size()) {
                                    l.usunWagon(wybor3);
                                }else{
                                    System.out.println("Wprowadzono niepoprawna liczbe");
                                }
                            }
                        }
                        break;
                    }
                    default:{
                        Lokomotywa l = null;
                        for(int i = 0; i< lokomotywy.length; i++){
                            if(wybor2==lokomotywy[i].getID()){
                                l = lokomotywy[i];
                                break;
                            }
                        }
                        if(l!=null) {
                            System.out.print("Podaj numer wagonu, ktory chcesz usunac , wpisz 1010, aby wyswietlic liste wagonow: ");
                            int wybor3 = -1;
                            try {
                                wybor3 = scanner.nextInt();
                            }catch (InputMismatchException e){
                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                            }
                            if (wybor3 == 1010) {
                                ArrayList<Wagon> wagony = l.getSklad();
                                for (int i = 0; i < wagony.size(); i++) {
                                    System.out.println(wagony.get(i) + " - numer: " + i);
                                }
                                System.out.println();
                                System.out.print("Podaj numer wagonu, ktory chcesz usunac: ");
                                try {
                                    wybor3 = scanner.nextInt();
                                }catch (InputMismatchException e){
                                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                }
                            }
                            switch (wybor3) {
                                case -1: {
                                    l.usunWagon((int) (Math.random() * l.getSklad().size()));
                                    break;
                                }
                                default: {
                                    if (wybor3 >= 0 && wybor3 < l.getSklad().size()) {
                                        l.usunWagon(wybor3);
                                    } else {
                                        System.out.println("Wprowadzono niepoprawna liczbe");
                                    }
                                }
                            }
                        }else{
                            System.out.println("Wprowadzono niepoprawna liczbe");
                        }
                    }
                }
                break;
            }
            case 3: {
                System.out.print("Podaj numer stacji, ktora chcesz usunac, wpisz 1010, aby wyswietlic liste stacji: ");
                int wybor2 = -1;
                try {
                    wybor2 = scanner.nextInt();
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                if (wybor2 == 1010) {
                    pobieranieStacji();
                    System.out.print("Podaj numer stacji, ktora chcesz usunac: ");
                    try {
                        wybor2 = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                switch (wybor2) {
                    case -1: {
                        try {
                            if (stacje.length > 94) {
                                int rand = (int) (Math.random() * stacje.length);
                                Stacja doUsuniecia = stacje[rand];
                                for (int i = 0; i < lokomotywy.length; i++) {
                                    lokomotywy[i].usunStacje(doUsuniecia);
                                }
                                Main.usunStacje(doUsuniecia);
                                stacje = Main.getStacje();
                            } else {
                                System.out.println("Nie mozna usunac wiecej stacji");
                            }
                        }catch (NieUdanaProbaUsunieciaStacjiException e){

                        }
                        break;
                    }
                    default: {
                        if (stacje.length > 94) {
                            try {
                                Stacja doUsuniecia = stacje[wybor2];
                                for (int i = 0; i < lokomotywy.length; i++) {
                                    lokomotywy[i].usunStacje(doUsuniecia);
                                }
                                Main.usunStacje(doUsuniecia);
                                stacje = Main.getStacje();
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Wprowadzono niepoprawna liczbe");
                            } catch (NieUdanaProbaUsunieciaStacjiException e) {

                            }
                        } else {
                            System.out.println("Nie mozna usunac wiecej stacji");
                        }
                    }
                }
                break;
            }
            case 4:{
                System.out.println("Wprowadz numer odpowiadajacy stacji, od "+0+" do "+(stacje.length-1) +", aby dodac pierwsza stacje, aby wyswietlic liste stacji wpisz 1010: ");
                int s = -1;
                try {
                    s = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                if(s==1010){
                    pobieranieStacji();
                    System.out.println("Wprowadz numer odpowiadajacy stacji, aby dodac ja jako pierwsza stacje: ");
                    try {
                        s = scanner.nextInt();
                    }catch (InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                Stacja s1 = ustawianieStacji(s, stacje);

                System.out.println();
                System.out.println("Wprowadz numer odpowiadajacy stacji, od "+0+" do "+(stacje.length-1) +", aby dodac druga stacje, aby wyswietlic liste stacji, do ktorej pociagi odjezdzaja z tej stacji, wpisz 1010: ");
                int ss2 = -1;
                try {
                    ss2 = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                Stacja[] rozklad = s1.getRozklad();
                if(ss2==1010){
                    for(int i = 0;i<rozklad.length;i++){
                        System.out.println(rozklad[i].getMiejscowosc()+" - numer: "+i+", ");
                    }
                    System.out.println();
                    System.out.println("Wprowadz numer odpowiadajacy stacji, aby dodac ja jako druga stacje: ");
                    try {
                        ss2 = scanner.nextInt();
                    }catch (InputMismatchException e){
                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                    }
                }
                try {
                    Stacja s2 = ustawianieStacji(ss2, rozklad);
                    s1.usunStacje(s2);
                }catch (NullPointerException | ArrayIndexOutOfBoundsException e){
                    System.out.println("Nie udalo sie usunac polaczenia");
                }

                break;
            }
            default:{
                System.out.println("Wprowadzono niepoprawna liczbe");
            }
        }
    }
    private void edycja() throws ZaDuzoPrzesylekException, ProblemZeSklademException {
        System.out.println("1 - liczbe pasazerow | 2 - ilosc towaru");
        System.out.print("Co chcesz edytowac?: ");
        int wybor = -1;
        try {
            wybor = scanner.nextInt();
        }catch (InputMismatchException e){
            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
        }
        System.out.println();
        switch (wybor){
            case 1:{
                System.out.println("1 - dodaj | 2 - usun");
                System.out.print("Co chcesz zrobic?: ");
                int wybor2 = -1;
                try {
                    wybor2 = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                switch (wybor2){
                    case 1:{
                        System.out.print("Podaj numer id lokomotywy, do ktorej chcesz dodac pasazerow, wpisz 1010, aby wyswietlic liste lokomotyw: ");
                        int wybor3 = -1;
                        try {
                            wybor3 = scanner.nextInt();
                        }catch (InputMismatchException e){
                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                        }
                        if(wybor3==1010){
                            pobieranieLokomotywZPasazerskim();
                            System.out.print("Podaj numer id lokomotywy, do ktorej chcesz dodac pasazerow: ");
                            try {
                                wybor3 = scanner.nextInt();
                            }catch (InputMismatchException e){
                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                            }
                        }
                        System.out.println();
                        switch (wybor3){
                            case -1:{
                                Lokomotywa l = lokomotywy[(int)(Math.random()*lokomotywy.length)];
                                System.out.print("Ilu pasazerow chcesz dodac?: ");
                                int iluPasazerow = -1;
                                try {
                                    iluPasazerow = scanner.nextInt();
                                }catch (InputMismatchException e){
                                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                }
                                System.out.println();
                                if(iluPasazerow==-1){
                                    l.dodajPasazerow((int)(Math.random()*50));
                                }else {
                                    l.dodajPasazerow(iluPasazerow);
                                }
                                break;
                            }
                            default:{
                                Lokomotywa l = null;
                                for(int i = 0;i<lokomotywy.length;i++) {
                                    if (wybor3 == lokomotywy[i].getID()) {
                                        l = lokomotywy[i];
                                        break;
                                    }
                                }
                                if(l!=null) {
                                    System.out.print("Ilu pasazerow chcesz dodac?: ");
                                    int iluPasazerow = -1;
                                    try {
                                        iluPasazerow = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                    System.out.println();
                                    if(iluPasazerow==-1){
                                        l.dodajPasazerow((int)(Math.random()*50));
                                    }else {
                                        l.dodajPasazerow(iluPasazerow);
                                    }
                                }else{
                                    System.out.println("Wprowadzono niepoprawna liczbe");
                                }
                            }
                        }
                    break;
                    }
                    case 2:{
                        System.out.print("Podaj numer id lokomotywy, z ktorej chcesz usunac pasazerow, wpisz 1010, aby wyswietlic liste lokomotyw: ");
                        int wybor3 = -1;
                        try {
                            wybor3 = scanner.nextInt();
                        }catch (InputMismatchException e){
                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                        }
                        if(wybor3==1010){
                            pobieranieLokomotywZPasazerskim();
                            System.out.print("Podaj numer id lokomotywy, z ktorej chcesz usunac wagon: ");
                            try {
                                wybor3 = scanner.nextInt();
                            }catch (InputMismatchException e) {
                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                            }
                        }
                        System.out.println();
                        switch (wybor3){
                            case -1:{
                                Lokomotywa l = lokomotywy[(int)(Math.random()*lokomotywy.length)];
                                System.out.print("Ilu pasazerow chcesz usunac?: ");
                                int iluPasazerow = -1;
                                try {
                                    iluPasazerow = scanner.nextInt();
                                }catch (InputMismatchException e){
                                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                }
                                if(iluPasazerow==-1){
                                    l.usunPasazerow((int)(Math.random()*50));
                                }else {
                                    l.usunPasazerow(iluPasazerow);
                                }
                                break;
                            }
                            default:{
                                Lokomotywa l = null;
                                for(int i = 0;i<lokomotywy.length;i++) {
                                    if (wybor3 == lokomotywy[i].getID()) {
                                        l = lokomotywy[i];
                                        break;
                                    }
                                }
                                if(l!=null) {
                                    System.out.print("Ilu pasazerow chcesz usunac?: ");
                                    int iluPasazerow = -1;
                                    try {
                                        iluPasazerow = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                    System.out.println();
                                    if (iluPasazerow == -1) {
                                        l.usunPasazerow((int) (Math.random() * 50));
                                    } else {
                                        l.usunPasazerow(iluPasazerow);
                                    }
                                }else{
                                    System.out.println("Wprowadzono niepoprawna liczbe");
                                }
                            }
                        }
                        break;
                    }
                    default:{
                        System.out.println("Wprowadzono niepoprawna liczbe");
                    }
                }
                break;
            }
            case 2:{
                System.out.println("1 - dodaj | 2 - usun");
                System.out.print("Co chcesz zrobic?: ");
                int wybor2 = -1;
                try {
                    wybor2 = scanner.nextInt();
                }catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                }
                System.out.println();
                switch (wybor2){
                    case 1:{
                        System.out.print("Podaj numer id lokomotywy, do ktorej chcesz dodac towar, wpisz 1010, aby wyswietlic liste lokomotyw: ");
                        int wybor3 = -1;
                        try {
                            wybor3 = scanner.nextInt();
                        }catch (InputMismatchException e){
                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                        }
                        if(wybor3==1010){
                            pobieranieLokomotyw();
                            System.out.print("Podaj numer id lokomotywy, do ktorej chcesz dodac towar: ");
                            try {
                                wybor3 = scanner.nextInt();
                            }catch (InputMismatchException e){
                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                            }
                        }
                        System.out.println();
                        switch (wybor3){
                            case -1:{
                                Lokomotywa l = lokomotywy[(int)(Math.random()*lokomotywy.length)];
                                ArrayList<Wagon> sklad = l.getSklad();
                                System.out.print("Wprowadz numer wagonu, do ktorego chcesz dodac towar, wpisz 1010, aby wyswietlic list wagonow: ");
                                int ktoryWagon = -1;
                                try {
                                    ktoryWagon = scanner.nextInt();
                                }catch (InputMismatchException e){
                                    scanner.nextLine();
                                    System.out.println("Wprowdzano bledna wartosc");
                                }
                                if(ktoryWagon==1010){
                                    for(int i = 0;i<sklad.size();i++){
                                        System.out.println("numer "+i+" - "+sklad.get(i));
                                    }
                                    System.out.print("Wprowadz numer wagonu, do ktorego chcesz dodac towar: ");
                                    try {
                                        ktoryWagon = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                }
                                System.out.println();
                                if(ktoryWagon==-1){
                                    Wagon w = sklad.get((int)(Math.random()*sklad.size()));
                                    System.out.print("Ile towaru chcesz dodac?: ");
                                    int ileTowaru = -1;
                                    try {
                                        ileTowaru = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                    System.out.println();
                                    if(ileTowaru==-1){
                                        dodawanieIOdejmowanieTowaru(w, (int)(Math.random()*1000));
                                    }else {
                                        dodawanieIOdejmowanieTowaru(w, ileTowaru);
                                    }
                                }else{
                                    try{
                                        Wagon w = sklad.get(ktoryWagon);
                                        System.out.print("Ile towaru chcesz dodac?: ");
                                        int ileTowaru = -1;
                                        try {
                                            ileTowaru = scanner.nextInt();
                                        }catch (InputMismatchException e){
                                            scanner.nextLine();
                                            System.out.println("Wprowdzano bledna wartosc");
                                        }
                                        System.out.println();
                                        if(ileTowaru==-1){
                                            dodawanieIOdejmowanieTowaru(w, (int)(Math.random()*1000));
                                        }else {
                                            dodawanieIOdejmowanieTowaru(w, ileTowaru);
                                        }
                                    }catch (IndexOutOfBoundsException e){
                                        System.out.println("Wprowadzono niepoprawna liczbe");
                                    }
                                }
                                break;
                            }
                            default:{
                                Lokomotywa l = null;
                                for(int i = 0;i< lokomotywy.length;i++){
                                    if(wybor3==lokomotywy[i].getID()){
                                        l = lokomotywy[i];
                                        break;
                                    }
                                }
                                if(l!=null) {
                                    ArrayList<Wagon> sklad = l.getSklad();
                                    System.out.print("Wprowadz numer wagonu, do ktorego chcesz dodac towar, wpisz 1010, aby wyswietlic list wagonow: ");
                                    int ktoryWagon = -1;
                                    try {
                                        ktoryWagon = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                    if (ktoryWagon == 1010) {
                                        for (int i = 0; i < sklad.size(); i++) {
                                            System.out.println("numer "+i+" - "+sklad.get(i));
                                        }
                                        System.out.print("Wprowadz numer wagonu, do ktorego chcesz dodac towar: ");
                                        try {
                                            ktoryWagon = scanner.nextInt();
                                        }catch (InputMismatchException e){
                                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                        }
                                    }
                                    System.out.println();
                                    if (ktoryWagon == -1) {
                                        Wagon w = sklad.get((int) (Math.random() * sklad.size()));
                                        System.out.print("Ile towaru chcesz dodac?: ");
                                        int ileTowaru = -1;
                                        try {
                                            ileTowaru = scanner.nextInt();
                                        }catch (InputMismatchException e){
                                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                        }
                                        System.out.println();
                                        if (ileTowaru == -1) {
                                            dodawanieIOdejmowanieTowaru(w, (int) (Math.random() * 1000));
                                        } else {
                                            dodawanieIOdejmowanieTowaru(w, ileTowaru);
                                        }
                                    } else {
                                        try {
                                            Wagon w = sklad.get(ktoryWagon);
                                            System.out.print("Ile towaru chcesz dodac?: ");
                                            int ileTowaru = -1;
                                            try {
                                                ileTowaru = scanner.nextInt();
                                            }catch (InputMismatchException e){
                                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                            }
                                            System.out.println();
                                            if (ileTowaru == -1) {
                                                dodawanieIOdejmowanieTowaru(w, (int) (Math.random() * 1000));
                                            } else {
                                                dodawanieIOdejmowanieTowaru(w, ileTowaru);
                                            }
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Wprowadzono niepoprawna liczbe");
                                        }
                                    }
                                }else{
                                    System.out.println("Wprowadzono niepoprawna liczbe");
                                }
                            }
                        }
                        break;
                    }
                    case 2:{
                        System.out.print("Podaj numer id lokomotywy, z ktorej chcesz usunac towar, wpisz 1010, aby wyswietlic liste lokomotyw: ");
                        int wybor3 = -1;
                        try {
                            wybor3 = scanner.nextInt();
                        }catch (InputMismatchException e){
                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                        }
                        if(wybor3==1010){
                            pobieranieLokomotyw();
                            System.out.print("Podaj numer id lokomotywy, z ktorej chcesz usunac towar: ");
                            try {
                                wybor3 = scanner.nextInt();
                            }catch (InputMismatchException e){
                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                            }
                        }
                        System.out.println();
                        switch (wybor3){
                            case -1:{
                                Lokomotywa l = lokomotywy[(int)(Math.random()*lokomotywy.length)];
                                ArrayList<Wagon> sklad = l.getSklad();
                                System.out.print("Wprowadz numer wagonu, z ktorego chcesz usunac towar, wpisz 1010, aby wyswietlic list wagonow: ");
                                int ktoryWagon = -1;
                                try {
                                    ktoryWagon = scanner.nextInt();
                                }catch (InputMismatchException e){
                                    scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                }
                                System.out.println();
                                if(ktoryWagon==1010){
                                    for(int i = 0;i<sklad.size();i++){
                                        System.out.println("numer "+i+" - "+sklad.get(i));
                                    }
                                    System.out.print("Wprowadz numer wagonu, z ktorego chcesz usunac towar: ");
                                    try {
                                        ktoryWagon = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                }
                                System.out.println();
                                if(ktoryWagon==-1){
                                    Wagon w = sklad.get((int)(Math.random()*sklad.size()));
                                    System.out.print("Ile towaru chcesz usunac?: ");
                                    int ileTowaru = -1;
                                    try {
                                        ileTowaru = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                    System.out.println();
                                    if(ileTowaru==-1){
                                        dodawanieIOdejmowanieTowaru(w, (int)(Math.random()*1000)*(-1));
                                    }else {
                                        dodawanieIOdejmowanieTowaru(w, -ileTowaru);
                                    }
                                }else{
                                    try{
                                        Wagon w = sklad.get(ktoryWagon);
                                        System.out.print("Ile towaru usunac dodac?: ");
                                        int ileTowaru = -1;
                                        try {
                                            ileTowaru = scanner.nextInt();
                                        }catch (InputMismatchException e){
                                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                        }
                                        System.out.println();
                                        if(ileTowaru==-1){
                                            dodawanieIOdejmowanieTowaru(w, (int)(Math.random()*1000)*(-1));
                                        }else {
                                            dodawanieIOdejmowanieTowaru(w, -ileTowaru);
                                        }
                                    }catch (IndexOutOfBoundsException e){
                                        System.out.println("Wprowadzono niepoprawna liczbe");
                                    }
                                }
                                break;
                            }
                            default:{
                                Lokomotywa l = null;
                                for(int i = 0;i< lokomotywy.length;i++){
                                    if(wybor3==lokomotywy[i].getID()){
                                        l = lokomotywy[i];
                                        break;
                                    }
                                }
                                if(l!=null) {
                                    ArrayList<Wagon> sklad = l.getSklad();
                                    System.out.print("Wprowadz numer wagonu, z ktorego chcesz usunac towar, wpisz 1010, aby wyswietlic list wagonow: ");
                                    int ktoryWagon = -1;
                                    try {
                                        ktoryWagon = scanner.nextInt();
                                    }catch (InputMismatchException e){
                                        scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                    }
                                    System.out.println();
                                    if (ktoryWagon == 1010) {
                                        for (int i = 0; i < sklad.size(); i++) {
                                            System.out.println("numer "+i+" - "+sklad.get(i));
                                        }
                                        System.out.print("Wprowadz numer wagonu, z ktorego chcesz usunac towar: ");
                                        try {
                                            ktoryWagon = scanner.nextInt();
                                        }catch (InputMismatchException e){
                                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                        }
                                    }
                                    System.out.println();
                                    if (ktoryWagon == -1) {
                                        Wagon w = sklad.get((int) (Math.random() * sklad.size()));
                                        System.out.print("Ile towaru chcesz usunac?: ");
                                        int ileTowaru = -1;
                                        try {
                                            ileTowaru = scanner.nextInt();
                                        }catch (InputMismatchException e){
                                            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                        }
                                        System.out.println();
                                        if (ileTowaru == -1) {
                                            dodawanieIOdejmowanieTowaru(w, (int) (Math.random() * 1000) * (-1));
                                        } else {
                                            dodawanieIOdejmowanieTowaru(w, -ileTowaru);
                                        }
                                    } else {
                                        try {
                                            Wagon w = sklad.get(ktoryWagon);
                                            System.out.print("Ile towaru chcesz usunac?: ");
                                            int ileTowaru = -1;
                                            try {
                                                ileTowaru = scanner.nextInt();
                                            }catch (InputMismatchException e){
                                                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
                                            }
                                            System.out.println();
                                            if (ileTowaru == -1) {
                                                dodawanieIOdejmowanieTowaru(w, (int) (Math.random() * 1000) * (-1));
                                            } else {
                                                dodawanieIOdejmowanieTowaru(w, -ileTowaru);
                                            }
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Wprowadzono niepoprawna liczbe");
                                        }
                                    }
                                }else{
                                    System.out.println("Wprowadzono niepoprawna liczbe");
                                }
                            }
                        }
                        break;
                    }
                    default:{
                        System.out.println("Wprowadzono niepoprawna liczbe");
                    }
                }
                break;
            }
            default:{
                System.out.println("Wprowadzono niepoprawna liczbe");
            }
        }

    }
    private void dodawanieIOdejmowanieTowaru(Wagon w, int ileTowaru){
        if (w instanceof WagonBagazowoPocztowy){
            if(ileTowaru>0) {
                ((WagonBagazowoPocztowy) w).dodajTowar(ileTowaru);
            }else{
                ((WagonBagazowoPocztowy) w).usunTowar(ileTowaru);
            }
        }
        if (w instanceof WagonPocztowy){
            if(ileTowaru>0) {
                ((WagonPocztowy) w).dodajTowar(ileTowaru);
            }else{
                ((WagonPocztowy) w).usunTowar(ileTowaru);
            }
        }
        if (w instanceof WagonTowarowyCiezki){
            if(ileTowaru>0) {
                ((WagonTowarowyCiezki) w).dodajTowar(ileTowaru);
            }else{
                ((WagonTowarowyCiezki) w).usunTowar(ileTowaru);
            }
        }
        if (w instanceof WagonTowarowyPodstawowy){
            if(ileTowaru>0) {
                ((WagonTowarowyPodstawowy) w).dodajTowar(ileTowaru);
            }else{
                ((WagonTowarowyPodstawowy) w).usunTowar(ileTowaru);
            }
        }

    }
    private void pobieranieLokomotyw(){
        for(int i = 0;i< lokomotywy.length;i++){
            System.out.println(lokomotywy[i]);
        }
        System.out.println();
    }
    private void pobieranieLokomotywZPasazerskim(){
        for(int i = 0;i< lokomotywy.length;i++){
            ArrayList<Wagon> w = lokomotywy[i].getSklad();
            for(int j = 0; j<w.size();j++) {
               if(w.get(j) instanceof WagonPasazerski){
                   System.out.println(lokomotywy[i]);
                   break;
               }
            }
        }
        System.out.println();
    }
    private void edycja(Wagon w){
        System.out.println("-1 - dla losowej lokomotywy");
        System.out.print("Podaj numer id lokomotywy, do ktorej chcesz dodac wagon, wpisz 1010, aby wyswietlic liste lokomotyw: ");
        int wybor = -1;
        try {
            wybor = scanner.nextInt();
        }catch (InputMismatchException e){
            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
        }
        if(wybor==1010){
            pobieranieLokomotyw();
            System.out.print("Podaj numer id lokomotywy, do ktorej chcesz dodac wagon: ");
            try {
                wybor = scanner.nextInt();
            }catch (InputMismatchException e){
                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
            }
        }
        switch (wybor){
            case -1: {
                lokomotywy[(int)(Math.random()* lokomotywy.length)].dodajWagon(w);
                break;
            }
            default:{
                for(int i = 0; i<lokomotywy.length;i++){
                    if(wybor == lokomotywy[i].getID()){
                        lokomotywy[i].dodajWagon(w);
                        break;
                    }
                }
            }
        }
    }
    private void raport(){
        System.out.println("Wprowadz \" -1 \" - jezeli chcesz, aby wartosc byla losowa");
        System.out.print("Wprowadz numer id lokomotywy, ktorej raport chcesz otrzymac, aby wyswietlic list lokomotyw wpisz 1010: ");
        int wybor = -1;
        try {
            wybor = scanner.nextInt();
        }catch (InputMismatchException e){
            scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
        }
        if(wybor==1010){
            pobieranieLokomotyw();
            System.out.print("Wprowadz numer id lokomotywy, ktorej raport chcesz otrzymac: ");
            try {
                wybor = scanner.nextInt();
            }catch (InputMismatchException e){
                scanner.nextLine();
                    System.out.println("Wprowdzano bledna wartosc");
            }
        }
        if(wybor==-1){
            Lokomotywa l = lokomotywy[(int)(Math.random()*lokomotywy.length)];
            System.out.println(l.getRaport());
        }
        else{
            Lokomotywa l = null;
            for(int i = 0; i<lokomotywy.length;i++){
                if(wybor==lokomotywy[i].getID()){
                    l = lokomotywy[i];
                    break;
                }
            }
            if(l!=null) {
                System.out.println(l.getRaport());
            }
            else{
                System.out.println("Wprowadzono niepoprawna liczbe");
            }
        }
    }
}
