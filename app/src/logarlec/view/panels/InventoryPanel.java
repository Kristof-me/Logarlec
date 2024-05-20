package logarlec.view.panels;

import logarlec.model.items.Inventory;
import logarlec.model.items.Item;
import logarlec.view.observerviews.View;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class InventoryPanel extends View {
    private Inventory inventory;
    private boolean isDark;
    List<ItemPanel<? extends Item>> itemPanels = new ArrayList<ItemPanel<? extends Item>>();

    public InventoryPanel(Inventory inventory) {
        this.inventory = inventory;
        this.isDark = inventory.getSize() == Integer.MAX_VALUE;
        drawItems();
    }

    @Override
    public void updateView() {
        drawItems();
        revalidate();
        repaint();
    }

    private void drawItems() {
        this.removeAll();
        itemPanels.clear();
        int size = inventory.getSize();
        if (size < 10) {
            this.setLayout(new GridLayout(1, 10));
        } else if (size <= 40) {
            this.setLayout(new GridLayout(inventory.getSize() / 8, 8));
        } else {
            size = 40;
            this.setLayout(new GridLayout(5, 8));
        }

        for (int i = 0; i < inventory.getItems().size(); i++) {
            ItemPanel<? extends Item> itemPanel = inventory.getItems().get(i).createOwnView();
            this.add(itemPanel);
            itemPanels.add(itemPanel);
        }

        for (int i = inventory.getItems().size(); i < size; i++) {
            this.add(new EmptyItemPanel(isDark));
        }
    }

    @Override
    public View removeView() {
        for (ItemPanel<? extends Item> itemPanel : itemPanels) {
            remove(itemPanel.removeView());
        }
        inventory.removeListener(this);
        return this;
    }
}
