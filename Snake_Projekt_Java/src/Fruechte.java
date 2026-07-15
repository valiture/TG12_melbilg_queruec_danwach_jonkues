import processing.core.PApplet;

import java.util.Random;
public class Fruechte {

    //boolean fruchtNichtAufFeld = true;

    private PApplet app;
    private Spielfeld spielfeld;
    Random random = new Random();
    int randomY;
    int randomX;

    public Fruechte(PApplet app, Spielfeld spielfeld) {
        this.app = app;
        this.spielfeld = spielfeld;
    }

    Zelle frucht;

    public void spawnFruit() {
        checkLocation();
        frucht = new Zelle(
                app,
                randomX * Spielfeld.extent,
                randomY * Spielfeld.extent,
                Spielfeld.extent,
                app.color(255,0,0)
        );
    }
    public void drawFruit() {
        if (frucht != null) {
            frucht.drawSquare();
        }
    }
    public Zelle getFrucht() {
        return frucht;
    }
    public void checkLocation() {
        boolean occupied;
        do {
            occupied = false;
            randomX = random.nextInt(Spielfeld.fieldExtent);
            randomY = random.nextInt(Spielfeld.fieldExtent);
            for (int i = 0; i < spielfeld.bodyCnt; i++) {
                if (randomX * Spielfeld.extent == spielfeld.snakeBody[i].getX() && randomY * Spielfeld.extent == spielfeld.snakeBody[i].getY()) {
                    occupied = true;
                    break;
                }
            }
            if (randomX * Spielfeld.extent == spielfeld.kopfZelle.getX() &&
                    randomY * Spielfeld.extent == spielfeld.kopfZelle.getY()) {

                occupied = true;
            }

        } while (occupied);
    }

}
//Daniel->todesscreen schlange
//^ imptotent