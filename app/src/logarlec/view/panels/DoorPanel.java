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
    private Room room;
    private CustomButton btn;

    public DoorPanel(Door door) {
        super();
        this.setLayout(new GridLayout(2, 1));
        this.door = door;
        setPreferredSize(new Dimension(85, 85));
        setMaximumSize(new Dimension(85, 85));
        //add label to second row
    }

    public void bindRoom(Room room) {
        this.room = room;
        updateView();
    }

	@Override
	public void updateView() {
        if (btn != null) remove(btn);
        
        btn = new CustomButton(IconLoader.getInstance().getIcon("door.png", 50), e ->{
            GameManager.getInstance().getCurrentPlayer().move(door);
        });
        
        add(btn);
        add(new JLabel("Room #" + door.leadsTo(room).getId()));

        revalidate();
        repaint();
	}

    
}