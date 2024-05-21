package logarlec.view.panels;

import javax.swing.*;

import logarlec.view.utility.ThemeManager;

import java.awt.*;

/**
 * A panel displaying an empty item slot
 */
public class EmptyItemPanel extends JPanel {
    /**
     * Creates a new empty item panel
     * @param darkBackground Whether the panel should have a dark background
     */
    public EmptyItemPanel(boolean darkBackground){
        super();
        this.setLayout(new GridLayout(1,1));
        this.setPreferredSize(new Dimension(50, 50));
        this.setMaximumSize(new Dimension(50, 50));

        this.setBackground(darkBackground ? ThemeManager.BACKGROUND_DARK : ThemeManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(darkBackground ? ThemeManager.BACKGROUND : ThemeManager.BUTTON, 2));
    }
}
