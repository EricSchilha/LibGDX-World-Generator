package gdx.pengwin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.pengwin.GamMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "PENGWIN";
        config.width = 1000;
        config.height = 750;
        //config.resizable = true;
        new LwjglApplication(new GamMain(), config);
	}
}
