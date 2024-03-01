package logarlec.model.characters;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.items.Item;

public class Inventory {
    protected List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }
}
