import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Random;

public class Spielfeld {
    public PApplet app;
    private int breite;
    private int hoehe;
    public int extent = 60;
    public int fieldExtent = 10;
    public int DARK_G; //
    public int LIGHT_G;  //app.color(170, 215, 81);
    public int kopfZelle_color; //app.color(110, 103, 103);
    private Zelle frucht;
    private Scoreboard scoreboard;

    Zelle kopfZelle;
    public Spielfeld(PApplet app) {
        System.out.println(fieldExtent);
        System.out.println((fieldExtent - 1) * extent);
        System.out.println(extent);
        this.app = app;
        DARK_G = app.color(162, 209, 73);
        kopfZelle_color = app.color(110,103,103);
        kopfZelle = new Zelle(app, (int) ((fieldExtent*extent)/2.0), (int) ((fieldExtent*extent)/2.0), extent, kopfZelle_color);

        scoreboard = new Scoreboard(app);

        for(int i = 0; i < fieldExtent; i++) {
            for(int j = 0; j < fieldExtent; j++) {
                zellArray[j][i] = new Zelle(app,j*extent, i*extent, extent, DARK_G);
                }
            }

        //this.breite = breite;
        //this.hoehe = hoehe;
    }

    public int getBreite() {return breite;}
    public int getHoehe() {return hoehe;}

    Zelle[][] zellArray = new Zelle[fieldExtent][fieldExtent];
    void generateField() {
        kopfZelle.setX((int) ((fieldExtent*extent)/2.0));
        kopfZelle.setY((int) ((fieldExtent*extent)/2.0));
        for(int i = 0; i < fieldExtent; i++) {
            for(int j = 0; j < fieldExtent; j++) {
                app.rectMode(PConstants.CORNER);
                zellArray[j][i].drawSquare();
                if((j*extent) == kopfZelle.getX() && (i*extent) == kopfZelle.getY()) {
                    kopfZelle.drawSquare();
                }
            }
        }
    }
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
