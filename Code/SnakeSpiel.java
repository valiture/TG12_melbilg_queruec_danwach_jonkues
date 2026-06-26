public abstract  class SnakeSpiel {
    private int id;
    private String name;
    private int schwierigkeit;
    static private int spielerNr;

    public SnakeSpiel(String name, int schwierigkeit) {}
    public int getSpielID(){return 0;}
    public String getSpielName(){return "";}

    public int getSchwierigkeit() {
        return schwierigkeit;
    }
}
