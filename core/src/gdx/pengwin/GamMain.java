package gdx.pengwin;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.Arrays;

public class GamMain extends Game {
    ArrayList<GameScreen> alScreens = new ArrayList<GameScreen>();
    //public static ScreenType currentScreen;

    public void update(GameScreen screen) {
        //System.out.println("GamMain update() called.");{
        //System.out.println("Screen \"" + screen.screenType + "\" set.");
        setScreen(screen);
    }

    @Override
    public void create() {
        //System.out.println("GamMain create() called.");
        alScreens.addAll(Arrays.asList(
                new ScrMenu(this),
                new ScrGame(this)
        ));
        //currentScreen = ScreenType.Menu;
        //update(currentScreen);
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
