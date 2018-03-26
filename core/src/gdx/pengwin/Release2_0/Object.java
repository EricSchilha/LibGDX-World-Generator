package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class Object extends Sprite {
    public static int OBJECT_WIDTH = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    public static int OBJECT_HEIGHT = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / Chunk.CHUNK_SIZE; //In pixels
    public static HashMap<ObjectType, Texture> txmObject = new HashMap<ObjectType, Texture>() {{
        put(ObjectType.Tree, new Texture(Gdx.files.internal("Tree1.png")));
    }};

    ObjectType objectType;

    public Object(ObjectType objectType) {
        super(txmObject.get(objectType));
        setSize(OBJECT_WIDTH, -OBJECT_WIDTH);
        this.objectType = objectType;
    }

    public void draw(SpriteBatch batch, float fX, float fY) {
        setPosition(fX, fY + OBJECT_WIDTH);
        super.draw(batch);
    }
}
