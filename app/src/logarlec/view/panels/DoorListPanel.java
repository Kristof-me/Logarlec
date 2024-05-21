package logarlec.view.panels;

import javax.swing.*;

import logarlec.view.utility.ThemeManager;

import java.awt.*;
/**
 * A panel displaying a list of doors
 */
public class DoorListPanel extends JPanel {
    /**
     * Creates a new door list panel, either horizontal or vertical
     * @param axis The axis to lay out the doors on
     */
    public DoorListPanel(int axis) {
        this.setLayout(new BoxLayout(this, axis));
        setBackground(ThemeManager.BUTTON);

        if (axis == BoxLayout.Y_AXIS){
            this.setPreferredSize(new Dimension(100, 300));
        }
        else {
            this.setPreferredSize(new Dimension(300, 100));
        }
    }
    /**
     * Adds a door to the list
     * @param doorPanel The door panel to add
     */
    public void addDoor(JPanel doorPanel) {
        this.add(doorPanel);
    }

}