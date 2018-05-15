import processing.core.PApplet;
import processing.core.PVector;

public class CollisionDetectionV2Scratch extends PApplet {
    public static void main(String[] args) {
        PApplet.main("CollisionDetectionV2Scratch", args);
    }

    boolean[][] arObjects;
    int TILE_SIZE;
    int nWidth = 40, nHeight = 40;
    Player player;
    public int[] arnKeys = new int[2];//0 up/down, 1 left/right

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        TILE_SIZE = width / nWidth;
        arObjects = new boolean[nHeight][nWidth];
        for (int y = 0; y < nHeight; y++) {
            for (int x = 0; x < nWidth; x++) {
                arObjects[y][x] = (random(10) > 9);
            }
        }
        player = new Player(nWidth, nHeight);
    }

    public void draw() {
        background(255);
        for (int y = 0; y < nHeight; y++) {
            for (int x = 0; x < nWidth; x++) {
                fill(arObjects[y][x] ? color(255, 0, 0, 200) : color(255));
                rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        fill(0, 0, 255, 200);
        noStroke();
        rect( player.vLocation.x * TILE_SIZE,  player.vLocation.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        stroke(1);
        player.move();
    }

    public void keyPressed() {
        if (key == 'w' || keyCode == UP) {
            arnKeys[0] = -1;
        } else if (key == 's' || keyCode == DOWN) {
            arnKeys[0] = 1;
        }

        if (key == 'a' || keyCode == LEFT) {
            arnKeys[1] = -1;
        } else if (key == 'd' || keyCode == RIGHT) {
            arnKeys[1] = 1;
        }
    }

    public void keyReleased() {
        if (key == 'w' || keyCode == UP || key == 's' || keyCode == DOWN) {
            arnKeys[0] = 0;
        } else if (key == 'a' || keyCode == LEFT || key == 'd' || keyCode == RIGHT) {
            arnKeys[1] = 0;
        }
    }

    class Player {
        PVector vLocation, vNewLocation;
        PVector vTopLeft, vTopRight, vBottomLeft, vBottomRight;
        double dSpeed = 0.1;

        public Player(int nW, int nH) {
            vLocation = new PVector((int) random(nW / 2 - nW / 4, nW / 2 + nW / 4) + (float)0.0001,
                    (int) random(nH / 2 - nH / 4, nH / 2 + nH / 4) + (float)0.0001);
        }

        public void move() {
            if (arnKeys[1] == -1 && (canMove(Direction.EAST)) || (arnKeys[1] == 1 && canMove(Direction.WEST))) {
                vLocation.x += arnKeys[1] * dSpeed;
            }
            if ((arnKeys[0] == -1 && canMove(Direction.NORTH)) || (arnKeys[0] == 1 && canMove(Direction.SOUTH))) {
                vLocation.y += arnKeys[0] * dSpeed;
            }
        }


        //TODO: improve this code
        boolean canMove(Direction direction) {
            try {
                vNewLocation = vLocation;
                if (direction == Direction.EAST || direction == Direction.WEST) {
                    vNewLocation.x += arnKeys[1] * dSpeed;
                }
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    vNewLocation.y += arnKeys[0] * dSpeed;
                }
                vTopLeft = vNewLocation;
                vTopRight = new PVector(vNewLocation.x + 1, vNewLocation.y);
                vBottomLeft = new PVector(vNewLocation.x, vNewLocation.y + 1);
                vBottomRight = new PVector(vNewLocation.x + 1, vNewLocation.y + 1);

                int nTileX, nTileY;

                nTileX = (int) vTopLeft.x;
                nTileY = (int) vTopLeft.y;
                if (arObjects[nTileY][nTileX]) return false;
                nTileX = (int) vTopRight.x;
                nTileY = (int) vTopRight.y;
                if (arObjects[nTileY][nTileX]) return false;
                nTileX = (int) vBottomLeft.x;
                nTileY = (int) vBottomLeft.y;
                if (arObjects[nTileY][nTileX]) return false;
                nTileX = (int) vBottomRight.x;
                nTileY = (int) vBottomRight.y;
                if (arObjects[nTileY][nTileX]) return false;
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
