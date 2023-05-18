public class Watki extends Thread{
    private Lokomotywa l;
    private int counter = 0;
    private double dlugoscAktualnegoOdcinka;
    private double przebytyOdcinek;
    private Lokomotywa[] arrLokomotywy;
    private int startCounter = 0;
    public Watki(Lokomotywa l, Lokomotywa[] arrLokomotywy){
        this.l = l;
        dlugoscAktualnegoOdcinka = l.getDlugoscAktualnegoOdcinka();
        this.arrLokomotywy = arrLokomotywy;
        l.setWatek(this);
    }
    @Override
    public void run(){
        if(startCounter==0) {
            for (int i = 0; i < arrLokomotywy.length; i++) {
                Stacja[] innyPociag = arrLokomotywy[i].gdzieJestem();
                Stacja[] naszPociag = l.gdzieJestem();
                if (innyPociag[0].getMiejscowosc().equals(naszPociag[0].getMiejscowosc()) && innyPociag[1].getMiejscowosc().equals(naszPociag[1].getMiejscowosc())) {
                    synchronized (this) {
                        try {
                             if(arrLokomotywy[i].getWatek().getState() != State.WAITING) {
                                arrLokomotywy[i].getNextThread(this);
                                wait();
                            }
                        } catch (InterruptedException e) {

                        }
                    }
                }
            }
            startCounter++;
        }
        while(!interrupted()){
            l.setPokonanyDystans();
            przebytyOdcinek = l.getPokonanyDystans();
            if(counter==1000){
                ustawPredkosc();
                try {
                    l.thredoweZadania();
                } catch (ZagrazaPasazeromException e) {

                } catch (RailroadHazard e) {
                    System.out.println(e.getMessage());
                }
                counter=0;
            }
            if(przebytyOdcinek>=dlugoscAktualnegoOdcinka){
                try {
                    l.dotarlismyNaStacje(this);
                    dlugoscAktualnegoOdcinka = l.getDlugoscAktualnegoOdcinka();
                    przebytyOdcinek = 0;
                } catch (ZaDuzoPrzesylekException | ProblemZeSklademException | PrzeterminowanePozwolenieException e) {
                    this.stop();
                } catch (InterruptedException e) {

                }
            }
            counter++;
            try{
                Thread.sleep(1);
            }
            catch (InterruptedException e){

            }
        }
    }
    private void ustawPredkosc(){
        try {
            l.predkosc();
        } catch (RailroadHazard e) {
            System.out.println(e);
        }
    }
    public void dojechalismyNaStacje() throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ProblemZeSklademException, InterruptedException {
        l.zadaniaNaStacji();
        try{
            sleep(2000);
        }
        catch(InterruptedException e){

        }
        for(int i = 0;i<arrLokomotywy.length;i++){
            Stacja[] innyPociag = arrLokomotywy[i].gdzieJestem();
            Stacja[] naszPociag = l.gdzieJestem();
            if(innyPociag[0].getMiejscowosc().equals(naszPociag[0].getMiejscowosc())&&innyPociag[1].getMiejscowosc().equals(naszPociag[1].getMiejscowosc())&&
                    innyPociag[1].getMiejscowosc().equals(naszPociag[0].getMiejscowosc())&&innyPociag[0].getMiejscowosc().equals(naszPociag[1].getMiejscowosc())){
                synchronized (this) {
                    if(arrLokomotywy[i].getWatek().getState() != State.WAITING) {
                        try {
                            arrLokomotywy[i].getNextThread(this);
                            wait();
                        } catch (InterruptedException e) {

                        }
                    }
                }
            }
        }
    }
    public Lokomotywa getLokomotywa(){
        return l;
    }
    public void dojechalismyNaStacjeKoncowa() throws ZaDuzoPrzesylekException, PrzeterminowanePozwolenieException, ProblemZeSklademException {
        l.dotarlismyNaStacjeKoncowa();
        try{
            sleep(30000);
        }
        catch(InterruptedException e){

        }
    }
}
