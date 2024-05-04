package logarlec.view.panels;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import logarlec.view.elements.CustomButton;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.Player;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PlayerPanel extends View {
    Student viewedPlayer;
    Controller player;

        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        CustomButton reset = new CustomButton(IconLoader.getInstance().getIcon("fa-solid--bible.svg", 18), null);
        
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
    
    @Override
    public void updateView() {

    }
}
