package gdx.pengwin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tile extends Sprite {
    public static int TILE_WIDTH = 32; //In pixels
    public static int TILE_HEIGHT = 32;

    TileType tileType;

    public Tile(int nX, int nY, Texture txTile, TileType tileType) {
        super(txTile, nX, nY, TILE_WIDTH, TILE_HEIGHT);
        this.tileType = tileType;
    }

    public void draw(Batch batch, float fX, float fY) {
        setPosition(fX, fY);
        super.draw(batch);
    }
}
