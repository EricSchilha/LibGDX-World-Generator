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
        rect(player.vLocation.x * TILE_SIZE, player.vLocation.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
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
        float fSpeed = (float) 0.1;

        public Player(int nW, int nH) {
            vLocation = new PVector((int) random(nW / 2 - nW / 4, nW / 2 + nW / 4) + (float) 0.0001,
                    (int) random(nH / 2 - nH / 4, nH / 2 + nH / 4) + (float) 0.0001);
            System.out.println(vLocation);
        }

        public void move() {

            if (arnKeys[1] == -1) {
                if (canMove(Direction.WEST)) {
                    vLocation.x += arnKeys[1] * fSpeed;
                }
            } else if (arnKeys[1] == 1) {
                if (canMove(Direction.EAST)) {
                    vLocation.x += arnKeys[1] * fSpeed;
                }
            }

            if (arnKeys[0] == -1) {
                if (canMove(Direction.NORTH)) {
                    vLocation.y += arnKeys[0] * fSpeed;
                }
            } else if (arnKeys[0] == 1) {
                if (canMove(Direction.SOUTH)) {
                    vLocation.y += arnKeys[0] * fSpeed;
                }
            }
        }


        //TODO: improve this code (try-catch's are supposedly quite slow)
        boolean canMove(Direction direction) {
            vNewLocation = vLocation.copy();
            if (direction == Direction.EAST || direction == Direction.WEST) {
                vNewLocation.x += arnKeys[1] * fSpeed;
            }
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                vNewLocation.y += arnKeys[0] * fSpeed;
            }
            vTopLeft = vNewLocation.copy();
            vTopRight = vNewLocation.copy();
            vTopRight.set(vNewLocation.x + 1, vNewLocation.y);
            vBottomLeft = vNewLocation.copy();
            vBottomLeft.set(vNewLocation.x, vNewLocation.y + 1);
            vBottomRight = vNewLocation.copy();
            vBottomRight.set(vNewLocation.x + 1, vNewLocation.y + 1);
            System.out.println(vTopLeft);

            if (arObjects[(int) vTopLeft.y][(int) vTopLeft.x]) return false;
            if (vTopLeft.x % 1 > fSpeed) {
                try {
                    if (arObjects[(int) vTopRight.y][(int) vTopRight.x]) return false;
                } catch (Exception e) {
                    return false;
                }
                if (vTopLeft.y % 1 > fSpeed) {
                    try {
                        if (arObjects[(int) vBottomLeft.y][(int) vBottomLeft.x]) return false;
                    } catch (Exception e) {
                        return false;
                    }
                    try {
                        if (arObjects[(int) vBottomRight.y][(int) vBottomRight.x]) return false;
                    } catch (Exception e) {
                        return false;
                    }
                }
            } else if (vTopLeft.y % 1 > fSpeed) {
                try {
                    if (arObjects[(int) vBottomLeft.y][(int) vBottomLeft.x]) return false;
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }
    }

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}