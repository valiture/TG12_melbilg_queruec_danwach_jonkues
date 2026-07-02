import processing.core.PApplet;

public class Snake_Original extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Snake_Original");
    }
    Spielfeld spielfeld;
    public void setup() {
        spielfeld = new Spielfeld(this);
    }

    public void settings() {
        size(600, 600);
    }

    boolean gameStart = false;

    public void draw() {
        update(mouseX, mouseY);
        background(0,0,0);
        if(!gameStart) {
            background(0,0,0);
            textSize(100);
            textAlign(CENTER);
            text("SNAKE", (float) (width/2), (float) (height*0.25));
            rectMode(CENTER);
            rect((float) height/2, (float) width /2, 120, 60);
            textSize(20);
            text("Start", (float) height /2, (float) width /2);

        } else {
            spielfeld.generateField();
        }
    }
    public void update(int mouseX, int mouseY) {

    }
    public void overButton(int rectX, int rectY, int rectSize) {

    }


    public void keyPressed() {
        if(key == CODED) {
            if(keyCode == UP) {
                spielfeld.kopfZelle.moveUp();
            }
            if(keyCode == DOWN) {
                spielfeld.kopfZelle.moveDown();
            }
            if(keyCode == LEFT) {
                spielfeld.kopfZelle.moveLeft();
            }
            if(keyCode == RIGHT) {
                spielfeld.kopfZelle.moveRight();
            }
        }
    }

}

