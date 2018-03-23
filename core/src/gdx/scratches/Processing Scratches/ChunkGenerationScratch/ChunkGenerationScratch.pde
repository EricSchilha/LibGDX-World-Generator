int scale;
float val;
boolean outOfBounds;
Map map;
void setup() {
  size(800, 800);
  map = new Map();
}

void draw() {
  noiseSeed(0);
  randomSeed(0);
  background(255);
  map.drawChunks();
}



class Map {
  float wOffset, hOffset;
  ArrayList<Chunk> alChunks = new ArrayList<Chunk>();
  Map() {
    this.wOffset = 0;
    this.hOffset = 0;
    alChunks.add(new Chunk());
  }

  void drawChunks() {
    for (Chunk chunk : alChunks) {
      for (int h = 0; h < chunk.CHUNK_HEIGHT; h++) {
        for (int w = 0; w < chunk.CHUNK_WIDTH; w++) {
          val = chunk.terrain[h][w].depth;
          if (chunk.terrain[h][w].TileType==TileType.Grass) {
            fill(0, map(val, 0, 1, 255, 0), 0);
          } else {
            fill(0, 0, 255);
          }
          rect(w*scale, h*scale, scale, scale);
        }
      }
    }
  }
}

class Chunk {
  public int CHUNK_WIDTH = 32;
  public int CHUNK_HEIGHT = 32;
  Tile terrain[][];
  public Chunk() {
    terrain = new Tile[CHUNK_HEIGHT][CHUNK_WIDTH];
    generateTerrain();
  }
  public void generateTerrain() {
    float wOffset = 0;
    float hOffset = 0;
    for (int h = 0; h < CHUNK_HEIGHT; h++) {
      wOffset = 0;
      for (int w = 0; w < CHUNK_WIDTH; w++) {
        terrain[h][w] = new Tile(noise(hOffset, wOffset), w, h, TileType.Grass);
        wOffset+=0.2;
      }
      hOffset+=0.2;
    }
  }
}

static class Tile {
  public static int TILE_WIDTH = 20, TILE_HEIGHT = 20;
  int x, y;
  float depth;
  TileType TileType;
  Tile(float depth, int x, int y, TileType TileType) {
    this.depth = depth;
    this.x = x;
    this.y = y;
    this.TileType = TileType;
  }
}

enum TileType {
  Tree, Grass;
}