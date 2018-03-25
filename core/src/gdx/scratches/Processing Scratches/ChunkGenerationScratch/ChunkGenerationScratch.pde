int nPlayerX, nPlayerY;
private int CHUNK_SIZE = 32;
private int TILE_SIZE;
static int nScale = 20;
int nMinDivisor = 4096; //Used for testing boundaries, startX and startY range from nMinDivisor*64 to nMinDivisor*192 with 4096
Map mapMap;
public void setup() {
  size(2000, 1200);
  mapMap = new Map();
  surface.setResizable(true);
  nPlayerX = (int)random(MAX_INT/nMinDivisor - MAX_INT/nMinDivisor/2, MAX_INT/nMinDivisor + MAX_INT/nMinDivisor/2);
  nPlayerY = (int)random(MAX_INT/nMinDivisor - MAX_INT/nMinDivisor/2, MAX_INT/nMinDivisor + MAX_INT/nMinDivisor/2);
  //nPlayerX = (int)MAX_INT/nMinDivisor; //FOR TESTING
  //nPlayerY = (int)MAX_INT/nMinDivisor;
}

void draw() {
  background(255);
  resizeTiles();
  mapMap.drawChunks();
  println(nPlayerX + "\t" + nPlayerY);
  if (keyPressed && key==CODED) {
    if (keyCode == UP) {
      nPlayerY--;
    } else if (keyCode == DOWN) {
      nPlayerY++;
    } else if (keyCode == LEFT) {
      nPlayerX--;
    } else if (keyCode == RIGHT) {
      nPlayerX++;
    }
  }
}

void resizeTiles() {
  TILE_SIZE = (width+height)/CHUNK_SIZE/3;
}

class Map {
  Chunk[][] arChunks = new Chunk[5][5];
  public Map() {
    PVector vPlayerChunk = getChunkIndices(new PVector(nPlayerX, nPlayerY));
    int nPlayerChunkX = (int)vPlayerChunk.x;
    int nPlayerChunkY = (int)vPlayerChunk.y;
    for (int y = 0; y < arChunks.length; y++) 
      for (int x = 0; x < arChunks[y].length; x++) 
        arChunks[y][x] = new Chunk(nPlayerChunkX - CHUNK_SIZE * (x - ((arChunks[y].length-1)/2)), nPlayerChunkY - CHUNK_SIZE * (y - ((arChunks.length-1)/2)));
  }

  public void drawChunks() {
    updateMap();
    for (Chunk[] chunkAr : arChunks)
      for (Chunk chunk : chunkAr) 
        chunk.drawTiles();
  }

  public void updateMap() {
    PVector vPlayerChunk = getChunkIndices(new PVector(nPlayerX, nPlayerY));
    if (arChunks[arChunks[arChunks.length/2].length/2][arChunks.length/2].vTopLeft.x == vPlayerChunk.x && arChunks[arChunks[arChunks.length/2].length/2][arChunks.length/2].vTopLeft.y == vPlayerChunk.y) {
      return;
    }
    int nPlayerChunkX = (int)vPlayerChunk.x;
    int nPlayerChunkY = (int)vPlayerChunk.y;
    for (int y = 0; y < arChunks.length; y++) 
      for (int x = 0; x < arChunks[y].length; x++) 
        arChunks[y][x] = new Chunk(nPlayerChunkX - CHUNK_SIZE * (x - ((arChunks[y].length-1)/2)), nPlayerChunkY - CHUNK_SIZE * (y - ((arChunks.length-1)/2)));
  }
}


class Chunk {
  PVector vTopLeft;
  Tile[][] arTiles;


  public Chunk(int nX, int nY) {
    arTiles = new Tile[CHUNK_SIZE][CHUNK_SIZE];
    vTopLeft = new PVector(nX, nY);
    generateTerrain();
  }

  public void generateTerrain() {
    float fWidthOffset = 0, fHeightOffset = 0, fPersistence = 0.08;
    for (int y = 0; y < CHUNK_SIZE; y++) {
      fWidthOffset = 0;
      for (int x = 0; x < CHUNK_SIZE; x++) {
        arTiles[y][x] = new Tile(noise(vTopLeft.x * fPersistence + fWidthOffset, vTopLeft.y * fPersistence + fHeightOffset), TileType.Grass);
        fWidthOffset+=fPersistence;
      }
      fHeightOffset+=fPersistence;
    }
  }

  public void drawTiles() {
    float fVal;
    for (int y = 0; y < CHUNK_SIZE; y++) 
      for (int x = 0; x < CHUNK_SIZE; x++) 
        if (TILE_SIZE * (vTopLeft.x - nPlayerX + x + 1) > 0 && TILE_SIZE * (vTopLeft.x - nPlayerX + x) < width && TILE_SIZE * (vTopLeft.y  - nPlayerY + y + 1) > 0 && TILE_SIZE * (vTopLeft.y  - nPlayerY + y) < height) {
          fVal = arTiles[y][x].fDepth;
          if (arTiles[y][x].tileType==TileType.Grass) {
            fill(0, map(fVal, 0, 1, 255, 0), 0);
          } else if (arTiles[y][x].tileType==TileType.Tree) {
            fill(0, 0, 255);
          }
          rect(TILE_SIZE * (vTopLeft.x - nPlayerX + x), TILE_SIZE * (vTopLeft.y  - nPlayerY + y), TILE_SIZE, TILE_SIZE);
        }
    drawChunkLines();
  }

  void drawChunkLines() {
    strokeWeight(3);
    stroke(255, 0, 0);//Factored out TILE_SIZE for math below
    line(TILE_SIZE * (vTopLeft.x - nPlayerX             ), TILE_SIZE * (vTopLeft.y - nPlayerY             ), TILE_SIZE * (vTopLeft.x - nPlayerX + CHUNK_SIZE), TILE_SIZE * (vTopLeft.y - nPlayerY             ));
    line(TILE_SIZE * (vTopLeft.x - nPlayerX             ), TILE_SIZE * (vTopLeft.y - nPlayerY             ), TILE_SIZE * (vTopLeft.x - nPlayerX             ), TILE_SIZE * (vTopLeft.y - nPlayerY + CHUNK_SIZE));
    line(TILE_SIZE * (vTopLeft.x - nPlayerX + CHUNK_SIZE), TILE_SIZE * (vTopLeft.y - nPlayerY             ), TILE_SIZE * (vTopLeft.x - nPlayerX + CHUNK_SIZE), TILE_SIZE * (vTopLeft.y - nPlayerY + CHUNK_SIZE));
    line(TILE_SIZE * (vTopLeft.x - nPlayerX             ), TILE_SIZE * (vTopLeft.y - nPlayerY + CHUNK_SIZE), TILE_SIZE * (vTopLeft.x - nPlayerX + CHUNK_SIZE), TILE_SIZE * (vTopLeft.y - nPlayerY + CHUNK_SIZE));
    stroke(0);
    strokeWeight(1);
  }
}

public PVector getChunkIndices(PVector vCur) {
  int nX = int(vCur.x - (vCur.x % CHUNK_SIZE));
  int nY = int(vCur.y - (vCur.y % CHUNK_SIZE));
  return new PVector(vCur.x < 0 ? nX - CHUNK_SIZE : nX, vCur.y < 0 ? nY - CHUNK_SIZE : nY);
}

class Tile {
  float fDepth;
  TileType tileType;
  Tile(float fDepth, TileType tileType) {
    this.fDepth = fDepth;
    this.tileType = tileType;
  }
}

enum TileType {
  Tree, Grass;
}