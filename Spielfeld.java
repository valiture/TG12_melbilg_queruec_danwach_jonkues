public class Spielfeld {
    private int breite;
    private int hoehe;

    public Spielfeld(int breite, int hoehe) {
        this.breite = breite;
        this.hoehe = hoehe;
        /*

        Zelle[] schlangeArr = new Zelle[breite*hoehe];
        schlangeArr[0] = new Zelle(breite/2, hoehe/2);
        schlangeArr[1] = new Zelle(schlangeArr[0].getX()-1,schlangeArr[0].getY()-1);
        schlangeArr[2] = new Zelle(schlangeArr[1].getX()-1,schlangeArr[1].getY()-1);
        for (int j = 0; j < hoehe; j++) {
            for (int i = 0; i < breite; i++) {
                zellArray[i][j] = new Zelle(i, j);
                if (j == schlangeArr[0].getY() && i == schlangeArr[0].getX()) System.out.print("x");
                else System.out.print("o");
                for (int k = 1; k < schlangeArr.length; k++) {

                }
                if (i == breite-1) System.out.println();
            }
        }*/
    }



    public int getBreite() {
        return breite;
    }
    public int getHoehe() {
        return hoehe;
    }
}