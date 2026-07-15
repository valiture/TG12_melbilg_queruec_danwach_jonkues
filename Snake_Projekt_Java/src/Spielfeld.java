import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Arrays;
import java.util.Random;

public class Spielfeld {
    public PApplet app;
    private int breite;
    private int hoehe;
    public static final int extent = 50;
    public static final int fieldExtent = 10; // nur gerade zahlen
    public int DARK_G; //
    public int LIGHT_G;  //app.color(170, 215, 81);
    public int kopfZelle_color; //app.color(110, 103, 103);
    int body_Color;
    private Fruechte fruechte;
    private Scoreboard scoreboard;
    int nextRichtung = Zelle.RIGHT;
    public boolean dirChange = false;
    int bodyCnt;


    Zelle kopfZelle;
    public Spielfeld(PApplet app) {
        System.out.println(fieldExtent);
        System.out.println((fieldExtent - 1) * extent);
        System.out.println(extent);
        this.app = app;
        DARK_G = app.color(162, 209, 73);
        LIGHT_G = app.color(170, 215, 81);
        kopfZelle_color = app.color(110,103,103);
        body_Color = app.color(145, 137, 137);
        kopfZelle = new Zelle(app, (int) ((fieldExtent*extent)/2.0), (int) ((fieldExtent*extent)/2.0), extent, kopfZelle_color);

        scoreboard = new Scoreboard(app);
        bodyCnt = 2;

        for(int i = 0; i < bodyCnt; i++) {
            snakeBody[i] = new Zelle(app, (kopfZelle.getX()-extent*(i+1)), kopfZelle.getY(), extent, body_Color);
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
    void generateField(float t) {
        colorField();
        for (int i = 0; i < fieldExtent; i++) {
            for (int j = 0; j < fieldExtent; j++) {
                app.rectMode(PConstants.CORNER);
                app.noStroke();
                zellArray[j][i].drawSquare();
            }
        }
        app.stroke(0,0,0);
        kopfZelle.drawSquare();
        //bewegungsanimationen
        //kopfZelle.drawSquare(t);
        snakeBody[0].drawSquare();
        //snakeBody[0].drawSquare(t);
        for (int i = 1; i < bodyCnt; i++) {
            snakeBody[i].drawSquare();
        }
        fruechte.drawFruit();
    }
    void colorField() {
        for (int i = 0; i < Spielfeld.fieldExtent; i++) {
            for (int j = 0; j < Spielfeld.fieldExtent; j++) {
                if ((i+j) % 2 == 0) {
                    zellArray[j][i].changeColor(LIGHT_G);
                }
            }
        }
    }
    public void resetSnake() {
        nextRichtung = Zelle.RIGHT;
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
    int oppositeDirection(int dir) {
        switch(dir) {
            case Zelle.UP:
                return Zelle.DOWN;
            case Zelle.DOWN:
                return Zelle.UP;
            case Zelle.LEFT:
                return Zelle.RIGHT;
            case Zelle.RIGHT:
                return Zelle.LEFT;
        }
        return 0;
    }
    void moveSnake() {
        kopfZelle.setRichtung(nextRichtung);
        kopfZelle.lastRichtung = nextRichtung;
        dirChange = false;
        kopfZelle.oldX = kopfZelle.getX();
        kopfZelle.oldY = kopfZelle.getY();
        for (int i = 0; i < bodyCnt; i++) {
            snakeBody[i].oldX = snakeBody[i].getX();
            snakeBody[i].oldY = snakeBody[i].getY();
        }
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
        if (kopfZelle.getX() == fruechte.getFrucht().getX() && kopfZelle.getY() == fruechte.getFrucht().getY()) {
            incSize();
            scoreboard.fruitCollected();
            fruechte.spawnFruit();
        }
    }

    public Zelle[] snakeBody = new Zelle[(fieldExtent*fieldExtent)-1];
    public void incSize() {
        //System.out.println("test!"); // testen
        snakeBody[bodyCnt] = new Zelle(app, kopfZelle.getX()-(bodyCnt+1), kopfZelle.getY(), extent, body_Color);
        bodyCnt++;
    }
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
