package gdx.pengwin.Release2_0Backup;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Map {
    public Chunk[][] arChunks = new Chunk[5][5];
    public int nSeed;
    public SprPlayer sprPlayer = new SprPlayer();

    public Map(int nSeed) {
        this.nSeed = nSeed;
        this.init();
    }

    private void init() {
        for (int y = 0; y < arChunks.length; y++)
            for (int x = 0; x < arChunks[y].length; x++)
                arChunks[y][x] = new Chunk(Chunk.CHUNK_SIZE * (x - ((arChunks[y].length - 1) / 2)), Chunk.CHUNK_SIZE * (y - ((arChunks.length - 1) / 2)));
    }


    public void update() {
        Vector2 vPlayerChunk = getChunkIndices(new Vector2((float) sprPlayer.getPlayerX(), (float) sprPlayer.getPlayerY()));
        if (arChunks[arChunks[arChunks.length / 2].length / 2][arChunks.length / 2].vTopLeft.x != vPlayerChunk.x || arChunks[arChunks[arChunks.length / 2].length / 2][arChunks.length / 2].vTopLeft.y != vPlayerChunk.y) {
            int nPlayerChunkX = (int) vPlayerChunk.x;
            int nPlayerChunkY = (int) vPlayerChunk.y;
            for (int y = 0; y < arChunks.length; y++)
                for (int x = 0; x < arChunks[y].length; x++)
                    arChunks[y][x] = new Chunk(nPlayerChunkX - Chunk.CHUNK_SIZE * (x - ((arChunks[y].length - 1) / 2)), nPlayerChunkY - Chunk.CHUNK_SIZE * (y - ((arChunks.length - 1) / 2)));
        }
        sprPlayer.move(this);
    }

    public void draw(SpriteBatch batch, ShapeRenderer sr) {
        for (Chunk arChunk[] : arChunks) {
            for (Chunk chunk : arChunk) {
                chunk.draw(batch, sprPlayer);
            }
        }
        sprPlayer.draw(batch, sr);
    }

    public Vector2 getChunkIndices(Vector2 vPos) {
        int nX = (int) (vPos.x - (vPos.x % Chunk.CHUNK_SIZE));
        int nY = (int) (vPos.y - (vPos.y % Chunk.CHUNK_SIZE));
        nX = vPos.x < 0 ? nX - Chunk.CHUNK_SIZE : nX; //Probably don't need these
        nY = vPos.y < 0 ? nY - Chunk.CHUNK_SIZE : nY;
        return new Vector2(nX, nY);
    }
}
