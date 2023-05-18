import java.util.ArrayList;
import java.util.Date;

public class SkladPociagu {
    private int counter;
    private ArrayList<Wagon> wagony;
    private boolean czyPrzedzialowy;
    private Lokomotywa lokomotywa;
    private int miesiac;
    private int rok;
    public SkladPociagu(Lokomotywa lokomotywa){
        this.lokomotywa = lokomotywa;
    }
    public ArrayList<Wagon> generate(int wagonyMax, int uciagMax, int eWagonyMax) throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ZaSlabeZabezpieczeniaException, ProblemZeSklademException, ZagrazaPasazeromException {
        wagony = new ArrayList<>();
        double wBrutto = 0;
        int counterRestauracyjnych = 0;
        int eCounter = 0;
        int spLength = (int)(Math.random()*6)+5;
        if(wagonyMax<spLength){
            throw new ProblemZeSklademException("Maksymalnie wagonow: "+wagonyMax+", probowano dodac "+spLength);
        }
        for(int i = 0; i<spLength; i++){
            int rand = (int)(Math.random()*12);
            switch (rand){
                case 0:
                    wagony.add(new WagonBagazowoPocztowy(setNadawca(), setZabezpieczenia(), (Math.random()*13500)+32500, setMiejsce(),setMiejsce()));
                    counter = 0;
                    break;
                case 1:
                    wagony.add(new WagonChlodniczy(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500,setTowar(2), Math.random()*2, setMiejsce()));
                    counter = 0;
                    break;
                case 2:
                    wagony.add(new WagonNaCiekleMaterialyToksyczne(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, setTowar(3), Math.random()>0.5,
                            (Math.random()*50000)+10000, setWaga(),(Math.random()*55)+20, (Math.random()*500)+1000,
                            (int)(Math.random()*4), Math.random()>0.5, Math.random()>0.5));
                    break;
                case 3:
                    wagony.add(new WagonNaMaterialyCiekle(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, setTowar(4), setWaga(),
                            (Math.random()*300)+400, (Math.random()*500)+1000));
                    break;
                case 4:
                    wagony.add(new WagonNaMaterialyGazowe(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, setTowar(5),
                            setWaga(), Math.random()*0.16, 0.15));
                    break;
                case 5:
                    wagony.add(new WagonNaMaterialyToksyczne(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, setTowar(6), setWaga(),
                            Math.random()>0.5, (Math.random()*50000)+10000, (int)(Math.random()*11),(int)(Math.random()*6)+5));
                    break;
                case 6:
                    wagony.add(new WagonNaMaterialyWybuchowe(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, setTowar(7), setWaga(),
                            Math.random()>0.5, (Math.random()*50000)+10000, Math.random()>0.5, new Date(rok(), miesiac(), dzien())));
                    miesiac = 0;
                    rok = 0;
                    break;
                case 7:
                    wagony.add(new WagonPasazerski(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, przedzialy(), miejsca(), (int)(Math.random()*300)+100));
                    break;
                case 8:
                    wagony.add(new WagonPocztowy(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, setMiejsce(), setMiejsce()));
                    counter = 0;
                    break;
                case 9:
                    if(counterRestauracyjnych==0) {
                        wagony.add(new WagonRestauracyjny(setNadawca(), setZabezpieczenia(), (Math.random() *13500)+32500, (int) (Math.random() * 10) + 5));
                        counterRestauracyjnych++;
                    }
                    else{
                        i--;
                    }
                    break;
                case 10:
                    wagony.add(new WagonTowarowyCiezki(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500,
                            setTowar(1), setWaga(), Math.random() > 0.5, (Math.random()*50000)+10000));
                    break;
                case 11:
                    wagony.add(new WagonTowarowyPodstawowy(setNadawca(),setZabezpieczenia(),(Math.random()*13500)+32500, setTowar(0), (Math.random()*15000)+1000));
                    break;
            }
            if(wagony.get(i).getElektyczny()){
                eCounter++;
            }
            wBrutto += wagony.get(i).getWBrutto();
            if(wBrutto>uciagMax){
                wBrutto -= wagony.get(i).getWBrutto();
                wagony.remove(i);
                throw new ProblemZeSklademException("Probowano dodac wagon, po dodaniu, ktorego maksymalny uciag zostałby przekroczony");
            }
        }
        if(eCounter>eWagonyMax){
            throw new ProblemZeSklademException("Maksymalnie elektycznych wagonow: "+eWagonyMax+", probowano dodac "+eCounter);
        }
        return wagony;
    }
    public NADAWCA setNadawca(){
        return NADAWCA.values()[(int)(Math.random()*NADAWCA.values().length)];
    }
    public ZABEZPIECZENIA setZabezpieczenia(){
        return ZABEZPIECZENIA.values()[(int)(Math.random()*ZABEZPIECZENIA.values().length)];
    }
    public TOWAR setTowar(int index){
        while(true){
            int rand = (int)(Math.random()*TOWAR.values().length);
            if(TOWAR.values()[rand].getValue()==index){
                return TOWAR.values()[rand];
            }
        }
    }
    public void setCounter(){
        counter = 0;
    }
    public void setMiesiac(){
        miesiac = 0;
    }
    public void setRok(){
        rok = 0;
    }
    public double setWaga(){
        return (Math.random()*30000)+10000;
    }
    public int setMiejsce(){
        if(counter>0){
            return (int)(Math.random()*counter);
        }
        return counter = (int)(Math.random()*1000)+500;
    }
    public boolean przedzialy(){
        return czyPrzedzialowy = Math.random() > 0.5;
    }
    public int miejsca(){
        if(czyPrzedzialowy){
            return (int)((Math.random()*10)+5)*6;
        }else{
            return (int)(Math.random()*200)+50;
        }
    }
    public int rok(){
        return rok = (int)(Math.random()*3)+123;
    }
    public int miesiac(){
        return miesiac = (int)(Math.random()*12)+1;
    }
    public int dzien(){
        if(miesiac == 2){
            if(rok%4==0){
                if(rok%400==0){
                    return (int)(Math.random()*29)+1;
                }
                else if(rok%100!=0){
                    return (int)(Math.random()*29)+1;
                }
                else{
                    return (int)(Math.random()*28)+1;
                }
            }
            else{
                return (int)(Math.random()*28)+1;
            }
        }
        if(miesiac<7){
            if(miesiac%2==0){
                return (int)(Math.random()*30)+1;
            }
            else{
                return (int)(Math.random()*31)+1;
            }
        }
        if(miesiac>8){
            if(miesiac%2==1){
                return (int)(Math.random()*30)+1;
            }
            else{
                return (int)(Math.random()*31)+1;
            }
        }
        return (int)(Math.random()*31)+1;
    }
}
