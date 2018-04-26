package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;



public class SprPlayer extends Sprite {
    private Vector2 vLocation;
    private int nPixelX = SprTile.TILE_SIZE * 8, nPixelY = SprTile.TILE_SIZE * 8 + SprTile.TILE_SIZE;
    private int nMinDivisor = 4096;
    private float fSpeed = (float) 0.1;
    public static Texture txPlayer = new Texture(Gdx.files.internal("Dude1.png"));
    int[] arnKeys;
    int nVertMovement = 0, nHoriMovement = 0;
    ArrayList<Chunk> alChunks = new ArrayList<Chunk>();
    ArrayList<Vector2> alvCorners = new ArrayList<Vector2>();

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
        if (nVertMovement == -1 || nVertMovement == 1) {
            setLocation(new Vector2(vLocation.x, vLocation.y + (nVertMovement * fSpeed)));
        }
        if (nHoriMovement == -1 || nHoriMovement == 1) {
            setLocation(new Vector2(vLocation.x + (nHoriMovement * fSpeed), vLocation.y));
        }
    }

    //TODO: I don't like this code, but it works (actually it doesn't yet). I might try to improve it later.
    //This needs to check if the characters bounding rectangle lies on multiple chunks

    boolean canMove (Direction direction, Map map) {
        Vector2 vNewLocation = new Vector2(vLocation);
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            vNewLocation.x += nVertMovement * fSpeed;
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            vNewLocation.y += nHoriMovement * fSpeed;
        }
        alvCorners.clear();
        alvCorners.add(vNewLocation);
        alvCorners.add(new Vector2(vNewLocation.x + (float) 1, vNewLocation.y));
        alvCorners.add(new Vector2(vNewLocation.x, vNewLocation.y + 1));
        alvCorners.add(new Vector2(vNewLocation.x + (float) 1, vNewLocation.y + 1));
        alChunks = map.addChunks(alvCorners);
        /*for (int i = 0; i < alvCorners.size(); i++) {
            try {
                Chunk chunk = (i >= alChunks.size()) ? alChunks.get(alChunks.size() - 1) : alChunks.get(i);
                int nTileX = (int) (alvCorners.get(i).x - chunk.vTopLeft.x);
                int nTileY = (int) (alvCorners.get(i).y - chunk.vTopLeft.y);
                if (chunk.arsprNPO[nTileY][nTileX] != null) {
                    return false;
                }

            } catch (Exception e) {

            }
//            for (int y = 0; y < chunk.arsprNPO.length; y++) {
//                for (int x = 0; x < chunk.arsprNPO[0].length; x++) {
//                    if(nTileX == x && nTileY == y) System.out.print("*");
//                    if (chunk.arsprNPO[y][x] == null) {
//                        System.out.print("N ");
//                    } else if (chunk.arsprNPO[y][x] != null && chunk.arsprNPO[y][x].npoType == NPOType.Tree) {
//                        System.out.print("T ");
//                    }
//                }
//                System.out.println();
//            }
        }*/
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
