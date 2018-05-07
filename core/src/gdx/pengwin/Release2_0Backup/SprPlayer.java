package gdx.pengwin.Release2_0Backup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;


public class SprPlayer extends Sprite {
    private Vector2 vLocation;
    public int nPixelX = SprTile.TILE_SIZE * 8, nPixelY = SprTile.TILE_SIZE * 8 + SprTile.TILE_SIZE;
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
                (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2) + (float) 0.0001,
                (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2) + (float) 0.0001);//Small decimal required for smooth collision
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
        if (nHoriMovement == -1 && canMove(Direction.WEST, map) || nHoriMovement == 1 && canMove(Direction.EAST, map)) {
            setLocation(new Vector2(vLocation.x + (nHoriMovement * fSpeed), vLocation.y));
        }
        if (nVertMovement == -1 && canMove(Direction.NORTH, map) || nVertMovement == 1 && canMove(Direction.SOUTH, map)) {
            setLocation(new Vector2(vLocation.x, vLocation.y + (nVertMovement * fSpeed)));
        }
    }

    //TODO: I don't like this code, but it works (actually it doesn't yet). I might try to improve it later.
    boolean canMove(Direction direction, Map map) {
        Vector2 vNewLocation = new Vector2(vLocation);
        if (direction == Direction.EAST || direction == Direction.WEST) {
            vNewLocation.x += nHoriMovement * fSpeed / 2;
        }
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            vNewLocation.y += nVertMovement * fSpeed / 2;
        }
        alvCorners.clear();
        alvCorners.add(new Vector2(vNewLocation.x, vNewLocation.y - 1));                      //Top Left
        alvCorners.add(new Vector2(vNewLocation.x - 1, vNewLocation.y + 1));               //Top Right
        alvCorners.add(vNewLocation);                                                            //Bottom Left
        alvCorners.add(new Vector2(vNewLocation.x + 1, vNewLocation.y));                      //Bottom Right
        alChunks = map.addChunks(alvCorners);
        for (int i = 0; i < alvCorners.size(); i++) {
            try {
                Chunk chunk = alChunks.get(i);
                Vector2 vTileLocation = new Vector2(alvCorners.get(i).x - chunk.vTopLeft.x, alvCorners.get(i).y - chunk.vTopLeft.y);
                if (chunk.arsprNPO[(int) vTileLocation.y][(int) vTileLocation.x] != null) {
                    System.out.println("1");
                    return false;
                }
                if (vTileLocation.x % 1 > fSpeed / 2) {
                    try {
                        if (chunk.arsprNPO[(int) vTileLocation.y][(int) vTileLocation.x + 1] != null) {
                            System.out.println("2");
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("3");
                        return false;
                    }
                    if (vTileLocation.x % 1 > fSpeed / 2) {
                        try {
                            if (chunk.arsprNPO[(int) vTileLocation.y + 1][(int) vTileLocation.x] != null) {
                                System.out.println("4");
                                return false;
                            }
                        } catch (Exception e) {
                            System.out.println("5");
                            return false;
                        }
                        try {
                            if (chunk.arsprNPO[(int) vTileLocation.y + 1][(int) vTileLocation.x + 1] != null) {
                                System.out.println("6");
                                return false;
                            }
                        } catch (Exception e) {
                            System.out.println("7");
                            return false;
                        }
                    }
                } else if (vTileLocation.x % 1 > fSpeed / 2) {
                    try {
                        if (chunk.arsprNPO[(int) vTileLocation.y + 1][(int) vTileLocation.x] != null) {
                            System.out.println("8");
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("9");
                        return false;
                    }
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