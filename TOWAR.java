public enum TOWAR {
    PROCH_STRZELNICZY(7), DYNAMIT(7), NITROGLICERYNA(7), TNT(7), MATERIALY_WYBUCHOWE_DO_DEMOLKI_BUDYNKOW(7), //wybuchowe
    TRUCIZNA_NA_SZCZURY(6), KWAS_SIARKOWY(6), CYJANOWODOR(6), ARSEN(6), KWAS_FLUOROWODOROWY(6), // toksyczne
    BUTAN(5), PROPAN(5), ACETYLEN(5), HEL(5), TLEN(5), // gazowe
    BENZYNA(4), OLEJ_NAPEDOWY(4), PLYN_DO_MYCIA_SZYB(4), PLYN_CHLODNICZY(4), ROZPUSZCZALNIK(4), //ciekle
    KWAS_SOLNY(3), KWAS_AZOTOWY(3), RTEC(3), CIEKLY_CHLOR(3), FORMALDEHYD(3), // ciekle toksyczne
    MIESO(2), RYBY(2), OWOCE(2), WARZYWA(2), NABIAL(2), LOD(2), //do chlodniczego
    CEMENT(1), PIASEK(1), ZWIR(1), ZELAZO(1), STAL(1), //ciezkie
    WODA(0), CHLEB(0), MLEKO(0), JAJKA(0), CUKIER(0); // podstawowe

    private final int value;
    private TOWAR(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
