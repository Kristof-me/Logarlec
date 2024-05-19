
package logarlec;

import logarlec.control.GameManager;
import logarlec.control.controller.Player;
import logarlec.model.actor.Student;
import logarlec.model.items.impl.AirFreshener;
import logarlec.model.items.impl.Beer;
import logarlec.model.items.impl.Transistor;
import logarlec.model.items.impl.Tvsz;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.view.frames.GameFrame;
import logarlec.view.frames.MenuFrame;
import logarlec.view.utility.ThemeManager;

public class App {
    public static void main(String[] args) {
        ThemeManager.getInstance().loadTheme();
        boolean readyToExit = false;

        // Game setup
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
        
    }
}
