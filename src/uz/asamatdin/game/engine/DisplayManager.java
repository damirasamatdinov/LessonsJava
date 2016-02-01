package uz.asamatdin.game.engine;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

/**
 * Created by Damir on 2016-01-30.
 */
public class DisplayManager {

    private static final int WiDTH = 1024;
    private static final int HEIGHT = 640;
    private static final int FPS_CAP = 120;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3, 2);
        attribs.withForwardCompatible(true);
        attribs.withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WiDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("My Java Game");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WiDTH, HEIGHT);
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }

}
