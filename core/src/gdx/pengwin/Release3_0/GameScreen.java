package gdx.pengwin.Release3_0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameScreen implements Screen, InputProcessor {
    public SpriteBatch batch;
    GamMain gamMain;
    OrthographicCamera oc;
    ShapeRenderer sr;
    ScreenType screenType;
    Texture txButtonUp, txButtonDown;
    ArrayList<SprButton> alsprButtons = new ArrayList<SprButton>();

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        alsprButtons.clear();
        alsprButtons.addAll(Arrays.asList(
                new SprButton(SprButton.BUTTON_WIDTH, SprButton.BUTTON_HEIGHT, 0, Gdx.graphics.getHeight() - SprButton.BUTTON_HEIGHT, ScrMenu.txButtonUp, ScrMenu.screenType),
                new SprButton(SprButton.BUTTON_WIDTH, SprButton.BUTTON_HEIGHT, Gdx.graphics.getWidth() - SprButton.BUTTON_WIDTH, Gdx.graphics.getHeight() - SprButton.BUTTON_HEIGHT, ScrGame.txButtonUp, ScrGame.screenType)
        ));
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(oc.combined);
        for (SprButton sprButton : alsprButtons)
            if (sprButton.screenType != screenType)
                sprButton.draw(batch);
        batch.end();
        sr.end();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int mouseButton) {
        if (mouseButton == Input.Buttons.LEFT)
            for (SprButton sprButton : alsprButtons)
                if (sprButton.getBoundingRectangle().contains(screenX, screenY)) {
                    for (GameScreen screen : gamMain.alScreens)
                        if (screen.screenType == sprButton.screenType)
                            alsprButtons.get(alsprButtons.indexOf(sprButton)).setTexture(screen.txButtonDown);
                }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int mouseButton) {
        if (mouseButton == Input.Buttons.LEFT)
            for (int i = 0; i < alsprButtons.size(); i++) //Using the other type of for loop caused an error (java.util.ConcurrentModificationException)
                if (alsprButtons.get(i).isPressed)
                    for (GameScreen screen : gamMain.alScreens)
                        if (screen.screenType == alsprButtons.get(i).screenType) {
                            alsprButtons.get(i).setTexture(screen.txButtonDown);
                            gamMain.update(screen);
                        }
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        txButtonUp.dispose();
        txButtonDown.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
