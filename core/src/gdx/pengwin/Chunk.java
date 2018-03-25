package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Chunk {
    public static /*final*/ int CHUNK_SIZE = 32;

    public Object[][] arobjObjects; //Stores the other things on the map (trees, rocks, etc.)
    public Tile[][] artilTiles;
    public static Noise noise = new Noise();
    Vector2 vTopLeft;

    public Chunk(int nX, int nY) {
        this.artilTiles = new Tile[CHUNK_SIZE][CHUNK_SIZE];
        this.arobjObjects = new Object[CHUNK_SIZE][CHUNK_SIZE];
        vTopLeft = new Vector2(nX, nY);
        generateTileMap();
        //generateObjectMap();
    }

    public void generateTileMap() {
        float fWidthOffset = 0;
        float fHeightOffset = 0;
        float fPersistence = (float) 0.05;
        for (int y = 0; y < artilTiles.length; y++) {
            for (int x = 0; x < artilTiles[y].length; x++) {
                double dNoiseVal = noise.noise(vTopLeft.x * fPersistence + fWidthOffset, vTopLeft.y * fPersistence + fHeightOffset);
                fWidthOffset += fPersistence;
                artilTiles[y][x] = (dNoiseVal < 0.35) ? new Tile(TileType.Water) : (dNoiseVal < 0.65 ? new Tile(TileType.Grass) : new Tile(TileType.Mountain));
            }
            fWidthOffset = 0;
            fHeightOffset += fPersistence;
        }
    }

    public void draw(SpriteBatch batch, Player player) {
        for (int y = 0; y < artilTiles.length; y++) {
            for (int x = 0; x < artilTiles[y].length; x++) {
                if (artilTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getX() + x + 1) > 0 && artilTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getX() + x) < Gdx.graphics.getWidth() && artilTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getY() + y + 1) > 0 && artilTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getY() + y) < Gdx.graphics.getHeight()) {
                    artilTiles[y][x].draw(batch, artilTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getX() + x), artilTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getY() + y));
                }
            }
        }
    }

}
