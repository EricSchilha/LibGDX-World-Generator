import processing.core.PApplet;

public class CollisionDetectionScratch extends PApplet {
    public static void main(String[] args) {
        PApplet.main("CollisionDetectionScratch", args);
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
                fill(arObjects[y][x] ? color(0, 255, 0, 200) : color(255));
                rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        fill(0, 0, 255, 200);
        noStroke();
        rect((float) player.dX * TILE_SIZE, (float) player.dY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
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
        double dX, dY;
        double dSpeed = 0.1;

        public Player(int nW, int nH) {
            dX = (int) random(nW / 2 - nW / 4, nW / 2 + nW / 4) + 0.0001;
            dY = (int) random(nH / 2 - nH / 4, nH / 2 + nH / 4) + 0.0001;
        }

        public void move() {
            if ((arnKeys[0] == -1 && canMove(Direction.NORTH)) || (arnKeys[0] == 1 && canMove(Direction.SOUTH))) {
                dY += arnKeys[0] * dSpeed;
            }
            if ((arnKeys[1] == -1 && canMove(Direction.EAST)) || (arnKeys[1] == 1 && canMove(Direction.WEST))) {
                dX += arnKeys[1] * dSpeed;
            }
        }


        //TODO: improve this code
        boolean canMove(Direction direction) {
            try {
                double dNewX = dX, dNewY = dY;
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    dNewY += arnKeys[0] * dSpeed / 2;
                } else if (direction == Direction.EAST || direction == Direction.WEST) {
                    dNewX += arnKeys[1] * dSpeed / 2;
                }
                int nTileX, nTileY;
                nTileX = (int) dNewX;
                nTileY = (int) dNewY;
                System.out.println(dNewX + "\t" + dNewY);
                if (arObjects[nTileY][nTileX]) return false;
                if (dNewX % 1 > dSpeed / 2) {
                    try {
                        if (arObjects[nTileY][nTileX + 1]) return false;
                    } catch (Exception e) {
                        return false;
                    }
                    if (dNewY % 1 > dSpeed / 2) {
                        try {
                            if (arObjects[nTileY + 1][nTileX]) return false;
                        } catch (Exception e) {
                            return false;
                        }
                        try {
                            if (arObjects[nTileY + 1][nTileX + 1]) return false;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                } else if (dNewY % 1 > dSpeed / 2) {
                    try {
                        if (arObjects[nTileY + 1][nTileX]) return false;
                    } catch (Exception e) {
                        return false;
                    }
                }
            } catch (Exception e) {
            }
            return true;
        }
    }

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
