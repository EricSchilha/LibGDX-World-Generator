package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ScrGame extends GameScreen {
    protected static ScreenType screenType = ScreenType.Game;
    protected static Texture txButtonUp = new Texture(Gdx.files.internal("GameButtonUp.png"));
    protected static Texture txButtonDown = new Texture(Gdx.files.internal("GameButtonDown.png"));
    protected Map mapMap;
    boolean showButtons = false;
    int nKeyCode;

    public ScrGame(GamMain gamMain) { //Would like to move the code from here into GameScreen constructor
        super.screenType = this.screenType;
        super.txButtonUp = this.txButtonUp;
        super.txButtonDown = this.txButtonDown;
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
        translate();
        //Tile.resize();
        mapMap.draw(batch);
        if (showButtons)
            for (Button button : alButtons)
                if (button.screenType != screenType)
                    button.draw(batch);
        batch.end();
    }

    public void translate() {
        if (nKeyCode == 19) {//UP
            mapMap.player.setY(mapMap.player.getPlayerY() - 0.1);
        } else if (nKeyCode == 20) {//DOWN
            mapMap.player.setY(mapMap.player.getPlayerY() + 0.1);
        } else if (nKeyCode == 21) {//LEFT
            mapMap.player.setX(mapMap.player.getPlayerX() - 0.1);
        } else if (nKeyCode == 22) {//RIGHT
            mapMap.player.setX(mapMap.player.getPlayerX() + 0.1);
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        nKeyCode = keyCode;
        if (keyCode == 131) {//ESC
            showButtons = !showButtons;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        nKeyCode = 0;
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int mouseButton) {
        if (showButtons) super.touchDown(screenX, screenY, pointer, mouseButton);
        return false;
    }
}
