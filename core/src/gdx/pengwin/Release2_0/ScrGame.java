package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class ScrGame extends GameScreen {
    protected static ScreenType screenType = ScreenType.Game;
    protected static Texture txButtonUp = new Texture(Gdx.files.internal("GameButtonUp.png"));
    protected static Texture txButtonDown = new Texture(Gdx.files.internal("GameButtonDown.png"));
    protected Map map;
    boolean showButtons = false;

    public ScrGame(GamMain gamMain) { //TODO: move the code from here into GameScreen constructor (maybe)
        this.gamMain = gamMain;
        super.screenType = this.screenType;
        super.txButtonUp = this.txButtonUp;
        super.txButtonDown = this.txButtonDown;
        arnKeys = new int[4];
        map = new Map(0);

    }

    @Override
    public void show() {
        map = new Map(0);
        super.show();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        map.update();
        oc.position.set(map.sprPlayer.getX() + map.sprPlayer.getWidth() / 2, map.sprPlayer.getY() + map.sprPlayer.getHeight() / 2, 0);
        oc.update();
        map.draw(batch);
        if (showButtons)
            for (SprButton sprButton : alsprButtons)
                if (sprButton.screenType != screenType)
                    sprButton.draw(batch);
        batch.end();
    }

    @Override
    public boolean keyDown(int keyCode) {   //TODO: add (keys, directions) to a HashMap
        switch (keyCode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                arnKeys[0] = 1;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                arnKeys[1] = 1;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                arnKeys[2] = 1;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                arnKeys[3] = 1;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                arnKeys[0] = 0;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                arnKeys[1] = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                arnKeys[2] = 0;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                arnKeys[3] = 0;
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int mouseButton) {
        if (showButtons) super.touchDown(screenX, screenY, pointer, mouseButton);
        return false;
    }
}
