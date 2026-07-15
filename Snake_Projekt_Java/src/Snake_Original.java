import processing.core.PApplet;

import java.awt.*;
import java.sql.SQLOutput;

public class Snake_Original extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Snake_Original");
    }
    Spielfeld spielfeld;
    public Buttons[] buttonArray = new Buttons[3];
    int selectedButton = 0;
    int moveTime = 125;
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
    int size = Spielfeld.extent*Spielfeld.fieldExtent;
    public void settings() {
        size(size, size);
    }

    enum GameState {
            MENU,
            PLAYING,
            GAMEOVER
    }
    GameState state = GameState.MENU;

    public void draw() {
        background(0,0,0);
        switch (state) {
            case MENU:
                spielfeld.resetSnake();
                background(0);
                fill(255, 255, 255);
                textSize(100);
                text("SNAKE", (float) (width / 2), (float) (height * 0.25));
                buttonArray[selectedButton].drawButton(buttonArray[selectedButton].getText());
                break;
            case PLAYING:
                float t = (millis() - lastMoveTime) / (float) moveTime;
                t = constrain(t, 0, 1);
                spielfeld.generateField(t);
                spielfeld.getScoreboard().draw();
                if (millis() - lastMoveTime >= moveTime) {
                        checkVerloren();
                        spielfeld.moveSnake();
                        lastMoveTime = millis();
                }
                break;
            case GAMEOVER:
                background(255);
                spielfeld.resetSnake();
                break;

    }       //Debug Code zum Überprüfen der einzelnen Zellen
            /*
            stroke(255, 0, 0);
            strokeWeight(3);
            noFill();
            rect(0, 0, width - 1, height - 1);
            */
    }
    public void checkVerloren() {
        if(spielfeld.kopfZelle.getX() >= Spielfeld.fieldExtent*Spielfeld.extent || spielfeld.kopfZelle.getX() < 0 ||
        (spielfeld.kopfZelle.getY()) >= Spielfeld.fieldExtent*Spielfeld.extent || spielfeld.kopfZelle.getY() < 0) {
            state = GameState.GAMEOVER;
        }
        for (int i = 0; i < spielfeld.bodyCnt; i++)  {
            if (spielfeld.kopfZelle.getX()==spielfeld.snakeBody[i].getX() && spielfeld.kopfZelle.getY() == spielfeld.snakeBody[i].getY()) {
                state = GameState.GAMEOVER;
            }
        }
    }
    public void changeDirection(int richtung, int opposite) {
        if (spielfeld.dirChange) {
            return;
        }
        if (spielfeld.kopfZelle.lastRichtung != opposite) {
            spielfeld.kopfZelle.lastRichtung = richtung;
            spielfeld.kopfZelle.setRichtung(richtung);
            spielfeld.dirChange = true;
        }
    }
    public void menuControlRight() {
        if (selectedButton < buttonArray.length-1) selectedButton++;
        else selectedButton = 0;
    }
    public void menuControlLeft() {
        if (selectedButton > 0) selectedButton--;
        else selectedButton = buttonArray.length-1;
    }
    public void keyPressed() {
        if (key == CODED) {
            if(keyCode == UP) {
                 if (state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.DOWN){
                    changeDirection(Zelle.UP, Zelle.DOWN);
                }
                 if (state == GameState.MENU) {
                     System.out.println("wip");
                 }
            }
            if(keyCode == DOWN) {
                 if(state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.UP){
                    changeDirection(Zelle.DOWN, Zelle.UP);
                }
                 if (state == GameState.MENU) {
                     System.out.println("wip");
                 }
            }
            if(keyCode == LEFT) {
                if(state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.RIGHT){
                    changeDirection(Zelle.LEFT, Zelle.RIGHT);
                }
                if (state == GameState.MENU) {
                    menuControlLeft();
                }
            }
            if(keyCode == RIGHT) {
                if(state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.LEFT){
                    changeDirection(Zelle.RIGHT, Zelle.LEFT);
                }
                if (state == GameState.MENU) {
                    menuControlRight();
                }
            }
            //if (state == GameState.PLAYING) {

        }
        if (key == ENTER) {
            if (state == GameState.MENU) {
                if (selectedButton == 0) {
                    spielfeld.getScoreboard().reset();
                    state = GameState.PLAYING;
                } else {
                    System.out.println("WIP");
                }
            }
            if (state == GameState.GAMEOVER) {
                state = GameState.MENU;
            }
        }
        if (key == 'r') {
            state = GameState.MENU;
        }
    }


}

