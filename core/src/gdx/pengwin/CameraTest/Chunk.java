package gdx.pengwin.CameraTest;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Chunk {
    public static final int CHUNK_SIZE = 32;

    Random randNPO;
    public SprNPO[][] arsprNPO; //Stores the other things on the map (trees, rocks, etc.)
    public SprTile[][] arsprTiles;
    public static Noise noise = new Noise();
    Vector2 vTopLeft;
    Vector2 vOrigin;//The x and y of the chunk in the 2D array of Chunks within the map.  (0, 0) is the top left chunk.

//    public Chunk(Vector2 vTopLeft) {
//        this.arsprTiles = new SprTile[CHUNK_SIZE][CHUNK_SIZE];
//        this.arsprNPO = new SprNPO[CHUNK_SIZE][CHUNK_SIZE];
//        this.vTopLeft = vTopLeft;
//        randNPO = new Random((int) noise.noise((int) this.vTopLeft.x, (int) this.vTopLeft.y) * 10000);
//        generateMap();
//    }

    public Chunk(Vector2 vTopLeft, Vector2 vOrigin) {
        this.arsprTiles = new SprTile[CHUNK_SIZE][CHUNK_SIZE];
        this.arsprNPO = new SprNPO[CHUNK_SIZE][CHUNK_SIZE];
        this.vTopLeft = vTopLeft;
        this.vOrigin = vOrigin;
        randNPO = new Random((int) noise.noise((int) this.vTopLeft.x, (int) this.vTopLeft.y) * 10000);
        generateMap();
    }

    public void generateMap() {
        TileType newTileType;
        float fWidthOffset = 0;
        float fHeightOffset = 0;
        float fPersistence = (float) 0.05;
        for (int y = 0; y < arsprTiles.length; y++) {
            for (int x = 0; x < arsprTiles[y].length; x++) {
                double dNoiseVal = noise.noise(vTopLeft.x * fPersistence + fWidthOffset, vTopLeft.y * fPersistence + fHeightOffset);
                fWidthOffset += fPersistence;
                newTileType = (dNoiseVal < 0.35) ? TileType.Water : (dNoiseVal < 0.65 ? TileType.Grass : TileType.Mountain);
                arsprTiles[y][x] = new SprTile(newTileType, vOrigin.x + x * SprTile.TILE_SIZE, vOrigin.y + y * SprTile.TILE_SIZE);
                if (x % 2 == 0 && y % 2 == 0) {
                    if (arsprTiles[y][x].tileType == TileType.Grass) {
                        try {
                            int nTreeX = randNPO.nextInt(3) - 1;
                            int nTreeY = randNPO.nextInt(3) - 1;
                            if (arsprTiles[y + nTreeY][x + nTreeX].tileType == TileType.Grass) {
                                arsprNPO[y + nTreeY][x + nTreeX] = new SprNPO(NPOType.Tree, vOrigin.x + x * SprNPO.OBJECT_WIDTH, vOrigin.y + y * SprNPO.OBJECT_HEIGHT);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
            fWidthOffset = 0;
            fHeightOffset += fPersistence;
        }
    }


//    public Vector2 setOrigin(Vector2 vOrigin) {
//        return new Vector2(vOrigin.x * SprTile.TILE_SIZE * CHUNK_SIZE, vOrigin.y * SprTile.TILE_SIZE * CHUNK_SIZE);
//    }

    public void draw(SpriteBatch batch, SprPlayer player) {
        for (int y = 0; y < arsprTiles.length; y++) {
            for (int x = 0; x < arsprTiles[0].length; x++) {
                if (Math.abs(player.getX() - Chunk.CHUNK_SIZE * SprTile.TILE_SIZE / 2) >= Math.abs(player.getX() - arsprTiles[y][x].getX())
                        && Math.abs(player.getY() - Chunk.CHUNK_SIZE * SprTile.TILE_SIZE / 2) >= Math.abs(player.getY() - arsprTiles[y][x].getY())) {
                    arsprTiles[y][x].draw(batch);//, arsprTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getLocation().x + x) + player.nPixelX, arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y) + player.nPixelY);
                    if (arsprNPO[y][x] != null) {
                        arsprNPO[y][x].draw(batch);//, arsprNPO[y][x].OBJECT_WIDTH * (vTopLeft.x - player.getLocation().x + x) + player.nPixelX, arsprNPO[y][x].OBJECT_HEIGHT * (vTopLeft.y - player.getLocation().y + y) + player.nPixelY);
                    }
                }
            }
        }
    }

}
