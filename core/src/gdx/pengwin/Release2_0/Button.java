package gdx.pengwin.Release2_0;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button extends Sprite {
    ScreenType screenType;
    boolean isPressed;
    final static int BUTTON_WIDTH = 250;
    final static int BUTTON_HEIGHT = 100;

    Button(int nW, int nH, int nX, int nY, Texture txButtonUp, ScreenType screenType) {
        super(txButtonUp);
        this.screenType = screenType;
        isPressed = false;
        setPosition(nX, nY);
        setFlip(false, true);
        setSize(nW, nH);
    }

    @Override
    public void setTexture(Texture texture) {
        isPressed = !isPressed;
        super.setTexture(texture);
    }
}
