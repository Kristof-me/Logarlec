package logarlec.view.panels;

import java.awt.*;

import javax.swing.*;

import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.ThemeManager;

/**
 * A panel displaying an effect
 */
public abstract class EffectPanel extends View {
    /**
     * The label displaying the number of turns left
     */
    private JLabel turnsLabel;
    /**
     * Creates a new effect panel
     * @param backGround The background color of the panel, used to differentiate between positive and negative effects
     * @param description The flavor text of the effect
     * @param icon The icon to display next to the effect
     */
    protected EffectPanel(Color backGround, String description, ImageIcon icon) {
        super();

        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(130, 75));
        this.setMaximumSize(new Dimension(130, 75));
        this.setMinimumSize(new Dimension(130, 75));
        this.setBackground(backGround);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;

        turnsLabel = new JLabel("Turns left: ");
        this.add(turnsLabel, c);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 1;
        JLabel iconLabel = new JLabel(icon);
        this.add(iconLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(ThemeManager.getInstance().getFont(Font.ITALIC, 10));
        this.add(descriptionLabel, c);
    }
    /**
     * Sets the number of turns left for the effect
     * @param turnsLeft The number of turns left
     */
    protected void setTurnsLeft(Integer turnsLeft) {
        String displayed = turnsLeft == Integer.MAX_VALUE ? "∞" : turnsLeft.toString();
        turnsLabel.setText("Turns left: " + displayed); 

        GameFrame.getInstance().getEffectListPanel().revalidate();
        GameFrame.getInstance().getEffectListPanel().repaint();
    }
}
