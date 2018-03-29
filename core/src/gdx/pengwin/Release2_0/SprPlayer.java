package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SprPlayer extends Sprite {
    private double dX, dY;
    private int nMinDivisor = 4096;
    private double dSpeed = 0.05;
    public static Texture txPlayer = new Texture(Gdx.files.internal("Dude1.png"));

    public SprPlayer() {
        super(txPlayer);
        setSize(SprTile.TILE_SIZE, -SprTile.TILE_SIZE);
        this.dX = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
        this.dY = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
    }

    public void draw(SpriteBatch batch) {
        //System.out.println(this.dX + "\t" + this.dY); //Use this to track player movements
        int nMiddleX = SprTile.TILE_SIZE * 8;
        int nMiddleY = SprTile.TILE_SIZE * 8;
        setPosition(nMiddleX, nMiddleY + SprTile.TILE_SIZE);
        super.draw(batch);
    }

    public boolean canMove(Direction direction, Map map) {
        double dNewX = dX, dNewY = dY;
        switch (direction) {
            case NORTH:
                dNewY -= dSpeed;
                break;
            case SOUTH:
                dNewY += dSpeed;
                break;
            case EAST:
                dNewX += dSpeed;
                break;
            case WEST:
                dNewX -= dSpeed;
                break;
        }
        Vector2 vTopLeftPlayer = map.getChunkIndices(new Vector2((float) dNewX, (float) dNewY));
        Chunk playerChunk = new Chunk((int) vTopLeftPlayer.x, (int) vTopLeftPlayer.y);    //need to populate?

        int nTileX = (int) (dNewX - playerChunk.vTopLeft.x);
        int nTileY = (int) (dNewY - playerChunk.vTopLeft.y);
        SprNPO sprHit = playerChunk.arsprNPO[nTileY][nTileX];
        System.out.println("Player X: " + dNewX + "\tPlayer Y: " + dNewY + "\tTile X: " + nTileX + "\tTile Y: " + nTileY);
        if (sprHit != null) {
            return false;
        }
        return true;
    }

    public void move(Direction direction, Map map) {
        if (!canMove(direction, map)) return;
        switch (direction) {
            case NORTH:
                setY(dY - dSpeed);
                break;
            case SOUTH:
                setY(dY + dSpeed);
                break;
            case EAST:
                setX(dX + dSpeed);
                break;
            case WEST:
                setX(dX - dSpeed);
                break;
        }
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

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
