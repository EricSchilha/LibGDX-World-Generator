package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public class Tile extends Sprite {
    public static int TILE_SIZE = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE / 3; //In pixels
    public static HashMap<TileType, Texture> txmTile = new HashMap<TileType, Texture>() {{
        put(TileType.Water, new Texture(Gdx.files.internal("WaterTile.png")));
        put(TileType.Grass, new Texture(Gdx.files.internal("GrassTile.png")));
        put(TileType.Mountain, new Texture(Gdx.files.internal("MountainTile.png")));
    }};

    TileType tileType;

    public Tile(TileType tileType) {
        super(txmTile.get(tileType), TILE_SIZE, TILE_SIZE);
        this.tileType = tileType;
    }

    public void draw(Batch batch, float fX, float fY) {
        setPosition(fX, fY);
        super.draw(batch);
    }
}
