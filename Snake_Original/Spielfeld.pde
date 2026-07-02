public class Spielfeld {
    private int breite;
    private int hoehe;  
    private int extent = 60;
    private int fieldExtent = 10;
    public color DARK_G = color(162, 209, 73);
    public color LIGHT_G = color(170, 215, 81);

    public color kopfZelle_color = color(110, 103, 103);
    
    public Spielfeld() {
      //this.breite = breite;
      //this.hoehe = hoehe;
    }
    
    public int getBreite() {return breite;}
    public int getHoehe() {return hoehe;}
    
    Zelle[][] zellArray = new Zelle[fieldExtent][fieldExtent];
    Zelle kopfZelle = new Zelle((fieldExtent*60)/2, (fieldExtent*60)/2, extent, kopfZelle_color);
    void generateField() {
      for(int i = 0; i < fieldExtent; i++) {
        for(int j = 0; j < fieldExtent; j++) {
          zellArray[j][i] = new Zelle(j*60, i*60, extent, DARK_G);
          zellArray[j][i].drawSquare();
          if((j*extent) == kopfZelle.getX() && (i*extent) == kopfZelle.getY()) {
            kopfZelle.drawSquare();
          }
        }
      }
    }
    
}
