package gdx.pengwin.Release2_0;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.Arrays;

public class GamMain extends Game {
    ArrayList<GameScreen> alScreens = new ArrayList<GameScreen>();

    public void update(GameScreen screen) {
        setScreen(screen);
    }

    @Override
    public void create() {
        alScreens.addAll(Arrays.asList(
                new ScrMenu(this),
                new ScrGame(this)
        ));
        update(alScreens.get(0));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
