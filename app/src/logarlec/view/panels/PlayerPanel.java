package logarlec.view.panels;

import logarlec.control.GameManager;
import logarlec.control.controller.Controller;
import logarlec.control.controller.Player;
import logarlec.model.actor.Student;
import logarlec.view.elements.CustomButton;
import logarlec.view.observerviews.View;
import logarlec.view.utility.ColorGenerator;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PlayerPanel extends View {
    Student viewedPlayer;
    Controller player;

    public PlayerPanel(Student student) {
        super();
        this.viewedPlayer = student;
        GridLayout layout = new GridLayout(1, 3);
        layout.setHgap(150);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(800, 75));
        player = GameManager.getInstance().getControllerForActor(viewedPlayer);
        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setOpaque(true);
        nameLabel.setBackground(player.getColor()); 
        nameLabel.setBorder(new LineBorder(Color.BLACK, 5));
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setForeground(ColorGenerator.getInstance().getForegroundColor(player.getColor()));
        this.add(nameLabel, BorderLayout.WEST);
        this.add(new JLabel("inventory"), BorderLayout.CENTER);
        this.add(new CustomButton("End Turn"), BorderLayout.EAST);
    }
    
    @Override
    public void updateView() {

    }
}
