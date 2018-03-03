package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameScreen implements Screen, InputProcessor {
    SpriteBatch batch;
    GamMain gamMain;
    OrthographicCamera oc;
    ScreenType screenType;
    Texture txButtonUp, txButtonDown;
    ArrayList<Button> alButtons = new ArrayList<Button>();

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        alButtons.clear();
        alButtons.addAll(Arrays.asList(
                new Button(Button.BUTTON_WIDTH, Button.BUTTON_HEIGHT, 0, Gdx.graphics.getHeight() - Button.BUTTON_HEIGHT, ScrMenu.txButtonUp, ScrMenu.screenType),
                new Button(Button.BUTTON_WIDTH, Button.BUTTON_HEIGHT, Gdx.graphics.getWidth() - Button.BUTTON_WIDTH, Gdx.graphics.getHeight() - Button.BUTTON_HEIGHT, ScrGame.txButtonUp, ScrGame.screenType)
        ));
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        for (Button button : alButtons)
            if (button.screenType != screenType)
                button.draw(batch);
        batch.end();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int mouseButton) {
        if (mouseButton == Input.Buttons.LEFT)
            for (Button button : alButtons)
                if (button.getBoundingRectangle().contains(screenX, screenY)) {
                    for (GameScreen screen : gamMain.alScreens)
                        if (screen.screenType == button.screenType)
                            alButtons.get(alButtons.indexOf(button)).setTexture(screen.txButtonDown);
                }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int mouseButton) {
        if (mouseButton == Input.Buttons.LEFT)
            //for(Button button : alButtons)
            for (int i = 0; i < alButtons.size(); i++) //Using the other type of for loop caused an error (java.util.ConcurrentModificationException)
                if (alButtons.get(i).isPressed)
                    for (GameScreen screen : gamMain.alScreens)
                        if (screen.screenType == alButtons.get(i).screenType) {
                            alButtons.get(i).setTexture(screen.txButtonDown);
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
