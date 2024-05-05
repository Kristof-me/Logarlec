
package logarlec;

import logarlec.control.GameManager;
import logarlec.control.controller.Player;
import logarlec.model.actor.Student;
import logarlec.model.items.impl.AirFreshener;
import logarlec.model.items.impl.Beer;
import logarlec.model.items.impl.Transistor;
import logarlec.model.items.impl.Tvsz;
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

        GameFrame gameFrame = GameFrame.getInstance();
        new Thread(() -> {
            gameFrame.setVisible(true);
        }).start();

        Student s = new Student();
        Player p = new Player(s);
        p.getActor().setName("asd");
        GameManager.getInstance().addPlayer(p);
        
        
        s.getInventory().addItem(new AirFreshener());
        s.getInventory().addItem(new Tvsz());
        Transistor a = new Transistor();
        s.getInventory().addItem(a);
        s.getInventory().addItem(new Transistor());
        s.use(a);
        Room r = new Room(10);
        Beer beer = new Beer();
        
        r.addItem(beer);
        s.teleport(r, false);
        s.pickUp(beer);
        //s.setActionState(new StunnedStep(s));
        GameManager.getInstance().addPlayer(p);
        
        gameFrame.setVisible(true);
        p.takeTurn();
        //s.use(beer);
        s.tick();
        s.tick();
        s.tick();
        s.tick();
       // s.use(beer);
        
        GameManager.getInstance().startGame();

        while (!GameManager.getInstance().isGameOver()) {
            GameManager.getInstance().playTurn();
        }
    }
}
