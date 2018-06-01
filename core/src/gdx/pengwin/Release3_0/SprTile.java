package gdx.pengwin.Release3_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class SprTile extends Sprite {
    public static int TILE_SIZE = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / (Chunk.CHUNK_SIZE)/* - Chunk.CHUNK_SIZE / 2)*/; //In pixels
    /*public static HashMap<TileType, Texture> txmTile = new HashMap<TileType, Texture>() {{
        put(TileType.Water, new Texture(Gdx.files.internal("WaterTile.png")));
        put(TileType.Grass, new Texture(Gdx.files.internal("GrassTile1.png"));
        put(TileType.Mountain, new Texture(Gdx.files.internal("MountainTile.png")));
    }};*/
    TileType tileType;
    /*static Texture[] artxGrassTile = {
            new Texture(Gdx.files.internal("GrassTile1.png")),
            new Texture(Gdx.files.internal("GrassTile2.png")),
            new Texture(Gdx.files.internal("GrassTile3.png")),
    };*/
    static Texture txGrassTile = new Texture(Gdx.files.internal("GrassTile6.png"));
    static Texture txWaterTile = new Texture(Gdx.files.internal("WaterTile.png"));
    static Texture txMountainTile = new Texture(Gdx.files.internal("MountainTile.png"));

    public SprTile(TileType tileType) {
        super(getTexture(tileType));
        setSize(TILE_SIZE, -TILE_SIZE);
        this.tileType = tileType;
    }

    public void draw(SpriteBatch batch, float fX, float fY) {
        setPosition(fX, fY + TILE_SIZE);
        super.draw(batch);
    }

    public static Texture getTexture(TileType tileType) {
        Texture txTile = null;
        switch (tileType) {
            case Grass:
                txTile = txGrassTile;
                break;
            case Water:
                txTile = txWaterTile;
                break;
            case Mountain:
                txTile = txMountainTile;
                break;
        }
        return txTile;
    }

    public static void resize() { //FIX THIS LATER
        TILE_SIZE = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    }
}
