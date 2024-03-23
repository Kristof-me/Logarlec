package logarlec.model.items;

import logarlec.model.actor.Actor;
import logarlec.model.room.IHasLocation;

public abstract class Item {
    protected int usesLeft;

    public void use(Actor invoker) {}

    public int getUsesLeft() {
        return -1;
    }

    public void onPickup(Actor actor) {}

    public void onDrop(IHasLocation location) {}

    public abstract void accept(ItemVisitor visitor);
}
