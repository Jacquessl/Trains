public class ZaSlabeZabezpieczeniaException extends Throwable {
    public ZaSlabeZabezpieczeniaException(int minimalneZabezpieczenia, ZABEZPIECZENIA zabezpieczenia){
        System.out.println("Zabezpieczenia: "+zabezpieczenia+" nie wystarcza, potrzeba przynajmniej "+ZABEZPIECZENIA.values()[minimalneZabezpieczenia]);
    }
}
