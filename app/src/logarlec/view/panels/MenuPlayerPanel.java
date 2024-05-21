package logarlec.view.panels;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;

import logarlec.control.controller.Player;
import logarlec.view.elements.CustomButton;
import logarlec.view.frames.MenuFrame;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

import java.awt.*;
/**
 * A panel displaying a player in the menu
 */
public class MenuPlayerPanel extends JPanel{
    /**
     * The player to display
     */
    private Player player;
    /**
     * Creates a new player panel
     * @param player The player to display
     * @param menu The menu frame that contains the player
     */
    public MenuPlayerPanel(Player player, MenuFrame menu){
        super();
        Color panelColor = new Color(180, 180, 180);
        
        this.player = player;
        this.setLayout(new GridBagLayout());
        this.setMaximumSize(new Dimension(300, 40));
        this.setBackground(panelColor);

        GridBagConstraints constraints = new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0);

        //create a row that has a border with the players color, the players name in an editable field, and two buttons to the right
        JTextField name = new JTextField(player.getActor().getName());
        name.setBackground(panelColor);
        name.setBorder(new EmptyBorder(0, 0, 0, 0));

        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                player.getActor().setName(name.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                player.getActor().setName(name.getText());
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                player.getActor().setName(name.getText());
            }
        });
        
        this.add(name, constraints);
        //Refresh button to generate a new color
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        CustomButton reset = new CustomButton(IconLoader.getInstance().getIcon("refresh.png", 20), e -> {
            player.getActor().newRandomColor();
            drawBorder();
        });

        reset.setPreferredSize(new Dimension(20, 20));
        this.add(reset, constraints);
        reset.makeOpaque(false);
        //Delete button to remove the player
        constraints.fill = GridBagConstraints.NONE;
        CustomButton delete = new CustomButton(IconLoader.getInstance().getIcon("close.png", 20), e -> {
            menu.deletePlayer(player, this);
            this.setVisible(false);
        });
        delete.setPreferredSize(new Dimension(20, 20));
        this.add(delete, constraints);
        delete.makeOpaque(false);

        drawBorder();
    }
    /**
     * Draws a border around the panel with the players color
     */
    private void drawBorder() {
        setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(ThemeManager.TRACK, 2), 
            BorderFactory.createLineBorder(player.getActor().getColor(), 4))
        );
    }
}
