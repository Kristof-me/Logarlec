package logarlec.model.items;

import java.util.List;
import java.util.ArrayList;
import logarlec.model.room.Room;

public class Inventory {
    private int size;
    private List<Item> items = new ArrayList<>();

    public Inventory() {
        this(Integer.MAX_VALUE);
    }

    public Inventory(int size) {
        this.size = size;
    }

    private boolean isFull() {
        return items.size() >= size;
    }

    public boolean addItem(Item item) {
        items.add(item);
        return false;
    }

    public Item removeItem(Item item) {
        items.remove(item);
        return item;
    }

    public void dropAll(Room target) {
        for (Item item : items) {
            target.addItem(item);
        }
        items.clear();
    }

    public void acceptVisitor(ItemVisitor visitor) {
        for (Item item : items) {
            item.accept(visitor);
        }
    }
}
