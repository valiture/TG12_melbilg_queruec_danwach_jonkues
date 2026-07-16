import processing.core.PApplet;
import processing.core.PConstants;
class Scoreboard {

    private PApplet p;
    private int score = 0;
    private int[] highscores = new int[3];
    private int currentSpeedIndex = 1;

    private int x = 10;
    private int y = 10;
    private int textSize = 24;

    Scoreboard(PApplet p) {
        this.p = p;
    }

    public void setDifficulty(int speedIndex) {
        currentSpeedIndex = speedIndex;
    }

    public void reset() {
        score = 0;
    }

    public void fruitCollected() {
        score++;
        if (score > highscores[currentSpeedIndex]) {
            highscores[currentSpeedIndex] = score;
        }
    }

    public int getHighscore(int speedIndex) {
        return highscores[speedIndex];
    }

    public void draw() {
        p.textAlign(PConstants.LEFT, PConstants.TOP);
        p.textSize(textSize);

        String text = "Score: " + score +
                "   Highscore: " + highscores[currentSpeedIndex];

        p.fill(0);
        p.text(text, x + 2, y + 2);
        p.fill(255);
        p.text(text, x, y);
    }
    public int getScore() {
        return score;
    }
}