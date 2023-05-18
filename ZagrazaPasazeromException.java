public class ZagrazaPasazeromException extends Exception{
    public ZagrazaPasazeromException(TOWAR towar){
        System.out.println(towar+" zagraza pasazerom");
    }
    public ZagrazaPasazeromException(TOWAR towar, double temperatura){
        System.out.println(towar+" w temepraturze " + temperatura+" zagraza wszystkim, usunieto wagon");
    }
    public ZagrazaPasazeromException(TOWAR towar, double stezenie, double maxStezenie){
        System.out.println("Stezenie: "+stezenie+" "+towar+" zagraza wszystkim, max stezenie: "+maxStezenie);
    }
    public ZagrazaPasazeromException(TOWAR towar, int poziomToksycznosci, int maxPoziomToksycznosc){
        System.out.println("Maksymalny poziom toksycznosci to: "+maxPoziomToksycznosc+", a "+towar+" ma poziom toksycznosci: "+poziomToksycznosci);
    }
}
