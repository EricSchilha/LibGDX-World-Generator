package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class SprPlayer extends Sprite {
    private Vector2 vLocation;
    private int nPixelX = SprTile.TILE_SIZE * 8, nPixelY = SprTile.TILE_SIZE * 8 + SprTile.TILE_SIZE;
    private int nMinDivisor = 4096;
    private float fSpeed = (float) 0.1;
    public static Texture txPlayer = new Texture(Gdx.files.internal("Dude1.png"));
    int[] arnKeys;
    int nVertMovement = 0, nHoriMovement = 0;

    public SprPlayer() {
        super(txPlayer);
        setSize(SprTile.TILE_SIZE, -SprTile.TILE_SIZE);
        this.vLocation = new Vector2(
                (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2),
                (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2));
        this.arnKeys = Arrays.copyOf(ScrGame.arnKeys, ScrGame.arnKeys.length);
    }

    public void draw(SpriteBatch batch, ShapeRenderer sr) {
        setPosition(nPixelX, nPixelY);
        super.draw(batch);
    }

    public void move(Map map) {
        arnKeys = Arrays.copyOf(ScrGame.arnKeys, ScrGame.arnKeys.length);
        nVertMovement = arnKeys[1] - arnKeys[0];
        nHoriMovement = arnKeys[3] - arnKeys[2];
        if ((nVertMovement == -1 && canMove(Direction.NORTH, map)) || (nVertMovement == 1 && canMove(Direction.SOUTH, map))) {
            setLocation(new Vector2(vLocation.x, vLocation.y + (nVertMovement * fSpeed)));
        }
        if ((nHoriMovement == -1 && canMove(Direction.EAST, map)) || (nHoriMovement == 1 && canMove(Direction.WEST, map))) {
            setLocation(new Vector2(vLocation.x + (nHoriMovement * fSpeed), vLocation.y));
        }
    }

    //TODO: I don't like this code, but it works (actually it doesn't yet). I might try to improve it later.
    //This needs to check if the characters bounding rectangle lies on multiple chunks

    boolean canMove(Direction direction, Map map) {
        Vector2 vNewLocation = new Vector2(vLocation.x + (float)0.5, vLocation.y + (float)0.5);
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            vNewLocation.x += nVertMovement * fSpeed / 2;
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            vNewLocation.y += nHoriMovement * fSpeed / 2;
        }
        Chunk thisChunk = new Chunk((int)map.getChunkIndices(vNewLocation).x, (int)map.getChunkIndices(vNewLocation).y);
        for(Chunk[] row : map.arChunks) {
            for(Chunk chunk : row) {
                if (chunk.vTopLeft.x == map.getChunkIndices(vNewLocation).x && chunk.vTopLeft.y == map.getChunkIndices(vNewLocation).y) {
                    thisChunk = chunk;
                    break;
                }
            }
        }
        SprNPO[][] arsprNPOTL = thisChunk.arsprNPO;
        int nTileX = (int)(vNewLocation.x - thisChunk.vTopLeft.x);
        int nTileY = (int)(vNewLocation.y - thisChunk.vTopLeft.y);

        for (int y = 0; y < arsprNPOTL.length; y++) {
            for (int x = 0; x < arsprNPOTL[0].length; x++) {
                if (y == nTileY && x == nTileX) System.out.print("*");
                if (arsprNPOTL[y][x] != null) System.out.print("T ");
                else System.out.print("N ");
            }
            System.out.println();
        }
        System.out.println();

        if(thisChunk.arsprNPO[(int)(vNewLocation.x - thisChunk.vTopLeft.x)][(int)(vNewLocation.y - thisChunk.vTopLeft.y)] != null) {
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                setLocation(new Vector2(vLocation.x, vLocation.y + (nVertMovement * -1 * fSpeed)));
            } else if (direction == Direction.EAST || direction == Direction.WEST) {
                setLocation(new Vector2(vLocation.x + (nHoriMovement * -1 * fSpeed), vLocation.y));
            }
            return false;
        }
        return true;
    }

    public Vector2 getLocation(){ return vLocation; }

    public void setLocation(Vector2 vLocation){ this.vLocation = vLocation;}

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
