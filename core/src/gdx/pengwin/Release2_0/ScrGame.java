package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        mapMap = new Map(0);
        this.gamMain = gamMain;
        arkKeysPressed = new int[2];
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
        if (arkKeysPressed[0] == Input.Keys.W || arkKeysPressed[0] == Input.Keys.UP) {//UP
            mapMap.player.move(Player.Direction.NORTH);
        } else if (arkKeysPressed[0] == Input.Keys.S || arkKeysPressed[0] == Input.Keys.DOWN) {//DOWN
            mapMap.player.move(Player.Direction.SOUTH);
        }
        if (arkKeysPressed[1] == Input.Keys.A || arkKeysPressed[1] == Input.Keys.LEFT) {//LEFT
            mapMap.player.move(Player.Direction.WEST);
        } else if (arkKeysPressed[1] == Input.Keys.D || arkKeysPressed[1] == Input.Keys.RIGHT) {//RIGHT
            mapMap.player.move(Player.Direction.EAST);
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
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
