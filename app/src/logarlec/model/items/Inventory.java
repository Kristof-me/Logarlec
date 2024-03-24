package logarlec.model.items;

import java.util.List;
import java.util.ArrayList;
import logarlec.model.room.Room;

import logarlec.model.logger.*;

public class Inventory {
    private int size;
    private List<Item> items = new ArrayList<>();

    public Inventory() {
        this(Integer.MAX_VALUE);
    }

    public Inventory(int size) {
        Logger.preConstructor(this, "Inventory", size);
        this.size = size;
        Logger.postConstructor(this);
    }

    @Uses(fields = {"size"})
    private boolean isFull() {
        Logger.preExecute(this, "isFull");
        return Logger.postExecute(items.size() >= size);
    }

    @Uses(fields = {"items"})
    public boolean addItem(Item item) {
        Logger.preExecute(this, "addItem", item);
        if (!isFull()) {
            items.add(item);
            return Logger.postExecute(true);
        }
        return Logger.postExecute(false);
    }

    @Uses(fields = {"items"})
    public Item removeItem(Item item) {
        Logger.preExecute(this, "removeItem", item);
        items.remove(item);
        return Logger.postExecute(item);
    }

    @Uses(fields = {"items"})
    public void dropAll(Room target) {
        Logger.preExecute(this, "dropAll", target);
        for (Item item : items) {
            target.addItem(item);
        }
        items.clear();
        Logger.postExecute();
    }

    @Uses(fields = {"items"})
    public void acceptVisitor(ItemVisitor visitor) {
        Logger.preExecute(this, "acceptVisitor", visitor);
        for (Item item : items) {
            item.accept(visitor);
        }
        Logger.postExecute();
    }
}
