
package logarlec;

import logarlec.control.GameManager;
import logarlec.control.controller.Player;
import logarlec.model.actor.Student;
import logarlec.model.items.impl.AirFreshener;
import logarlec.model.items.impl.Transistor;
import logarlec.model.items.impl.Tvsz;
import logarlec.view.frames.GameFrame;
import logarlec.view.frames.MenuFrame;
import logarlec.view.panels.PlayerPanel;
import logarlec.view.utility.ThemeManager;

public class App {
    public static void main(String[] args) {
        ThemeManager.getInstance().loadTheme();
        boolean readyToExit = false;

        // Game setup
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
        GameFrame gameFrame = GameFrame.getInstance();
        Student s = new Student();
        Player p = new Player();
        p.setActor(s);
        p.setName("asd");
        s.getInventory().addItem(new AirFreshener());
        s.getInventory().addItem(new Tvsz());
        Transistor a = new Transistor();
        s.getInventory().addItem(a);
        s.getInventory().addItem(new Transistor());
        s.use(a);
        Transistor b = new Transistor();
        s.getInventory().addItem(b);
        s.getInventory().addItem(new Transistor());
        s.use(b);
        GameManager.getInstance().addPlayer(p);
        gameFrame.setVisible(true);
        gameFrame.setPlayerPanel(s.createOwnView());
        /* 
        while (!readyToExit) {
            ...
        } 
        */
    }
}
