package gdx.pengwin.Release2_0;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Object extends Sprite {
    public static int OBJECT_WIDTH = 32; //In pixels
    public static int OBJECT_HEIGHT = 32;

    ObjectType objectType;

    public Object(int nX, int nY, Texture txObject, ObjectType objectType) {
        super(txObject, nX, nY, OBJECT_WIDTH, OBJECT_HEIGHT);
        this.objectType = objectType;
    }

    public void draw(Batch batch, float fX, float fY) {
        setPosition(fX, fY);
        super.draw(batch);
    }
}
