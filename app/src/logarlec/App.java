
package logarlec;

import logarlec.view.frames.MenuFrame;
import logarlec.view.utility.UiManager;

public class App {
    public static void main(String[] args) {
        UiManager.getInstance().loadTheme();
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
