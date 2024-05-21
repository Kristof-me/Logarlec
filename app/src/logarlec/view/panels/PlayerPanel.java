package logarlec.view.panels;

import logarlec.control.GameManager;
import logarlec.model.actor.Student;
import logarlec.view.elements.CustomButton;
import logarlec.view.frames.GameFrame;
import logarlec.view.observerviews.View;
import logarlec.view.utility.ColorGenerator;
import logarlec.view.utility.ThemeManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/**
 * A panel displaying the current player's name, inventory and an end turn button
 */
public class PlayerPanel extends View {
    /**
     * The student lof the player to display
     */
    protected Student viewedPlayer;
    /**
     * Gets the student being displayed
     * @return The student being displayed
     */
    public Student getViewedPlayer() {
        return viewedPlayer;
    }
    /**
     * The label displaying the player's name
     */
    protected JLabel nameLabel;
    /**
     * The panel displaying the player's inventory
     */
    protected InventoryPanel inventoryPanel;
    /**
     * The button that allows the player to end their turn early
     */
    CustomButton endTurnButton;
    /**
     * Creates a new player panel
     * @param student The student to display
     */
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

        this.setPreferredSize(new Dimension(800, 52));
        nameLabel = new JLabel("-");
        nameLabel.setOpaque(true);
        nameLabel.setBorder(new LineBorder(Color.BLACK, 5));
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setPreferredSize(new Dimension(180, 52));

        this.add(nameLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 2;
        c.weighty = 1;
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.CENTER;

        inventoryPanel = viewedPlayer.getInventory().createOwnView();
        inventoryPanel.setBorder(BorderFactory.createLineBorder(ThemeManager.TRACK, 1));
        this.add(inventoryPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.EAST;
        //Create the end turn button
        endTurnButton = new CustomButton("End Turn (" + GameManager.getInstance().getStepCount() + ")", e -> {
                 GameManager.getInstance().getCurrentPlayer().skipTurn();
                });

        endTurnButton.setPreferredSize(new Dimension(180, 50));
        endTurnButton.setBackground(ThemeManager.PRIMARY);
        this.add(endTurnButton, c);

        nameLabel.setText(" " + student.getName());
        nameLabel.setBackground(student.getColor());
        nameLabel.setForeground(ColorGenerator.getInstance().getForegroundColor(student.getColor()));
        nameLabel.setFont(ThemeManager.getInstance().getFont(Font.ITALIC, 18));
    }
    /**
     * Updates the view to reflect the current state of the observed student, calls the updateStudent of the GameFrame.
     * Also sets the text of the end turn button to reflect the current remaining step count.
     */
    @Override
    public void updateView() {
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
