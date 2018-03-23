import java.util.Arrays;
//THIS IS NOT COMPLETE
int nCellSize = 30, nWidth, nHeight, nChunkSize = 50;
Location location = new Location((int)random(10000), (int)random(10000));
Location startLocation = location;
World world = new World(nChunkSize, location);
void setup() {
  size(750, 750);
  nWidth = width/nCellSize;
  nHeight = height/nCellSize;
  world.arTiles[location.nY][location.nX] = new Tile(Type.Player);
}

void draw() {
  background(255);
  for (int y = -(int)Math.floor(nHeight/2); y < (int)Math.ceil(nHeight/2); y++) {
    for (int x = -(int)Math.floor(nWidth/2); x < (int)Math.ceil(nWidth/2); x++) {
      if (world.arTiles[location.nY+y][location.nX+x].type==Type.Grass) {
        fill(0, 255, 0);
      } else if (world.arTiles[location.nY+y][location.nX+x].type==Type.Player) {
        fill(255, 0, 0);
      }
      rect(nCellSize*(x+(int)Math.floor(nWidth/2)), nCellSize*(y+(int)Math.floor(nHeight/2)), nCellSize, nCellSize);
    }
  }
}

void keyPressed() {
  if (key=='w') {
    location = moveToLocation(location, new Location(location.nX, location.nY-1));
  } else if (key=='a') {
    location = moveToLocation(location, new Location(location.nX-1, location.nY));
  } else if (key=='s') {
    location = moveToLocation(location, new Location(location.nX, location.nY+1));
  } else if (key=='d') {
    location = moveToLocation(location, new Location(location.nX+1, location.nY));
  }
}

Location moveToLocation(Location oldLocation, Location newLocation) {
  //if(!object at location) {
  world.arTiles[oldLocation.nY][oldLocation.nX].type = Type.Player;
  println(newLocation.nX + " " + newLocation.nY + " " + world.arTiles[newLocation.nY][newLocation.nX] + " " + startLocation.nX + " " + startLocation.nY);
  world.arTiles[newLocation.nY][newLocation.nX].type = Type.Grass;
  if (world.arChunks[1][1].TL.nX>newLocation.nX||world.arChunks[1][1].TL.nY>newLocation.nY||world.arChunks[1][1].BR.nX<newLocation.nX||world.arChunks[1][1].BR.nY<newLocation.nY) {
    world.generateChunks(startLocation, location);
  }
  return newLocation;
  //} else { return oldLocation; }
}

class World {
  int nChunkSize;
  Chunk arChunks[][] = new Chunk[3][3];
  Tile arTiles[][] = new Tile[10000][10000];
  World(int nChunkSize, Location start) {
    this.nChunkSize = nChunkSize;
    generateChunks(start, start);
  }

  void generateChunks(Location start, Location current) {
    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        //arChunks[y][x] = new Chunk(new Location(current.nX-((current.nX-start.nX)%nChunkSize) + x*nChunkSize, current.nY-((current.nY-start.nY)%nChunkSize) + y*nChunkSize), nChunkSize);
        for (int i = arChunks[y][x].TL.nY; i < arChunks[y][x].TL.nY+nChunkSize; i++) {
          for (int j = arChunks[y][x].TL.nY; j < arChunks[y][x].TL.nY+nChunkSize; j++) {
            arTiles[i][j] = new Tile(Type.Grass);
          }
        }
      }
    }
  }
}

class Chunk {
  int nChunkSize;
  Location TL, BR;//TopLeft, BottomRight
  double[][] ardHeightMap;
  int[][] arnTiles;
  //color chunkColor = color((int)random(0, 255), (int)random(0, 255), (int)random(0, 255));
  Chunk(int nX, int nY, int[][] arnTiles, int nChunkSize) {
    this.nChunkSize = nChunkSize;
    this.arnTiles = arnTiles;
    this.ardHeightMap = NoiseMap.getMap(nChunkSize, nChunkSize, nX, nY);
  }

  void render(float fX, float fY, Player player) {
    TL = getMapArrayIndices(new Location(player.position.nX - (width/2), player.position.nY - (height/2)));
    BR = getMapArrayIndices(new Location(player.position.nX + (width/2) + player.nWidth * 2, player.position.nY + (height/2) + player.nHeight));
    if (TL.nX < 0)
      TL.nX = 0;
    if (TL.nY < 0)
      TL.nY = 0;
    if (BR.nX < 0)
      BR.nX = 0;
    if (BR.nY < 0)
      BR.nY = 0;
      
    for(int x = (int) TL.nX; x < (int) BR.nX; x++){
      for(int y = (int) TL.nY; y < (int) BR.nY; y++){
        int nTileType = arnTiles[x][y];
        //int nObjectType = arnObjects[x][y];
      }
    }
  }

  Location getMapArrayIndices(Location position) {
    int nX = (int) (position.nY / Tile.HEIGHT);
    int nY = (int) (position.nX / Tile.WIDTH);
    return new Location(nX, nY);
  }
}

class Player {
  Location position;
  int nWidth, nHeight;
  Player(Location position, int nWidth, int nHeight) {
    this.position = position;
    this.nWidth = nWidth;
    this.nHeight = nHeight;
  }
}

static class Tile {
  Type type;
  public static int TILE_HEIGHT = 64;
  public static int TILE_WIDTH = 64;
  Tile(Type type) {
    this.type = type;
  }
}

enum Type {
  Grass, Tree, Rock, Water, Player;
}

class Location {
  int nX, nY;
  Location(int nX, int nY) {
    this.nX = nX;
    this.nY = nY;
  }
}