package logarlec.model.items;

import logarlec.model.effects.Event;
import logarlec.model.labyrinth.Room;
import logarlec.model.characters.Actor;

public abstract class Item {
    public boolean use(Actor invoker, Event event) {
        return false;
    }

    public boolean use(Item invoker, Event event) {
        return false;
    }

    public void onPickup(Actor newOwner) {}

    public void onDrop(Room inRoom) {}

    public Integer getUsesLeft(Event event) {
        return -1;
    }

}
