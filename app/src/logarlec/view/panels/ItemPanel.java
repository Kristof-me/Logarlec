package logarlec.view.panels;

import logarlec.control.GameManager;
import logarlec.model.items.Item;
import logarlec.view.observerviews.View;
import logarlec.view.utility.IconLoader;
import logarlec.view.utility.ThemeManager;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class ItemPanel<T extends Item> extends View {
    protected T item;
    protected String icon;

    protected JLabel usesLeft;
    protected JLabel iconLabel;
    protected JPopupMenu popupMenu;
    boolean isSelected = false;

    public ItemPanel(T item, String icon) {
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

        // add a number at bottom right corner
        usesLeft = new JLabel(item.getUsesLeft().toString());
        usesLeft.setOpaque(false);
        usesLeft.setForeground(Color.WHITE);
        usesLeft.setHorizontalAlignment(SwingConstants.RIGHT);
        usesLeft.setVerticalAlignment(SwingConstants.BOTTOM);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        this.add(usesLeft, gbc);

        popupMenu = new JPopupMenu();
        addPopupmenuListener(popupMenu);
        popupMenu.setBackground(ThemeManager.BACKGROUND);
        popupMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        this.setComponentPopupMenu(popupMenu);

        updateView();
    }

    @Override
    public void updateView() {
        usesLeft.setText(item.getUsesLeft().toString());
        
        if (item.isEquipped()) {
            setActorPopupMenu();
        } else {
            setRoomPopupMenu();
        }

        setColors();
    }

    protected void setColors() {
        boolean darkBackground = !item.isEquipped();

        if(isSelected) {
            this.setBackground(ThemeManager.TRACK);
        } else {
            this.setBackground(darkBackground ? ThemeManager.BACKGROUND_DARK : ThemeManager.BACKGROUND);
        }

        this.setBorder(BorderFactory.createLineBorder(darkBackground ? ThemeManager.BACKGROUND : ThemeManager.BUTTON, 2));
        repaint();
    }

    public String getIcon() {
        return icon;
    }

    protected void setActorPopupMenu() {
        popupMenu.removeAll();

        JMenuItem useItem = new JMenuItem("Use");
        useItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().use(item);
        });

        useItem.setBackground(ThemeManager.BACKGROUND);
        popupMenu.add(useItem);
        
        JMenuItem dropItem = new JMenuItem("Drop");
        dropItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().drop(item);
        });

        dropItem.setBackground(ThemeManager.BACKGROUND);
        popupMenu.add(dropItem);
    }

    protected void setRoomPopupMenu() {
        popupMenu.removeAll();

        JMenuItem pickupItem = new JMenuItem("Pick up");
        pickupItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().pickUp(item);
            System.out.println("Picked up " + item.getClass().getSimpleName());
        });

        pickupItem.setBackground(ThemeManager.BACKGROUND);
        popupMenu.add(pickupItem);
    }

    void addPopupmenuListener(JPopupMenu popupMenu) {
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                isSelected = true;
                setColors();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                isSelected = false;
                setColors();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                isSelected = false;
                setColors();
            }
            
        });
    }

    @Override
    public View removeView() {
        item.removeListener(this);
        return this;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension original = super.getPreferredSize();
        int minSize = Math.min(original.width, original.height);
        return new Dimension(minSize, minSize);
    }
}
