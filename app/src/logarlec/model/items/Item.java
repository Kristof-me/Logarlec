package logarlec.model.items;

import logarlec.model.actor.Actor;
import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;

public abstract class Item {
    protected int usesLeft;

    public void use(Actor invoker) {}

    public int getUsesLeft() {
        return Integer.MAX_VALUE;
    }

    public void onPickup(Actor actor) {}

    public void onDrop(Room location) {}

    public abstract void accept(ItemVisitor visitor);
}
