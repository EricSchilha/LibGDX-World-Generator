package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.pengwin.Release1_0.Tile;

import java.util.HashMap;

public class SprNPO extends Sprite {
    public static int OBJECT_WIDTH = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    public static int OBJECT_HEIGHT = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    public static HashMap<NPOType, Texture> txmObject = new HashMap<NPOType, Texture>() {{
        put(NPOType.Tree, new Texture(Gdx.files.internal("Tree1.png")));
    }};

    NPOType npoType;

    public SprNPO(NPOType npoType, float fX, float fY) {
        super(txmObject.get(npoType));
        setSize(OBJECT_WIDTH, -OBJECT_WIDTH);
        setPosition(fX, fY);
        this.npoType = npoType;
    }
}
