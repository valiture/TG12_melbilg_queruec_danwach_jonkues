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
    int moveTime = 150;
    int lastMoveTime = 0;

    // --- Settings ---
    String[] speedNames = {"Langsam", "Mittel", "Schnell"};
    int[] speedValues = {200, 150, 90};
    int selectedSpeed = 1; // Start: Normal

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
        SETTINGS,
        HIGHSCORES,
        PLAYING,
        GAMEOVER
    }
    GameState state = GameState.MENU;
    boolean hasStarted = false;
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
            case SETTINGS:
                background(0);
                fill(255);
                textAlign(CENTER);
                textSize(50);
                text("SETTINGS", width/2f, height*0.25f);
                textSize(30);
                text("Geschwindigkeit: " + speedNames[selectedSpeed], width/2f, height/2f);
                textSize(18);
                text("< / > wechseln, ENTER = zurück", width/2f, height*0.65f);
                break;
            case HIGHSCORES:
                background(0);
                fill(255);
                textAlign(CENTER);
                textSize(50);
                text("HIGHSCORES", width/2f, height*0.25f);
                textSize(28);
                for (int i = 0; i < speedNames.length; i++) {
                    text(speedNames[i] + ": " + spielfeld.getScoreboard().getHighscore(i), width/2f, height*0.45f + i*40);
                }
                textSize(18);
                text("ENTER = zurück", width/2f, height*0.8f);
                break;
            case PLAYING:
                /*
                experimente für animierte bewegung mithilfe von lerp()
                problematisch bei 90 grad drehungen da es diagonal gezogen wird
                 */
                float t = (millis() - lastMoveTime) / (float) moveTime;
                t = constrain(t, 0, 1);
                spielfeld.generateField(t);
                spielfeld.getScoreboard().draw();
                if (hasStarted) {
                    if (millis() - lastMoveTime >= moveTime) {
                        checkVerloren();
                        spielfeld.moveSnake();
                        lastMoveTime = millis();
                    }
                } else {
                    textAlign(CENTER);
                    textSize(30);
                    text("Press any direction to begin!", size/2, (size/2)-Spielfeld.extent);

                }
                break;
            case GAMEOVER:
                background(0);
                fill(255, 0, 0);
                textAlign(CENTER);
                textSize(80);
                text("GAME OVER", width/2f, height*0.35f);

                fill(255);
                textSize(30);
                text("Score: " + spielfeld.getScoreboard().getScore(), width/2f, height*0.5f);
                text("Highscore: " + spielfeld.getScoreboard().getHighscore(selectedSpeed), width/2f, height*0.5f + 40);

                textSize(20);
                text("ENTER = zurueck ins Menü", width/2f, height*0.75f);
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
        hasStarted = true;
        if (spielfeld.kopfZelle.lastRichtung != opposite) {
            spielfeld.nextRichtung = richtung;
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
                if (state == GameState.SETTINGS) {
                    selectedSpeed = (selectedSpeed - 1 + speedValues.length) % speedValues.length;
                }
            }
            if(keyCode == RIGHT) {
                if(state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.LEFT){
                    changeDirection(Zelle.RIGHT, Zelle.LEFT);
                }
                if (state == GameState.MENU) {
                    menuControlRight();
                }
                if (state == GameState.SETTINGS) {
                    selectedSpeed = (selectedSpeed + 1) % speedValues.length;
                }
            }
            //if (state == GameState.PLAYING) {

        }
        if (key == ENTER) {
            if (state == GameState.MENU) {
                if (selectedButton == 0) {
                    spielfeld.getScoreboard().reset();
                    spielfeld.getScoreboard().setDifficulty(selectedSpeed);
                    hasStarted = false;
                    moveTime = speedValues[selectedSpeed];
                    state = GameState.PLAYING;
                } else if (selectedButton == 1) {
                    state = GameState.SETTINGS;
                } else if (selectedButton == 2) {
                    state = GameState.HIGHSCORES;
                }
            } else if (state == GameState.SETTINGS || state == GameState.HIGHSCORES) {
                state = GameState.MENU;
            } else if (state == GameState.GAMEOVER) {
                state = GameState.MENU;
            }
        }
        if (key == 'r') {
            hasStarted = false;
            state = GameState.MENU;
        }
    }
}