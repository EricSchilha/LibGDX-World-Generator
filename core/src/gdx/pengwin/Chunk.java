package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Chunk {
    public static final int CHUNK_WIDTH = 32;
    public static final int CHUNK_HEIGHT = 32;

    public double[][] ardHeightMap; //Stores the perlin noise
    public Object[][] arobjObjects; //Stores the other things on the map (trees, rocks, etc.)
    public Tile[][] artilTiles;
    public static Noise noise = new Noise();
    Vector2 vTopLeft;
    Vector2 vBottomRight;
    public int nX, nY;

    public Chunk(int nX, int nY) {
        this.artilTiles = new Tile[CHUNK_HEIGHT][CHUNK_WIDTH];
        this.arobjObjects = new Object[CHUNK_HEIGHT][CHUNK_WIDTH];
        this.ardHeightMap = new double[CHUNK_HEIGHT][CHUNK_WIDTH];
        this.nX = nX;
        this.nY = nY;
        vTopLeft = this.getMapArrayIndices(new Vector2(nX - (Gdx.graphics.getWidth() / 2), nY - (Gdx.graphics.getHeight() / 2)));
        vBottomRight = this.getMapArrayIndices(new Vector2(nX + (Gdx.graphics.getWidth() / 2) + Tile.TILE_WIDTH * 2, nY + (Gdx.graphics.getHeight() / 2) + Tile.TILE_WIDTH * 2));
        createHeightMap();
        createTileMap();
        //createObjects();
    }

    public void createHeightMap() {
        for (int y = 0; y < ardHeightMap.length; y++) {
            for (int x = 0; x < ardHeightMap[y].length; x++) {
                this.ardHeightMap[y][x] = noise.noise(vTopLeft.y + y, vTopLeft.x + x);
            }
        }
    }

    public void createTileMap() {
        for (int y = 0; y < ardHeightMap.length; y++) {
            for (int x = 0; x < ardHeightMap[y].length; x++) {
                int nX = x * Tile.TILE_WIDTH;
                int nY = y * Tile.TILE_HEIGHT;
                artilTiles[y][x] = (ardHeightMap[y][x] < 0.3) ? new Tile(nX, nY, new Texture(Gdx.files.internal("WaterTile.png")), TileType.Water) :
                        (ardHeightMap[y][x] < 0.8 ? new Tile(nX, nY, new Texture(Gdx.files.internal("GrassTile.png")), TileType.Grass) :
                                new Tile(nX, nY, new Texture(Gdx.files.internal("MountainTile.png")), TileType.Mountain));
            }
        }
    }

    public void draw(int nX, int nY, SpriteBatch batch) {
        Vector2 vTopLeft = this.getMapArrayIndices(new Vector2(nX - (Gdx.graphics.getWidth() / 2), nY - (Gdx.graphics.getHeight() / 2)));
        Vector2 vBottomRight = this.getMapArrayIndices(new Vector2(nX + (Gdx.graphics.getWidth() / 2) + Tile.TILE_WIDTH * 2, nY + (Gdx.graphics.getHeight() / 2) + Tile.TILE_HEIGHT * 2));
        if (vTopLeft.x < 0)
            vTopLeft.x = 0;
        if (vTopLeft.y < 0)
            vTopLeft.y = 0;
        if (vBottomRight.x < 0)
            vBottomRight.x = 0;
        if (vBottomRight.y < 0)
            vBottomRight.y = 0;

        for (int y = (int) vTopLeft.y; y < (int) vBottomRight.y / (float) (CHUNK_WIDTH / Tile.TILE_WIDTH); y++) {
            for (int x = (int) vTopLeft.x; x < (int) vBottomRight.x / (float) (CHUNK_HEIGHT / Tile.TILE_HEIGHT); x++) {
                TileType tileType = artilTiles[y][x].tileType;
                //ObjectType objectType = arobjObjects[y][x].objectType;

                switch (tileType) { // Redundancy only temporary... hopefully
                    case Grass:
                        artilTiles[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
                        break;
                    case Water:
                        artilTiles[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
                        break;
                    case Mountain:
                        artilTiles[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
                        break;
                }

                /*switch (objectType) {
                    case Tree:
                        arobjObjects[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
                        break;
                }*/
            }
        }
    }

    public Vector2 getMapArrayIndices(Vector2 vPos) {
        int nX = (int) (vPos.y / Tile.TILE_HEIGHT);
        int nY = (int) (vPos.x / Tile.TILE_WIDTH);

        return new Vector2(nX, nY);
    }
}
