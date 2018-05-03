package gdx.pengwin.Release2_0;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Map {
    public Chunk[][] arChunks = new Chunk[3][3];
    public int nSeed;
    public SprPlayer sprPlayer = new SprPlayer(this);

    public Map(int nSeed) {
        this.nSeed = nSeed;
        this.init();
    }

    private void init() {
        for (int y = 0; y < arChunks.length; y++)
            for (int x = 0; x < arChunks[y].length; x++)
                arChunks[y][x] = addChunk(new Vector2(Chunk.CHUNK_SIZE * (x - ((arChunks[y].length - 1) / 2)), Chunk.CHUNK_SIZE * (y - ((arChunks.length - 1) / 2))), new Vector2(x, y));
    }


    public void update() {
        Vector2 vPlayerChunk = getChunkIndices(sprPlayer.getLocation());
        if (arChunks[arChunks[arChunks.length / 2].length / 2][arChunks.length / 2].vTopLeft.x != vPlayerChunk.x || arChunks[arChunks[arChunks.length / 2].length / 2][arChunks.length / 2].vTopLeft.y != vPlayerChunk.y) {
            int nPlayerChunkX = (int) vPlayerChunk.x;
            int nPlayerChunkY = (int) vPlayerChunk.y;
            for (int y = 0; y < arChunks.length; y++)
                for (int x = 0; x < arChunks[y].length; x++)
                    arChunks[y][x] = addChunk(new Vector2(nPlayerChunkX - Chunk.CHUNK_SIZE * (x - ((arChunks[y].length - 1) / 2)), nPlayerChunkY - Chunk.CHUNK_SIZE * (y - ((arChunks.length - 1) / 2))), new Vector2(x, y));
        }

        for (int y = 0; y < arChunks.length; y++) {
            for (int x = 0; x < arChunks[y].length; x++) {
                arChunks[y][x].setOrigin(new Vector2(x, y));
            }
        }
        sprPlayer.move(this);
    }

    public void draw(SpriteBatch batch) {
        for (Chunk arChunk[] : arChunks) {
            for (Chunk chunk : arChunk) {
                chunk.draw(batch, sprPlayer);
            }
        }
        sprPlayer.draw(batch);
    }

    public Chunk addChunk(Vector2 vTopLeft, Vector2 vOrigin) {
        vTopLeft = getChunkIndices(vTopLeft);
        for (int y = 0; y < arChunks.length; y++) {
            for (int x = 0; x < arChunks[y].length; x++) {
                Chunk chunk = arChunks[y][x];
                if (chunk != null)
                    if (chunk.vTopLeft.x == vTopLeft.x && chunk.vTopLeft.y == vTopLeft.y)
                        return chunk;
            }
        }
        return new Chunk(vTopLeft, vOrigin);
    }

    public ArrayList<Chunk> addChunks(ArrayList<Vector2> alvTopLefts) {
        ArrayList<Chunk> alChunks = new ArrayList<Chunk>();
        VectorLoop: for (Vector2 vTopLeft : alvTopLefts) {
            for (int i = 0; i < alChunks.size(); i++) {
                vTopLeft = getChunkIndices(vTopLeft);
                if (alChunks.get(i).vTopLeft.x == vTopLeft.x && alChunks.get(i).vTopLeft.y == vTopLeft.y) continue VectorLoop;
            }
            alChunks.add(addChunk(vTopLeft, new Vector2(0, 0)));
        }
        return alChunks;
    }

    public Vector2 getChunkIndices(Vector2 vPos) {
        int nX = (int) (vPos.x - (vPos.x % Chunk.CHUNK_SIZE));
        int nY = (int) (vPos.y - (vPos.y % Chunk.CHUNK_SIZE));
        nX = vPos.x < 0 ? nX - Chunk.CHUNK_SIZE : nX; //Probably don't need these
        nY = vPos.y < 0 ? nY - Chunk.CHUNK_SIZE : nY;
        return new Vector2(nX, nY);
    }
}
