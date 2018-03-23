package gdx.pengwin;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Map {
    public ArrayList<Chunk> alChunks = new ArrayList<Chunk>();
    //    public Chunk[][] arChunks = new Chunk[2][2];
    public int nSeed;

    public Map(int nSeed) {
        this.nSeed = nSeed;
        this.init();
    }

    private void init() {
        alChunks.add(new Chunk(0, 0));

        /*alChunks.add(new Chunk(nX, nY));
        for (int y = 0; y < arChunks.length; y++) {
            for (int x = 0; x < arChunks[y].length; x++) {
                x--;
                y--;
                arChunks[y + 1][x + 1] = new Chunk(nX + (x * Chunk.CHUNK_WIDTH * Tile.TILE_WIDTH), nY + (y * Chunk.CHUNK_WIDTH * Tile.TILE_WIDTH));
                x++;
                y++;
            }
        }*/
    }

    public void draw(SpriteBatch batch) {

        for (Chunk chunk : alChunks)
            chunk.draw(chunk.nX, chunk.nY, batch);
        /*for (int y = 0; y < arChunks.length; y++) {
            for (int x = 0; x < arChunks[y].length; x++) {
                Chunk chunk = arChunks[y][x];
                chunk.draw(chunk.nX, chunk.nY, batch);
            }
        }*/


    }
}
