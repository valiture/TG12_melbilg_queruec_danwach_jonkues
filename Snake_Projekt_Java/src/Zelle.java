import processing.core.PApplet;

class Zelle {
    PApplet app = new PApplet();
    private int x;
    private int y;
    public int extent;
    private int farbe;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public int richtung;
    public int lastRichtung = RIGHT;

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

    public void setRichtung(int richtung) {
        this.richtung = richtung;
    }
    public int getRichtung() {
        return richtung;
    }

    void move() {
        switch (richtung) {
            case 0:
                y -= extent;
                break;
            case 1:
                y += extent;
                break;
            case 2:
                x -= extent;
                break;
            case 3:
                x += extent;
                break;
        }
    }



    /*
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
     */

}
