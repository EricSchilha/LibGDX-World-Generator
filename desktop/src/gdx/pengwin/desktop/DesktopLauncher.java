package gdx.pengwin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    public static void main (String[] args) {
        int nScale = 6;
        config.title = "PENGWIN";
        config.width = 160*nScale;
        config.height = 120*nScale;
        new LwjglApplication(new gdx.pengwin.Release3_1.GamMain(), config);
    }
}
