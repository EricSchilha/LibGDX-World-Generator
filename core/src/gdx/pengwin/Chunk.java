package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Chunk {
    public static /*final*/ int CHUNK_WIDTH = 32;
    public static /*final*/ int CHUNK_HEIGHT = 32;

    public Object[][] arobjObjects; //Stores the other things on the map (trees, rocks, etc.)
    public Tile[][] artilTiles;
    public static Noise noise = new Noise();
    Vector2 vTopLeft;
    Vector2 vBottomRight;
    public int nX, nY;

    public Chunk(int nX, int nY) {
        this.artilTiles = new Tile[CHUNK_HEIGHT][CHUNK_WIDTH];
        this.arobjObjects = new Object[CHUNK_HEIGHT][CHUNK_WIDTH];
        this.nX = nX;
        this.nY = nY;
        //vTopLeft = this.getMapArrayIndices(new Vector2(nX - (Gdx.graphics.getWidth() / 2), nY - (Gdx.graphics.getHeight() / 2)));
        //vBottomRight = this.getMapArrayIndices(new Vector2(nX + (Gdx.graphics.getWidth() / 2) + Tile.TILE_WIDTH * 2, nY + (Gdx.graphics.getHeight() / 2) + Tile.TILE_WIDTH * 2));
        generateTileMap();
        //generateObjectMap();
    }

    public void generateTileMap() {
        float fHeightOffset = 0;
        float fWidthOffset = 0;
        for (int y = 0; y < artilTiles.length; y++) {
            fWidthOffset = 0;
            for (int x = 0; x < artilTiles[y].length; x++) {
                double dNoiseVal = noise.noise(fWidthOffset, fHeightOffset);
                fWidthOffset += 0.1;
                artilTiles[y][x] = (dNoiseVal < 0.35) ? new Tile(x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT, new Texture(Gdx.files.internal("WaterTile.png")), TileType.Water) :
                        (dNoiseVal < 0.65 ? new Tile(x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT, new Texture(Gdx.files.internal("GrassTile.png")), TileType.Grass) :
                                new Tile(x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT, new Texture(Gdx.files.internal("MountainTile.png")), TileType.Mountain));
            }
            fHeightOffset += 0.1;
        }
    }

    public void draw(int nX, int nY, SpriteBatch batch) {
        for (int y = 0; y < artilTiles.length; y++) {
            for (int x = 0; x < artilTiles[y].length; x++) {
                artilTiles[y][x].draw(batch, (float) (nX + (x * artilTiles[y][x].TILE_WIDTH)), (float) (nY + (y * artilTiles[y][x].TILE_HEIGHT)));
            }
        }
//        Vector2 vTopLeft = this.getMapArrayIndices(new Vector2(nX - (Gdx.graphics.getWidth() / 2), nY - (Gdx.graphics.getHeight() / 2)));
//        Vector2 vBottomRight = this.getMapArrayIndices(new Vector2(nX + (Gdx.graphics.getWidth() / 2) + Tile.TILE_WIDTH * 2, nY + (Gdx.graphics.getHeight() / 2) + Tile.TILE_HEIGHT * 2));
//        System.out.println(vTopLeft.x + "\t" + vTopLeft.y);
//        System.out.println(vTopLeft.x + "\t" + vTopLeft.y);
//        if (vTopLeft.x < 0)
//            vTopLeft.x = 0;
//        if (vTopLeft.y < 0)
//            vTopLeft.y = 0;
//        if (vBottomRight.x < 0)
//            vBottomRight.x = 0;
//        if (vBottomRight.y < 0)
//            vBottomRight.y = 0;
//
//        for (int y = (int) vTopLeft.y; y < (int) vBottomRight.y / (float) (CHUNK_WIDTH / Tile.TILE_WIDTH); y++) {
//            for (int x = (int) vTopLeft.x; x < (int) vBottomRight.x / (float) (CHUNK_HEIGHT / Tile.TILE_HEIGHT); x++) {
//                TileType tileType = artilTiles[y][x].tileType;
//                //ObjectType objectType = arobjObjects[y][x].objectType;
//
//                switch (tileType) { // Redundancy only temporary
//                    case Grass:
//                        artilTiles[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
//                        break;
//                    case Water:
//                        artilTiles[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
//                        break;
//                    case Mountain:
//                        artilTiles[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
//                        break;
//                }
//
//                /*switch (objectType) {
//                    case Tree:
//                        arobjObjects[y][x].draw(batch, (float) (nX + (x * Tile.TILE_WIDTH)), (float) (nY + (y * Tile.TILE_HEIGHT)));
//                        break;
//                }*/
//            }
//        }
    }

    public Vector2 getMapArrayIndices(Vector2 vPos) {
        int nX = (int) (vPos.y / Tile.TILE_HEIGHT);
        int nY = (int) (vPos.x / Tile.TILE_WIDTH);

        return new Vector2(nX, nY);
    }
}
