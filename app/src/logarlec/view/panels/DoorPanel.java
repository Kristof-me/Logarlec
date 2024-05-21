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
 * A panel displaying a door
 */
public class DoorPanel extends View {
    /**
     * The door to display
     */
    private Door door;
    /**
     * The button that allows the player to move through the door
     */
    private CustomButton button;
    /**
     * Creates a new door panel
     * @param door The door to display
     */
    public DoorPanel(Door door) {
        this.setLayout(new GridLayout(2, 1));
        this.door = door;
        
        setPreferredSize(new Dimension(85, 85));
        setMaximumSize(new Dimension(85, 85));
        setBackground(ThemeManager.BUTTON);
    }
    /**
     * Binds the room to the panel, allowing us to display the opposite room name below the icon
     * @param room The room to bind, which is the room from which the door is viewed
     */
    public void bindRoom(Room room) {
        if (door.leadsTo(room) == null) return; //If the door leads to nowhere, don't display it (it's a one-way door)
        if (button != null)
            remove(button);

        button = new CustomButton(IconLoader.getInstance().getIcon("door.png", 50), e -> {
            Player player = GameManager.getInstance().getCurrentPlayer();
            boolean success = player.move(door);
            
            //If the player moved successfully and a student died, display a message
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
