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

        randomX = random.nextInt(Spielfeld.fieldExtent);
        randomY = random.nextInt(Spielfeld.fieldExtent);
        checkLocation();
        frucht = new Zelle(
                app,
                randomX * Spielfeld.extent,
                randomY * Spielfeld.extent,
                Spielfeld.extent,
                app.color(255, 0, 0));
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
        for (int i = 0; i < spielfeld.bodyCnt; i++) {
            if (randomX == spielfeld.snakeBody[i].getX() && randomY == spielfeld.snakeBody[i].getY()) {
                randomX = random.nextInt(Spielfeld.extent);
                randomY = random.nextInt(Spielfeld.extent);
                frucht.setX(randomX * Spielfeld.extent);
                frucht.setY(randomY * Spielfeld.extent);
            }
        }
    }

}
//Daniel->todesscreen schlange
//^ imptotent