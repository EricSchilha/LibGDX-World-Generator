package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Sprite {
    private double dX, dY;
    private int nMinDivisor = 4096;
    public static Texture txPlayer = new Texture(Gdx.files.internal("Dude1.png"));

    public Player() {
        super(txPlayer);
        setSize(Tile.TILE_SIZE, -Tile.TILE_SIZE);
        this.dX = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
        this.dY = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
    }

    public void draw(SpriteBatch batch) {
        //System.out.println(this.dX + "\t" + this.dY); //Use this to track player movements
        int nMiddleX = Tile.TILE_SIZE * 8;
        int nMiddleY = Tile.TILE_SIZE * 8;
        setPosition(nMiddleX, nMiddleY + Tile.TILE_SIZE);
        super.draw(batch);
    }

    public double getPlayerX() {
        return dX;
    }

    public double getPlayerY() {
        return dY;
    }

    public void setX(double dX) {
        this.dX = dX;
    }

    public void setY(double dY) {
        this.dY = dY;
    }
}
