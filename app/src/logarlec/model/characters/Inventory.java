package logarlec.model.characters;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.items.Item;

public class Inventory {
    protected Item[] items;
    private int taken;

    public Inventory(int size) {
        items = new Item[size];
        taken = 0;
    }

    public boolean addItem(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                taken++;
                return true;
            }
        }

        return false;
    }

    public boolean removeItem(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
                taken--;
                return true;
            }
        }

        return false;
    }

    public Item[] getItems() {
        return items;
    }

    public int size() {
        return items.length;
    }
}
