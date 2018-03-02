package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ScrMenu extends GameScreen {
    public ScrMenu(GamMain gamMenu) {
        screenType = ScreenType.Menu;
        txButtonUp = new Texture(Gdx.files.internal("badlogic.jpg"/*"MenuButtonUp.jpg"*/));
        txButtonDown = new Texture(Gdx.files.internal("badlogic.jpg"/*"MenuButtonDown.jpg"*/));
        this.gamMain = gamMenu;
    }


}
