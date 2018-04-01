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
    private double dX, dY;
    private int nMinDivisor = 4096;
    private double dSpeed = 0.1;
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
        int nMiddleX = SprTile.TILE_SIZE * 8;
        int nMiddleY = SprTile.TILE_SIZE * 8;
        setPosition(nMiddleX, nMiddleY + SprTile.TILE_SIZE);
        super.draw(batch);
    }

    public void move(Map map) {
        arnKeys = Arrays.copyOf(ScrGame.arnKeys, ScrGame.arnKeys.length);
        nVertMovement = arnKeys[1] - arnKeys[0];
        nHoriMovement = arnKeys[3] - arnKeys[2];
        if ((nVertMovement == -1 && canMove(Direction.NORTH, map)) || (nVertMovement == 1 && canMove(Direction.SOUTH, map))) {
            setY(dY + nVertMovement * dSpeed);
        }
        if ((nHoriMovement == -1 && canMove(Direction.EAST, map)) || (nHoriMovement == 1 && canMove(Direction.WEST, map))) {
            setX(dX + nHoriMovement * dSpeed);
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
        Vector2 vTopLeft = map.getChunkIndices(new Vector2((float) dNewX, (float) dNewY)); //Sprite draws at bottom left?
        Vector2 vTopRight = map.getChunkIndices(new Vector2((float) dNewX + 1, (float) dNewY));
        Vector2 vBottomLeft = map.getChunkIndices(new Vector2((float) dNewX, (float) dNewY + 1));
        Vector2 vBottomRight = map.getChunkIndices(new Vector2((float) dNewX + 1, (float) dNewY + 1));
        ArrayList<Location> alCornerLocs = new ArrayList<>();

        System.out.println(vTopLeft + "\t" + vTopRight);
        System.out.println(vBottomLeft + "\t" + vBottomRight);
        System.out.println();
        alCornerLocs.add(new Location((float) dNewX - vTopLeft.x, (float) dNewY - vTopLeft.y, new Chunk((int) vTopLeft.x, (int) vTopLeft.y)));
        alCornerLocs.add(new Location((float) dNewX + 1 - vTopRight.x, (float) dNewY - vTopRight.y, new Chunk((int) vTopRight.x, (int) vTopRight.y)));
        alCornerLocs.add(new Location((float) dNewX - vBottomLeft.x, (float) dNewY + 1 - vBottomLeft.y, new Chunk((int) vBottomLeft.x, (int) vBottomLeft.y)));
        alCornerLocs.add(new Location((float) dNewX + 1 - vBottomRight.x, (float) dNewY + 1 - vBottomRight.y, new Chunk((int) vBottomRight.x, (int) vBottomRight.y)));

        /*for (Chunk[] arChunk : map.arChunks) {
            for (Chunk chunk : arChunk) {
                if (chunk.vTopLeft.equals(vTopLeft)) {
                    alCornerLocs.add(new Location((float)dNewX - chunk.vTopLeft.x, (float)dNewY - chunk.vTopLeft.y, chunk));
                }
                if (chunk.vTopLeft.equals(vTopRight)) {
                    alCornerLocs.add(new Location((float)dNewX - chunk.vTopLeft.x, (float)dNewY - chunk.vTopLeft.y, chunk));
                }
                if (chunk.vTopLeft.equals(vBottomLeft)) {
                    alCornerLocs.add(new Location((float)dNewX - chunk.vTopLeft.x, (float)dNewY - chunk.vTopLeft.y, chunk));
                }
                if (chunk.vTopLeft.equals(vBottomRight)) {
                    alCornerLocs.add(new Location((float)dNewX - chunk.vTopLeft.x, (float)dNewY - chunk.vTopLeft.y, chunk));
                }
            }
        }*/

        if (alCornerLocs.get(0).chunk.arsprNPO[alCornerLocs.get(0).nY][alCornerLocs.get(0).nX] != null) return false;
        if (dNewX % 1 > dSpeed / 2) {
            try {
                if (alCornerLocs.get(1).chunk.arsprNPO[alCornerLocs.get(1).nY][alCornerLocs.get(1).nX] != null)
                    return false;
            } catch (Exception e) {
                return false;
            }
            if (dNewY % 1 > dSpeed / 2) {
                try {
                    if (alCornerLocs.get(2).chunk.arsprNPO[alCornerLocs.get(2).nY][alCornerLocs.get(2).nX] != null)
                        return false;
                } catch (Exception e) {
                    return false;
                }
                try {
                    if (alCornerLocs.get(3).chunk.arsprNPO[alCornerLocs.get(3).nY][alCornerLocs.get(3).nX] != null)
                        return false;
                } catch (Exception e) {
                    return false;
                }
            }
        } else if (dNewY % 1 > dSpeed / 2) {
            try {
                if (alCornerLocs.get(1).chunk.arsprNPO[alCornerLocs.get(1).nY][alCornerLocs.get(1).nX] != null)
                    return false;
            } catch (Exception e) {
                return false;
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

    class Location {
        float fX, fY;
        int nX, nY;
        Chunk chunk;

        public Location(float fX, float fY, Chunk chunk) {
            this.fX = fX;
            this.fY = fY;
            this.nX = (int) fX;
            this.nY = (int) fY;
            this.chunk = chunk;
        }
    }

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
