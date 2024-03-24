package logarlec.model.items.impl;

import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;

public class Beer extends Item {

    public Beer() {
        usesLeft = 1;
    }

    @Override
    public void use(Actor invoker) {
        usesLeft--;
    }

    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }

}
