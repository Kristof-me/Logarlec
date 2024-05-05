package logarlec.view.panels;

import javax.swing.*;
import java.awt.*;
/**
 * DoorsPanel
 */
public class DoorListPanel extends JPanel {

    public DoorListPanel(int axis) {
        this.setLayout(new BoxLayout(this, axis));
        if (axis == BoxLayout.Y_AXIS){
            this.setPreferredSize(new Dimension(100, 500));
        }
        else {
            this.setPreferredSize(new Dimension(500, 100));
        }
    }

    public void addDoor(JPanel doorPanel) {
        this.add(doorPanel);
    }

}