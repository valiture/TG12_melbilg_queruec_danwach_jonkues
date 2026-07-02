public class Highscores {
    private int spielerID;
    private int spielID;
    private int highscore;

    public Highscores(int spielerID, int spielID, int highscore){
        this.spielerID = spielerID;
        this.spielID = spielID;
        this.highscore = highscore;

    }
    public int getHighscore() {
        return highscore;
    }
}