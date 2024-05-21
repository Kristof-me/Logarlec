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
/**
 * A panel displaying an item, with a generic T item
 */
public class ItemPanel<T extends Item> extends View {
    /**
     * The item to display
     */
    protected T item;
    /**
     * The icon to display next to the item
     */
    protected String icon;
    /**
     * The label displaying the number of uses left
     */
    protected JLabel usesLeft;
    /**
     * The label displaying the icon
     */
    protected JLabel iconLabel;
    /**
     * The popup menu for the item when it right clicked
     */
    protected JPopupMenu popupMenu;
    /**
     * Whether the item is selected
     */
    boolean isSelected = false;
    /**
     * Creates a new item panel
     * @param item The item to display
     * @param icon The icon to display next to the item
     */
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

        // add a number at bottom right corner to display the number of uses left
        usesLeft = new JLabel(item.getUsesLeft().toString());
        usesLeft.setOpaque(false);
        usesLeft.setForeground(Color.WHITE);
        usesLeft.setHorizontalAlignment(SwingConstants.RIGHT);
        usesLeft.setVerticalAlignment(SwingConstants.BOTTOM);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        this.add(usesLeft, gbc);
        //Create the popup menu
        popupMenu = new JPopupMenu();
        addPopupmenuListener(popupMenu);
        popupMenu.setBackground(ThemeManager.BACKGROUND);
        popupMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        this.setComponentPopupMenu(popupMenu);

        updateView();
    }
    /**
     * Updates the view of the item panel to reflect the current state of the item.
     * This includes updating the number of uses left and the popup menu depending on whether the item is equipped or not.
     */
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
    /**
     * Sets the colors of the item panel based on whether the item is equipped or not.
     */
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
    /**
     * Returns the icon of the item, used when creating a fake item panel
     * @return
     */
    public String getIcon() {
        return icon;
    }
    /**
     * Sets the popup menu for the item when it is equipped with a Use and Drop option
     */
    protected void setActorPopupMenu() {
        popupMenu.removeAll();
        // add a use option
        JMenuItem useItem = new JMenuItem("Use");
        useItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().use(item);
        });

        useItem.setBackground(ThemeManager.BACKGROUND);
        popupMenu.add(useItem);
        
        // add a drop option
        JMenuItem dropItem = new JMenuItem("Drop");
        dropItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().drop(item);
        });

        dropItem.setBackground(ThemeManager.BACKGROUND);
        popupMenu.add(dropItem);
    }
    /**
     * Sets the popup menu for the item when it is in a room with a Pick up option
     */
    protected void setRoomPopupMenu() {
        popupMenu.removeAll();
        // add a pick up option
        JMenuItem pickupItem = new JMenuItem("Pick up");
        pickupItem.addActionListener(e -> {
            GameManager.getInstance().getCurrentPlayer().pickUp(item);
        });

        pickupItem.setBackground(ThemeManager.BACKGROUND);
        popupMenu.add(pickupItem);
    }
    /**
     * Adds a popup menu listener to the popup menu
     * @param popupMenu The popup menu to add the listener to
     */
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
