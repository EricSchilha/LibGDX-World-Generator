package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;


public class SprPlayer extends Sprite {
    private Vector2 vLocation, vNewLocation, vNewNewLocation;
    public int nPixelX = SprTile.TILE_SIZE * 8, nPixelY = SprTile.TILE_SIZE * 8 + SprTile.TILE_SIZE;
    private int nMinDivisor = 4096;
    private float fSpeed = (float) 0.1;
    public static Texture txPlayer = new Texture(Gdx.files.internal("Dude1.png"));
    public int[] arnKeys = {0, 0, 0, 0};
    int nVertMovement = 0, nHoriMovement = 0;
    Vector2[] arvCorners = new Vector2[4];

    public SprPlayer() {
        super(txPlayer);
        setSize(SprTile.TILE_SIZE, -SprTile.TILE_SIZE);
        this.vLocation = new Vector2(
                (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2),
                (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2));
        this.vNewLocation = new Vector2();
        this.vNewNewLocation = new Vector2();
    }

    public void draw(SpriteBatch batch, ShapeRenderer sr) {
        setPosition(nPixelX, nPixelY);
        super.draw(batch);
    }

    public void move(Map map) {
        nVertMovement = arnKeys[1] - arnKeys[0];
        nHoriMovement = arnKeys[3] - arnKeys[2];
        if (nHoriMovement == -1 && canMove(Direction.WEST, map) || nHoriMovement == 1 && canMove(Direction.EAST, map)) {
            setLocation(new Vector2(vLocation.x + (nHoriMovement * fSpeed), vLocation.y));
        }
        if (nVertMovement == -1 && canMove(Direction.NORTH, map) || nVertMovement == 1 && canMove(Direction.SOUTH, map)) {
            setLocation(new Vector2(vLocation.x, vLocation.y + (nVertMovement * fSpeed)));
        }
    }

    boolean canMove(Direction direction, Map map) {
        vNewLocation.set(vLocation);
        if (direction == Direction.EAST || direction == Direction.WEST) {
            vNewLocation.x += nHoriMovement * fSpeed / 2;
        }
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            vNewLocation.y += nVertMovement * fSpeed / 2;
        }
        vNewNewLocation.set(vNewLocation);                                  //Bottom Left
        arvCorners[2] = vNewLocation.cpy();
        vNewLocation.set(vNewNewLocation.x, vNewNewLocation.y - 1);         //Top Left
        arvCorners[0] = vNewLocation.cpy();
        vNewLocation.set(vNewNewLocation.x + 1, vNewNewLocation.y - 1);     //Top Right
        arvCorners[1] = vNewLocation.cpy();
        vNewLocation.set(vNewNewLocation.x + 1, vNewNewLocation.y);         //Bottom Right
        arvCorners[3] = vNewLocation.cpy();

        for (int i = 0; i < arvCorners.length; i++) {
            try {
                Chunk chunk = map.addChunk(arvCorners[i]);
                vNewLocation.set(arvCorners[i].x - chunk.vTopLeft.x, arvCorners[i].y - chunk.vTopLeft.y);
                System.out.println(i + "\t" + arvCorners[i]);
                if (chunk.arsprNPO[(int) vNewLocation.y][(int) vNewLocation.x] != null) {
                    return false;
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

    public Vector2 getLocation() {
        return vLocation;
    }

    public void setLocation(Vector2 vLocation) {
        this.vLocation = vLocation;
    }

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}