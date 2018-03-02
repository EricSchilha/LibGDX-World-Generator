package gdx.pengwin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public abstract class GameScreen implements Screen, InputProcessor {
    SpriteBatch batch;
    GamMain gamMain;
    OrthographicCamera oc;
    ScreenType screenType;
    Texture txButtonUp, txButtonDown;
    ArrayList<Button> alButtons = new ArrayList<>();

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        GameScreen screen;
        for (int i = 0; i < gamMain.alScreens.size(); i++) {//Want to change this
            screen = (GameScreen) gamMain.alScreens.get(i);
            alButtons.add(new Button(Button.BUTTON_WIDTH, Button.BUTTON_HEIGHT, 0, Gdx.graphics.getHeight() - 50, screen.txButtonUp, screen.txButtonDown, screen.screenType));
        }
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        for (Button button : alButtons)
            button.draw(batch);
        batch.end();
        dispose();
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
    public void dispose() {
        batch.dispose();
        txButtonUp.dispose();
        txButtonDown.dispose();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int mouseButton) {
        if (mouseButton == Input.Buttons.LEFT)
            for (Button button : alButtons)
                if (button.getBoundingRectangle().overlaps(new Rectangle(screenX, screenY, screenX, screenY))) {
                    //button.setTexture((GameScreen)gamMain.alScreens.get(alButtons.indexOf(button)).txButtonDown); //PROBLEMS
                }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int mouseButton) {
        if (mouseButton == Input.Buttons.LEFT)
            for (Button button : alButtons)
                if (!button.isPressed) {
                    gamMain.update(button.screenType);
                    //button.setTexture((GameScreen)gamMain.alScreens.get(alButtons.indexOf(button)).txButtonUp); //PROBLEMS
                }
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
