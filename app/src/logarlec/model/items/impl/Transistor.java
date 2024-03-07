package logarlec.model.items.impl;

import logarlec.model.characters.Actor;
import logarlec.model.enums.Event;
import logarlec.model.items.IItem;
import logarlec.model.labyrinth.IHasLocation;
import logarlec.model.labyrinth.Room;

public class Transistor implements IItem {
    private IItem pair; // The item that this is paired with
    private IHasLocation locationReference;

    public boolean use(IItem invoker, Event event) {
        if (event == Event.TRANSISTOR_PAIR_REQUEST && pair == null) {
            setPair(invoker);
            return true;
        }

        return false;
    }

    public boolean use(Actor invoker, Event event) {
        if (pair == null && event == Event.CONTROLLER_ACTIVATED) {
            return invoker.getInventoryManager().getPair(this);
        } else if (event == Event.CONTROLLER_ACTIVATED) {
            if (pair.use(invoker, Event.TRANSISTOR_TELEPORT)) {
                pair = null;
                return true;
            }
            return false;
        } else if (event == Event.TRANSISTOR_TELEPORT) {
            if (invoker.teleport(locationReference.getLocation())) {
                pair = null;
                return true;
            }
            return false;
        }

        return false;
    }

    @Override
    public void onPickup(Actor newOwner) {
        locationReference = newOwner;
    }

    @Override
    public void onDrop(Room inRoom) {
        locationReference = inRoom;
    }

    public void setPair(IItem pair) {
        this.pair = pair;
    }
}
