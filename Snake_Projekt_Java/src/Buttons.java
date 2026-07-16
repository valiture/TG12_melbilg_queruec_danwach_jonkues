import processing.core.PApplet;
import processing.core.PConstants;

public class Buttons {
    PApplet app;
    private float x;
    private float y;
    private int width;
    private int height;
    private String text;

    public Buttons(PApplet app, int x, int y, int width, int height, String text) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    void drawButton() {
        app.rectMode(PConstants.CENTER);
        app.rect(x,y,width,height);
    }
    void drawButton(String text) {
        drawButton();
        app.textAlign(PConstants.CENTER);
        app.fill(0,0,0);
        app.textSize(40);
        app.text(text, x, y+10);
    }

    public String getText() {
        return text;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
