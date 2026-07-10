import processing.core.PApplet;

import java.util.Random;
public class Fruechte {

    //boolean fruchtNichtAufFeld = true;

    private PApplet app;
    private Spielfeld spielfeld;
    Random random = new Random();

    public Fruechte(PApplet app, Spielfeld spielfeld) {
        this.app = app;
        this.spielfeld = spielfeld;
    }

    Zelle frucht;

    public void spawnFruit() {

        int randomX = random.nextInt(Spielfeld.fieldExtent);
        int randomY = random.nextInt(Spielfeld.fieldExtent);

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
}
//Daniel->todesscreen schlange
