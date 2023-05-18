import java.util.Arrays;
import java.util.Comparator;


public class Trasa implements Comparable<Trasa>{

    private Stacja s1, s2;
    public Trasa(Stacja s1, Stacja s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    public double przelicznikTrasy(){
        double x1 = s1.getLocationX();
        double y1 = s1.getLocationY();
        double x2 = s2.getLocationX();
        double y2 = s2.getLocationY();

        double trasa = Math.sqrt((Math.pow((x2-x1),2)+Math.pow((Math.cos((x1*Math.PI)/180)*(y2-y1)),2)))*(40075.704/360);

        return trasa;
    }
    public Stacja[] najDroga(){
        Stacja[] najDroga = new Stacja[100];
        najDroga[0] = s1;
        int i=0;
        int z=1;
        int counter = 0;
        while(true) {
            boolean mamyRozklad = false;
            Stacja[] rozklad = s1.getRozklad();
            while(!mamyRozklad) {
                try {
                    najDroga[i].odjazdDo();
                    rozklad = najDroga[i].getRozklad();
                    mamyRozklad = true;
                } catch (NullPointerException e) {
                    if((i-z)>1) {
                        for (int k = i-z; k < najDroga.length; k++) {
                            najDroga[k] = null;
                        }
                        for (int j = 0; j < 5; j++) {
                            Stacja inna = trzebaInnaTrase(najDroga[i - z - 1]);
                            if (uniqStacja(najDroga, inna.getMiejscowosc())) {
                                najDroga[i - z] = inna;
                                najDroga[i - z].odjazdDo();
                                rozklad = najDroga[i - z].getRozklad();
                                i = i - z;
                                mamyRozklad = true;
                                break;
                            }
                        }
                        if(counter>150){
                            try {
                                najDroga[i].setMaxOdjazd();
                                counter=0;
                            }catch (NullPointerException n){

                            }
                        }
                        counter++;
                        z++;
                    }
                    else {
                        Arrays.fill(najDroga, null);
                        najDroga[0] = s1;
                        i = 1;
                        z=1;
                        najDroga[1] = trzebaInnaTrase(s1);
                        najDroga[1].odjazdDo();
                        rozklad = najDroga[1].getRozklad();
                        Stacja inna = trzebaInnaTrase(najDroga[1]);
                        if(uniqStacja(najDroga,inna.getMiejscowosc())){
                            najDroga[2] = inna;
                            najDroga[2].odjazdDo();
                            rozklad = najDroga[2].getRozklad();
                            i=2;
                        }
                        mamyRozklad = true;
                    }
                }
            }
            for (Stacja s : rozklad) {
                String strs = s.getMiejscowosc();
                String strs2 = s2.getMiejscowosc();
                if(strs.equals(strs2)){//porownanie stringow, poniewaz porownanie obiektu s2 i rozklad[index] nie da nigdy true
                    najDroga[i+1] = s2;
                    return najDroga;
                }
            }
            Arrays.sort(rozklad, Comparator.comparingDouble(o -> new Trasa(o, s2).przelicznikTrasy()));

            for(int j = 0; j<rozklad.length;j++) {
                if (uniqStacja(najDroga, rozklad[j].getMiejscowosc())) {
                    najDroga[i + 1] = rozklad[j];
                    break;
                }
            }
            i++;
        }
    }
    public Stacja trzebaInnaTrase(Stacja poprzednia){
        Stacja[] rozklad = poprzednia.getRozklad();
        int trasa1 = (int)(Math.random()*(rozklad.length));
        return rozklad[trasa1];
    }
    boolean uniqStacja(Stacja[] spr, String str2){
        for(int i = 0; i<spr.length;i++){
            try {
                String str1 = spr[i].getMiejscowosc();
                if (str1.equals(str2)) {
                    return false;
                }
            }catch(NullPointerException e){
                return true;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Trasa o)  {
        return (int)(this.przelicznikTrasy()-o.przelicznikTrasy());
    }
}
