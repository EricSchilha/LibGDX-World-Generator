package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Chunk {
    public static /*final*/ int CHUNK_SIZE = 32;

    Random randObj;
    public Object[][] arobjObjects; //Stores the other things on the map (trees, rocks, etc.)
    public Tile[][] artilTiles;
    public static Noise noise = new Noise();
    Vector2 vTopLeft;

    public Chunk(int nX, int nY) {
        this.artilTiles = new Tile[CHUNK_SIZE][CHUNK_SIZE];
        this.arobjObjects = new Object[CHUNK_SIZE][CHUNK_SIZE];
        vTopLeft = new Vector2(nX, nY);
        randObj = new Random((int) noise.noise(nX, nY) * 10000);
        generateMap();
    }

    public void generateMap() {
        float fWidthOffset = 0;
        float fHeightOffset = 0;
        float fPersistence = (float) 0.05;
        for (int y = 0; y < artilTiles.length; y++) {
            for (int x = 0; x < artilTiles[y].length; x++) {
                double dNoiseVal = noise.noise(vTopLeft.x * fPersistence + fWidthOffset, vTopLeft.y * fPersistence + fHeightOffset);
                fWidthOffset += fPersistence;
                artilTiles[y][x] = (dNoiseVal < 0.35) ? new Tile(TileType.Water) : (dNoiseVal < 0.65 ? new Tile(TileType.Grass) : new Tile(TileType.Mountain));
                if (x % 2 == 0 && y % 2 == 0) {
                    if (artilTiles[y][x].tileType == TileType.Grass) {
                        try {
                            int nTreeX = randObj.nextInt(3) - 1;
                            int nTreeY = randObj.nextInt(3) - 1;
                            if (artilTiles[y + nTreeY][x + nTreeX].tileType == TileType.Grass) {
                                arobjObjects[y + nTreeY][x + nTreeX] = new Object(ObjectType.Tree);
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

    public void draw(SpriteBatch batch, Player player) {
        for (int y = 0; y < artilTiles.length; y++) {
            for (int x = 0; x < artilTiles[y].length; x++) {
                if (artilTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getPlayerX() + x + 1) > 0 && artilTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getPlayerX() + x) < Gdx.graphics.getWidth() && artilTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getPlayerY() + y + 1) > 0 && artilTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getPlayerY() + y) < Gdx.graphics.getHeight()) {
                    artilTiles[y][x].draw(batch, (float) (artilTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getPlayerX() + x)), (float) (artilTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getPlayerY() + y)));
                    if (arobjObjects[y][x] != null) {
                        arobjObjects[y][x].draw(batch, (float) (arobjObjects[y][x].OBJECT_WIDTH * (vTopLeft.x - player.getPlayerX() + x)), (float) (arobjObjects[y][x].OBJECT_HEIGHT * (vTopLeft.y - player.getPlayerY() + y)));
                    }
                }
            }
        }

    }

}
