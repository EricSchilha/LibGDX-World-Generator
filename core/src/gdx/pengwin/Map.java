package gdx.pengwin;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Map {
    public ArrayList<Chunk> alChunks = new ArrayList<Chunk>();
    public int nSeed;
    public int nX = 0, nY = 0;

    public Map(int nSeed) {
        this.nSeed = nSeed;
        this.init();
    }

    private void init() {
        alChunks.add(new Chunk());
    }

    public void draw(SpriteBatch batch) {
        for (Chunk chunk : alChunks)
            chunk.draw(nX, nY, batch);
    }
}
