package gdx.pengwin.Release3_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ScrMenu extends GameScreen {
    protected static ScreenType screenType = ScreenType.Menu;
    protected static Texture txButtonUp = new Texture(Gdx.files.internal("MenuButtonUp.png"));
    protected static Texture txButtonDown = new Texture(Gdx.files.internal("MenuButtonDown.png"));

    public ScrMenu(GamMain gamMain) { //Would like to move the code from here into GameScreen constructor
        super.screenType = this.screenType;
        super.txButtonUp = this.txButtonUp;
        super.txButtonDown = this.txButtonDown;
        this.gamMain = gamMain;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1); //Red background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
    }


}
