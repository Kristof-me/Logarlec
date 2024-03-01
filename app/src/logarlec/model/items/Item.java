package logarlec.model.items;

import logarlec.model.characters.Actor;
import logarlec.model.labyrinth.Room;

public abstract class Item {
    protected Actor owner;

    public abstract void use(Room room);

    public void onPickup(Actor newOwner) {
        owner = newOwner;
    }

    public void onDrop() {
        owner = null;
    }
}
