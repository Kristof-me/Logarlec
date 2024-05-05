package logarlec.view.panels;
import javax.swing.*;

import logarlec.model.items.Inventory;
import logarlec.view.observerviews.View;

import java.awt.*;

public class InventoryPanel extends View {
    private Inventory inventory;
    public InventoryPanel(Inventory inventory){
        this.inventory = inventory;
        drawItems();
    }
    @Override
    public void updateView() {
        drawItems();
        revalidate();
        repaint();
    }

    private void drawItems(){
        System.out.println("Drawing items");
        this.removeAll();
        int size = inventory.getSize();
        if (size < 10) {
            this.setLayout(new GridLayout(1, 10));
        } else if (size <= 40) {
            this.setLayout(new GridLayout(inventory.getSize() / 8, 8));
        }
        else {
            size = 40;
            this.setLayout(new GridLayout(5, 8));
        }
        for (int i = 0; i < inventory.getItems().size(); i++) {
            ItemPanel itemPanel = inventory.getItems().get(i).createOwnView();
            this.add(itemPanel);
        }
        for (int i = inventory.getItems().size(); i < size; i++) {
            this.add(new EmptyItemPanel());
        }
    }
}
