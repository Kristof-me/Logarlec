package logarlec.view.panels;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import logarlec.view.utility.Player;

import java.awt.*;

public class PlayerPanel extends JPanel{
    private Player player;
    public PlayerPanel(Player player){
        super();
        this.player = player;
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //create a row that has a border with the players color, the players name in an editable field, and two buttons to the right
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        JTextField name = new JTextField(player.getName());
        name.setBackground(new Color(238, 238, 238));
        name.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(name, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        Button reset = new Button("R");
        //reset onclick
        reset.addActionListener(e -> {
            player.generateColor();
            //redraw the border
            drawBorder();
        });
        reset.setPreferredSize(new Dimension(20, 20));
        this.add(reset, constraints);

        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        Button delete = new Button("D");
        delete.setPreferredSize(new Dimension(20, 20));
        this.add(delete, constraints);

        drawBorder();
        
    }

    private void drawBorder(){
        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(player.getColor(), 2)));
    }
}
