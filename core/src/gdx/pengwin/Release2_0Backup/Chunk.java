package gdx.pengwin.Release2_0Backup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Chunk {
    public static /*final*/ int CHUNK_SIZE = 32;

    Random randNPO;
    public SprNPO[][] arsprNPO; //Stores the other things on the map (trees, rocks, etc.)
    public SprTile[][] arsprTiles;
    public static Noise noise = new Noise();
    Vector2 vTopLeft;

    public Chunk(Vector2 vTopLeft) {
        this.arsprTiles = new SprTile[CHUNK_SIZE][CHUNK_SIZE];
        this.arsprNPO = new SprNPO[CHUNK_SIZE][CHUNK_SIZE];
        this.vTopLeft = vTopLeft;
        randNPO = new Random((int) noise.noise((int) this.vTopLeft.x, (int) this.vTopLeft.y) * 10000);
        generateMap();
    }

    public void generateMap() {
        float fWidthOffset = 0;
        float fHeightOffset = 0;
        float fPersistence = (float) 0.05;
        for (int y = 0; y < arsprTiles.length; y++) {
            for (int x = 0; x < arsprTiles[y].length; x++) {
                arsprTiles[y][x] = new SprTile(TileType.Grass);
                double dNoiseVal = noise.noise(vTopLeft.x * fPersistence + fWidthOffset, vTopLeft.y * fPersistence + fHeightOffset);
                fWidthOffset += fPersistence;
                arsprTiles[y][x] = (dNoiseVal < 0.35) ? new SprTile(TileType.Water) : (dNoiseVal < 0.65 ? new SprTile(TileType.Grass) : new SprTile(TileType.Mountain));
                if (x % 2 == 0 && y % 2 == 0) {
//                    arsprNPO[y][x] = new SprNPO(NPOType.Tree);
                    if (arsprTiles[y][x].tileType == TileType.Grass) {
                        try {
                            int nTreeX = randNPO.nextInt(3) - 1;
                            int nTreeY = randNPO.nextInt(3) - 1;
                            if (arsprTiles[y + nTreeY][x + nTreeX].tileType == TileType.Grass) {
                                arsprNPO[y + nTreeY][x + nTreeX] = new SprNPO(NPOType.Tree);
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

    public void draw(SpriteBatch batch, SprPlayer player) {
        for (int y = 0; y < arsprTiles.length; y++) {
            for (int x = 0; x < arsprTiles[0].length; x++) {
                if (arsprTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getLocation().x + x + 1) > 0 && arsprTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getLocation().x + x) < Gdx.graphics.getWidth() && arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y + 1) > 0 && arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y) < Gdx.graphics.getHeight()) {
                    arsprTiles[y][x].draw(batch, (arsprTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getLocation().x + x)), (arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y)));
                    if (arsprNPO[y][x] != null) {
                        arsprNPO[y][x].draw(batch, (arsprNPO[y][x].OBJECT_WIDTH * (vTopLeft.x - player.getLocation().x + x)), (arsprNPO[y][x].OBJECT_HEIGHT * (vTopLeft.y - player.getLocation().y + y)));
                    }
                }
            }
        }
    }

}
