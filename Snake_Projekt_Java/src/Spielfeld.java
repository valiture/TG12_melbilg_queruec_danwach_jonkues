import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Random;

public class Spielfeld {
    public PApplet app;
    private int breite;
    private int hoehe;
    public static final int extent = 60;
    public static final int fieldExtent = 12;
    public int DARK_G; //
    public int LIGHT_G;  //app.color(170, 215, 81);
    public int kopfZelle_color; //app.color(110, 103, 103);
    int body_Color;
    private Fruechte fruechte;
    private Scoreboard scoreboard;
    int bodyCnt;


    Zelle kopfZelle;
    public Spielfeld(PApplet app) {
        System.out.println(fieldExtent);
        System.out.println((fieldExtent - 1) * extent);
        System.out.println(extent);
        this.app = app;
        DARK_G = app.color(162, 209, 73);
        kopfZelle_color = app.color(110,103,103);
        body_Color = app.color(145, 137, 137);
        kopfZelle = new Zelle(app, (int) ((fieldExtent*extent)/2.0), (int) ((fieldExtent*extent)/2.0), extent, kopfZelle_color);

        scoreboard = new Scoreboard(app);
        bodyCnt = 2;
        for(int i = 0; i < bodyCnt; i++) {
            snakeBody[i] = new Zelle(app, (kopfZelle.getX()-extent*i+1), kopfZelle.getY(), extent, body_Color);
        }
        for(int i = 0; i < fieldExtent; i++) {
            for(int j = 0; j < fieldExtent; j++) {
                zellArray[j][i] = new Zelle(app,j*extent, i*extent, extent, DARK_G);
                }
            }
        fruechte = new Fruechte(app, this);
        fruechte.spawnFruit();
        //this.breite = breite;
        //this.hoehe = hoehe;
    }

    public int getBreite() {return breite;}
    public int getHoehe() {return hoehe;}

    Zelle[][] zellArray = new Zelle[fieldExtent][fieldExtent];
    void generateField() {
        for (int i = 0; i < fieldExtent; i++) {
            for (int j = 0; j < fieldExtent; j++) {
                app.rectMode(PConstants.CORNER);
                zellArray[j][i].drawSquare();

            }
        }
        kopfZelle.drawSquare();
        for (int i = 0; i < bodyCnt; i++) {
            snakeBody[i].drawSquare();
        }

        fruechte.drawFruit();
    }
    public void resetSnake() {
        kopfZelle.setX((int) ((fieldExtent*extent)/2.0));
        kopfZelle.setY((int) ((fieldExtent*extent)/2.0));
        kopfZelle.richtung = Zelle.RIGHT;
        kopfZelle.lastRichtung = Zelle.RIGHT;

        bodyCnt = 2;
        snakeBody[0] = new Zelle(app,
                kopfZelle.getX() - extent,
                kopfZelle.getY(),
                extent,
                body_Color);
        snakeBody[1] = new Zelle(app,
                kopfZelle.getX() - 2 * extent,
                kopfZelle.getY(),
                extent,
                body_Color);
        fruechte.spawnFruit();
    }

    void moveSnake() {
        int oldX = kopfZelle.getX();
        int oldY = kopfZelle.getY();
        kopfZelle.move();
        for (int i = 0; i < bodyCnt; i++) {
            int tempX = snakeBody[i].getX();
            int tempY = snakeBody[i].getY();
            snakeBody[i].setX(oldX);
            snakeBody[i].setY(oldY);
            oldX = tempX;
            oldY = tempY;
        }

        if (kopfZelle.getX() == fruechte.getFrucht().getX()
                && kopfZelle.getY() == fruechte.getFrucht().getY()) {
            incSize();
            scoreboard.fruitCollected();
            fruechte.spawnFruit();
        }
    }

    public Zelle[] snakeBody = new Zelle[(fieldExtent*fieldExtent)-1];
    public void incSize() {
        System.out.println("test!");
        snakeBody[bodyCnt] = new Zelle(app, kopfZelle.getX()-(bodyCnt+1), kopfZelle.getY(), extent, body_Color);
        bodyCnt++;
    }


    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
