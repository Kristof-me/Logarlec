package logarlec.model.items;

import java.util.List;
import java.util.ArrayList;

import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;
import logarlec.view.observerviews.View;
import logarlec.view.panels.InventoryPanel;
import logarlec.model.GameObject;
import logarlec.model.actor.Actor;

/**
 * Inventory class that holds items. Both the actors and the rooms have an
 * inventory. <br>
 * Has a limit, and rejects any item being added above the limit.
 */
public class Inventory extends GameObject {
    private Integer size = 5;
    private List<Item> items = new ArrayList<>();
    private IHasLocation owner = null;

    public Inventory(IHasLocation owner) {
        this(Integer.MAX_VALUE, owner);
    }

    public Inventory(Integer size, IHasLocation owner) {
        if(size != null) {
            this.size = size;
        }
        if(owner != null) {
            this.owner = owner;
        }
    }

    /**
     * Checks if the inventory is full.
     * 
     * @return true if the inventory is full, false otherwise
     */
    private boolean isFull() {
        return size != null && items.size() >= size;
    }

    /**
     * Adds an item to the inventory if there is space left.
     * 
     * @param item the item to be added
     * @return true if the item was added, false otherwise
     */
    public boolean addItem(Item item) {
        if (!isFull()) {
            items.add(item);
        }
        return false;
    }

    /**
     * Removes an item from the inventory.
     * 
     * @param item the item to be removed
     * @return the removed item, or null if the item was not found
     */
    public Item removeItem(Item item) {
        if (items.remove(item)) {
            return item;
        }
        return null;
    }

    /**
     * Removes all items from the inventory and give them to a room.
     * 
     * @param target the room to give the items to
     */
    public void dropAll(Room target) {
        for (Item item : items) {
            target.addItem(item);
        }
        items.clear();
        
    }

    /**
     * Accepts a visitor to visit all items in the inventory.
     * 
     * @param visitor the visitor to accept
     */
    public void acceptVisitor(ItemVisitor visitor) {
        for (Item item : items) {
            item.accept(visitor);
        }   
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public List<Item> getItems() {
        return items;
    }

    public IHasLocation getOwner() {
        return owner;
    }

    @Override
    public InventoryPanel createOwnView() {
        return new InventoryPanel(this);
    }

    
}
