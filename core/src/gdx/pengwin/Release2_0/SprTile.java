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

    public SprTile(TileType tileType, float fX, float fY) {
        super(txmTile.get(tileType), TILE_SIZE, TILE_SIZE);
        setPosition(fX, fY);
        this.tileType = tileType;
    }

    /*public static void resize() { //FIX THIS LATER
        TILE_SIZE = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    }*/
}
