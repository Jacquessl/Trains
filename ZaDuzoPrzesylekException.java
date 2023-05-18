public class ZaDuzoPrzesylekException extends Exception {
    public ZaDuzoPrzesylekException(int miejsce, int ilePrzesylek, int idWagonu){
        System.out.println("Wagon "+idWagonu+": "+ilePrzesylek+" nie zmiesci sie w "+miejsce);
    }
    public ZaDuzoPrzesylekException(double miejsce, double ilePrzesylek, int idWagonu){
        System.out.println("Wagon "+idWagonu +": "+ilePrzesylek+" nie zmiesci sie w "+miejsce);
    }
}
