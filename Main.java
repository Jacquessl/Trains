public class Main {
    private static Stacja[] stacje;
    public static void main(String[] args) {
        stacjeIni();
        Lokomotywa[] l = new Lokomotywa[25];
        for(int i = 0; i<l.length; i++) {
            try {
                int rand1 = (int)(Math.random()*100);
                int rand2 = (int)(Math.random()*100);
                if(rand1!=rand2) {
                    l[i] = new Lokomotywa((int)(Math.random()*6)+5, (int)(Math.random()*500000)+250000, (int)(Math.random()*6),stacje[(int) (Math.random() * 100)], stacje[rand1],
                    stacje[rand2]);
                }
                else{
                    i--;
                }
            }
            catch(ZaSlabeZabezpieczeniaException | ZaDuzoPrzesylekException | PrzeterminowanePozwolenieException | ProblemZeSklademException | ZagrazaPasazeromException e){
                i--;
            }
        }
        Watki[] w = new Watki[l.length];
        for(int i = 0; i<w.length;i++){
            Lokomotywa[] arrLokomotywy = new Lokomotywa[l.length-1];
            int k = 0;
            for(int j = 0; j<arrLokomotywy.length+1;j++){
                if(j!=i){
                    arrLokomotywy[k] = l[j];
                    k++;
                }
            }
            w[i] = new Watki(l[i], arrLokomotywy);
        }
        for(Watki wa : w){
            wa.start();
        }
        Menu menu = new Menu(l, l, stacje);
        menu.start();
    }
    public static void stacjeIni(){
        stacje = new Stacja[100]; //wsiadaja ludnos/1000
        stacje[0] = new Stacja(52.259, 21.020, "Warszawa", 1793579, maxOdjazd());
        stacje[1] = new Stacja(50.060, 19.959, "Krakow", 779115, maxOdjazd());
        stacje[2] = new Stacja(52.399,16.900,"Poznan", 538633, maxOdjazd());
        stacje[3] = new Stacja(51.110,17.030,"Wroclaw", 639258, maxOdjazd());
        stacje[4] = new Stacja(54.249, 20.809,"Bartoszyce", 23960, maxOdjazd());
        stacje[5] = new Stacja(53.139,23.159,"Bialystok", 297338, maxOdjazd());
        stacje[6] = new Stacja(53.839,23,"Augustow", 29366, maxOdjazd());
        stacje[7] = new Stacja(50.340,19.129,"Bedzin", 60891, maxOdjazd());
        stacje[8] = new Stacja(51.369,19.360, "Belchatow", 58798, maxOdjazd());
        stacje[9] = new Stacja(49.819, 19.049,"Bielsko-Biala", 172610, maxOdjazd() );
        stacje[10] = new Stacja(53.120,18.010,"Bydgoszcz", 348310, maxOdjazd());
        stacje[11] = new Stacja(50.349, 18.909,"Bytom", 174857, maxOdjazd());
        stacje[12] = new Stacja(51.139,23.490,"Chelm", 67560, maxOdjazd());
        stacje[13] = new Stacja(50.299,19.030,"Chorzow", 111027, maxOdjazd());
        stacje[14] = new Stacja(52.880, 20.620, "Ciechanow", 18257, maxOdjazd());
        stacje[15] = new Stacja(49.750, 18.629,"Cieszyn", 36050, maxOdjazd());
        stacje[16] = new Stacja(50.810,19.129,"Czestochowa", 224376, maxOdjazd());
        stacje[17] = new Stacja(49.910,19.020,"Czechowice-Dziedzice", 34107, maxOdjazd());
        stacje[18] = new Stacja(50.330,19.180,"Dabrowa Gornicza", 121489, maxOdjazd());
        stacje[19] = new Stacja(50.060,21.420,"Debica", 48243, maxOdjazd());
        stacje[20] = new Stacja(53.990,20.390,"Dobre Miasto", 10206, maxOdjazd());
        stacje[21] = new Stacja(53.229,20.180,"Dzialdowo", 19498,maxOdjazd());
        stacje[22] = new Stacja(54.179,19.399,"Elblag", 121736,maxOdjazd());
        stacje[23] = new Stacja(53.830,22.359,"Elk", 55052,maxOdjazd());
        stacje[24] = new Stacja(51.909,21.620,"Garwolin", 16352,maxOdjazd());
        stacje[25] = new Stacja(54.360,18.639,"Gdansk", 470907, maxOdjazd());
        stacje[26] = new Stacja(54.520,18.530,"Gdynia", 246538, maxOdjazd());
        stacje[27] = new Stacja(54.040,21.759,"Gizycko", 29047, maxOdjazd());
        stacje[28] = new Stacja(50.310,18.669,"Gliwice", 182190, maxOdjazd());
        stacje[29] = new Stacja(52.530,17.610,"Gniezno", 70827, maxOdjazd());
        stacje[30] = new Stacja(51.990,21.209,"Gora Kalwaria", 11779, maxOdjazd());
        stacje[31] = new Stacja(52.740,15.230,"Gorzow Wielkopolski", 124829, maxOdjazd());
        stacje[32] = new Stacja(51.879,17,"Gostyn", 13530, maxOdjazd());
        stacje[33] = new Stacja(52.109,20.620,"Grodzisk Mazowiecki", 29926, maxOdjazd());
        stacje[34] = new Stacja(53.490,18.749,"Grudziadz", 96692, maxOdjazd());
        stacje[35] = new Stacja(53.229,14.479,"Gryfino", 16305, maxOdjazd());
        stacje[36] = new Stacja(52.729,23.579,"Hajnowka", 21819, maxOdjazd());
        stacje[37] = new Stacja(50.810,23.880,"Hrubieszow", 16096, maxOdjazd());
        stacje[38] = new Stacja(53.589,19.560,"Ilawa", 32992, maxOdjazd());
        stacje[39] = new Stacja(52.780,18.249,"Inowroclaw", 75894, maxOdjazd());
        stacje[40] = new Stacja(52.429,17.03,"Janikowo", 6536, maxOdjazd());
        stacje[41] = new Stacja(49.739,21.469,"Jaslo", 36467, maxOdjazd());
        stacje[42] = new Stacja(49.990,18.59,"Jastrzebie-Zdroj", 89312, maxOdjazd());
        stacje[43] = new Stacja(51.06,16.2,"Jawor", 24332, maxOdjazd());
        stacje[44] = new Stacja(51.029,17.299,"Jelcz-Laskowice", 14484, maxOdjazd());
        stacje[45] = new Stacja(52.15,21.26,"Jozefow", 5979, maxOdjazd());
        stacje[46] = new Stacja(51.77,18.1,"Kalisz", 106641, maxOdjazd());
        stacje[47] = new Stacja(53.97,14.799,"Kamien Pomorski", 8711, maxOdjazd());
        stacje[48] = new Stacja(54.329,18.2,"Kartuzy", 14785, maxOdjazd());
        stacje[49] = new Stacja(50.259,19.02,"Katowice", 296615, maxOdjazd());
        stacje[50] = new Stacja(50.34,18.209,"Kedzierzyn-Kozle", 60188, maxOdjazd());
        stacje[51] = new Stacja(51.28,18,"Kepno", 14234, maxOdjazd());
        stacje[52] = new Stacja(54.079,21.37,"Ketrzyn", 28035, maxOdjazd());
        stacje[53] = new Stacja(49.87,19.219,"Kety", 17650, maxOdjazd());
        stacje[54] = new Stacja(50.889,20.649,"Kielce", 195992, maxOdjazd());
        stacje[55] = new Stacja(50.98,18.209,"Kluczbork", 24827, maxOdjazd());
        stacje[56] = new Stacja(53.419,21.929,"Kolno", 11897, maxOdjazd());
        stacje[57] = new Stacja(54.189,15.569,"Kolobrzeg", 46489, maxOdjazd());
        stacje[58] = new Stacja(51.750,19.819,"Koluszki", 12375, maxOdjazd());
        stacje[59] = new Stacja(52.209,18.249,"Konin", 75082, maxOdjazd());
        stacje[60] = new Stacja(51.2,20.399,"Konskie", 19438, maxOdjazd());
        stacje[61] = new Stacja(52.099,21.110,"Konstancin-Jeziorna", 17300, maxOdjazd());
        stacje[62] = new Stacja(52.589,14.649,"Kostrzyn", 18928, maxOdjazd());
        stacje[63] = new Stacja(54.189,16.18,"Koszalin", 107450, maxOdjazd());
        stacje[64] = new Stacja(50.779,15.829,"Kowary", 6591, maxOdjazd());
        stacje[65] = new Stacja(50.479,17.96,"Krapkowice", 17947, maxOdjazd());
        stacje[66] = new Stacja(50.929,22.219,"Krasnik", 33333, maxOdjazd());
        stacje[67] = new Stacja(49.7,21.759,"Krosno", 47451, maxOdjazd());
        stacje[68] = new Stacja(53.74,18.93,"Kwidzyn", 38627, maxOdjazd());
        stacje[69] = new Stacja(54.549,17.75,"Lebork", 37381, maxOdjazd());
        stacje[70] = new Stacja(52.409,20.92,"Leginowo", 11397, maxOdjazd());
        stacje[71] = new Stacja(51.209,16.159,"Legnica", 101682, maxOdjazd());
        stacje[72] = new Stacja(51.849,16.57,"Leszno", 64200, maxOdjazd());
        stacje[73] = new Stacja(54.139,20.59,"Lidzbark Warminski", 10487, maxOdjazd());
        stacje[74] = new Stacja(51.24,22.57,"Lublin", 339682, maxOdjazd());
        stacje[75] = new Stacja(51.79,14.96,"Lubsko", 13332, maxOdjazd());
        stacje[76] = new Stacja(52.989,22.89,"Lapy", 9876, maxOdjazd());
        stacje[77] = new Stacja(51.77,19.459,"Lodz", 682679, maxOdjazd());
        stacje[78] = new Stacja(52.349,20.889,"Lomianki", 22000, maxOdjazd());
        stacje[79] = new Stacja(53.180,22.07,"Lomza", 63752, maxOdjazd());
        stacje[80] = new Stacja(54.04,19.04,"Malbork", 38124, maxOdjazd());
        stacje[81] = new Stacja(52.45, 15.569,"Miedzyrzecz", 11239, maxOdjazd());
        stacje[82] = new Stacja(50.29,21.43,"Mielec", 60774, maxOdjazd());
        stacje[83] = new Stacja(52.179,21.559,"Minsk Mazowiecki", 17258, maxOdjazd());
        stacje[84] = new Stacja(53.12,20.36,"Mlawa", 18504, maxOdjazd());
        stacje[85] = new Stacja(53.88,21.3,"Mragowo", 21880, maxOdjazd());
        stacje[86] = new Stacja(53.779,20.489,"Olsztyn", 173997, maxOdjazd());
        stacje[87] = new Stacja(51.339,21.159,"Radom", 214942, maxOdjazd());
        stacje[88] = new Stacja(54.439,18.559,"Sopot", 38542, maxOdjazd());
        stacje[89] = new Stacja(50.279,19.120,"Sosnowiec", 220363, maxOdjazd());
        stacje[90] = new Stacja(53.43,14.529,"Szczecin", 402465, maxOdjazd());
        stacje[91] = new Stacja(53.02,18.609,"Torun", 202562, maxOdjazd());
        stacje[92] = new Stacja(50.16,19,"Tychy", 132999, maxOdjazd());
        stacje[93] = new Stacja(49.879,19.489,"Wadowice", 19228, maxOdjazd());
        stacje[94] = new Stacja(51.94,15.489,"Zielona Gora", 139316, maxOdjazd());
        stacje[95] = new Stacja(49.87,19.18,"Zywiec", 32593, maxOdjazd());
        stacje[96] = new Stacja(54.009,15.979,"Bialogard", 9695, maxOdjazd());
        stacje[97] = new Stacja(52.829,15.829,"Drezdenko", 4847, maxOdjazd());
        stacje[98] = new Stacja(50.189,17.83,"Glubczyce", 12791, maxOdjazd());
        stacje[99] = new Stacja(50.679,17.94,"Opole", 128364, maxOdjazd());
    }
    public static Stacja[] getStacje(){
        return stacje;
    }
    public static void setStacje(Stacja s){
        Stacja[] tmp = stacje;
        stacje = new Stacja[tmp.length+1];
        for(int i = 0;i< tmp.length;i++){
            stacje[i] = tmp[i];
        }
        stacje[tmp.length] = s;
    }
    public static void usunStacje(Stacja s){
        Stacja[] tmp = stacje;
        stacje = new Stacja[tmp.length-1];
        int k = 0;
        for(int i = 0;i< tmp.length;i++){
            if(!tmp[i].getMiejscowosc().equals(s.getMiejscowosc())) {
                stacje[k] = tmp[i];
                k++;
            }
        }
    }
    private static int maxOdjazd(){
        return (int)(Math.random()*6)+5;
    }
}