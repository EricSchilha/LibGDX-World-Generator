/*
* This is a scratch program to test creating realistic
* world environments.  The shaded dark areas represent
* depth (darker = higher), and the blue areas on the
* map represent trees.
*/

int W = 25, H = 25, D = 25;
int val, scale;
boolean outOfBounds;
Map map;
void setup() {
  size(1200, 1200);
  scale = 30;
  W = height/scale;
  H = width/scale;
  map = new Map(W, H);
  vegetation();
}

void draw() {
  background(255);
  drawMap();
}

void drawMap() {
  for (int h = 0; h < map.getHeight(); h++) {
    for (int w = 0; w < map.getWidth(); w++) {
      val = map.terrain[h][w].depth;
      if(map.terrain[h][w].state==State.Grass){
        fill(0, map(val, 0, 10, 255, 0), 0);
      } else {
        fill(0, 0, 255);
      }
      rect(w*scale, h*scale, scale, scale);
    }
  }
}

class Map {
  private final int W, H;
  Tile terrain[][];
  Map(int W, int H) {
    this.W = W;
    this.H = H;
    terrain = new Tile[H][W];
    generateTerrain();
  }
  public void generateTerrain() {
    float hOffset = 0, wOffset = 0;
    for (int h = 0; h < W; h++) {
      wOffset = 0;
      for (int w = 0; w < H; w++) {
        terrain[h][w] = new Tile(parseInt(map(noise(hOffset, wOffset), 0, 1, 0, 10)), w, h, State.Grass);
        wOffset+=0.2;
      }
      hOffset+=0.2;
    }
  }

  public int getWidth() {
    return W;
  }
  public int getHeight() {
    return H;
  }
}

class Tile {
  int x, y, depth;
  State state;
  Tile(int depth, int x, int y, State state) {
    this.depth = depth;
    this.x = x;
    this.y = y;
    this.state = state;
  }
}

enum State {
  Tree, Grass;
}