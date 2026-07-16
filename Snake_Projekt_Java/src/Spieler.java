public class Spieler {
    private int id;
    private String name;
    static private int spielerNr;

    public Spieler(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}

