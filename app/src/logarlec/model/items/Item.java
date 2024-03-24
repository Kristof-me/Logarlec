package logarlec.model.items;

import logarlec.model.actor.Actor;
import logarlec.model.room.Room;

public abstract class Item {
    private Inventory inventory;

    protected Item() {
        inventory = null;
    }

    protected int usesLeft = Integer.MAX_VALUE;

    public void use(Actor invoker) {
        if (usesLeft <= 0 && inventory != null) {
            inventory.removeItem(this);
        }
        invoker.getInventory();
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    public void onPickup(Actor actor) {
        inventory = actor.getInventory();
    }

    public void onDrop(Room location) {
        inventory = null; // this should be Room.getInventory();
    }

    public abstract void accept(ItemVisitor visitor);
}
