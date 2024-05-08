package logarlec.view.panels;

import javax.swing.*;
import java.awt.*;


public class EmptyItemPanel extends JPanel {
    public EmptyItemPanel(){
        super();
        this.setLayout(new GridLayout(1,1));
        this.setPreferredSize(new Dimension(50, 50));
        this.setMaximumSize(new Dimension(50, 50));
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }
}
