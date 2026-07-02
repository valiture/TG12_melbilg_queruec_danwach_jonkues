int extent = 60;

void setup() {
  size(600, 600);
  background(#000000);
}

int startX = 0;
int startY = 0;

Zelle[][] zellArray;
Spielfeld spielfeld = new Spielfeld();

void draw() {
  for(int i = 0; i < 10; i++) {
     for(int j = 0; j < 10; j++) {
       spielfeld.generateField();
     }
  }
}

void keyPressed() {
  if(key == CODED) {
    if(keyCode == UP) {
      spielfeld.kopfZelle.moveUp();
      print(spielfeld.kopfZelle.getY());
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
