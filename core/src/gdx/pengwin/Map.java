package gdx.pengwin;

import java.util.ArrayList;

public class Map {
    public ArrayList<Chunk> alChunks;
    public double[][] ardHeightMap; //Stores the perlin noise
    public int[][] arnObjects; //Stores the other things on the map (trees, rocks, etc.)
    public Tile[][] artilTiles;

    //    int nWidth, nHeight;
    //    int nX, nY;
    public Map() {

    }


}
