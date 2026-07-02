import processing.core.PApplet;

class Zelle {
    PApplet app = new PApplet();

    private int x;
    private int y;
    private int extent;
    private int farbe;
    int letzteRichtung = 3;

    public Zelle(PApplet app,int x, int y, int extent, int farbe) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.extent = extent;
        this.farbe = farbe;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    void drawSquare() {
        app.fill(farbe);
        app.square(x,y,extent);
    }

    void moveUp() {
        if(letzteRichtung != 1) {
            setY(extent*((y/extent)-1));
            letzteRichtung = 0;
        }
    }
    void moveDown() {
        if(letzteRichtung != 0) {
            setY(extent*((y/extent)+1));
            letzteRichtung = 1;
        }
    }
    void moveLeft() {
        if(letzteRichtung != 3) {
            setX(extent*((x/extent)-1));
            letzteRichtung = 2;
        }
    }
    void moveRight() {
        if(letzteRichtung != 2) {
            setX(extent*((x/extent)+1));
            letzteRichtung = 3;
        }
    }




}
