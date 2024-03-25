package logarlec.model.items;

import logarlec.model.logger.*;
import logarlec.model.actor.Actor;
import logarlec.model.room.Room;

/**
 * Abstract super class for every item.
 */
public abstract class Item {
    private Inventory inventory;

    @State(name = "usesLeft", min = 0, max = Integer.MAX_VALUE)
    protected Integer usesLeft = null;

    protected Item() {
        inventory = null;
    }

    /**
     * Use the item.
     * 
     * @param invoker The actor that uses the item.
     */
    @Uses(fields = { "usesLeft" })
    public void use(Actor invoker) {
        if (usesLeft <= 0 && inventory != null) {
            inventory.removeItem(this);
        }
    }

    /**
     * Get the uses left of the item.
     * 
     * @return The uses left of the item.
     */
    @Uses(fields = { "usesLeft" })
    public int getUsesLeft() {
        Logger.preExecute(this, "getUsesLeft");
        return Logger.postExecute(usesLeft);
    }

    /**
     * When the item gets picked up by an actor it keeps track of the inventory it's
     * in.
     * 
     * @param actor The actor that picks up the item.
     */
    public void onPickup(Actor actor) {
        Logger.preExecute(this, "onPickup", actor);
        inventory = actor.getInventory();
        Logger.postExecute();
    }

    /**
     * When the item gets dropped it keeps track of the inventory it's in.
     * 
     * @param location The room the item gets dropped in.
     */
    public void onDrop(Room location) {
        Logger.preExecute(this, "onDrop", location);
        inventory = location.getInventory();
        Logger.postExecute();
    }

    public abstract void accept(ItemVisitor visitor);
}
