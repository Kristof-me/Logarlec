
package logarlec;

import logarlec.view.frames.MenuFrame;
import logarlec.view.utility.ThemeManager;

public class App {
    public static void main(String[] args) {
        ThemeManager.getInstance().loadTheme();
        boolean readyToExit = false;

        // Game setup
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
        /* 
        while (!readyToExit) {
            ...
        } 
        */
    }
}
