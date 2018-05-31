package gdx.pengwin.Release3_0;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    public Noise noise;
    public Chunk[][] arChunks = new Chunk[3][3];
    public int nSeed;
    public SprPlayer sprPlayer;
    private int nMinDivisor = 4096;
    private Vector2 vSpawnLocation;
    private Random random;

    public Map(int nSeed) {
        this.nSeed = nSeed;
        this.noise = new Noise(this.nSeed);
        this.random = new Random(this.nSeed);
        this.init();
    }

    private void init() {
        vSpawnLocation = new Vector2(
                (int) (random.nextFloat() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2) + (float)0.0001,
                (int) (random.nextFloat() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2) + (float)0.0001);    //getChunkIndices not needed, already does it in addChunk()
        Vector2 vSpawnLocation2 = vSpawnLocation.cpy();
        for (int y = 0; y < arChunks.length; y++)
            for (int x = 0; x < arChunks[y].length; x++) {
                vSpawnLocation2.set(vSpawnLocation.x - Chunk.CHUNK_SIZE * (1 - x), vSpawnLocation.y + Chunk.CHUNK_SIZE * (y - ((arChunks.length - 1) / 2)));
                arChunks[y][x] = addChunk(vSpawnLocation2);
            }
        this.sprPlayer = new SprPlayer(vSpawnLocation);
    }


    public void update() {
        //*
        Vector2 vPlayerChunk = getChunkIndices(sprPlayer.getLocation());
        if (arChunks[arChunks[arChunks.length / 2].length / 2][arChunks.length / 2].vTopLeft.x != vPlayerChunk.x || arChunks[arChunks[arChunks.length / 2].length / 2][arChunks.length / 2].vTopLeft.y != vPlayerChunk.y) {
            int nPlayerChunkX = (int) vPlayerChunk.x;
            int nPlayerChunkY = (int) vPlayerChunk.y;
            for (int y = 0; y < arChunks.length; y++)
                for (int x = 0; x < arChunks[y].length; x++)
                    arChunks[y][x] = addChunk(new Vector2(nPlayerChunkX - Chunk.CHUNK_SIZE * (x - ((arChunks[y].length - 1) / 2)), nPlayerChunkY - Chunk.CHUNK_SIZE * (y - ((arChunks.length - 1) / 2))));
        }
        /*///*/
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

    public Chunk addChunk(Vector2 vTopLeft) {
        vTopLeft = getChunkIndices(vTopLeft);
        for (Chunk arChunk[] : arChunks) {
            for (Chunk chunk : arChunk) {
                if (chunk != null)
                    if (chunk.vTopLeft.x == vTopLeft.x && chunk.vTopLeft.y == vTopLeft.y)
                        return chunk;
            }
        }
        return new Chunk(vTopLeft, this);
    }

    /*public ArrayList<Chunk> addChunks(ArrayList<Vector2> alvTopLefts) {
        ArrayList<Chunk> alChunks = new ArrayList<Chunk>();
        VectorLoop: for (Vector2 vTopLeft : alvTopLefts) {
            for (int i = 0; i < alChunks.size(); i++) {
                vTopLeft = getChunkIndices(vTopLeft);
                if (alChunks.get(i).vTopLeft.x == vTopLeft.x && alChunks.get(i).vTopLeft.y == vTopLeft.y) continue VectorLoop;
            }
            alChunks.add(addChunk(vTopLeft));
        }
        return alChunks;
    }*/

    public Vector2 getChunkIndices(Vector2 vPos) {
        int nX = (int) (vPos.x - (vPos.x % Chunk.CHUNK_SIZE));
        int nY = (int) (vPos.y - (vPos.y % Chunk.CHUNK_SIZE));
        nX = vPos.x < 0 ? nX - Chunk.CHUNK_SIZE : nX; //Probably don't need these
        nY = vPos.y < 0 ? nY - Chunk.CHUNK_SIZE : nY;
        return new Vector2(nX, nY);
    }
}