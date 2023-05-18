import java.text.SimpleDateFormat;
import java.util.Date;

public class PrzeterminowanePozwolenieException extends Exception{
    public PrzeterminowanePozwolenieException(Date pozwolenie, Date dzis){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Dzisiaj jest "+sdf.format(dzis)+", pozwolenie uplynelo dnia "+sdf.format(pozwolenie));
    }
}
