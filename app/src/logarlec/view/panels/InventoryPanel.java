package logarlec.view.panels;

import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.view.observerviews.View;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
/**
 * A panel displaying the contents of an inventory in a grid
 */
public class InventoryPanel extends View {
    /**
     * The inventory to display
     */
    private Inventory inventory;
    /**
     * Whether the panel should have a dark background, large inventories have a dark background
     */
    private boolean isDark;
    /**
     * The item panels in the inventory
     */
    List<ItemPanel<? extends Item>> itemPanels = new ArrayList<ItemPanel<? extends Item>>();
    /**
     * Creates a new inventory panel
     * @param inventory The inventory to display
     */
    public InventoryPanel(Inventory inventory) {
        this.inventory = inventory;
        this.isDark = inventory.getSize() == Integer.MAX_VALUE;
        drawItems();
    }
    /**
     * Updates the view to reflect the current state of the inventory, redrawing the items
     */
    @Override
    public void updateView() {
        drawItems();
        revalidate();
        repaint();
    }
    /**
     * Redraws the items in the inventory
     */
    private void drawItems() {
        this.removeAll();
        itemPanels.clear();
        int size = inventory.getSize();
        if (size < 10) {
            //Small inventories are displayed in a single row
            this.setLayout(new GridLayout(1, 10));
        } else if (size <= 40) {
            //large inventories are displayed in a grid
            this.setLayout(new GridLayout(inventory.getSize() / 8, 8));
        } else {
            //Large inventories are displayed in a grid, with the maximum display size of 40 
            size = 40;
            this.setLayout(new GridLayout(5, 8));
        }
        //Add an item panel for each item in the inventory
        for (int i = 0; i < inventory.getItems().size(); i++) {
            ItemPanel<? extends Item> itemPanel = inventory.getItems().get(i).createOwnView();
            this.add(itemPanel);
            itemPanels.add(itemPanel);
        }
        //Add empty item panels for any remaining slots
        for (int i = inventory.getItems().size(); i < size; i++) {
            this.add(new EmptyItemPanel(isDark));
        }
    }
    /**
     * Removes the view from the panel, removing all item panels
     */
    @Override
    public View removeView() {
        for (ItemPanel<? extends Item> itemPanel : itemPanels) {
            remove(itemPanel.removeView());
        }
        inventory.removeListener(this);
        return this;
    }
}
