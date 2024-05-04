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
        this.setMaximumSize(new Dimension(300, 40));
        GridBagConstraints constraints = new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0);

        //create a row that has a border with the players color, the players name in an editable field, and two buttons to the right
        JTextField name = new JTextField(player.getName());
        
        name.setBackground(new Color(238, 238, 238));
        name.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(name, constraints);

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

        constraints.fill = GridBagConstraints.NONE;
        Button delete = new Button("D");
        // TODO delete player onclick
        delete.setPreferredSize(new Dimension(20, 20));
        this.add(delete, constraints);

        drawBorder();
    }

    private void drawBorder(){
        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(player.getColor(), 2)));
    }
}
