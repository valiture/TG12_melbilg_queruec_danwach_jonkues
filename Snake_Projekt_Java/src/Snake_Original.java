import processing.core.PApplet;

import java.awt.*;

public class Snake_Original extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Snake_Original");
    }
    Spielfeld spielfeld;
    public Buttons[] buttonArray = new Buttons[3];
    int selectedButton = 0;
    int moveTime = 200;
    int lastMoveTime = 0;
    public void setup() {
        spielfeld = new Spielfeld(this);
        Buttons startButton = new Buttons(this,height/2, width/2, 200, 80, "Start");
        Buttons settingButton = new Buttons(this,height/2, width/2, 200, 80, "Settings");
        Buttons highScoreButton = new Buttons(this,height/2, width/2, 200, 80, "Highscores");
        buttonArray[0] = startButton;
        buttonArray[1] = settingButton;
        buttonArray[2] = highScoreButton;
    }
    public void settings() {
        size(600, 600);
    }

    boolean menuState = true;
    boolean gameStart = false;
    boolean gameOver = false;
    public void draw() {
        background(0,0,0);
        if(menuState) {
            background(0);
            fill(255, 255, 255);
            textSize(100);
            text("SNAKE", (float) (width / 2), (float) (height * 0.25));
            if (selectedButton > 2) {
                selectedButton = 0;
            } else if (selectedButton < 0) {
                selectedButton = 2;
            } else {
                buttonArray[selectedButton].drawButton(buttonArray[selectedButton].getText());
            }
        }
        if (gameStart)  {
            spielfeld.generateField();
            spielfeld.getScoreboard().draw();
            if (millis() - lastMoveTime >= moveTime) {
                    checkVerloren();
                    spielfeld.moveSnake();
                    lastMoveTime = millis();
            }
        }
        if (gameOver) {
            spielfeld.resetSnake();
            background(255);
        }
        /*stroke(255, 0, 0);
        strokeWeight(3);
        noFill();
        rect(0, 0, width - 1, height - 1);
        */
    }
    public void checkVerloren() {
        if(spielfeld.kopfZelle.getX() >= spielfeld.fieldExtent*spielfeld.extent || spielfeld.kopfZelle.getX() <= 0 ||
        (spielfeld.kopfZelle.getY()) >= spielfeld.fieldExtent*spielfeld.extent || spielfeld.kopfZelle.getY() <= 0) {
            gameStart = false;
            gameOver = true;
        }
    }

    public void keyPressed() {
        if (key == CODED && gameStart) {
            if(keyCode == UP && spielfeld.kopfZelle.lastRichtung != Zelle.DOWN){
                spielfeld.kopfZelle.lastRichtung = Zelle.UP;
                spielfeld.kopfZelle.setRichtung(Zelle.UP);
                for (int i = 0; i < spielfeld.bodyCnt; i++) {
                    spielfeld.kopfZelle.setRichtung(Zelle.UP);
                }
            }
            if(keyCode == DOWN && spielfeld.kopfZelle.lastRichtung != Zelle.UP){
                spielfeld.kopfZelle.lastRichtung = Zelle.DOWN;
                spielfeld.kopfZelle.setRichtung(Zelle.DOWN);
                for (int i = 0; i < spielfeld.bodyCnt; i++) {
                    spielfeld.kopfZelle.setRichtung(Zelle.DOWN);
                }
            }
            if(keyCode == LEFT && spielfeld.kopfZelle.lastRichtung != Zelle.RIGHT){
                spielfeld.kopfZelle.lastRichtung = Zelle.LEFT;
                spielfeld.kopfZelle.setRichtung(Zelle.LEFT);
                for (int i = 0; i < spielfeld.bodyCnt; i++) {
                    spielfeld.kopfZelle.setRichtung(Zelle.LEFT);
                }
            }
            if(keyCode == RIGHT && spielfeld.kopfZelle.lastRichtung != Zelle.LEFT){
                spielfeld.kopfZelle.lastRichtung = Zelle.RIGHT;
                spielfeld.kopfZelle.setRichtung(Zelle.RIGHT);
                for (int i = 0; i < spielfeld.bodyCnt; i++) {
                    spielfeld.kopfZelle.setRichtung(Zelle.RIGHT);
                }
            }
        }
        if (key == ENTER) {
            if (menuState) {
                if (selectedButton == 0) {
                    spielfeld.getScoreboard().reset();
                    gameStart = true;
                    menuState = false;
                } else {
                    System.out.println("WIP");
                }
            }
            if (gameOver) {
                gameOver = false;
                gameStart = false;
                menuState = true;
            }
        }
    }


}

