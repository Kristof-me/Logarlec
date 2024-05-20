package logarlec.view.panels;

import javax.swing.*;

import logarlec.view.utility.ThemeManager;

import java.awt.*;


public class EmptyItemPanel extends JPanel {
    public EmptyItemPanel(boolean darkBackground){
        super();
        this.setLayout(new GridLayout(1,1));
        this.setPreferredSize(new Dimension(50, 50));
        this.setMaximumSize(new Dimension(50, 50));

        this.setBackground(darkBackground ? ThemeManager.BACKGROUND_DARK : ThemeManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(darkBackground ? ThemeManager.BACKGROUND : ThemeManager.BUTTON, 2));
    }
}
