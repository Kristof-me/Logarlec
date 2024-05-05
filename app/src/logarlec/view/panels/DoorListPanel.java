package logarlec.view.panels;

import javax.swing.*;
import java.awt.*;
/**
 * DoorsPanel
 */
public class DoorListPanel extends JPanel {

    public DoorListPanel(int axis) {
        this.setLayout(new BoxLayout(this, axis));
    }
    
}