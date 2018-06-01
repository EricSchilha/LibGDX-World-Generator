package gdx.pengwin.Release3_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class SprNPO extends Sprite {    //NPO stands for Non-Player Object
    public static int OBJECT_WIDTH = SprTile.TILE_SIZE;//(Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / (Chunk.CHUNK_SIZE)/* - Chunk.CHUNK_SIZE / 2)*/; //In pixels
    //public static int OBJECT_HEIGHT = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / (Chunk.CHUNK_SIZE - Chunk.CHUNK_SIZE / 2); //In pixels
    public static HashMap<NPOType, Texture> txmObject = new HashMap<NPOType, Texture>() {{
        put(NPOType.Tree, new Texture(Gdx.files.internal("Tree2.png")));
    }};

    NPOType npoType;

    public SprNPO(NPOType npoType) {
        super(txmObject.get(npoType));
        setSize(OBJECT_WIDTH, -OBJECT_WIDTH);
        this.npoType = npoType;
    }

    public void draw(SpriteBatch batch, float fX, float fY) {
        setPosition(fX, fY + OBJECT_WIDTH);
        super.draw(batch);
    }
}