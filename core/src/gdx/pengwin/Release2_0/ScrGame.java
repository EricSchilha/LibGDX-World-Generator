package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ScrGame extends GameScreen {
    protected static ScreenType screenType = ScreenType.Game;
    protected static Texture txButtonUp = new Texture(Gdx.files.internal("GameButtonUp.png"));
    protected static Texture txButtonDown = new Texture(Gdx.files.internal("GameButtonDown.png"));
    protected Map map;
    boolean showButtons = false;

    public ScrGame(GamMain gamMain) { //Would like to move the code from here into GameScreen constructor
        super.screenType = this.screenType;
        super.txButtonUp = this.txButtonUp;
        super.txButtonDown = this.txButtonDown;
        map = new Map(0);
        this.gamMain = gamMain;
        arkKeysPressed = new int[2];
    }

    @Override
    public void show() {
        map = new Map(0);
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        translate();
        //SprTile.resize();
        map.draw(batch);
        if (showButtons)
            for (SprButton sprButton : alsprButtons)
                if (sprButton.screenType != screenType)
                    sprButton.draw(batch);
        batch.end();
    }

    public void translate() {
        if (arkKeysPressed[0] == Input.Keys.W || arkKeysPressed[0] == Input.Keys.UP) {//UP
            map.sprPlayer.move(SprPlayer.Direction.NORTH, map);
        } else if (arkKeysPressed[0] == Input.Keys.S || arkKeysPressed[0] == Input.Keys.DOWN) {//DOWN
            map.sprPlayer.move(SprPlayer.Direction.SOUTH, map);
        }
        if (arkKeysPressed[1] == Input.Keys.A || arkKeysPressed[1] == Input.Keys.LEFT) {//LEFT
            map.sprPlayer.move(SprPlayer.Direction.WEST, map);
        } else if (arkKeysPressed[1] == Input.Keys.D || arkKeysPressed[1] == Input.Keys.RIGHT) {//RIGHT
            map.sprPlayer.move(SprPlayer.Direction.EAST, map);
        }
    }

    @Override
    public boolean keyDown(int keyCode) {   //Want to add keys, directions to a HashMap
        switch (keyCode) {
            case Input.Keys.W:
            case Input.Keys.S:
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                arkKeysPressed[0] = keyCode;
                break;
            case Input.Keys.A:
            case Input.Keys.D:
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                arkKeysPressed[1] = keyCode;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        arkKeysPressed[0] = (arkKeysPressed[0] == keyCode) ? 0 : arkKeysPressed[0];
        arkKeysPressed[1] = (arkKeysPressed[1] == keyCode) ? 1 : arkKeysPressed[1];
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int mouseButton) {
        if (showButtons) super.touchDown(screenX, screenY, pointer, mouseButton);
        return false;
    }
}
