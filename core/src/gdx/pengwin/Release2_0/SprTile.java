package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class SprTile extends Sprite {
    public static int TILE_SIZE = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    public static HashMap<TileType, Texture> txmTile = new HashMap<TileType, Texture>() {{
        put(TileType.Water, new Texture(Gdx.files.internal("WaterTile.png")));
        put(TileType.Grass, new Texture(Gdx.files.internal("GrassTile.png")));
        put(TileType.Mountain, new Texture(Gdx.files.internal("MountainTile.png")));
    }};

    TileType tileType;

    public SprTile(TileType tileType) {
        super(txmTile.get(tileType), TILE_SIZE, TILE_SIZE);
        this.tileType = tileType;
    }

    public void draw(SpriteBatch batch, float fX, float fY) {
        setPosition(fX, fY);
        super.draw(batch);
    }

    public static void resize() { //FIX THIS LATER
        TILE_SIZE = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    }
}
