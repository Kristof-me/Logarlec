package logarlec.view.panels;

import java.awt.*;

import javax.swing.*;

import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;

public abstract class EffectPanel extends View {
    private JLabel turnsLabel;

    public EffectPanel(Color backGround, String description, ImageIcon icon) {
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

        turnsLabel = new JLabel("");
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
        this.add(descriptionLabel, c);
    }

    protected void setTurnsLeft(int turnsLeft){
        turnsLabel.setText("Turns left: " + turnsLeft);
        
        GameFrame.getInstance().getEffectListPanel().revalidate();
        GameFrame.getInstance().getEffectListPanel().repaint();
    }
}
