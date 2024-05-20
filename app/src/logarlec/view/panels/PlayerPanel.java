package logarlec.view.panels;

import logarlec.control.GameManager;
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

    protected JLabel nameLabel;
    protected InventoryPanel inventoryPanel;
    CustomButton endTurnButton;
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
        nameLabel = new JLabel("-");
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

        inventoryPanel = viewedPlayer.getInventory().createOwnView();
        this.add(inventoryPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.EAST;

        endTurnButton =
                new CustomButton("End Turn (" + GameManager.getInstance().getStepCount() + ")",
                 e -> GameManager.getInstance().getCurrentPlayer().skipTurn());

        endTurnButton.setPreferredSize(new Dimension(100, 50));
        this.add(endTurnButton, c);

        nameLabel.setText(student.getName());
        nameLabel.setBackground(student.getColor());
        nameLabel.setForeground(ColorGenerator.getInstance().getForegroundColor(student.getColor()));
    }

    @Override
    public void updateView() {
        long stepCount = GameManager.getInstance().getStepCount();
        if(stepCount == 0) {
            System.out.println("it's 0");
        }
        
        endTurnButton.setText("End Turn (" + GameManager.getInstance().getStepCount() + ")");
        GameFrame.getInstance().updateStudent();
    }

    @Override
    public View removeView() {
        if (inventoryPanel != null)
            remove(inventoryPanel.removeView());
        viewedPlayer.removeListener(this);
        return this;
    }
}
