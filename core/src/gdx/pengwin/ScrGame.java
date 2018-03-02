package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ScrGame extends GameScreen {
    public ScrGame(GamMain gamMenu) {
        screenType = ScreenType.Game;
        txButtonUp = new Texture(Gdx.files.internal("badlogic.jpg"/*"GameButtonUp.jpg"*/));
        txButtonDown = new Texture(Gdx.files.internal("badlogic.jpg"/*"GameButtonDown.jpg"*/));
        this.gamMain = gamMenu;
    }
}
