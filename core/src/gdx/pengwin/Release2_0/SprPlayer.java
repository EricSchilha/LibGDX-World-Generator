package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import gdx.pengwin.Release1_0.Tile;

import java.util.Arrays;

public class SprPlayer extends Sprite {
    private double dX, dY;
    private int nPixelX = SprTile.TILE_SIZE * 8, nPixelY = SprTile.TILE_SIZE * 8 + SprTile.TILE_SIZE;
    private int nMinDivisor = 4096;
    private double dSpeed = 1;
    public static Texture txPlayer = new Texture(Gdx.files.internal("Dude1.png"));
    int[] arnKeys;
    int nVertMovement = 0, nHoriMovement = 0;

    public SprPlayer() {
        super(txPlayer);
        setSize(SprTile.TILE_SIZE, -SprTile.TILE_SIZE);
        this.dX = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
        this.dY = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
        this.arnKeys = Arrays.copyOf(ScrGame.arnKeys, ScrGame.arnKeys.length);
    }

    public void draw(SpriteBatch batch, ShapeRenderer sr) {
        //System.out.println(this.dX + "\t" + this.dY); //Use this to track player movements
        setPosition(nPixelX, nPixelY);
        super.draw(batch);
    }

    public void move(Map map) {
        arnKeys = Arrays.copyOf(ScrGame.arnKeys, ScrGame.arnKeys.length);
        nVertMovement = arnKeys[1] - arnKeys[0];
        nHoriMovement = arnKeys[3] - arnKeys[2];
        if ((nVertMovement == -1 && canMove(Direction.NORTH, map)) || (nVertMovement == 1 && canMove(Direction.SOUTH, map))) {
            setY(dY + (nVertMovement * dSpeed));
        }
        if ((nHoriMovement == -1 && canMove(Direction.EAST, map)) || (nHoriMovement == 1 && canMove(Direction.WEST, map))) {
            setX(dX + (nHoriMovement * dSpeed));
        }
    }

    //TODO: I don't like this code, but it works (actually it doesn't yet). I might try to improve it later.
    //This needs to check if the characters bounding rectangle lies on multiple chunks

    boolean canMove(Direction direction, Map map) {
        double dNewX = dX, dNewY = dY;
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            dNewY += nVertMovement * dSpeed / 2;
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            dNewX += nHoriMovement * dSpeed / 2;
        }
        Vector2 vTopLeft = map.getChunkIndices(new Vector2((float) dNewX, (float) dNewY - 1)); //These store the chunk top left corner
        Vector2 vTopRight = map.getChunkIndices(new Vector2((float) dNewX + 1, (float) dNewY - 1));
        Vector2 vBottomLeft = map.getChunkIndices(new Vector2((float) dNewX, (float) dNewY));
        Vector2 vBottomRight = map.getChunkIndices(new Vector2((float) dNewX + 1, (float) dNewY));
        SprNPO[][] arsprNPOTL = new Chunk((int) vTopLeft.x, (int) vTopLeft.y).arsprNPO;//new SprNPO[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
        SprNPO[][] arsprNPOTR = new Chunk((int) vTopRight.x, (int) vTopRight.y).arsprNPO;//new SprNPO[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
        SprNPO[][] arsprNPOBL = new Chunk((int) vBottomLeft.x, (int) vBottomLeft.y).arsprNPO;//new SprNPO[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
        SprNPO[][] arsprNPOBR = new Chunk((int) vBottomRight.x, (int) vBottomRight.y).arsprNPO;//new SprNPO[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
        Rectangle rectPlayer = new Rectangle(nPixelX, nPixelY, Tile.TILE_SIZE, Tile.TILE_SIZE);
        for (int y = 0; y < arsprNPOTL.length; y++) {
            for (int x = 0; x < arsprNPOTL[0].length; x++) {
                if (arsprNPOTL[y][x] == null) continue;
                if (arsprNPOTL[y][x].getBoundingRectangle().overlaps(rectPlayer)) return false;
            }
        }
        return true;
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
