package logarlec.view.panels;

import logarlec.model.items.Item;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;

import java.awt.*;
import javax.swing.*;
public class ItemPanel extends View {

    Item item;
    String icon;

    JLabel usesLeft;
    public ItemPanel(Item item, String icon){
        super();
        this.item = item;
        this.icon = icon;
        this.setLayout(new GridLayout(1,1));
        this.setPreferredSize(new Dimension(50, 50));
        this.setMaximumSize(new Dimension(50, 50));
        JLabel iconLabel = new JLabel(new ImageIcon(IconLoader.getInstance().getImage(icon, 75)));
        iconLabel.setForeground(Color.WHITE);
        //add a number at bottom right corner
        // usesLeft = new JLabel(item.getUsesLeft() + "");
        // usesLeft.setForeground(Color.WHITE);
        // usesLeft.setHorizontalAlignment(SwingConstants.RIGHT);
        // usesLeft.setVerticalAlignment(SwingConstants.BOTTOM);
        // usesLeft.setForeground(Color.WHITE);
        // iconLabel.add(usesLeft);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }
    @Override
    public void updateView() {
        usesLeft.setText(item.getUsesLeft() + "");
    }

    public String getIcon() {
        return icon;
    }
    
}
