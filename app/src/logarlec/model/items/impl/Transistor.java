package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.items.TransistorPairFinder;
import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;

import logarlec.model.logger.*;

public class Transistor extends Item {
    private Transistor pair; // The transistor that this is paired with
    private IHasLocation location;

    public Transistor() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    @Uses(fields = {"pair", "location"})
    @Override
    public void use(Actor invoker) {
        Logger.preExecute(this, "use", invoker);
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
        super.use(invoker);
        Logger.postExecute();
    }

    @Uses(fields = {"location"})
    @Override
    public void onPickup(Actor actor) {
        Logger.preExecute(this, "onPickup", actor);
        location = actor;
        super.onPickup(actor);
        Logger.postExecute();
    }

    @Uses(fields = {"location"})
    @Override
    public void onDrop(Room location) {
        Logger.preExecute(this, "onDrop", location);
        this.location = location;
        super.onDrop(location);
        Logger.postExecute();
    }

    @Override
    public void accept(ItemVisitor visitor) {
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }

    @Uses(fields = {"pair"})
    public void pairWith(Transistor pair) {
        Logger.preExecute(this, "pairWith", pair);
        this.pair = pair;
        Logger.postExecute();
    }
}
