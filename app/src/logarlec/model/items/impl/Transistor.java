package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.items.TransistorPairFinder;
import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;

public class Transistor extends Item {
    private Transistor pair; // The transistor that this is paired with
    private IHasLocation location;

    @Override
    public void use(Actor invoker) {
        if (pair == null) {
            TransistorPairFinder pairFinder = new TransistorPairFinder(this);
            Transistor p = pairFinder.findIn(invoker.getInventory());
            pairWith(p);
            p.pairWith(this);
        } else { // Already paired
            Room loc = pair.location.getLocation();
            invoker.teleport(loc, false);
            pair.pairWith(null);
            pairWith(null);
        }
    }

    @Override
    public void onPickup(Actor actor) {
        location = actor;
    }

    @Override
    public void onDrop(Room location) {
        this.location = location;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

    public void pairWith(Transistor pair) {
        this.pair = pair;
    }
}
