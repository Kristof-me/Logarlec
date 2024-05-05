package logarlec.view.panels;

import logarlec.control.GameManager;
import logarlec.model.items.Item;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;

import java.awt.*;
import javax.swing.*;
public class ItemPanel <T extends Item> extends View {
    protected T item;
    protected String icon;

    protected JLabel usesLeft;
    protected JLabel iconLabel;
    protected JPopupMenu popupMenu;
    public ItemPanel(T item, String icon){
        super();
        this.item = item;
        this.icon = icon;

        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(50, 50));
        this.setMaximumSize(new Dimension(50, 50));
    
        iconLabel = new JLabel(IconLoader.getInstance().getIcon(icon, 35));
        iconLabel.setPreferredSize(new Dimension(50, 50));
        iconLabel.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(iconLabel, gbc);
    
        //add a number at bottom right corner
        usesLeft = new JLabel(item.getUsesLeft().toString());
        usesLeft.setOpaque(false);
        usesLeft.setForeground(Color.WHITE);
        usesLeft.setHorizontalAlignment(SwingConstants.RIGHT);
        usesLeft.setVerticalAlignment(SwingConstants.BOTTOM);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        this.add(usesLeft, gbc);
    
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        if (item.isEquipped()) {
            setActorPopupMenu();
        }
        else {
            setRoomPopupMenu();
        }
    }
    
    @Override
    public void updateView() {
        usesLeft.setText(item.getUsesLeft().toString());
    }

    public String getIcon() {
        return icon;
    }

    protected void setActorPopupMenu(){
        popupMenu = new JPopupMenu();
        JMenuItem useItem = new JMenuItem("Use");
        useItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().use(item);
        });
        popupMenu.add(useItem);
        JMenuItem dropItem = new JMenuItem("Drop");
        dropItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().drop(item);
        });
        popupMenu.add(dropItem);
        this.setComponentPopupMenu(popupMenu);
    }

    protected void setRoomPopupMenu(){
        popupMenu = new JPopupMenu();
        JMenuItem pickupItem = new JMenuItem("Pick up");
        pickupItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().pickUp(item);
        });
        popupMenu.add(pickupItem);
        this.setComponentPopupMenu(popupMenu);
    }
    
}
