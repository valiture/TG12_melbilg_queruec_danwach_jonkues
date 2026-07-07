import processing.core.PApplet;

import java.awt.*;

public class Snake_Original extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Snake_Original");
    }
    Spielfeld spielfeld;
    public Buttons[] buttonArray = new Buttons[3];
    int selectedButton = 0;
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

    boolean gameStart = false;
    public void draw() {
        background(0,0,0);
        if(!gameStart) {
            background(0);
            fill(255,255,255);
            textSize(100);
            text("SNAKE", (float) (width/2), (float) (height*0.25));
            if (selectedButton > 2) {
                selectedButton = 0;
            } else if (selectedButton < 0) {
                selectedButton = 2;
            } else {
                buttonArray[selectedButton].drawButton(buttonArray[selectedButton].getText());
            }
        } else {
            spielfeld.generateField();
        }
    }

    public void keyPressed() {
        if(key == CODED) {
            if(keyCode == UP) {
                if (gameStart) {
                    spielfeld.kopfZelle.moveUp();
                }
            }
            if(keyCode == DOWN) {
                spielfeld.kopfZelle.moveDown();
            }
            if(keyCode == LEFT) {
                if (gameStart) {
                    spielfeld.kopfZelle.moveLeft();
                }
                else {
                    System.out.println(selectedButton);
                    selectedButton--;
                }
            }
            if(keyCode == RIGHT) {
                if (gameStart) {
                    spielfeld.kopfZelle.moveRight();
                }
                else {
                    System.out.println(selectedButton);
                    selectedButton++;
                }
            }
        }
        if (key == ENTER) {
            switch (selectedButton) {
                case 0:
                    gameStart = true;
                    break;
                default:
                    System.out.println("WIP");
            }
        }
    }

}

