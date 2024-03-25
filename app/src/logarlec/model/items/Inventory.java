package logarlec.model.items;

import java.util.List;
import java.util.ArrayList;
import logarlec.model.room.Room;

import logarlec.model.logger.*;

/**
 * Inventory class that holds items. Both the actors and the rooms have an
 * inventory. <br>
 * Has a limit, and rejects any item being added above the limit.
 */
public class Inventory {
    @State(name = "size", min = 1, max = Integer.MAX_VALUE)
    private Integer size = null;
    private List<Item> items = new ArrayList<>();

    public Inventory() {
        this(Integer.MAX_VALUE);
    }

    public Inventory(Integer size) {
        Logger.preConstructor(this, size);
        this.size = size;
        Logger.postConstructor(this);
    }

    /**
     * Checks if the inventory is full.
     * 
     * @return true if the inventory is full, false otherwise
     */
    @Uses(fields = { "size" })
    private boolean isFull() {
        Logger.preExecute(this, "isFull");
        return Logger.postExecute(items.size() >= size);
    }

    /**
     * Adds an item to the inventory if there is space left.
     * 
     * @param item the item to be added
     * @return true if the item was added, false otherwise
     */
    public boolean addItem(Item item) {
        Logger.preExecute(this, "addItem", item);
        if (!isFull()) {
            items.add(item);
            return Logger.postExecute(true);
        }
        return Logger.postExecute(false);
    }

    /**
     * Removes an item from the inventory.
     * 
     * @param item the item to be removed
     * @return the removed item, or null if the item was not found
     */
    public Item removeItem(Item item) {
        Logger.preExecute(this, "removeItem", item);
        if (items.remove(item)) {
            return Logger.postExecute(item);
        }
        return Logger.postExecute(null);
    }

    /**
     * Removes all items from the inventory and give them to a room.
     * 
     * @param target the room to give the items to
     */
    public void dropAll(Room target) {
        Logger.preExecute(this, "dropAll", target);
        for (Item item : items) {
            target.addItem(item);
        }
        items.clear();
        Logger.postExecute();
    }

    /**
     * Accepts a visitor to visit all items in the inventory.
     * 
     * @param visitor the visitor to accept
     */
    public void acceptVisitor(ItemVisitor visitor) {
        Logger.preExecute(this, "acceptVisitor", visitor);
        for (Item item : items) {
            item.accept(visitor);
        }
        Logger.postExecute();
    }
}
