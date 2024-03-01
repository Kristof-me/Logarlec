package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.effects.Event;
import logarlec.model.items.Item;
import logarlec.model.labyrinth.IHasLocation;
import logarlec.model.labyrinth.Room;

public class Transistor extends Item {
    private Item pair; // The item that this is paired with
    private IHasLocation location;

    @Override
    public boolean use(Item invoker, Event event) {
        if (event == Event.TRANSISTOR_PAIR_REQUEST && pair == null) {
            setPair(invoker);
            return true;
        }

        return false;
    }

    @Override
    public void onPickup(Actor newOwner) {
        location = newOwner;
    }

    @Override
    public void onDrop(Room inRoom) {
        location = inRoom;
    }

    public void setPair(Item pair) {
        this.pair = pair;
    }
}
