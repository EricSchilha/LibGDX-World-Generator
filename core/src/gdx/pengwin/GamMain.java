package gdx.pengwin;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.Arrays;

public class GamMain extends Game {
    ArrayList<Object> alScreens = new ArrayList<>();
    public static ScreenType currentScreen;

    public void update(ScreenType screenType) {
        System.out.println("GamMain update() called.");
        GameScreen screen;
        for (int i = 0; i < alScreens.size(); i++) {
            screen = (GameScreen) alScreens.get(i);
            if (screen.screenType == screenType) {
                setScreen(screen);
                break;
            }
        }
    }

	@Override
	public void create () {
        System.out.println("GamMain create() called.");
        alScreens.addAll(Arrays.asList(
                new ScrMenu(this),
                new ScrGame(this)
        ));
        currentScreen = ScreenType.Menu;
        update(currentScreen);

    }


	@Override
	public void render () {
        super.render();
    }

    @Override
	public void dispose () {
        super.dispose();
    }
}
