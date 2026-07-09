import java.util.Random;
public class Fruechte {
    boolean fruchtNichtAufFeld = true;
    int score;
    Scoreboard scoreboard;
    Spielfeld spielfeld;
    Zelle zelle;

    void fruechte() {
        Random random = new Random();

        if (fruchtNichtAufFeld) {
            int randomX = random.nextInt(10);
            int randomY = random.nextInt(10);
            Zelle[][] Frucht = new Zelle[randomX][randomY];
            //Frucht[randomX][randomY].setFarbe(255, 0, 0);
            fruchtNichtAufFeld=false;
            if (zelle.getX() == Frucht[randomX][randomY].getX()
                    && zelle.getY() == Frucht[randomX][randomY].getY()) {
                scoreboard.fruitCollected();
                fruchtNichtAufFeld=true;
            }
        }

    }
}