import javax.swing.*;
import java.awt.*;

public class Snake_Original{
private int anzKoerperZellen;
private boolean verloren;
private Spielfeld spielfeld;
JFrame frame = new JFrame("Snake");
JLabel label = new JLabel("Kopf");
    public Snake_Original(int anzKoerperZellen, Spielfeld spielfeld) {
        label.setSize(spielfeld.getBreite()*100, spielfeld.getHoehe()*100);
        frame.add(label);
        frame.setSize(spielfeld.getBreite()*100, spielfeld.getHoehe()*100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.anzKoerperZellen = anzKoerperZellen;
        this.spielfeld = spielfeld;
        int breite = spielfeld.getBreite();
        int hoehe = spielfeld.getHoehe();
        Zelle kopfZelle = new Zelle(breite/2, hoehe/2);
        Zelle[] koerperZellen = new Zelle[breite*hoehe];
        Zelle[][] zellArray = new Zelle[breite][hoehe];
        for (int i = 0; i < anzKoerperZellen+1; i++) {
            koerperZellen[i] = new Zelle(kopfZelle.getX()-i,kopfZelle.getY());
        }
        for (int i = 1; i <= spielfeld.getHoehe(); i++) {
            for (int j = 1; j <= spielfeld.getBreite(); j++) {
                if(j == kopfZelle.getX() && i == kopfZelle.getY()) {
                    System.out.print("X");
                    j++;
                }
                zellArray[j-1][i-1] = new Zelle(j-1,i-1);
                System.out.print("O");
                if (j == spielfeld.getBreite()) System.out.println();
            }
        }
        frame.setVisible(true);
    }
    public void generate(Spielfeld spielfeld, Zelle[][] zellArray) {

    }
    public int getAnzKoerperZellen() {
        return anzKoerperZellen;
    }


}


