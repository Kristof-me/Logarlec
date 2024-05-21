package logarlec.view.panels;

import logarlec.view.observerviews.View;
import javax.swing.*;
import java.awt.*;

import logarlec.model.actor.Student;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import logarlec.view.elements.*;
import logarlec.view.utility.*;
import logarlec.control.GameManager;
import logarlec.control.controller.Player;

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
        setBackground(ThemeManager.BUTTON);
    }

    public void bindRoom(Room room) {
        if (door.leadsTo(room) == null) return;
        if (button != null)
            remove(button);

        button = new CustomButton(IconLoader.getInstance().getIcon("door.png", 50), e -> {
            Player player = GameManager.getInstance().getCurrentPlayer();
            boolean success = player.move(door);
            
            
            Student student = player.getActor();
            if(success && !student.isAlive()) {
                JOptionPane.showMessageDialog(this, "\"" + student.getName() + "\" died in room " + student.getLocation().getName(), "Death", JOptionPane.PLAIN_MESSAGE);
            }
        });

        add(button);

        add(new JLabel(door.leadsTo(room).getName(), SwingConstants.CENTER)); 

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
