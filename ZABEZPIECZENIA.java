public enum ZABEZPIECZENIA {
    DRZWI(1), ZAMKI(2), KAMERY(3), LASERY(4);
    private final int value;
    ZABEZPIECZENIA(int value){
        this.value = value;
    }
    private String toNapis(){
        String str = "";
        for(ZABEZPIECZENIA zab : ZABEZPIECZENIA.values()){
            if(zab.value <= this.value){
                str += " "+zab;
            }
        }
        return str;
    }
    public int getValue(){
        return value;
    }
}
