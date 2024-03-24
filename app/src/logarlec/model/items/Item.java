package logarlec.model.items;

import logarlec.model.actor.Actor;
import logarlec.model.room.Room;

public abstract class Item {
    protected int usesLeft = Integer.MAX_VALUE;

    public void use(Actor invoker) {}

    public int getUsesLeft() {
        return usesLeft;
    }

    public void onPickup(Actor actor) {}

    public void onDrop(Room location) {}

    public abstract void accept(ItemVisitor visitor);
}
