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
    private CustomButton button;

    public DoorPanel(Door door) {
        this.setLayout(new GridLayout(2, 1));
        this.door = door;

        setPreferredSize(new Dimension(85, 85));
        setMaximumSize(new Dimension(85, 85));
    }

    public void bindRoom(Room room) {
        if (button != null)
            remove(button);

        button = new CustomButton(IconLoader.getInstance().getIcon("door.png", 50), e -> {
            GameManager.getInstance().getCurrentPlayer().move(door);
        });

        add(button);
        add(new JLabel("Room #" + door.leadsTo(room).getId()));

        revalidate();
        repaint();
    }

    @Override
    public void updateView() {
        // NOOP: no updates needed
    }

    @Override
    public View removeView() {
        door.removeListener(this);
        return this;
    }
}
