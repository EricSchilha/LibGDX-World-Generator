package gdx.pengwin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button extends Sprite {
    ScreenType screenType;
    boolean isPressed;
    final static int BUTTON_WIDTH = 100;
    final static int BUTTON_HEIGHT = 50;

    Button(int nW, int nH, int nX, int nY, Texture txButtonUp, Texture txButtonDown, ScreenType screenType) {
        super(txButtonUp);
        this.screenType = screenType;
        isPressed = false;
        setPosition(nX, nY);
        setFlip(false, true);
        setSize(nW, nH);
    }

    @Override
    public void setTexture(Texture texture) {
        //super(texture);
        this.setTexture(texture);
        isPressed = !isPressed;
    }
}
