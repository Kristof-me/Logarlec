package logarlec.view.panels;

import logarlec.view.observerviews.View;
import javax.swing.*;
import java.awt.*;

import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.view.elements.*;
import logarlec.view.utility.*;
import logarlec.control.GameManager;
/**
 * DoorPanel
 */
public class DoorPanel extends View {

    private Door door;
    CustomButton btn;
    public DoorPanel(Room from, Door door) {
        super();
        this.setLayout(new GridLayout(1, 1));
        btn = new CustomButton(IconLoader.getInstance().getIcon("door.png", 50), "Door", e ->{
            GameManager.getInstance().getCurrentPlayer().move(door);
        });
        this.add(btn);
    }
	@Override
	public void updateView() {
        btn = new CustomButton(IconLoader.getInstance().getIcon("door.png", 50), "Door", e ->{
            GameManager.getInstance().getCurrentPlayer().move(door);
        });
	}

    
}