package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.items.TransistorPairFinder;
import logarlec.model.room.IHasLocation;
import logarlec.model.room.Room;

public class Transistor extends Item {
    private Item pair; // The item that this is paired with
    private IHasLocation location;

    @Override
    public void use(Actor invoker) {
        if (pair == null) {
            TransistorPairFinder pairFinder = new TransistorPairFinder();
            Transistor pair = pairFinder.findIn(invoker.getInventory());
            pairWith(pair);
            pair.pairWith(this);
        } else { // Already paired

        }
    }

    @Override
    public int getUsesLeft() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsesLeft'");
    }

    @Override
    public void onPickup(Actor actor) {
        location = actor;
    }

    @Override
    public void onDrop(IHasLocation location) {
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
