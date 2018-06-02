package gdx.pengwin.Release3_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class SprPlayer extends Sprite {
    public int[] arnKeys = {0, 0, 0, 0};
    public int nPixelX = (Gdx.graphics.getWidth() - SprTile.TILE_SIZE) / 2;
    public int nPixelY = (Gdx.graphics.getHeight() + SprTile.TILE_SIZE) / 2;

    private Animation<TextureRegion>[] araniPlayer;
    private Vector2 vLocation, vNewLocation, vNewNewLocation;
    private Vector2[] arvCorners;
    private int nVertMovement = 0, nHoriMovement = 0;
    private int nRows = 2, nColumns = 9;                                    //NUMBER OF ROWS AND COLUMNS IN SPRITESHEET.
    private int nFrame = 0, nPos = 0;                                       //USED FOR ANIMATION.

    //MODIFIERS
    private float fSpeed = 0.05f;                                           //SPEED OF THE PLAYER, IN FRACTIONS OF TILESIZE.
    private float fPlayerSizeInTiles = 1.0f;                                //NUMBER OF TILES THE PLAYER TAKES UP (DON'T MAKE >1).
    private float fAniSpeed = 5.2f;                                         //ANIMATION SPEED. SHOULD BE CHANGED WITH SPEED.


    public SprPlayer(Vector2 vLocation) {
        this.create(vLocation);
    }

    public void create(Vector2 vLocation) {
        this.arvCorners = new Vector2[4];
        this.vLocation = vLocation.cpy();
        this.vNewLocation = vLocation.cpy();
        this.vNewNewLocation = vLocation.cpy();
        this.araniPlayer = new Animation[2];

        Texture txPlayerSprite = new Texture(Gdx.files.internal("PlayerSprite.png"));
        TextureRegion[][] artrFull = TextureRegion.split(txPlayerSprite,
                        txPlayerSprite.getWidth() / nColumns, txPlayerSprite.getHeight() / nRows);
        Sprite[] arsprFrames = new Sprite[nColumns];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                arsprFrames[j] = new Sprite(artrFull[i][j]);
            }
            araniPlayer[i] = new Animation<TextureRegion>(fAniSpeed, arsprFrames);
            arsprFrames = new Sprite[nColumns];
        }
    }

    public void draw(SpriteBatch batch) {
        setPosition(nPixelX, nPixelY);
        setRegion(araniPlayer[nPos].getKeyFrame(nFrame, true));
        setSize(SprTile.TILE_SIZE * fPlayerSizeInTiles, -SprTile.TILE_SIZE * fPlayerSizeInTiles);
        super.draw(batch);
    }

    public void move(Map map) {
        nVertMovement = arnKeys[1] - arnKeys[0];
        nHoriMovement = arnKeys[3] - arnKeys[2];
        if(nVertMovement == 0 && nHoriMovement == 0) {
            if(nPos == 0) {
                nFrame = 0;
            } else {
                nFrame = 45; //I stole the sprites from Thomas and he said 45 worked so I'm sticking with it.
            }
            return;
        }
        nPos = (nHoriMovement == -1) ? 1 : (nHoriMovement == 1) ? 0 : nPos;
        if (nHoriMovement == -1 && canMove(Direction.WEST, map) || nHoriMovement == 1 && canMove(Direction.EAST, map)) {
            setLocation(new Vector2(vLocation.x + (nHoriMovement * fSpeed), vLocation.y));
        }
        if (nVertMovement == -1 && canMove(Direction.NORTH, map) || nVertMovement == 1 && canMove(Direction.SOUTH, map)) {
            setLocation(new Vector2(vLocation.x, vLocation.y + (nVertMovement * fSpeed)));
        }
        nFrame++;
    }

    //TODO: improve this code (try-catch's are supposedly quite slow)
    boolean canMove(Direction direction, Map map) {
        vNewLocation.set(vLocation);
        if (direction == Direction.EAST || direction == Direction.WEST) {
            vNewLocation.x += nHoriMovement * fSpeed;
        }
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            vNewLocation.y += nVertMovement * fSpeed;
        }

        //Reordered array to make it easier to use (0 is Top Left)
        vNewNewLocation.set(vNewLocation.x, vNewLocation.y);                                                        //Bottom Left
        arvCorners[2] = vNewLocation.cpy();
        vNewLocation.set(vNewNewLocation.x, vNewNewLocation.y - fPlayerSizeInTiles / 2);                                //Top Left
        arvCorners[0] = vNewLocation.cpy();
        vNewLocation.set(vNewNewLocation.x + fPlayerSizeInTiles, vNewNewLocation.y - fPlayerSizeInTiles / 2);           //Top Right
        arvCorners[1] = vNewLocation.cpy();
        vNewLocation.set(vNewNewLocation.x + fPlayerSizeInTiles, vNewNewLocation.y);                                    //Bottom Right
        arvCorners[3] = vNewLocation.cpy();

        //*
        Chunk chunk = map.addChunk(arvCorners[0]);  //TL
        vNewLocation.set(arvCorners[0].x - chunk.vTopLeft.x, arvCorners[0].y - chunk.vTopLeft.y);
        if (chunk.arsprNPO[(int) vNewLocation.y][(int) vNewLocation.x] != null) return false;
        if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Mountain) return false;
        if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Water) return false;
        if (arvCorners[0].x % 1 > fSpeed + (1 - fPlayerSizeInTiles)) {
            try {
                chunk = map.addChunk(arvCorners[1]);  //TR
                vNewLocation.set(arvCorners[1].x - chunk.vTopLeft.x, arvCorners[1].y - chunk.vTopLeft.y);
                if (chunk.arsprNPO[(int) vNewLocation.y][(int) vNewLocation.x] != null) return false;
                if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Mountain)
                    return false;
                if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Water)
                    return false;
            } catch (Exception e) {
                return false;
            }
            if (arvCorners[0].y % 1 > fSpeed + (1 - fPlayerSizeInTiles)) {
                try {
                    chunk = map.addChunk(arvCorners[2]);  //BL
                    vNewLocation.set(arvCorners[2].x - chunk.vTopLeft.x, arvCorners[2].y - chunk.vTopLeft.y);
                    if (chunk.arsprNPO[(int) vNewLocation.y][(int) vNewLocation.x] != null) return false;
                    if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Mountain)
                        return false;
                    if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Water)
                        return false;
                } catch (Exception e) {
                    return false;
                }
                try {
                    chunk = map.addChunk(arvCorners[3]);  //BR
                    vNewLocation.set(arvCorners[3].x - chunk.vTopLeft.x, arvCorners[3].y - chunk.vTopLeft.y);
                    if (chunk.arsprNPO[(int) vNewLocation.y][(int) vNewLocation.x] != null) return false;
                    if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Mountain)
                        return false;
                    if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Water)
                        return false;
                } catch (Exception e) {
                    return false;
                }
            }
        } else if (arvCorners[0].y % 1 > fSpeed + (1 - fPlayerSizeInTiles)) {
            try {
                chunk = map.addChunk(arvCorners[2]);  //BL
                vNewLocation.set(arvCorners[2].x - chunk.vTopLeft.x, arvCorners[2].y - chunk.vTopLeft.y);
                if (chunk.arsprNPO[(int) vNewLocation.y][(int) vNewLocation.x] != null) return false;
                if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Mountain)
                    return false;
                if (chunk.arsprTiles[(int) vNewLocation.y][(int) vNewLocation.x].tileType == TileType.Water)
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        /*/ //BEFORE (didn't work)
        for (int i = 0; i < arvCorners.length; i++) {
            System.out.println(i + "\t" + arvCorners[i]);
            try {
                Chunk chunk = map.addChunk(arvCorners[i]);
                vNewLocation.set(arvCorners[i].x - chunk.vTopLeft.x, arvCorners[i].y - chunk.vTopLeft.y);
                if (chunk.arsprNPO[(int) vNewLocation.y][(int) vNewLocation.x] != null) {
                    return false;
                }
            } catch (Exception e) {
            }
        }
        //*/
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