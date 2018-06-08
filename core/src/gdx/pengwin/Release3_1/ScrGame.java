package gdx.pengwin.Release3_1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ScrGame extends GameScreen {
    protected static ScreenType screenType = ScreenType.Game;
    protected static Texture txButtonUp = new Texture(Gdx.files.internal("GameButtonUp.png"));
    protected static Texture txButtonDown = new Texture(Gdx.files.internal("GameButtonDown.png"));
    protected static int nSeed;
    protected Map map;
    boolean showButtons = false;

    public ScrGame(GamMain gamMain) { //TODO: move the code from here into GameScreen constructor (maybe)
        this.gamMain = gamMain;
        this.nSeed = (int)(Math.random()* 100000000);
        System.out.println(nSeed);
        super.screenType = this.screenType;
        super.txButtonUp = this.txButtonUp;
        super.txButtonDown = this.txButtonDown;
        //PROBLEMS
        //Spawn In Tree Seed: 54530980
        //Top of Tree Off Seed: 12218387
        //Water Collision Problems: 97531629
        map = new Map(50204515);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        map.update();
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
                map.sprPlayer.arnKeys[0] = 1;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                map.sprPlayer.arnKeys[1] = 1;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                map.sprPlayer.arnKeys[2] = 1;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                map.sprPlayer.arnKeys[3] = 1;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                map.sprPlayer.arnKeys[0] = 0;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                map.sprPlayer.arnKeys[1] = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                map.sprPlayer.arnKeys[2] = 0;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                map.sprPlayer.arnKeys[3] = 0;
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
