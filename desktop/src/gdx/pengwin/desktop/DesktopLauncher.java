package gdx.pengwin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    public static void main (String[] arg) {
        config.title = "PENGWIN";
        config.width = 800;
        config.height = 800;
        //config.resizable = false;
        new LwjglApplication(new gdx.pengwin.Release2_0.GamMain(), config);
    }
}
