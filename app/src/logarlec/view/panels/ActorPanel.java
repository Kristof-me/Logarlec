package logarlec.view.panels;

import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;

import logarlec.model.actor.Actor;

public class ActorPanel extends View {
    public ActorPanel(Actor actor, String icon) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));

        JLabel label = new JLabel(IconLoader.getInstance().getIcon(icon, 30));
        label.setForeground(actor.getColor());

        JLabel nameLabel = new JLabel(actor.getName());
        nameLabel.setFont(ThemeManager.getInstance().getFont(20));

        if (actor.isAlive()) {
            nameLabel.setForeground(actor.getColor());
        } else {
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setText("<html><strike>" + actor.getName() + "</strike></html>");
        }

        add(label);
        add(nameLabel);
    }

    public void updateView() {
        // NOOP: the name and color of the actor won't change during the game
    }

    public View removeView() {
        return this;
    }
}
