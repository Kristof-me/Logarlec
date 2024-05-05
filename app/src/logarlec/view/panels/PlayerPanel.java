package logarlec.view.panels;

import logarlec.control.GameManager;
import logarlec.control.controller.Controller;
import logarlec.control.controller.Player;
import logarlec.model.actor.Student;
import logarlec.view.elements.CustomButton;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.ColorGenerator;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PlayerPanel extends View {
    protected Student viewedPlayer;
    public Student getViewedPlayer() {
        return viewedPlayer;
    }
    protected Player player;
    protected JLabel nameLabel;

    public PlayerPanel(Student student) {
        super();
        this.viewedPlayer = student;
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        this.setPreferredSize(new Dimension(800, 50));
        nameLabel = new JLabel("placeholder");
        nameLabel.setOpaque(true);
        nameLabel.setBorder(new LineBorder(Color.BLACK, 5));
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setPreferredSize(new Dimension(100, 50));
        
        this.add(nameLabel, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 2;
        c.weighty = 1;
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.CENTER;
        this.add(viewedPlayer.getInventory().createOwnView(), c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.EAST;
        CustomButton endTurnButton = new CustomButton("End Turn");
        endTurnButton.setPreferredSize(new Dimension(100, 50));
        this.add(endTurnButton, c);
    }
    
    public void bindPlayer(Player player) {
        this.player = player;
        Student student = player.getActor();
        
        nameLabel.setText(student.getName());
        nameLabel.setBackground(student.getColor());
        nameLabel.setForeground(ColorGenerator.getInstance().getForegroundColor(student.getColor()));
        nameLabel.revalidate();
        nameLabel.repaint();
    }
    @Override
    public void updateView() {
        
    }
}
