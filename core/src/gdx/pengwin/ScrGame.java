package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ScrGame extends GameScreen {
    protected static ScreenType screenType = ScreenType.Game;
    protected static Texture txButtonUp = new Texture(Gdx.files.internal("GameButtonUp.png"));
    protected static Texture txButtonDown = new Texture(Gdx.files.internal("GameButtonDown.png"));
    protected Map mapMap;
    public ScrGame(GamMain gamMain) { //Would like to move the code from here into GameScreen constructor
        super.screenType = this.screenType;
        super.txButtonUp = this.txButtonUp;
        super.txButtonDown = this.txButtonDown;
        mapMap = new Map(0);
        this.gamMain = gamMain;
    }

    @Override
    public void show() {
        mapMap = new Map(0);
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        mapMap.draw(batch);
        for (Button button : alButtons)
            if (button.screenType != screenType)
                button.draw(batch);
        batch.end();
    }
}
