
package logarlec;

import logarlec.view.frames.MenuFrame;
import logarlec.view.utility.ThemeManager;

public class App {
    public static void main(String[] args) {
        ThemeManager.getInstance().loadTheme();

        // Game setup
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
        
    }
}
