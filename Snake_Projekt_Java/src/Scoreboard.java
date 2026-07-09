import processing.core.*;

class Scoreboard {

    private PApplet p;

    private int score = 0;
    private int highscore = 0;

    // Position links oben
    private int x = 10;
    private int y = 10;

    private int textSize = 24;

    Scoreboard(PApplet p) {
        this.p = p;
    }

    // Score auf 0 setzen (neues Spiel)
    public void reset() {
        score = 0;
    }

    // Wird aufgerufen, wenn eine Frucht gegessen wurde
    public void fruitCollected() {
        score++;

        if (score > highscore) {
            highscore = score;
        }
    }

    // Zeichnet Score oben links
    public void draw() {

        p.textAlign(PConstants.LEFT, PConstants.TOP);
        p.textSize(textSize);

        String text = "Score: " + score +
                "   Highscore: " + highscore;

        // Schwarzer Schatten
        p.fill(0);
        p.text(text, x + 2, y + 2);

        // Weißer Text
        p.fill(255);
        p.text(text, x, y);
    }

    public int getScore() {
        return score;
    }

    public int getHighscore() {
        return highscore;
    }

}