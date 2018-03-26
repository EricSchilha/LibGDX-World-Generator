package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Sprite {
    private int nX, nY;
    private int nMinDivisor = 4096;
    public static Texture txPlayer = new Texture(Gdx.files.internal("Dude1.png"));

    public Player() {
        super(txPlayer);
        setSize(Tile.TILE_SIZE, -Tile.TILE_SIZE);
        this.nX = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
        this.nY = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
    }

    public void draw(SpriteBatch batch, int nX, int nY) {
        int nMiddleX = Tile.TILE_SIZE * 8;
        int nMiddleY = Tile.TILE_SIZE * 8;
        setPosition(nMiddleX, nMiddleY + Tile.TILE_SIZE);
        super.draw(batch);
    }

    public int getPlayerX() {
        return nX;
    }

    public int getPlayerY() {
        return nY;
    }

    public void setX(int nX) {
        this.nX = nX;
    }

    public void setY(int nY) {
        this.nY = nY;
    }
}
