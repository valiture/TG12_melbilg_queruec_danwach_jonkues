import processing.core.PApplet;
import java.util.Random;

public class Spielfeld {
    public PApplet app;
    private int breite;
    private int hoehe;
    private int extent = 60;
    private int fieldExtent = 10;
    public int DARK_G; //
    public int LIGHT_G;  //app.color(170, 215, 81);
    public int kopfZelle_color; //app.color(110, 103, 103);
    private Zelle frucht;

    Zelle kopfZelle;
    public Spielfeld(PApplet app) {
        this.app = app;
        DARK_G = app.color(162, 209, 73);
        kopfZelle_color = app.color(110,103,103);
        kopfZelle = new Zelle(app,(fieldExtent*60)/2, (fieldExtent*60)/2, extent, kopfZelle_color);
        //this.breite = breite;
        //this.hoehe = hoehe;
    }

    public int getBreite() {return breite;}
    public int getHoehe() {return hoehe;}

    Zelle[][] zellArray = new Zelle[fieldExtent][fieldExtent];
    void generateField() {
        for(int i = 0; i < fieldExtent; i++) {
            for(int j = 0; j < fieldExtent; j++) {
                zellArray[j][i] = new Zelle(app,j*60, i*60, extent, DARK_G);
                zellArray[j][i].drawSquare();
                if((j*extent) == kopfZelle.getX() && (i*extent) == kopfZelle.getY()) {
                    kopfZelle.drawSquare();
                }
            }
        }
    }
    
}
