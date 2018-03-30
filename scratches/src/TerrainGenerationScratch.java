import processing.core.PApplet;

/*
* This is a scratch program to test creating realistic
* world environments.  The shaded dark areas represent
* depth (darker = higher), and the blue areas on the
* map represent trees.
*/
public class TerrainGenerationScratch extends PApplet {
    public static void main(String[] args) {
        PApplet.main("TerrainGenerationScratch", args);
    }

    int W, H, D;
    int scale;
    float val;
    boolean outOfBounds;
    Map map;

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        scale = 20;
        W = height / scale;
        H = width / scale;
        map = new Map(W, H);
        vegetation();
    }

    public void draw() {
        noiseSeed(0);
        randomSeed(0);
        background(255);
        drawMap();
    }

    void drawMap() {
        for (int h = 0; h < map.getHeight(); h++) {
            for (int w = 0; w < map.getWidth(); w++) {
                val = map.terrain[h][w].depth;
                if (map.terrain[h][w].TileType == TileType.Grass) {
                    fill(0, map(val, 0, 1, 255, 0), 0);
                } else {
                    fill(0, 0, 255);
                }
                rect(w * scale, h * scale, scale, scale);
            }
        }
    }

    void vegetation() {
        for (int y = 0; y < H; y += 3) {
            for (int x = 0; x < W; x += 3) {
                do {
                    outOfBounds = false;
                    try {
                        map.terrain[(int) random(y - 2, y + 2)][(int) random(x - 2, x + 2)].TileType = TileType.Tree;
                    } catch (Exception e) {
                        outOfBounds = true;
                    }
                } while (outOfBounds);
            }
        }
    }

    class Map {
        private final int W, H;
        Tile terrain[][];
        float wOffset, hOffset;

        Map(int W, int H) {
            this.W = W;
            this.H = H;
            this.wOffset = 0;
            this.hOffset = 0;
            terrain = new Tile[H][W];
            generateTerrain();
        }

        public void generateTerrain() {
            wOffset = 0;
            hOffset = 0;
            for (int h = 0; h < H; h++) {
                wOffset = 0;
                for (int w = 0; w < W; w++) {
                    terrain[h][w] = new Tile(noise(hOffset, wOffset), w, h, TileType.Grass);
                    wOffset += 0.2;
                }
                hOffset += 0.2;
            }
        }

        public int getWidth() {
            return W;
        }

        public int getHeight() {
            return H;
        }
    }

    class Tile {
        int x, y;
        float depth;
        TileType TileType;

        Tile(float depth, int x, int y, TileType TileType) {
            this.depth = depth;
            this.x = x;
            this.y = y;
            this.TileType = TileType;
        }
    }

    enum TileType {
        Tree, Grass;
    }
}