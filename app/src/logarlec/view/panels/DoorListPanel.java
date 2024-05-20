package logarlec.view.panels;

import javax.swing.*;

import logarlec.view.utility.ThemeManager;

import java.awt.*;
/**
 * DoorsPanel
 */
public class DoorListPanel extends JPanel {

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

    public void addDoor(JPanel doorPanel) {
        this.add(doorPanel);
    }

}