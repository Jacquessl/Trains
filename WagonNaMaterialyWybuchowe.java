import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
public class WagonNaMaterialyWybuchowe extends WagonTowarowyCiezki{
    private boolean elektryka = false;
    private boolean zagrazaPasazerom;
    private Date dataWaznosciPozwolenia;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public WagonNaMaterialyWybuchowe(NADAWCA nadawca, ZABEZPIECZENIA zabezpieczenia, double wNetto, TOWAR towar, double waga,
            boolean ubezpieczony, double maxWaga,boolean zagrazaPasazerom, Date dataWaznosciPozwolenia) throws PrzeterminowanePozwolenieException, ZaDuzoPrzesylekException {
        super(nadawca, zabezpieczenia, wNetto, towar, waga, ubezpieczony, maxWaga);
        this.zagrazaPasazerom = zagrazaPasazerom;
        this.dataWaznosciPozwolenia = dataWaznosciPozwolenia;
        try {
            sprawdzWaznoscPozwolenia();
        }catch(PrzeterminowanePozwolenieException e){
            zaryzkujJazdeBezPozwolenia();
        }
    }
    private void sprawdzWaznoscPozwolenia() throws PrzeterminowanePozwolenieException {
        Date dzis = new Date();
        sdf.format(dzis);
        sdf.format(dataWaznosciPozwolenia);
        if(dataWaznosciPozwolenia.compareTo(dzis) < 0){
            throw new PrzeterminowanePozwolenieException(dataWaznosciPozwolenia, dzis);
        }
    }
    private void zaryzkujJazdeBezPozwolenia() throws PrzeterminowanePozwolenieException {
        if (Math.random() > 0.1) {
            sprawdzWaznoscPozwolenia();
        }
    }
    public void kontrola() throws PrzeterminowanePozwolenieException {
        if(Math.random() < 0.1){
            sprawdzWaznoscPozwolenia();;
        }
    }
    public boolean czyZagrazaPasazerom(){
        return zagrazaPasazerom;
    }
    public TOWAR getTowar(){
        return towar;
    }
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return "WagonMaterialyWybuchowe id: "+id+" nadawca: "+nadawca+" zabezpieczenia: "+zabezpieczenia+" waga netto: "+wNetto+" waga brutto: "+
                wBrutto+" towar: "+towar+" waga towaru: "+wagaTowaru+" data waznosci pozwolenia: "+sdf.format(dataWaznosciPozwolenia);
    }
}
