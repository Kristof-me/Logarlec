package logarlec.view.panels;

import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import logarlec.model.actor.Actor;

public class ActorPanel extends View {
    public ActorPanel(Actor actor, String icon) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0);
        setMaximumSize(new Dimension(1000, 40));

        JLabel label = new JLabel(IconLoader.getInstance().getIcon(icon, 30));
        label.setForeground(actor.getColor());
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        JLabel nameLabel = new JLabel(actor.getName());
        nameLabel.setFont(ThemeManager.getInstance().getFont(20));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        if (actor.isAlive()) {
            nameLabel.setForeground(actor.getColor());
        } else {
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setText("<html><strike>" + actor.getName() + "</strike></html>");
        }


        add(label, gbc);
        add(nameLabel, gbc);
        
        gbc.weightx = 1;
        add(new JLabel(), gbc);
    }

    public void updateView() {
        // NOOP: the name and color of the actor won't change during the game
    }

    public View removeView() {
        return this;
    }
}
