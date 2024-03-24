package logarlec.model.items;

import logarlec.model.logger.*;
import logarlec.model.actor.Actor;
import logarlec.model.room.Room;

public abstract class Item {
    private Inventory inventory;

    @State(name = "usesLeft", popupQuestion = "How many uses does this item have?")
    protected Integer usesLeft = null;

    protected Item() {
        inventory = null;
    }

    public void use(Actor invoker) {
        if (usesLeft <= 0 && inventory != null) {
            inventory.removeItem(this);
        }
        invoker.getInventory();
    }

    public int getUsesLeft() {
        Logger.preExecute(this, "getUsesLeft");
        return Logger.postExecute(usesLeft);
    }

    public void onPickup(Actor actor) {
        inventory = actor.getInventory();
    }

    public void onDrop(Room location) {
        inventory = null; // this should be Room.getInventory();
    }

    public abstract void accept(ItemVisitor visitor);
}
