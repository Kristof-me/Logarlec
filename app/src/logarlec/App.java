
package logarlec;

import java.util.Random;

import logarlec.App;
import logarlec.view.frames.MenuFrame;
import logarlec.view.utility.ThemeManager;

public class App {
    public static Random random = new Random(); // TODO change every App.random.nextDouble() to Math.random()
    public static void main(String[] args) {
        random.setSeed(0);
        ThemeManager.getInstance().loadTheme();

        // Game setup
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
        
    }
}
